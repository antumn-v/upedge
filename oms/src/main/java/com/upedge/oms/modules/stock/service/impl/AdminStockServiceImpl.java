package com.upedge.oms.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;
import com.upedge.oms.modules.stock.dao.*;
import com.upedge.oms.modules.stock.entity.*;
import com.upedge.oms.modules.stock.request.*;
import com.upedge.oms.modules.stock.response.*;
import com.upedge.oms.modules.stock.service.AdminStockService;
import com.upedge.oms.modules.stock.vo.AdminStockOrderVo;
import com.upedge.oms.modules.stock.vo.CustomerProductStockVo;
import com.upedge.oms.modules.stock.vo.CustomerStockRecordVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.createProcurement.ApiCreateProcurementProductList;
import com.upedge.thirdparty.saihe.entity.createProcurement.ApiCreateProcurementResponse;
import com.upedge.thirdparty.saihe.entity.getProducts.ApiGetProductResponse;
import com.upedge.thirdparty.saihe.entity.getProducts.ApiProductInfo;
import com.upedge.thirdparty.saihe.entity.getProducts.ProductInfoList;
import com.upedge.thirdparty.saihe.service.SaiheService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AdminStockServiceImpl implements AdminStockService {

    @Autowired
    private StockOrderDao stockOrderDao;
    @Autowired
    private StockOrderItemDao stockOrderItemDao;
    @Autowired
    private SaiheSkuRelateDao saiheSkuRelateDao;
    @Autowired
    private StockOrderRefundDao stockOrderRefundDao;
    @Autowired
    private StockOrderRefundItemDao stockOrderRefundItemDao;
    @Autowired
    private CustomerProductStockDao customerProductStockDao;
    @Autowired
    private CustomerStockRecordDao customerStockRecordDao;
    @Autowired
    private AdminSaiheInventoryDao adminSaiheInventoryDao;
    @Autowired
    private UmsFeignClient umsFeignClient;
    @Autowired
    private PmsFeignClient pmsFeignClient;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 备库订单列表
     * @param request
     * @param session
     * @return
     */
    @Override
    public StockOrderListResponse stockList(AdminStockOrderListRequest request, Session session) {

        if(null == request.getT()){
            request.setT(new StockOrder());
        }
        request.initFromNum();
        StockOrder stockOrder = request.getT();
        //支付状态,待支付=0，已支付=1，取消订单=-1
        stockOrder.setPayState(1);
        //退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
        stockOrder.setRefundState(0);

        List<AdminStockOrderVo> results = stockOrderDao.selectAdminStockOrderList(request);
        Long total = stockOrderDao.countAdminStockOrderList(request);
        request.setTotal(total);
        StockOrderListResponse res = new StockOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 历史备库订单
     * @param request
     * @param session
     * @return
     */
    @Override
    public StockOrderListResponse historyStockOrder(AdminStockOrderListRequest request, Session session) {
        if(null == request.getT()){
            request.setT(new StockOrder());
        }
        request.initFromNum();
        StockOrder stockOrder = request.getT();

        List<AdminStockOrderVo> results = stockOrderDao.selectAdminStockOrderList(request);
        Long total = stockOrderDao.countAdminStockOrderList(request);
        request.setTotal(total);
        StockOrderListResponse res = new StockOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 生成备库订单
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse createProcurement(CreateProcurementRequest request) throws CustomerException {
        if(!SaiheConfig.SAIHE_ORDER_SWITCH){
            return new BaseResponse(ResultCode.FAIL_CODE,"未开启");
        }
        StockOrder stockOrder=stockOrderDao.selectByPrimaryKey(request.getId());
        if(stockOrder==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(stockOrder.getPayState()!=1&&stockOrder.getRefundState()!=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"状态异常!");
        }
        if(stockOrder.getSaiheCode()!=null){
            return new BaseResponse(ResultCode.FAIL_CODE,"已导入赛盒!");
        }
        refreshSaiheSku(request.getId());
        long num=stockOrderDao.countWithOutSaiheSku(request.getId());
        if(num>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"存在不匹配赛盒sku的子体，<br/>请先同步赛盒sku！");
        }
        List<StockOrderItem> itemList=stockOrderItemDao.listOrderItemByOrderId(stockOrder.getId());
        List<ApiCreateProcurementProductList> procurementProductList=new ArrayList<>();
        for(StockOrderItem orderItem:itemList){
            if(StringUtils.isBlank(orderItem.getSaiheSku())
                    ||orderItem.getQuantity()<=0){
                return new BaseResponse(ResultCode.FAIL_CODE,"参数异常");
            }
            ApiCreateProcurementProductList apiCreateProcurementProductList1=new ApiCreateProcurementProductList();
            apiCreateProcurementProductList1.setNum(orderItem.getQuantity());
            apiCreateProcurementProductList1.setSKU(orderItem.getSaiheSku());
            procurementProductList.add(apiCreateProcurementProductList1);
        }
        int res=stockOrderDao.markStockOrder(stockOrder.getId());
        if(res==0){
            throw new CustomerException("备库订单状态异常");
        }
        ApiCreateProcurementResponse apiCreateProcurementResponse= SaiheService.createProcurement(procurementProductList);
        if (apiCreateProcurementResponse.getCreateProcurementResult().getStatus().equals("OK")) {
            String saiheCode=apiCreateProcurementResponse.getCreateProcurementResult().getOrderCode();
            log.debug("潘达备库订单id:{},saiheCode:{}",stockOrder.getId(),saiheCode);
            if(!StringUtils.isBlank(saiheCode)){
                stockOrder.setUpdateTime(new Date());
                stockOrder.setSaiheState(1);
                //处理人
                Session session=UserUtil.getSession(redisTemplate);
                stockOrder.setSaiheCode(saiheCode);
                stockOrderDao.updateOrderStockedSuccess(stockOrder);
                log.debug("创建备库单成功");
                return new BaseResponse(ResultCode.SUCCESS_CODE,"生成备库单成功！");
            }
        }else{
            String msg=apiCreateProcurementResponse.getCreateProcurementResult().getMsg();
            throw new CustomerException(msg);
        }
        throw new CustomerException("失败");
    }

    /**
     * 同步赛盒SKU
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse refreshSaiheSku(Long id) {
        List<String> listGroupSku=stockOrderDao.listVariantSkuForRefresh(id);
        for(String groupSku:listGroupSku) {
            ApiGetProductResponse response = SaiheService.getProductsByClientSKUs(groupSku,null);
            if (response.getGetProductsResult().getStatus().equals("OK")) {
                ProductInfoList pInfoList = response.getGetProductsResult().getProductInfoList();
                if (pInfoList != null && pInfoList.getProductInfoList() != null
                        && pInfoList.getProductInfoList().size() > 0) {
                    for (ApiProductInfo apiProductInfo : pInfoList.getProductInfoList()) {
                        saveAdminVariantSku(apiProductInfo.getClientSKU(),apiProductInfo.getSKU());
                    }
                }
            }
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 更新赛盒SKU
     * @param request
     * @return
     * @throws CustomerException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse updateSaiheSku(UpdateSaiheSkuRequest request){
        ApiGetProductResponse apiGetProductResponse = SaiheService.getProductBySKU(request.getSaiheSku());
        if (apiGetProductResponse.getGetProductsResult().getStatus().equals("OK")) {
            Integer nextToken=apiGetProductResponse.getGetProductsResult().getNextToken();
            ProductInfoList productInfoList = apiGetProductResponse.getGetProductsResult().getProductInfoList();
            if (productInfoList != null && productInfoList.getProductInfoList() != null
                    && productInfoList.getProductInfoList().size() ==1) {

                ApiProductInfo apiProductInfo=productInfoList.getProductInfoList().get(0);
                if(StringUtils.isBlank(apiProductInfo.getClientSKU())){
                    return new BaseResponse(ResultCode.FAIL_CODE,"自定义SKU为空");
                }
                if(!apiProductInfo.getClientSKU().equals(request.getVariantSku())){
                    return new BaseResponse(ResultCode.FAIL_CODE,"赛盒自定义SKU与admin的SKU不一致");
                }
                saveAdminVariantSku(apiProductInfo.getClientSKU(),apiProductInfo.getSKU());
            }else {
                return new BaseResponse(ResultCode.FAIL_CODE,"查询异常");
            }
        }else {
            if(!StringUtils.isBlank(apiGetProductResponse.getGetProductsResult().getMsg())){
                return new BaseResponse(ResultCode.FAIL_CODE,apiGetProductResponse.getGetProductsResult().getMsg());
            }
        }
        return new BaseResponse(ResultCode.FAIL_CODE,"更新失败");
    }

    public void saveAdminVariantSku(String clientSku, String sku){
        SaiheSkuRelate saiheSkuRelate =saiheSkuRelateDao.selectByPrimaryKey(clientSku);
        if(saiheSkuRelate==null){
            saiheSkuRelate=new SaiheSkuRelate();
            saiheSkuRelate.setVariantSku(clientSku);
            saiheSkuRelate.setSaiheSku(sku);
            saiheSkuRelateDao.insert(saiheSkuRelate);
        }
    }

    public StockOrderItemListResponse loadRefundData(Long orderId) {
        StockOrder stockOrder=stockOrderDao.selectByPrimaryKey(orderId);
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(stockOrder.getPayState()!=1&&stockOrder.getRefundState()!=0){
            return new StockOrderItemListResponse(ResultCode.FAIL_CODE,"订单状态过期,请刷新页面");
        }
        List<StockOrderItem> stockOrderItems=stockOrderItemDao.listOrderItemByOrderId(orderId);
        return new StockOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,stockOrderItems,null);
    }

    @Override
    public StockOrderRefundListResponse refundOrderList(StockOrderRefundListRequest request, Session session) {
        if(null == request.getT()){
            request.setT(new StockOrderRefund());
        }
        request.initFromNum();
        StockOrderRefund stockOrderRefund = request.getT();
        //退款状态，申请中=0，通过=1，驳回=2
        stockOrderRefund.setState(0);
        List<StockOrderRefund> results = stockOrderRefundDao.selectAdminStockOrderRefundList(request);
        Long total = stockOrderRefundDao.countAdminStockOrderRefundList(request);
        request.setTotal(total);
        StockOrderRefundListResponse res = new StockOrderRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @Override
    public StockOrderRefundListResponse historyRefundOrder(StockOrderRefundListRequest request, Session session) {
        if(null == request.getT()){
            request.setT(new StockOrderRefund());
        }
        request.initFromNum();
        StockOrderRefund stockOrderRefund = request.getT();
        //退款状态>=1，通过=1，驳回=2
        stockOrderRefund.setGteState(1);
        List<StockOrderRefund> results = stockOrderRefundDao.selectAdminStockOrderRefundList(request);
        Long total = stockOrderRefundDao.countAdminStockOrderRefundList(request);
        request.setTotal(total);
        StockOrderRefundListResponse res = new StockOrderRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplyStockOrderRefundResponse applyRefund(ApplyStockOrderRefundRequest request,Session session) throws CustomerException {
        StockOrder stockOrder=stockOrderDao.selectByPrimaryKey(request.getOrderId());
        if(stockOrder==null){
                return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"备库订单不存在！");
        }
        //已付款
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(stockOrder.getPayState()!=1&&stockOrder.getRefundState()!=0){
            return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"状态过期");
        }
        List<StockOrderRefundItem> refundItemList=request.getRefundItemList();

        BigDecimal amount= BigDecimal.ZERO;
        Long stockRefundId= IdGenerate.nextId();
        for(StockOrderRefundItem item:refundItemList){
            Long stockOrderItemId=item.getStockOrderItemId();
            item.setId(IdGenerate.nextId());
            //获取退款数量
            Integer quantity=item.getQuantity();
            if(stockOrderItemId==null||quantity==null||quantity<=0){
                return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"参数异常");
            }
            //查询备库数量
            StockOrderItem stockOrderItem=stockOrderItemDao.queryStockOrderItem(stockOrderItemId,stockOrder.getId());
            if(stockOrderItem==null||stockOrderItem.getQuantity()==null){
                return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"参数异常");
            }
            if(quantity>stockOrderItem.getQuantity()){
                return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"退款数量超出范围");
            }
            item.setStockRefundId(stockRefundId);
            item.setPrice(stockOrderItem.getPrice());
            item.setProductId(stockOrderItem.getProductId());
            item.setVariantId(stockOrderItem.getVariantId());
            item.setProductTitle(stockOrderItem.getProductTitle());
            item.setVariantName(stockOrderItem.getVariantName());
            item.setVariantSku(stockOrderItem.getVariantSku());
            item.setVariantImage(stockOrderItem.getVariantImage());
            BigDecimal subAmount=stockOrderItem.getPrice().multiply(new BigDecimal(quantity));
            amount=amount.add(subAmount);
        }

        //修改备库单为退款中
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        Integer res=stockOrderDao.applyStockOrderRefund(stockOrder.getId());
        if(res==0){
            throw new CustomerException("申请退款失败!");
        }
        StockOrderRefund stockOrderRefund=request.toStockOrderRefund(stockOrder,session.getLoginname());
        stockOrderRefund.setId(stockRefundId);
        stockOrderRefund.setReason(request.getReason());
        //金额
        stockOrderRefund.setAmount(amount);
        stockOrderRefundDao.insert(stockOrderRefund);
        stockOrderRefundItemDao.insertByBatch(refundItemList);
        return new ApplyStockOrderRefundResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 客户商品库存列表
     * @param request
     * @return
     */
    @Override
    public CustomerProductStockListResponse productStockList(CustomerProductStockListRequest request) {
        request.initFromNum();
        List<CustomerProductStock> results = customerProductStockDao.select(request);
        Long total = customerProductStockDao.count(request);
        request.setTotal(total);
        List<CustomerProductStockVo> customerProductStockVoList=new ArrayList<>();
        results.forEach(customerProductStock -> {
            CustomerProductStockVo customerProductStockVo=new CustomerProductStockVo();
            BeanUtils.copyProperties(customerProductStock,customerProductStockVo);
            //查询赛盒库存信息
            /*AdminSaiheInventory adminSaiheInventory=adminSaiheInventoryDao.
                    queryAdminSaiheInventory(customerProductStock.getVariantSku(),
                            customerProductStock.getWarehouseCode().intValue());*/
            ProductSaiheInventoryVo productSaiheInventoryVo = new ProductSaiheInventoryVo();
            productSaiheInventoryVo.setVariantSku(customerProductStock.getVariantSku());
//            productSaiheInventoryVo.setWarehouseCode(customerProductStock.getWarehouseCode().intValue());
//            BaseResponse baseResponse = pmsFeignClient.queryProductSaiheInventory(productSaiheInventoryVo);
//
//            if (baseResponse.getCode() != -1 && baseResponse.getData() != null){
//                List<ProductSaiheInventoryVo> data = (List<ProductSaiheInventoryVo>) baseResponse.getData();
//                if(data.size() != 0) {
//                customerProductStockVo.setProductSaiheInventoryVo(data.get(0));
//                 }
//            }
//            String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,customerProductStock.getCustomerId().toString());
//            customerProductStockVo.setManagerCode(managerCode);
//
//            BaseResponse customerResponse = umsFeignClient.customerInfo(customerProductStock.getCustomerId());
//            if (customerResponse.getCode() == ResultCode.SUCCESS_CODE){
//                CustomerVo customerVo =   JSON.parseObject(JSON.toJSONString( customerResponse.getData()),CustomerVo.class);
//                if (customerVo != null){
//                    customerProductStockVo.setCustomerLoginName(customerVo.getLoginName());
//                    customerProductStockVo.setCustomerName(customerVo.getUsername());
//                }
//            }
            customerProductStockVoList.add(customerProductStockVo);
        });
        CustomerProductStockListResponse res = new CustomerProductStockListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,customerProductStockVoList,request);
        return res;
    }

    /**
     * 库存记录列表
     * @param request
     * @return
     */
    @Override
    public BaseResponse stockRecordList(CustomerStockRecordListRequest request) {
        request.initFromNum();
        List<CustomerStockRecord> results = customerStockRecordDao.select(request);
        Long total = customerStockRecordDao.count(request);
        request.setTotal(total);
        ArrayList<CustomerStockRecordVo> list = new ArrayList<>();

        for (CustomerStockRecord result : results) {

            CustomerStockRecordVo customerStockRecordVo = new CustomerStockRecordVo();
            BeanUtils.copyProperties(result,customerStockRecordVo);
   /*         String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,result.getCustomerId().toString());
            customerStockRecordVo.setManagerCode(managerCode);*/


            BaseResponse customerResponse = umsFeignClient.customerInfo(result.getCustomerId());
            if (customerResponse.getCode() == ResultCode.SUCCESS_CODE){
                CustomerVo customerVo =   JSON.parseObject(JSON.toJSONString( customerResponse.getData()),CustomerVo.class);
                if (customerVo != null){
                    customerStockRecordVo.setCustomerLoginName(customerVo.getLoginName());
                    customerStockRecordVo.setCustomerName(customerVo.getUsername());
                }
            }
            list.add(customerStockRecordVo);
        }
       return BaseResponse.success(list,request);
    }

    /**
     * 备库退款确认
     */
    @GlobalTransactional//分布式事务注解
    @Override
    public BaseResponse confirmRefund(ConfirmStockOrderRefundRequest request, Session session) throws CustomerException {
        //确认退款
        StockOrderRefund stockOrderRefund=stockOrderRefundDao.selectByPrimaryKey(request.getId());
        if(stockOrderRefund==null||stockOrderRefund.getState()==null
                ||stockOrderRefund.getCustomerId()==null||stockOrderRefund.getStockOrderId()==null||
                stockOrderRefund.getAmount()==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"参数异常");
        }
        //退款状态，申请中=0，通过=1，驳回=2
        //备库订单状态为退款中
        if(stockOrderRefund.getState()!=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"状态过时请刷新");
        }
        //查询备库订单
        Long orderId=stockOrderRefund.getStockOrderId();
        StockOrder stockOrder=stockOrderDao.selectByPrimaryKey(orderId);
        if(stockOrder==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"找不到备库订单");
        }
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(stockOrder.getPayState()==null||stockOrder.getPayState()!=1){
            return new BaseResponse(ResultCode.FAIL_CODE,"备库订单支付状态异常");
        }
        if(stockOrder.getRefundState()==null||stockOrder.getRefundState()!=1){
            return new BaseResponse(ResultCode.FAIL_CODE,"备库订单退款状态异常");
        }
        if(stockOrder.getAmount()==null||stockOrder.getAmount().compareTo(BigDecimal.ZERO)<=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"备库订单金额异常");
        }
        if(stockOrderRefund.getAmount().compareTo(stockOrder.getAmount())>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款金额超出限制");
        }

        //查询退款详情
        List<StockOrderRefundItem> stockOrderRefundItems=stockOrderRefundItemDao.
                listStockOrderRefundItem(request.getId());
        if(stockOrderRefundItems==null||stockOrderRefundItems.size()==0){
            return new BaseResponse(ResultCode.FAIL_CODE,"无退款项");
        }

        Integer state= 2;//AppStockOrderEnum.PARTREFUNDED.getValue();
        //与可退金额做对比
        if(stockOrderRefund.getAmount().compareTo(stockOrder.getAmount())==0){
            state=3;//AppStockOrderEnum.REFUNDED.getValue();
        }

        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        //修改备库订单状态
        int res=stockOrderDao.updateOrderAsRefund(stockOrder.getId(),state);
        if(res==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"修改备库订单状态异常");
        }
        //更新状态 已确认
        stockOrderRefund.setState(1);
        String userCode= String.valueOf(session.getId());

        stockOrderRefund.setUpdateTime(new Date());
        int rsOrderRefund=stockOrderRefundDao.updateConfirmRefund(stockOrderRefund);
        if(rsOrderRefund==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"修改退款单状态异常");
        }

        for(StockOrderRefundItem item:stockOrderRefundItems){
            //检查库存每个退款项的库存
            CustomerProductStock customerProductStock=customerProductStockDao.queryStockForCustomerProduct(stockOrderRefund.getCustomerId(),item.getVariantId(),stockOrderRefund.getWarehouseCode());
            if(customerProductStock==null||customerProductStock.getStock()==null||customerProductStock.getStock()<0){
                throw new CustomerException(ResultCode.FAIL_CODE,"库存异常");
            }
            if(item.getQuantity()==null||item.getQuantity()<=0){
                throw new CustomerException(ResultCode.FAIL_CODE,"申请数量异常");
            }
            if(customerProductStock.getStock()<item.getQuantity()){
                throw new CustomerException(ResultCode.FAIL_CODE,"库存不足");
            }
            //扣库存
            log.info("用户当前库存:Id{},UserId{},ProductId{},VariantId{},库存{},扣减{}",customerProductStock.getId(),stockOrderRefund.getCustomerId(),customerProductStock.getProductId(),customerProductStock.getVariantId(),customerProductStock.getStock(),item.getQuantity());

            Integer rs=customerProductStockDao.subStockForRefund(customerProductStock.getId(),item.getQuantity());
            if(rs<=0){
                throw new CustomerException(ResultCode.FAIL_CODE,"扣库存失败");
            }
            //增加扣库存记录
            CustomerStockRecord customerStockRecord=new CustomerStockRecord();
            customerStockRecord.setCustomerId(IdGenerate.nextId());
            customerStockRecord.setCustomerId(stockOrderRefund.getCustomerId());
            customerStockRecord.setProductId(item.getProductId());
            customerStockRecord.setVariantId(item.getVariantId());
            customerStockRecord.setWarehouseCode(stockOrderRefund.getWarehouseCode());
            customerStockRecord.setRelateId(stockOrderRefund.getId());
            //交易类型  增加=0，抵扣=1，退款=2
            customerStockRecord.setType(2);
            customerStockRecord.setOrderType(OrderType.STOCK);
            customerStockRecord.setQuantity(item.getQuantity());
            customerStockRecord.setCreateTime(new Date());
            customerStockRecord.setUpdateTime(new Date());
            customerStockRecord.setVariantImage(item.getVariantImage());
            customerStockRecordDao.insert(customerStockRecord);

        }

        AccountOrderRefundedRequest accountOrderRefundedRequest=new AccountOrderRefundedRequest();
        accountOrderRefundedRequest.setOrderId(stockOrder.getId());
        accountOrderRefundedRequest.setRefundId(request.getId());
        accountOrderRefundedRequest.setCustomerId(stockOrder.getCustomerId());
        accountOrderRefundedRequest.setPayAmount(stockOrder.getAmount());
        accountOrderRefundedRequest.setRefundAmount(stockOrderRefund.getAmount());
        accountOrderRefundedRequest.setOrderType(OrderType.STOCK);
        //账户退款

        BaseResponse response=umsFeignClient.accountOrderRefunded(accountOrderRefundedRequest);


        // 统计退款信息
        OrderRefundDailyCountRequest orderRefundDailyCountRequest = new OrderRefundDailyCountRequest();
        orderRefundDailyCountRequest.setRefundId(request.getId());
        orderRefundDailyCountRequest.setOrderType(OrderType.STOCK);
        orderRefundDailyCountRequest.setRefundTime(new Date());
        redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE,orderRefundDailyCountRequest);
        if(response.getCode()!=1){
            throw new CustomerException(ResultCode.FAIL_CODE,response.getMsg());
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 备库退款驳回
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse rejectApply(RejectApplyStockOrderRefundRequest request, Session session) throws CustomerException {
        StockOrderRefund stockOrderRefund=stockOrderRefundDao.selectByPrimaryKey(request.getId());
        if(stockOrderRefund==null||stockOrderRefund.getState()==null){
            return new BaseResponse(ResultCode.SUCCESS_CODE,"参数异常");
        }
        if(stockOrderRefund.getState()!=0){
            return new BaseResponse(ResultCode.SUCCESS_CODE,"状态过时请刷新");
        }
        Long orderId=stockOrderRefund.getStockOrderId();
        StockOrder stockOrder=stockOrderDao.selectByPrimaryKey(orderId);
        //备库订单状态为退款中
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(stockOrder==null||stockOrder.getId()==null||stockOrder.getPayState()==null||stockOrder.getRefundState()==null
                ||stockOrder.getPayState()!=1||stockOrder.getRefundState()!=1){
            return new BaseResponse(ResultCode.SUCCESS_CODE,"订单异常");
        }
        ////退款状态，申请中=0，通过=1，驳回=2
     //   String adminUserId=String.valueOf(session.getId());
        stockOrderRefund.setState(2);
        stockOrderRefund.setUpdateTime(new Date());
        int rs=stockOrderRefundDao.rejectApply(stockOrderRefund);
        if(rs==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"驳回失败");
        }
        //todo 备库订单状态改为 已付款
        //订单状态,待支付=0，已支付=1，申请退款=2，部分退款=3，全部退款=4，取消订单=-1
        Integer res=stockOrderDao.restoreStockOrderAsPaid(stockOrder.getId());
        if(res==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"驳回失败");
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}

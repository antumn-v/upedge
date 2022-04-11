package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.dao.CustomerStockRecordDao;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.request.ManualAddCustomerStockRequest;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.vo.CustomerSkuStockVo;
import com.upedge.oms.modules.stock.vo.CustomerWarehouseVariantStockVo;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ApiProductInventory;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ProductInventoryList;
import com.upedge.thirdparty.saihe.response.GetProductInventoryResponse;
import com.upedge.thirdparty.saihe.service.SaiheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class CustomerProductStockServiceImpl implements CustomerProductStockService {

    @Autowired
    private CustomerProductStockDao customerProductStockDao;

    @Autowired
    CustomerStockRecordDao customerStockRecordDao;

    @Autowired
    private PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerProductStock record = new CustomerProductStock();
        record.setId(id);
        return customerProductStockDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerProductStock record) {
        return customerProductStockDao.insert(record);
    }

    /**
     *
     */
    public int insertSelective(CustomerProductStock record) {
        return customerProductStockDao.insert(record);
    }

    @Transactional
    @Override
    public BaseResponse revokeManualAddRecord(Long recordId, Session session) {
        CustomerStockRecord record = customerStockRecordDao.selectByPrimaryKey(recordId);
        if (null == record
        || record.getRevokeState() != 0){
            return BaseResponse.failed();
        }
        CustomerProductStock customerProductStock
                = customerProductStockDao.selectStockByVariantAndCustomerId(record.getVariantId(),record.getCustomerId(),record.getWarehouseCode());
        if (customerProductStock.getStock() < record.getQuantity()){
            return BaseResponse.failed("库存不足");
        }
        record.setRevokeState(1);
        record.setCustomerShowState(0);
        customerStockRecordDao.updateByPrimaryKey(record);

        CustomerStockRecord customerStockRecord = new CustomerStockRecord();
        BeanUtils.copyProperties(record,customerStockRecord);
        customerStockRecord.setId(IdGenerate.nextId());
        customerStockRecord.setRelateId(IdGenerate.nextId());
        customerStockRecord.setRevokeState(1);
        customerStockRecord.setCreateTime(new Date());
        customerStockRecord.setType(4);
        customerStockRecord.setCustomerShowState(0);
        customerStockRecordDao.insert(customerStockRecord);

        customerProductStockDao.subStockForRefund(customerProductStock.getId(),record.getQuantity());
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse manualAddCustomerVariantStock(ManualAddCustomerStockRequest request, Session session) {
        Long productId = request.getProductId();
        Long customerId = request.getCustomerId();
        List<VariantDetail> variantDetails = pmsFeignClient.getVariantDetail(productId);
        if (ListUtils.isEmpty(variantDetails)){
            return BaseResponse.failed("产品信息有误");
        }
        List<CustomerSkuStockVo> customerSkuStockVos = request.getCustomerSkuStockVos();
        Map<String,VariantDetail> map = new HashMap<>();
        for (VariantDetail variantDetail : variantDetails) {
            map.put(variantDetail.getVariantSku(),variantDetail);
        }
        Date date = new Date();
        List<CustomerProductStock> customerProductStocks = new ArrayList<>();
        List<CustomerProductStock> updateStock = new ArrayList<>();
        List<CustomerStockRecord> customerStockRecords = new ArrayList<>();
        String warehouseCode = request.getWarehouseCode();
        List<Long> variantIds = customerProductStockDao.selectWarehouseVariantIdsByCustomer(request.getCustomerId(),request.getWarehouseCode());
        for (CustomerSkuStockVo customerSkuStockVo : customerSkuStockVos) {
            if(0 == customerSkuStockVo.getStock()) {
                continue;
            }
            VariantDetail variantDetail = map.get(customerSkuStockVo.getVariantSku());
            if (null == variantDetail){
                return BaseResponse.failed("sku: "+customerSkuStockVo.getVariantSku()+" 不存在");
            }
            CustomerProductStock customerProductStock = new CustomerProductStock();
            if (!variantIds.contains(variantDetail.getVariantId())){
                BeanUtils.copyProperties(variantDetail,customerProductStock);
                customerProductStock.setVariantImage(variantDetail.getVariantImage());
                customerProductStock.setCustomerId(request.getCustomerId());
                customerProductStock.setStock(customerSkuStockVo.getStock());
                customerProductStock.setCreateTime(date);
                customerProductStock.setUpdateTime(date);
                customerProductStock.setStockType(1);
                customerProductStock.setWarehouseCode(warehouseCode);
                customerProductStock.setCustomerShowState(1);
                customerProductStocks.add(customerProductStock);
            }else {
                customerProductStock.setVariantImage(variantDetail.getVariantImage());
                customerProductStock.setCustomerId(customerId);
                customerProductStock.setVariantId(customerSkuStockVo.getVariantId());
                customerProductStock.setStock(customerSkuStockVo.getStock());
                customerProductStock.setWarehouseCode(warehouseCode);
                updateStock.add(customerProductStock);
            }
            CustomerStockRecord customerStockRecord = new CustomerStockRecord();
            customerStockRecord.setCustomerId(customerId);
            customerStockRecord.setCreateTime(date);
            customerStockRecord.setVariantId(variantDetail.getVariantId());
            customerStockRecord.setVariantSku(variantDetail.getVariantSku());
            customerStockRecord.setProductId(variantDetail.getProductId());
            customerStockRecord.setOrderType(4);
            customerStockRecord.setType(3);
            customerStockRecord.setQuantity(customerSkuStockVo.getStock());
            customerStockRecord.setWarehouseCode(warehouseCode);
            customerStockRecord.setRelateId(System.currentTimeMillis());
            customerStockRecord.setVariantImage(variantDetail.getVariantImage());
            customerStockRecord.setUpdateTime(date);
            customerStockRecord.setRevokeState(0);
            customerStockRecord.setVariantName(variantDetail.getVariantName());
            customerStockRecords.add(customerStockRecord);
        }
        if (ListUtils.isNotEmpty(customerStockRecords)){
            customerStockRecordDao.insertByBatch(customerStockRecords);
        }
        if(ListUtils.isNotEmpty(customerProductStocks)){
            customerProductStockDao.insertByBatch(customerProductStocks);
        }
        if (ListUtils.isNotEmpty(updateStock)) {
            customerProductStockDao.increaseVariantStock(updateStock);
        }

        return BaseResponse.success();
    }

    /**
     *
     */
    public CustomerProductStock selectByPrimaryKey(Long id){
        CustomerProductStock record = new CustomerProductStock();
        record.setId(id);
        return customerProductStockDao.selectByPrimaryKey(record);
    }

    /**
     * 见擦汗
     * @param dischargeQuantityVos
     * @param customerId
     * @return
     */
//    public boolean orderItemStockCheck(List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId) {
//        if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
//            CustomerProductStock customerProductStock = new CustomerProductStock();
//            customerProductStock.setCustomerId(customerId);
//            Page<CustomerProductStock> page = new Page<>();
//            page.setT(customerProductStock);
//            page.setCondition("stock > 0");
//            List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
//            if (ListUtils.isNotEmpty(productStocks)) {
//                Map<Long, Integer> map = new HashMap<>();
//                for (CustomerProductStock productStock : productStocks) {
//                    map.put(productStock.getVariantId(), productStock.getStock());
//                }
//                for (ItemDischargeQuantityVo itemDischargeQuantityVo : dischargeQuantityVos) {
//                    Integer stock = map.get(itemDischargeQuantityVo.getVariantId());
//                    if (null == stock || stock < itemDischargeQuantityVo.getDischargeQuantity()) {
//                        return false;
//                    }
//                }
//            }else {
//                return false;
//            }
//        }
//        return true;
//    }
    @Override
    public boolean orderItemStockCheck(List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId) {
        if (ListUtils.isEmpty(dischargeQuantityVos)){
            return true;
        }
        List<CustomerWarehouseVariantStockVo> customerWarehouseVariantStockVos = customerProductStockDao.selectCustomerWarehouseVariantStock(customerId);
        if (ListUtils.isEmpty(customerWarehouseVariantStockVos)){
            return false;
        }
        a:
        for (ItemDischargeQuantityVo dischargeQuantityVo : dischargeQuantityVos) {
            b:
            for (CustomerWarehouseVariantStockVo customerWarehouseVariantStockVo : customerWarehouseVariantStockVos) {
                if (dischargeQuantityVo.getVariantId().equals(customerWarehouseVariantStockVo.getVariantId())
                && dischargeQuantityVo.getShippingWarehouse().equals(customerWarehouseVariantStockVo.getWarehouseCode())){
                    if (customerWarehouseVariantStockVo.getStock() < dischargeQuantityVo.getDischargeQuantity()){
                        return false;
                    }else {
                        continue a;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean increaseCustomerLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        if (ListUtils.isNotEmpty(items)) {
            //redis事务修改产品库存
            SessionCallback<Object> callback = new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    for (ItemDischargeQuantityVo item : items) {
                        boolean a = redisCheckCustomerVariantStock(customerId,item.getVariantId(),item.getShippingWarehouse(),item.getDischargeQuantity());
                        if (a){
                           redisReduceCustomerVariantStock(customerId,item.getVariantId(),item.getShippingWarehouse(),item.getDischargeQuantity());
                        }else {
                            throw new DataAccessException("Insufficient inventory of overseas warehouse products") {
                                @Override
                                public String getMessage() {
                                    return super.getMessage();
                                }
                            };
                        }
                    }
                    return redisOperations.exec();
                }
            };
        }
        boolean b = customerProductStockDao.increaseCustomerLockStock(customerId, items);
        return b;
    }

    @Override
    public int reduceFromLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        for (ItemDischargeQuantityVo item : items) {
            redisReduceCustomerVariantStock(customerId,item.getVariantId(),item.getShippingWarehouse(),item.getDischargeQuantity());
        }
        return customerProductStockDao.reduceFromLockStock(customerId, items);
    }

    @Override
    public int increaseFromLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        return customerProductStockDao.increaseFromLockStock(customerId, items);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerProductStock record) {
        return customerProductStockDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerProductStock record) {
        return customerProductStockDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerProductStock> select(Page<CustomerProductStock> record){
        record.initFromNum();
        return customerProductStockDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerProductStock> record){
        return customerProductStockDao.count(record);
    }

    @Override
    public List<CustomerProductStockNumVo> getCustomerStockNum() {

        return customerProductStockDao.getCustomerStockNum();
    }

    /**
     * 备库管理/客户商品库存/同步库存
     * @param id
     * @return
     */
    @Override
    public BaseResponse refreshSaiheInventory(Long id) throws CustomerException {
        // 获取调用赛盒参数
        CustomerProductStock customerProductStock = new CustomerProductStock();
        customerProductStock.setId(id);
        customerProductStock = customerProductStockDao.selectByPrimaryKey(customerProductStock);
        if (customerProductStock == null || customerProductStock.getWarehouseCode() == null){
            throw new CustomerException(CustomerExceptionEnum.FIND_NULL);
        }

        //赛盒
        GetProductInventoryResponse getProductInventoryResponse = SaiheService.getProductInventory(Integer.valueOf(customerProductStock.getWarehouseCode().toString()), customerProductStock.getVariantSku());
        if (getProductInventoryResponse.getGetProductInventoryResult().getStatus().equals("OK")) {
            ProductInventoryList productInventoryList=getProductInventoryResponse.
                    getGetProductInventoryResult().getProductInventoryList();
            if(productInventoryList.getProductInventoryList()==null||
                    productInventoryList.getProductInventoryList().size()==0){
                throw new CustomerException(CustomerExceptionEnum.FIND_NULL);
            }
            if(productInventoryList.getProductInventoryList().size()>1){
                throw new CustomerException(CustomerExceptionEnum.DATA_OF_THE_REPEATED);
            }
            ApiProductInventory apiProductInventory=productInventoryList.getProductInventoryList().get(0);
            if(StringUtils.isBlank(apiProductInventory.getClientSKU())||
                    !apiProductInventory.getClientSKU().equals(customerProductStock.getVariantSku())){
                throw new CustomerException(CustomerExceptionEnum.DATA_OF_THE_EXCEPTION);
            }
            //同步赛盒库存信息
            ProductSaiheInventoryVo productSaiheInventoryVo=new ProductSaiheInventoryVo();
            productSaiheInventoryVo.setVariantSku(apiProductInventory.getClientSKU());
            productSaiheInventoryVo.setWarehouseId(Integer.valueOf(customerProductStock.getWarehouseCode().toString()));
            productSaiheInventoryVo.setGoodNum(apiProductInventory.getGoodNum());
            productSaiheInventoryVo.setLockNum(apiProductInventory.getLockNum());
            productSaiheInventoryVo.setUpdateTime(apiProductInventory.getUpdateTime());
            pmsFeignClient.insertProductSaiheInventory(productSaiheInventoryVo);
        }
        return BaseResponse.success();


    }


    public void redisAddCustomerVariantStock(Long customerId,Long variantId,String warehouseCode,long stock){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        if (redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().increment(key,stock);
        }else {
            redisTemplate.opsForValue().set(key,stock);
        }

    }

    @Override
    public void redisReduceCustomerVariantStock(Long customerId,Long variantId,String warehouseCode,long stock){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        redisTemplate.opsForValue().decrement(key,stock);
    }

    @Override
    public long redisGetCustomerVariantStock(Long customerId,Long variantId,String warehouseCode){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        return (long) redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean redisCheckCustomerVariantStock(Long customerId,Long variantId,String warehouseCode,long stock){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        if (redisTemplate.hasKey(key)) {
            long nowStock = (long) redisTemplate.opsForValue().get(key);
            if (nowStock >= stock){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }


}
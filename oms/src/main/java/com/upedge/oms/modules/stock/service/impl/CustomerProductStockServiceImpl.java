package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.oms.stock.CustomerStockVo;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.dao.CustomerStockRecordDao;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.request.CustomerProductStockCustomUpdateRequest;
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
import org.springframework.data.redis.core.RedisTemplate;
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
        redisReduceCustomerVariantStock(customerProductStock.getCustomerId(),customerProductStock.getVariantId(),customerProductStock.getWarehouseCode(),record.getQuantity());

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
            customerStockRecord.setRelateId(IdGenerate.nextId());
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
        for (CustomerSkuStockVo customerSkuStockVo : customerSkuStockVos) {
            redisAddCustomerVariantStock(customerId,customerSkuStockVo.getVariantId(),warehouseCode,customerSkuStockVo.getStock());
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
        for (ItemDischargeQuantityVo item : items) {
            redisReduceCustomerVariantStock(customerId,item.getVariantId(),item.getShippingWarehouse(),item.getDischargeQuantity());
        }
        boolean b = customerProductStockDao.increaseCustomerLockStock(customerId, items);
        return b;
    }

    @Override
    public int reduceFromLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        return customerProductStockDao.reduceFromLockStock(customerId, items);
    }

    @Override
    public int increaseFromLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        int i = customerProductStockDao.increaseFromLockStock(customerId, items);
        for (ItemDischargeQuantityVo item : items) {
            redisAddCustomerVariantStock(customerId,item.getVariantId(),item.getShippingWarehouse(),item.getDischargeQuantity());
        }
        return i;
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
        List<CustomerProductStock> customerProductStocks = customerProductStockDao.select(record);
        for (CustomerProductStock customerProductStock : customerProductStocks) {
            WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + customerProductStock.getWarehouseCode());
            if(null != warehouseVo){
                customerProductStock.setWarehouseName(warehouseVo.getWarehouseEnEame());
            }
        }
        return customerProductStocks;
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

    @Override
    public List<CustomerStockVo> selectCustomerStockByVariantIds(Long customerId, List<Long> variantIds) {
        if (customerId == null || ListUtils.isEmpty(variantIds)){
            return new ArrayList<>();
        }
        List<CustomerStockVo> customerStockVos = new ArrayList<>();
        List<CustomerProductStock> customerProductStocks = customerProductStockDao.selectCustomerStockByVariantIds(customerId,variantIds);
        for (CustomerProductStock customerProductStock : customerProductStocks) {
            CustomerStockVo customerStockVo = new CustomerStockVo();
            customerStockVo.setStock(customerProductStock.getStock());
            customerStockVo.setVariantId(customerProductStock.getVariantId());
            customerStockVos.add(customerStockVo);
        }
        return customerStockVos;
    }

    @Transactional
    @Override
    public BaseResponse customUpdateCustomerProductStock(CustomerProductStockCustomUpdateRequest request, Session session) {
        Long variantId = request.getVariantId();
        Long customerId = request.getCustomerId();
        String warehouseCode = request.getWarehouseCode();
        Integer quantity = request.getQuantity();

        String lockKey = RedisKey.LOCK_CUSTOMER_PRODUCT_STOCK_UPDATE + customerId + ":" + warehouseCode + ":" + variantId;
        boolean b = RedisUtil.lock(redisTemplate,lockKey,1000L,1000L);
        if (!b){
            return BaseResponse.failed();
        }

        CustomerProductStock customerProductStock = customerProductStockDao.selectStockByVariantAndCustomerId(variantId,request.getCustomerId(),request.getWarehouseCode());

        if (null == customerProductStock){
            RedisUtil.unLock(redisTemplate,lockKey);
            return BaseResponse.failed();
        }
        Integer nowStock = customerProductStock.getStock();
        if (quantity == nowStock){
            RedisUtil.unLock(redisTemplate,lockKey);
            return BaseResponse.success();
        }

        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        redisTemplate.opsForValue().set(key,quantity);

        customerProductStockDao.customUpdateCustomerProductStock(request);

        CustomerStockRecord customerStockRecord = new CustomerStockRecord();
        customerStockRecord.setCustomerId(customerId);
        customerStockRecord.setCreateTime(new Date());
        customerStockRecord.setVariantId(variantId);
        customerStockRecord.setVariantSku(customerProductStock.getVariantSku());
        customerStockRecord.setProductId(customerProductStock.getProductId());
        customerStockRecord.setOrderType(4);
        customerStockRecord.setType(6);
        customerStockRecord.setQuantity(quantity);
        customerStockRecord.setWarehouseCode(warehouseCode);
        customerStockRecord.setRelateId(IdGenerate.nextId());
        customerStockRecord.setVariantImage(customerProductStock.getVariantImage());
        customerStockRecord.setUpdateTime(new Date());
        customerStockRecord.setRevokeState(0);
        customerStockRecord.setVariantName(customerProductStock.getVariantName());
        customerStockRecord.setCustomerShowState(1);
        customerStockRecordDao.insert(customerStockRecord);
        RedisUtil.unLock(redisTemplate,lockKey);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public void orderRefundItemStock(Long customerId, String warehouseCode,List<CustomerStockRecord> customerStockRecords) {
        if (ListUtils.isEmpty(customerStockRecords)){
            return;
        }

        List<Long> variantIds = customerProductStockDao.selectWarehouseVariantIdsByCustomer(customerId,warehouseCode);
        if (variantIds == null){
            variantIds = new ArrayList<>();
        }

        List<CustomerProductStock> update = new ArrayList<>();
        List<CustomerProductStock> insert = new ArrayList<>();
        List<CustomerProductStock> customerProductStocks = new ArrayList<>();
        for (CustomerStockRecord customerStockRecord : customerStockRecords) {
            Long variantId = customerStockRecord.getVariantId();

            Integer quantity = customerStockRecord.getQuantity();
            CustomerProductStock customerProductStock = new CustomerProductStock();
            customerProductStock.setCustomerId(customerId);
            customerProductStock.setStock(quantity);
            customerProductStock.setVariantId(variantId);
            customerProductStock.setWarehouseCode(warehouseCode);
            if(variantIds.contains(variantId)){
                update.add(customerProductStock);
            }else {
                BeanUtils.copyProperties(customerStockRecord,customerProductStock);
                customerProductStock.setCustomerShowState(1);
                customerProductStock.setLockStock(0);
                insert.add(customerProductStock);
            }
        }
        if (ListUtils.isNotEmpty(update)){
            customerProductStockDao.increaseVariantStock(update);
        }
        if (ListUtils.isNotEmpty(insert)){
            customerProductStockDao.insertByBatch(insert);
        }
        customerStockRecordDao.insertByBatch(customerStockRecords);

        for (CustomerProductStock customerProductStock : customerProductStocks) {
            redisAddCustomerVariantStock(customerId,customerProductStock.getVariantId(),customerProductStock.getWarehouseCode(),customerProductStock.getStock());
        }

    }

    @Override
    public List<WarehouseVo> selectCustomerStockWarehouses(Long customerId) {
        List<WarehouseVo> warehouseVos = new ArrayList<>();
        List<String> warehouseCodes = customerProductStockDao.selectCustomerStockWarehouses(customerId);
        for (String warehouseCode : warehouseCodes) {
            WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + warehouseCode);
            warehouseVos.add(warehouseVo);
        }
        return warehouseVos;
    }

    @Override
    public void redisInit() {
        List<CustomerProductStock> customerProductStocks = customerProductStockDao.selectAllCustomerStocks();
        Set<String> keys = redisTemplate.keys(RedisKey.STRING_CUSTOMER_VARIANT_STOCK + "*");
        if (keys != null && keys.size() > 0){
            redisTemplate.delete(keys);
        }
        for (CustomerProductStock customerProductStock : customerProductStocks) {
            Long customerId = customerProductStock.getCustomerId();
            Long variantId = customerProductStock.getVariantId();
            String warehouseCode = customerProductStock.getWarehouseCode();
            long stock = customerProductStock.getStock();
            String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
            redisTemplate.opsForValue().set(key,stock);
        }
    }

    @Override
    public void redisAddCustomerVariantStock(Long customerId, Long variantId, String warehouseCode, long stock){
        String lockKey = RedisKey.LOCK_CUSTOMER_PRODUCT_STOCK_UPDATE + customerId + ":" + warehouseCode + ":" + variantId;
        boolean b = RedisUtil.lock(redisTemplate,lockKey,1000L,1000L);
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        if (!b){
            //从数据库读取最新数据更新
            return;
        }

        b = redisTemplate.hasKey(key);
        if (b) {
            long nowStock = (long) redisTemplate.opsForValue().get(key);
            stock = stock + nowStock;
        }
        redisTemplate.opsForValue().set(key,stock);
        RedisUtil.unLock(redisTemplate,lockKey);
    }

    @Override
    public void redisReduceCustomerVariantStock(Long customerId,Long variantId,String warehouseCode,long stock){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        if (!redisTemplate.hasKey(key)){
            return;
        }
        String lockKey = RedisKey.LOCK_CUSTOMER_PRODUCT_STOCK_UPDATE + customerId + ":" + warehouseCode + ":" + variantId;
        boolean b = RedisUtil.lock(redisTemplate,lockKey,1000L,1000L);

        long nowStock = 0;
        if (!b){
            nowStock = customerProductStockDao.selectVariantStockByCustomer(customerId, variantId, warehouseCode);
        }else {
            nowStock = (long) redisTemplate.opsForValue().get(key);
        }
        nowStock = nowStock - stock;
        if(nowStock <= 0){
            redisTemplate.delete(key);
        }else {
            redisTemplate.opsForValue().set(key,nowStock);
        }
        RedisUtil.unLock(redisTemplate,lockKey);
    }


    @Override
    public long redisGetCustomerVariantStock(Long customerId,Long variantId,String warehouseCode){
        String lockKey = RedisKey.LOCK_CUSTOMER_PRODUCT_STOCK_UPDATE + customerId + ":" + warehouseCode + ":" + variantId;
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        //加锁保证获取的是最新值
        boolean b = RedisUtil.lock(redisTemplate,lockKey,1000L,1000L);
        if (!b){
            //锁获取失败
            //从数据库读取最新数据更新
            long stock = customerProductStockDao.selectVariantStockByCustomer(customerId, variantId, warehouseCode);
            redisTemplate.opsForValue().set(key,stock);
            return stock;
        }
        long stock = (long) redisTemplate.opsForValue().get(key);
        RedisUtil.unLock(redisTemplate,lockKey);
        return stock;
    }

    @Override
    public boolean redisCheckCustomerVariantStock(Long customerId,Long variantId,String warehouseCode,long stock){
        String key = RedisKey.STRING_CUSTOMER_VARIANT_STOCK + customerId + ":" + warehouseCode + ":" + variantId;
        boolean b = redisTemplate.hasKey(key);
        if (b) {
            long nowStock = redisGetCustomerVariantStock(customerId, variantId, warehouseCode);
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
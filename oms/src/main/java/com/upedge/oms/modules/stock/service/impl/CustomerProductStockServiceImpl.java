package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ApiProductInventory;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ProductInventoryList;
import com.upedge.thirdparty.saihe.response.GetProductInventoryResponse;
import com.upedge.thirdparty.saihe.service.SaiheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerProductStockServiceImpl implements CustomerProductStockService {

    @Autowired
    private CustomerProductStockDao customerProductStockDao;

    @Autowired
    private PmsFeignClient pmsFeignClient;

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
    @Transactional
    public int insertSelective(CustomerProductStock record) {
        return customerProductStockDao.insert(record);
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
    @Override
    public boolean orderItemStockCheck(List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId) {
        if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
            CustomerProductStock customerProductStock = new CustomerProductStock();
            customerProductStock.setCustomerId(customerId);
            Page<CustomerProductStock> page = new Page<>();
            page.setT(customerProductStock);
            page.setCondition("stock > 0");
            List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
            if (ListUtils.isNotEmpty(productStocks)) {
                Map<Long, Integer> map = new HashMap<>();
                for (CustomerProductStock productStock : productStocks) {
                    map.put(productStock.getVariantId(), productStock.getStock());
                }
                for (ItemDischargeQuantityVo itemDischargeQuantityVo : dischargeQuantityVos) {
                    Integer stock = map.get(itemDischargeQuantityVo.getVariantId());
                    if (null == stock || stock < itemDischargeQuantityVo.getDischargeQuantity()) {
                        return false;
                    }
                }
            }else {
                return false;
            }

        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean increaseCustomerLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
        if (ListUtils.isNotEmpty(items)) {
            CustomerProductStock customerProductStock = new CustomerProductStock();
            customerProductStock.setCustomerId(customerId);
            Page<CustomerProductStock> page = new Page<>();
            page.setT(customerProductStock);
            page.setCondition("stock > 0");
            List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
            if (ListUtils.isNotEmpty(productStocks)) {
                Map<Long, Integer> map = new HashMap<>();
                for (CustomerProductStock productStock : productStocks) {
                    map.put(productStock.getVariantId(), productStock.getStock());
                }
                for (ItemDischargeQuantityVo itemDischargeQuantityVo : items) {
                    Integer stock = map.get(itemDischargeQuantityVo.getVariantId());
                    if (null == stock || stock < itemDischargeQuantityVo.getDischargeQuantity()) {
                        return false;
                    }
                }
            }else {
                return false;
            }
        }
        return customerProductStockDao.increaseCustomerLockStock(customerId, items);
    }

    @Override
    public int reduceFromLockStock(Long customerId, List<ItemDischargeQuantityVo> items) {
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
        if (customerProductStock == null || customerProductStock.getWarehouseId() == null){
            throw new CustomerException(CustomerExceptionEnum.FIND_NULL);
        }

        //赛盒
        GetProductInventoryResponse getProductInventoryResponse = SaiheService.getProductInventory(Integer.valueOf(customerProductStock.getWarehouseId().toString()), customerProductStock.getVariantSku());
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
            productSaiheInventoryVo.setWarehouseId(Integer.valueOf(customerProductStock.getWarehouseId().toString()));
            productSaiheInventoryVo.setGoodNum(apiProductInventory.getGoodNum());
            productSaiheInventoryVo.setLockNum(apiProductInventory.getLockNum());
            productSaiheInventoryVo.setUpdateTime(apiProductInventory.getUpdateTime());
            pmsFeignClient.insertProductSaiheInventory(productSaiheInventoryVo);

        }
        return BaseResponse.success();


    }

    @Override
    public CustomerProductStock selectStockByVariantAndCustomerId(Long variantId, Long userId) {

        return customerProductStockDao.selectStockByVariantAndCustomerId(variantId,userId);

    }

}
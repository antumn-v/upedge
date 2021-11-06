package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.DailySales;
import com.upedge.common.model.product.StoreProductDailySales;
import com.upedge.common.model.product.StoreProductSalesVo;
import com.upedge.oms.modules.order.dao.StoreOrderItemDao;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.service.StoreOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class StoreOrderItemServiceImpl implements StoreOrderItemService {

    @Autowired
    private StoreOrderItemDao storeOrderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreOrderItem record = new StoreOrderItem();
        record.setId(id);
        return storeOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreOrderItem record) {
        return storeOrderItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreOrderItem record) {
        return storeOrderItemDao.insert(record);
    }

    @Override
    public List<StoreProductSalesVo> selectStoreProductSales() {
        List<StoreProductDailySales> storeProductDailySales = storeOrderItemDao.selectStoreProductSales();
        List<StoreProductSalesVo> salesVos = new ArrayList<>();
        for (StoreProductDailySales productDailySales: storeProductDailySales) {
            StoreProductSalesVo salesVo = new StoreProductSalesVo();
            List<DailySales> dailySalesList = productDailySales.getDailySales();
            for (DailySales dailySales: dailySalesList) {
                switch (dailySales.getDays()){
                    case "one":
                        salesVo.setOne(dailySales.getSales());
                        break;
                    case "seven":
                        salesVo.setSeven(dailySales.getSales());
                        break;
                    case "fifteen":
                        salesVo.setFifteen(dailySales.getSales());
                        break;
                }
            }
            if(BigDecimal.ZERO.compareTo( salesVo.getOne()) == 0
            && BigDecimal.ZERO.compareTo( salesVo.getSeven()) == 0
            && BigDecimal.ZERO.compareTo( salesVo.getFifteen()) == 0){
                continue;
            }
            salesVo.initDailyAverage();
            salesVo.setOrgId(productDailySales.getOrgId());
            salesVo.setCustomerId(productDailySales.getCustomerId());
            salesVo.setStoreId(productDailySales.getStoreId());
            salesVo.setStoreProductId(productDailySales.getStoreProductId());
            salesVos.add(salesVo);
        }
        return salesVos;
    }

    @Override
    public List<Long> selectStoreOrderIdByStoreVariantIdAndState(Long storeVariantId, Integer state) {
        return storeOrderItemDao.selectStoreOrderIdByStoreVariantIdAndState(storeVariantId,state);
    }

    @Override
    public StoreOrderItem selectByStoreVariantId(Long storeOrderId, Long storeVariantId) {
        return storeOrderItemDao.selectByStoreVariantId(storeOrderId, storeVariantId);
    }

    /**
     *
     */
    public StoreOrderItem selectByPrimaryKey(Long id){
        StoreOrderItem record = new StoreOrderItem();
        record.setId(id);
        return storeOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreOrderItem record) {
        return storeOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreOrderItem record) {
        return storeOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreOrderItem> select(Page<StoreOrderItem> record){
        record.initFromNum();
        return storeOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreOrderItem> record){
        return storeOrderItemDao.count(record);
    }

}
package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.VariantWarehouseStockRecordDao;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantStockListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import com.upedge.pms.modules.purchase.vo.VariantWarehouseStockRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VariantWarehouseStockRecordServiceImpl implements VariantWarehouseStockRecordService {

    @Autowired
    private VariantWarehouseStockRecordDao variantWarehouseStockRecordDao;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        VariantWarehouseStockRecord record = new VariantWarehouseStockRecord();
        record.setId(id);
        return variantWarehouseStockRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.insert(record);
    }

    @Override
    public int insertByBatch(List<VariantWarehouseStockRecord> records) {
        if (ListUtils.isNotEmpty(records)){
            return variantWarehouseStockRecordDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.insert(record);
    }

    @Override
    public BaseResponse variantStockRecordList(VariantStockListRequest request) {
        List<VariantWarehouseStockRecordVo> variantWarehouseStockRecordVos = variantWarehouseStockRecordDao.selectVariantStockRecordVos(request);
        Long total = variantWarehouseStockRecordDao.countVariantStockRecordVos(request);
        request.setTotal(total);
        return BaseResponse.success(variantWarehouseStockRecordVos,request);
    }

    /**
     *
     */
    public VariantWarehouseStockRecord selectByPrimaryKey(Integer id){
        VariantWarehouseStockRecord record = new VariantWarehouseStockRecord();
        record.setId(id);
        return variantWarehouseStockRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantWarehouseStockRecord> select(Page<VariantWarehouseStockRecord> record){
        record.initFromNum();
        return variantWarehouseStockRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantWarehouseStockRecord> record){
        return variantWarehouseStockRecordDao.count(record);
    }

}
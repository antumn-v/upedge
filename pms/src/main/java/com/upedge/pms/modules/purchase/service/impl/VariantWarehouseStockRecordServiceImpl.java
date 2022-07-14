package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.VariantWarehouseStockRecordDao;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import com.upedge.pms.modules.purchase.vo.VariantWarehouseStockRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class VariantWarehouseStockRecordServiceImpl implements VariantWarehouseStockRecordService {

    @Autowired
    private VariantWarehouseStockRecordDao variantWarehouseStockRecordDao;

    @Autowired
    ProductVariantService productVariantService;



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

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.insert(record);
    }

    @Override
    public BaseResponse variantStockRecordList(VariantWarehouseStockRecordListRequest request) {
        List<VariantWarehouseStockRecordVo> variantWarehouseStockRecordVos = new ArrayList<>();
        List<VariantWarehouseStockRecord> variantWarehouseStockRecords = select(request);
        if (ListUtils.isNotEmpty(variantWarehouseStockRecords)){
            List<Long> variantIds = new ArrayList<>();
            for (VariantWarehouseStockRecord variantWarehouseStockRecord : variantWarehouseStockRecords) {
                variantIds.add(variantWarehouseStockRecord.getVariantId());
            }
            List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
            variantWarehouseStockRecords.forEach(variantWarehouseStockRecord -> {
                VariantWarehouseStockRecordVo variantWarehouseStockRecordVo = new VariantWarehouseStockRecordVo();
                for (ProductVariant productVariant : productVariants) {
                    if (productVariant.getId().equals(variantWarehouseStockRecord.getVariantId())){
                        BeanUtils.copyProperties(productVariant,variantWarehouseStockRecordVo);
                    }
                }
                BeanUtils.copyProperties(variantWarehouseStockRecord,variantWarehouseStockRecordVo);
                variantWarehouseStockRecordVos.add(variantWarehouseStockRecordVo);
            });
        }
        Long total = count(request);
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
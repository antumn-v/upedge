package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.product.vo.VariantWarehouseStockVo;
import com.upedge.pms.modules.purchase.dao.VariantWarehouseStockDao;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class VariantWarehouseStockServiceImpl implements VariantWarehouseStockService {

    @Autowired
    private VariantWarehouseStockDao variantWarehouseStockDao;

    @Autowired
    private VariantWarehouseStockRecordService variantWarehouseStockRecordService;

    @Autowired
    ProductVariantService productVariantService;
    
    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long variantId) {
        VariantWarehouseStock record = new VariantWarehouseStock();
        record.setVariantId(variantId);
        return variantWarehouseStockDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantWarehouseStock record) {
        return variantWarehouseStockDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantWarehouseStock record) {
        return variantWarehouseStockDao.insert(record);
    }

    @Override
    public BaseResponse variantWarehouseStockList(VariantWarehouseStockListRequest request) {
        List<VariantWarehouseStockVo> variantWarehouseStockVos = new ArrayList<>();
        List<VariantWarehouseStock> variantWarehouseStocks = select(request);
        if (ListUtils.isNotEmpty(variantWarehouseStocks)){
            List<Long> variantIds = new ArrayList<>();
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                variantIds.add(variantWarehouseStock.getVariantId());
            }
            List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
            List<VariantPreSaleQuantity> variantPreSaleQuantities = omsFeignClient.selectVariantPreSaleQuantity(variantIds);
            a:
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                VariantWarehouseStockVo variantWarehouseStockVo = new VariantWarehouseStockVo();
                BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockVo);
                variantWarehouseStockVos.add(variantWarehouseStockVo);
                for (ProductVariant productVariant : productVariants) {
                    if (productVariant.getId().equals(variantWarehouseStock.getVariantId())){
                        BeanUtils.copyProperties(productVariant,variantWarehouseStockVo);
                        productVariants.remove(productVariant);
                    }
                }
                if (ListUtils.isEmpty(variantPreSaleQuantities)){
                    continue a;
                }
                for (VariantPreSaleQuantity variantPreSaleQuantity : variantPreSaleQuantities) {
                    if (variantPreSaleQuantity.getVariantId().equals(variantWarehouseStock.getVariantId())){
                        variantWarehouseStockVo.setPreSaleQuantity(variantPreSaleQuantity.getPreSaleQuantity());
                        variantPreSaleQuantities.remove(variantPreSaleQuantity);
                        continue a;
                    }
                }
            }
        }
        Long total = count(request);
        request.setTotal(total);
        return BaseResponse.success(variantWarehouseStockVos,request);
    }

    @Transactional
    @Override
    public BaseResponse updateVariantStock(VariantStockUpdateRequest request, Session session) {
        Long variantId = request.getVariantId();
        String warehouseCode = request.getWarehouseCode();
        String key = "key:variantUpdateStock:"+ variantId + ":" + warehouseCode;
        boolean b = RedisUtil.lock(redisTemplate,key,10L,10 * 1000L);
        if (!b){
            return BaseResponse.failed();
        }
        Integer originalQuantity = 0;

        VariantWarehouseStock variantWarehouseStock = selectByPrimaryKey(variantId,warehouseCode);
        if (null == variantWarehouseStock){
            variantWarehouseStock = new VariantWarehouseStock(variantId,warehouseCode,1, request.getStock(), 0,0,"","");
            insert(variantWarehouseStock);
        }else {
            originalQuantity = variantWarehouseStock.getSafeStock();
            variantWarehouseStock = new VariantWarehouseStock();
            variantWarehouseStock.setSafeStock(request.getStock());
            variantWarehouseStock.setVariantId(variantId);
            variantWarehouseStock.setWarehouseCode(warehouseCode);
            variantWarehouseStock.setUpdateTime(new Date());
            if (StringUtils.isNotBlank(request.getRemark())){
                variantWarehouseStock.setRemark(request.getRemark());
            }
            updateByPrimaryKeySelective(variantWarehouseStock);
        }

        VariantWarehouseStockRecord variantWarehouseStockRecord =
                new VariantWarehouseStockRecord(variantId,
                        warehouseCode,
                        request.getStock(),
                        VariantWarehouseStockRecord.CUSTOM_UPDATE,
                        originalQuantity,
                        request.getStock(),
                        IdGenerate.nextId(),
                        new Date(),
                        null,
                        session.getId());
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    /**
     *
     */
    public VariantWarehouseStock selectByPrimaryKey(Long variantId,String warehouseCode){

        return variantWarehouseStockDao.selectByPrimaryKey(variantId, warehouseCode);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantWarehouseStock record) {
        return variantWarehouseStockDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantWarehouseStock record) {
        return variantWarehouseStockDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record){
        record.initFromNum();
        return variantWarehouseStockDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantWarehouseStock> record){
        return variantWarehouseStockDao.count(record);
    }

}
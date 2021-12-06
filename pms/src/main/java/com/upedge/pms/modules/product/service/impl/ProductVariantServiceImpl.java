package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductLogDao;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.entity.ProductLog;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ProductVariantAttrService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.product.vo.SaiheSkuVo;
import com.upedge.pms.modules.product.vo.VariantAttrVo;
import com.upedge.pms.modules.product.vo.VariantValVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantDao productVariantDao;

    @Autowired
    ProductVariantAttrService productVariantAttrService;

    @Autowired
    ProductLogDao productLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductVariant record) {
        return productVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductVariant record) {
        return productVariantDao.insert(record);
    }

    @Override
    public ProductVariantUpdateWeightResponse updateWeight(ProductVariantUpdateWeightRequest request, Session session) {
        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(request.getIds());
        List<ProductLog> productLogList = new ArrayList<>();
        for (ProductVariant productVariant : productVariantList) {
            if (productVariant.getWeight().compareTo(request.getWeight()) != 0) {
                ProductLog productLog = new ProductLog();
                productLog.setId(IdGenerate.nextId());
                productLog.setAdminUser(String.valueOf(session.getId()));
                productLog.setCreateTime(new Date());
                productLog.setProductId(productVariant.getProductId());
                productLog.setSku(productVariant.getVariantSku());
                //操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
                productLog.setOptType(1);
                productLog.setOldInfo(String.valueOf(productVariant.getWeight()));
                productLog.setNewInfo(String.valueOf(request.getWeight()));
                productLogList.add(productLog);
            }
        }
        productVariantDao.updateWeight(request.getIds(), request.getWeight());
        if (productLogList.size() > 0) {
            productLogDao.insertByBatch(productLogList);
        }
        return new ProductVariantUpdateWeightResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public ProductVariantUpdateVolumeWeightResponse updateVolumeWeight(ProductVariantUpdateVolumeWeightRequest request, Session session) {
        return null;
    }

    @Override
    public ProductVariantUpdateVariantImageResponse updateVariantImage(ProductVariantUpdateVariantImageRequest request, Session session) {
        return null;
    }

    @Override
    public ProductVariantEnableResponse enableVariant(ProductVariantEnableRequest request) {
        return null;
    }

    @Override
    public ProductVariantDisableResponse disableVariant(ProductVariantDisableRequest request) {
        return null;
    }

    @Override
    public ProductVariantUpdateAttrResponse updateAttr(ProductVariantUpdateAttrRequest request) {
        return null;
    }

    @Override
    public ProductVariantUpdatePriceResponse updatePrice(ProductVariantUpdatePriceRequest request, Session session) {
        return null;
    }

    @Override
    public ProductVariantsResponse listVariantByIds(List<Long> variantIds) {
        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(variantIds);
        ProductVariantsResponse res = new ProductVariantsResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, productVariantList);
        return res;
    }

    @Override
    public Map<String, BigDecimal> selectVariantPriceRange(Long productId) {

        if (productId != null){
            return productVariantDao.selectVariantPriceRange(productId);
        }
        return null;
    }

    @Override
    public List<SaiheSkuVo> listSaiheSkuVo(List<Long> ids) {
        if (ListUtils.isNotEmpty(ids)){
            return productVariantDao.listSaiheSkuVo(ids);
        }
        return null;
    }

    @Override
    public List<ProductVariant> getProductVariantList(List<Long> variantIds, Map<String, VariantAttrVo> attrMap, Map<String, Set<String>> attrValSet, Map<Long, ProductVariant> productVariantMap) {
        //获取产品属性
        List<ProductVariantAttr> productVariantAttrList = productVariantAttrService.selectByVariantIds(variantIds);
        productVariantAttrList.forEach(productVariantAttr -> {

            //属性
            VariantAttrVo variantAttrVo = attrMap.get(productVariantAttr.getVariantAttrCname());
            if (variantAttrVo == null) {
                variantAttrVo = new VariantAttrVo();
                variantAttrVo.setCname(productVariantAttr.getVariantAttrCname());
                variantAttrVo.setEname(productVariantAttr.getVariantAttrEname());
                attrMap.put(productVariantAttr.getVariantAttrCname(), variantAttrVo);
                attrValSet.put(productVariantAttr.getVariantAttrCname(), new HashSet<>());
            }
            //获取属性的值列表
            Set<String> valSet = attrValSet.get(productVariantAttr.getVariantAttrCname());
            //属性值
            if (!valSet.contains(productVariantAttr.getOriginalAttrCvalue())) {
                VariantValVo variantValVo = new VariantValVo();
                variantValVo.setOriginalCvalue(productVariantAttr.getOriginalAttrCvalue());
                variantValVo.setCvalue(productVariantAttr.getVariantAttrCvalue());
                variantValVo.setEvalue(productVariantAttr.getVariantAttrEvalue());
                variantAttrVo.getVariantValVoList().add(variantValVo);
                valSet.add(productVariantAttr.getOriginalAttrCvalue());
            }

            ProductVariant variant = productVariantMap.get(productVariantAttr.getVariantId());
            variant.getProductVariantAttrList().add(productVariantAttr);
        });
        List<ProductVariant> productVariantList = new ArrayList(productVariantMap.values());
        return productVariantList;
    }

    @Override
    public List<ProductVariant> selectByProductId(Long productId) {
        return productVariantDao.selectByProductId(productId);
    }

    @Override
    public int insertByBatch(List<ProductVariant> productVariants) {
        return productVariantDao.insertByBatch(productVariants);
    }

    /**
     *
     */
    public ProductVariant selectByPrimaryKey(Long id){
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductVariant record) {
        return productVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductVariant record) {
        return productVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductVariant> select(Page<ProductVariant> record){
        record.initFromNum();
        return productVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductVariant> record){
        return productVariantDao.count(record);
    }

}
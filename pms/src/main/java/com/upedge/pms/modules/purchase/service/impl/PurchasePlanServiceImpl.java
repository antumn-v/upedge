package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.PurchasePlanDao;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.request.PurchasePlanAddRequest;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.pms.modules.purchase.service.PurchasePlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class PurchasePlanServiceImpl implements PurchasePlanService {

    @Autowired
    private PurchasePlanDao purchasePlanDao;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        PurchasePlan record = new PurchasePlan();
        record.setId(id);
        return purchasePlanDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchasePlan record) {
        return purchasePlanDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchasePlan record) {
        return purchasePlanDao.insert(record);
    }

    @Override
    public List<Long> selectPlaningVariantIds() {
        return purchasePlanDao.selectPlaningVariantIds();
    }

    @Override
    public BaseResponse addPurchasePlan(PurchasePlanAddRequest request, Session session) {
        Long variantId = request.getVariantId();
        ProductVariant productVariant = productVariantService.selectByPrimaryKey(variantId);
        if (null == productVariant
        || null == productVariant.getPurchaseSku()){
            return BaseResponse.failed("产品未配置采购信息");
        }
        ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(productVariant.getPurchaseSku());
        if (null == productPurchaseInfo){
            return BaseResponse.failed("采购信息异常");
        }
        PurchasePlan purchasePlan = new PurchasePlan();
        BeanUtils.copyProperties(productVariant,purchasePlan);
        BeanUtils.copyProperties(productPurchaseInfo,purchasePlan);
        purchasePlan.setState(0);
        purchasePlan.setVariantId(variantId);
        purchasePlan.setQuantity(request.getQuantity());
        purchasePlan.setCreateTime(new Date());
        purchasePlan.setUpdateTime(new Date());
        purchasePlan.setOperatorId(session.getId());
        insert(purchasePlan);
        return BaseResponse.success();
    }

    /**
     *
     */
    public PurchasePlan selectByPrimaryKey(Integer id){
        PurchasePlan record = new PurchasePlan();
        record.setId(id);
        return purchasePlanDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchasePlan record) {
        return purchasePlanDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchasePlan record) {
        return purchasePlanDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchasePlan> select(Page<PurchasePlan> record){
        record.initFromNum();
        return purchasePlanDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchasePlan> record){
        return purchasePlanDao.count(record);
    }

}
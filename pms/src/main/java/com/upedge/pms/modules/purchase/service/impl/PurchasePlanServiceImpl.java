package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.PurchasePlanDao;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.request.PurchasePlanAddRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanListRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanUpdateRequest;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.pms.modules.purchase.service.PurchasePlanService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import com.upedge.pms.modules.purchase.vo.PurchasePlanVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class PurchasePlanServiceImpl implements PurchasePlanService {

    @Autowired
    private PurchasePlanDao purchasePlanDao;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;



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
    public BaseResponse selectPurchasePlanList() {

        List<PurchasePlan> results = purchasePlanDao.selectPurchasePlanList();
        Map<String,List<PurchasePlan>> map = new HashMap<>();
        for (PurchasePlan result : results) {
            if (!map.containsKey(result.getSupplierName())){
                map.put(result.getSupplierName(), new ArrayList<>());
            }
            map.get(result.getSupplierName()).add(result);
        }
        List<PurchasePlanVo> purchasePlanVos = new ArrayList<>();
        map.forEach((s, purchasePlans) -> {
            PurchasePlanVo purchasePlanVo = new PurchasePlanVo();
            purchasePlanVo.setSupplierName(s);
            purchasePlanVo.setPurchasePlans(purchasePlans);
            purchasePlanVos.add(purchasePlanVo);
        });
        return BaseResponse.success(purchasePlanVos);
    }

    @Override
    public BaseResponse updatePurchaseSku(PurchasePlanUpdateRequest request, Session session) {
        if (null == request.getId()
        || StringUtils.isBlank(request.getPurchaseSku())){
            return BaseResponse.failed();
        }
        PurchasePlan purchasePlan = selectByPrimaryKey(request.getId());
        if (null == purchasePlan
        || 0 != purchasePlan.getState()){
            return BaseResponse.failed();
        }
        if (purchasePlan.getPurchaseSku().equals(request.getPurchaseSku())){
            return  BaseResponse.success();
        }
        ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(request.getPurchaseSku());
        purchasePlan = new PurchasePlan();
        purchasePlan.setId(request.getId());
        purchasePlan.setPurchaseSku(productPurchaseInfo.getPurchaseSku());
        purchasePlan.setSupplierName(productPurchaseInfo.getSupplierName());
        purchasePlan.setPurchaseLink(productPurchaseInfo.getPurchaseLink());
        purchasePlan.setUpdateTime(new Date());
        updateByPrimaryKeySelective(purchasePlan);
        return BaseResponse.success();
    }

    @Override
    public int updatePriceById(Integer id, BigDecimal price) {
        return purchasePlanDao.updatePriceById(id, price);
    }

    @Override
    public int updateVariantStockByIds(List<Integer> ids) {
        if (ListUtils.isNotEmpty(ids)){
            return purchasePlanDao.updateVariantStockByIds(ids);
        }
        return 0;
    }

    @Override
    public int updatePurchaseOrderIdByIds(List<Integer> ids, Long purchaseOrderId) {
        return purchasePlanDao.updatePurchaseOrderIdByIds(ids,purchaseOrderId);
    }

    @Override
    public List<PurchasePlan> selectByIds(List<Integer> ids) {


        if (ListUtils.isNotEmpty(ids)){
            return purchasePlanDao.selectByIds(ids);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Long> selectPlaningVariantIds() {
        return purchasePlanDao.selectPlaningVariantIds();
    }

    @Override
    public BaseResponse addPurchasePlan(PurchasePlanAddRequest request, Session session) {

        String message = "";
        List<PurchasePlan> purchasePlans = request.getPurchasePlans();
        for (PurchasePlan purchasePlan : purchasePlans) {
            String result = addVariantToPurchasePlan(purchasePlan.getVariantId(), purchasePlan.getQuantity(),session.getId());
            if (!result.equals("success")){
                message = message + "  " + result;
            }
        }
        if (!message.equals("")){
            return BaseResponse.failed(message);
        }

        return BaseResponse.success();
    }

    private String addVariantToPurchasePlan(Long variantId,Integer quantity,Long operatorId){
        ProductVariant productVariant = productVariantService.selectByPrimaryKey(variantId);
        if (null == productVariant
                || null == productVariant.getPurchaseSku()){
            return "产品未配置采购信息";
        }
        if(null == productVariant.getVariantImage()){
            Product product = productService.selectByPrimaryKey(productVariant.getProductId());
            productVariant.setVariantImage(product.getProductImage());
        }
        ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(productVariant.getPurchaseSku());
        if (null == productPurchaseInfo){
            return productVariant.getBarcode() + " 采购信息异常";
        }
        PurchasePlan purchasePlan = purchasePlanDao.selectBySkuAndState(productVariant.getPurchaseSku(),0);
        if (null == purchasePlan){
            purchasePlan = new PurchasePlan();
            BeanUtils.copyProperties(productPurchaseInfo,purchasePlan);
            BeanUtils.copyProperties(productVariant,purchasePlan);
            purchasePlan.setState(0);
            purchasePlan.setPrice(productVariant.getVariantPrice());
            purchasePlan.setRequireQuantity(quantity);
            purchasePlan.setVariantId(variantId);
            purchasePlan.setQuantity(quantity);
            purchasePlan.setCreateTime(new Date());
            purchasePlan.setUpdateTime(new Date());
            purchasePlan.setOperatorId(operatorId);
            insert(purchasePlan);
        }else {
            purchasePlanDao.addQuantityById(purchasePlan.getId(), quantity);
        }
        redisTemplate.opsForHash().delete(RedisKey.HASH_PURCHASE_ADVICE_LIST, variantId.toString() );
        return "success";
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
    public List<PurchasePlan> select(PurchasePlanListRequest request){
        request.initFromNum();
        return purchasePlanDao.select(request);
    }

    /**
    *
    */
    public long count(Page<PurchasePlan> record){
        return purchasePlanDao.count(record);
    }

}
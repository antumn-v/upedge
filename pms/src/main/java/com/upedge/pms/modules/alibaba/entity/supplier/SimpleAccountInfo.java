package com.upedge.pms.modules.alibaba.entity.supplier;

import com.upedge.pms.modules.alibaba.vo.SupplierVo;

/**
 * Created by guoxing on 2020/6/9.
 */
public class SimpleAccountInfo {

    /**
     *登录名
     */
    String loginId;
    /**
     * 主营行业的类目名称
     */
    String categoryName;
    /**
     *公司名
     */
    String companyName;
    /**
     *旺铺首页地址
     */
    String shopUrl;
    /**
     * 供应商名称
     */
    String supplierName;

    /**
     *是否跨境宝
     */
    Boolean kuaJingBao;


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Boolean getKuaJingBao() {
        return kuaJingBao;
    }

    public void setKuaJingBao(Boolean kuaJingBao) {
        this.kuaJingBao = kuaJingBao;
    }

    public SupplierVo toSupplierVo() {
        SupplierVo supplierVo=new SupplierVo();
        supplierVo.setLoginId(loginId);
        supplierVo.setSupplierName(supplierName);
        supplierVo.setSupplierLink(shopUrl);
        supplierVo.setCompanyName(companyName);
        supplierVo.setCategoryName(categoryName);
        return supplierVo;
    }
}

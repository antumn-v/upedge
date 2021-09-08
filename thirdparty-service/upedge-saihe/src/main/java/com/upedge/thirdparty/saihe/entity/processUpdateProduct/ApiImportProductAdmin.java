package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/3/29.
 */
public class ApiImportProductAdmin {

    private String AssignDevelopAdminName;//负责人员
    private String EditAdminName;//编辑人员
    private String ImageAdminName;//图片处理人员
    private String DevelopAdminName;//开发人员

    String ToProcurementCheckMemo;//质检备注
    String ToDeliveryPackNoteMemo;//发货打包备注

    @XmlElement(name="ToProcurementCheckMemo")
    public String getToProcurementCheckMemo() {
        return ToProcurementCheckMemo;
    }

    public void setToProcurementCheckMemo(String toProcurementCheckMemo) {
        ToProcurementCheckMemo = toProcurementCheckMemo;
    }

    @XmlElement(name="ToDeliveryPackNoteMemo")
    public String getToDeliveryPackNoteMemo() {
        return ToDeliveryPackNoteMemo;
    }

    public void setToDeliveryPackNoteMemo(String toDeliveryPackNoteMemo) {
        ToDeliveryPackNoteMemo = toDeliveryPackNoteMemo;
    }

    @XmlElement(name="AssignDevelopAdminName")
    public String getAssignDevelopAdminName() {
        return AssignDevelopAdminName;
    }

    public void setAssignDevelopAdminName(String assignDevelopAdminName) {
        AssignDevelopAdminName = assignDevelopAdminName;
    }

    @XmlElement(name="EditAdminName")
    public String getEditAdminName() {
        return EditAdminName;
    }

    public void setEditAdminName(String editAdminName) {
        EditAdminName = editAdminName;
    }

    @XmlElement(name="ImageAdminName")
    public String getImageAdminName() {
        return ImageAdminName;
    }

    public void setImageAdminName(String imageAdminName) {
        ImageAdminName = imageAdminName;
    }

    @XmlElement(name="DevelopAdminName")
    public String getDevelopAdminName() {
        return DevelopAdminName;
    }

    public void setDevelopAdminName(String developAdminName) {
        DevelopAdminName = developAdminName;
    }
}

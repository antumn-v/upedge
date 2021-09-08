package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Created by cjq on 2019/3/29.
 */
public class ApiImportProductDeclaration {

    String DeclarationName;//产品报关英文名
    String DeclarationNameCN;//产品报关中文名
    BigDecimal DeclarationPriceRate;//报关价
    String DeclarationUnit;//报关单位

    @XmlElement(name="DeclarationName")
    public String getDeclarationName() {
        return DeclarationName;
    }

    public void setDeclarationName(String declarationName) {
        DeclarationName = declarationName;
    }

    @XmlElement(name="DeclarationNameCN")
    public String getDeclarationNameCN() {
        return DeclarationNameCN;
    }

    public void setDeclarationNameCN(String declarationNameCN) {
        DeclarationNameCN = declarationNameCN;
    }

    @XmlElement(name="DeclarationPriceRate")
    public BigDecimal getDeclarationPriceRate() {
        return DeclarationPriceRate;
    }

    public void setDeclarationPriceRate(BigDecimal declarationPriceRate) {
        DeclarationPriceRate = declarationPriceRate;
    }

    @XmlElement(name="DeclarationUnit")
    public String getDeclarationUnit() {
        return DeclarationUnit;
    }

    public void setDeclarationUnit(String declarationUnit) {
        DeclarationUnit = declarationUnit;
    }
}

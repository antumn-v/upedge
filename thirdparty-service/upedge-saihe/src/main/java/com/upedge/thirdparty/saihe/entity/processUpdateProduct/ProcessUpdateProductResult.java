package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;


@Data
public class ProcessUpdateProductResult {


    @XmlElement(name="SkuUpdateList")
    private SkuUpdateListDTO skuUpdateList;

    @XmlElement(name="skuAddList")
    private SkuUpdateListDTO skuAddList;

    public ProcessUpdateProductResult() {
    }
    public ProcessUpdateProductResult(String status) {
        Status = status;
    }


    String Status;
    Presult presult=new Presult();



    @XmlElement(name="Result")
    public Presult getPresult() {
        return presult;
    }

    public void setPresult(Presult presult) {
        this.presult = presult;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @NoArgsConstructor
    @Data
    public static class SkuUpdateListDTO {
        @XmlElement(name = "SkuResult")
        private List<SkuResultDTO> skuResult;
    }

    @NoArgsConstructor
    @Data
    public static class SkuResultDTO {
        @XmlElement(name ="Sku")
        private String sku;
        @XmlElement(name ="ClientSku")
        private String clientSku;
    }
}

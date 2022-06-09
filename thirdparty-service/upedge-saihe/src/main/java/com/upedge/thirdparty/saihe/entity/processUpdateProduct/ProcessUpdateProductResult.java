package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cjq on 2019/4/4.
 */
public class ProcessUpdateProductResult {


    public ProcessUpdateProductResult() {
    }
    public ProcessUpdateProductResult(String status) {
        Status = status;
    }


    String Status;
    Presult presult=new Presult();

    SkuAddList SkuAddList;
    SkuUpdateList SkuUpdateList;

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

    @XmlElement(name="SkuAddList")
    public SkuAddList getSkuAddList() {
        return SkuAddList;
    }

    public void setSkuAddList(ProcessUpdateProductResult.SkuAddList skuAddList) {
        SkuAddList = skuAddList;
    }

    @XmlElement(name="SkuUpdateList")
    public SkuUpdateList getSkuUpdateList() {
        return SkuUpdateList;
    }

    public void setSkuUpdateList(ProcessUpdateProductResult.SkuUpdateList skuUpdateList) {
        SkuUpdateList = skuUpdateList;
    }

    public static class SkuUpdateList{
        List<SkuResult> SkuResult;

        @XmlElement(name="SkuResult")
        public List<SkuResult> getSkuResult() {
            return SkuResult;
        }

        public void setSkuResult(List<SkuResult> skuResult) {
            SkuResult = skuResult;
        }
    }

    public static class SkuAddList{
        List<SkuResult> SkuResult;

        @XmlElement(name="SkuResult")
        public List<SkuResult> getSkuResult() {
            return SkuResult;
        }

        public void setSkuResult(List<SkuResult> skuResult) {
            SkuResult = skuResult;
        }
    }
}

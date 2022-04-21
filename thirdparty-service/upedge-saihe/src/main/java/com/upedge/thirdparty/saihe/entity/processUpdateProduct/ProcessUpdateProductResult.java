package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

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

}

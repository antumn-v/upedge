package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.PayMethod;
import com.upedge.ums.modules.account.entity.PayMethodAttr;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class PayMethodAddUpdateRequest {

    @NotNull
    private Integer routeType;

    @NotNull
    private String routeName;

    List<Attr> attrs;


    @Data
    public static class Attr{

        String attrName;

        String attrKey;

        Integer needed;

        Integer obtainType;

        String remarks;

        public Attr() {
        }
    }

    public PayMethod toPayMethod(){
        PayMethod payMethod = new PayMethod();
        payMethod.setRouteType(routeType);
        payMethod.setRouteName(routeName);
        payMethod.setStatus(1);
        return payMethod;
    }

    public List<PayMethodAttr> toPayMethodAttrs(Integer payMethodId){
        List<PayMethodAttr> payMethodAttrs = new ArrayList<>();
        for (Attr attr: attrs) {
            PayMethodAttr payMethodAttr = new PayMethodAttr();

            payMethodAttr.setAttrKey(attr.attrKey);
            payMethodAttr.setAttrName(attr.attrName);
            payMethodAttr.setObtainType(attr.obtainType);
            payMethodAttr.setNeeded(attr.needed);
            payMethodAttr.setPaymethodId(payMethodId);
            payMethodAttr.setRemarks(attr.remarks);
            payMethodAttrs.add(payMethodAttr);
        }
        return payMethodAttrs;
    }

}

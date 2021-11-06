package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerInfo;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ManagerInfoAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 赛盒来源渠道id
    */
    private Integer orderSourceId;
    /**
    * 赛盒来源渠道名称
    */
    private String orderSourceName;
    /**
    * 
    */
    private String managerCode;
    /**
    *
    */
    private String managerName;
    /**
    * 0=客户经理，1=助理
    */
    private Integer managerType;
    /**
    * 1=正常 0=停用
    */
    private Integer managerState;
    /**
    * 助理所属的客户经理ID
    */
    private String assistantSupeior;

    public ManagerInfo toManagerInfo(){
        ManagerInfo managerInfo=new ManagerInfo();
        managerInfo.setCustomerId(customerId);
        managerInfo.setOrderSourceId(orderSourceId);
        managerInfo.setOrderSourceName(orderSourceName);
        managerInfo.setManagerCode(managerCode);
        managerInfo.setManagerName(managerName);
        managerInfo.setManagerType(managerType);
        managerInfo.setManagerState(managerState);
        managerInfo.setAssistantSupeior(assistantSupeior);
        return managerInfo;
    }

}

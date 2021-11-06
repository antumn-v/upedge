package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerInfo;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ManagerInfoUpdateRequest{

    /**
     * id
     */
    private Long id;

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

    private String skype;
    private String whatsapp;
    private String wechat;
    private String facebook;

    public ManagerInfo toManagerInfo(Long id){
        ManagerInfo managerInfo=new ManagerInfo();
        managerInfo.setId(id);
        managerInfo.setOrderSourceId(orderSourceId);
        managerInfo.setOrderSourceName(orderSourceName);
        managerInfo.setManagerName(managerName);
        managerInfo.setManagerType(managerType);
        managerInfo.setManagerState(managerState);
        if(null != managerType && 0 == managerType){
            managerInfo.setAssistantSupeior("0");
        }else {
            managerInfo.setAssistantSupeior(assistantSupeior);
        }
        return managerInfo;
    }

}

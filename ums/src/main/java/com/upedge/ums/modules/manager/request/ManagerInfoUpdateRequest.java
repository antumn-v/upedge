package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerInfo;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class ManagerInfoUpdateRequest{

    /**
     * 
     */
    private Long userId;
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
     * 0=客户经理，1=助理
     */
    private Integer managerType;
    /**
     * 1=正常 0=停用,2=删除
     */
    private Integer managerState;
    /**
     * 客户经理英文名
     */
    private String managerName;
    /**
     * 助理所属的客户经理代码
     */
    private String assistantSuperior;
    /**
     * 
     */
    private Date createTime;
    /**
     * 创建者
     */
    private Long creatorId;
    /**
     * 邀请注册码
     */
    private String inviteCode;

    public ManagerInfo toManagerInfo(Long id){
        ManagerInfo managerInfo=new ManagerInfo();
        managerInfo.setId(id);
        managerInfo.setUserId(userId);
        managerInfo.setOrderSourceId(orderSourceId);
        managerInfo.setOrderSourceName(orderSourceName);
        managerInfo.setManagerCode(managerCode);
        managerInfo.setManagerType(managerType);
        managerInfo.setManagerState(managerState);
        managerInfo.setManagerName(managerName);
        managerInfo.setAssistantSuperior(assistantSuperior);
        managerInfo.setCreateTime(createTime);
        managerInfo.setCreatorId(creatorId);
        managerInfo.setInviteCode(inviteCode);
        return managerInfo;
    }

}

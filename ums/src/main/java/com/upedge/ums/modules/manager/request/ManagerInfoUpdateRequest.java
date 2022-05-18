package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.user.entity.User;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class ManagerInfoUpdateRequest{


    private Long id;
    /**
     * 客户经理英文名
     */
    private String managerName;
    /**
     * 邀请注册码
     */
    private String inviteCode;

    private String loginPass;

    private String avatar;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;

    private BigDecimal perCommission;

    public ManagerInfo toManagerInfo(){
        ManagerInfo managerInfo=new ManagerInfo();
        managerInfo.setId(id);
        managerInfo.setManagerName(managerName);
        managerInfo.setInviteCode(inviteCode);
        managerInfo.setPerCommission(perCommission);
        return managerInfo;
    }

    public User toUser(Long userId){
        User user = new User();
        user.setId(userId);
        user.setUsername(managerName);
        user.setAvatar(avatar);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setWhatsapp(whatsapp);
        user.setWechat(wechat);
        user.setFbInfo(fbInfo);
        user.setSkype(skype);
        return user;
    }

}

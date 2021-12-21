package com.upedge.cms.modules.notice.request;

import com.upedge.cms.modules.notice.entity.AppNotice;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AppNoticeUpdateRequest{

    @NotNull
    private Long id;
    /**
     * 类型
     */
    @NotBlank
    private String type;
    /**
     * 标题
     */
    @NotBlank
    private String title;
    /**
     * 内容
     */
    @NotBlank
    private String content;
    /**
     * 操作人
     */
    private String adminUserId;

    public AppNotice toAppNotice(Long id,String adminUserId){
        AppNotice appNotice=new AppNotice();
        appNotice.setId(id);
        appNotice.setType(type);
        appNotice.setTitle(title);
        appNotice.setContent(content);
        appNotice.setUpdateTime(new Date());
        appNotice.setAdminUserId(adminUserId);
        return appNotice;
    }

}

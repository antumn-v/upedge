package com.upedge.cms.modules.notice.request;

import com.upedge.cms.modules.notice.entity.AppNotice;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AppNoticeAddRequest{

    /**
    * 类型
    */
    @NotBlank(message = "公告类型为空")
    private String type;
    /**
    * 标题
    */
    @NotBlank(message = "公告标题为空")
    private String title;
    /**
    * 内容
    */
    @NotBlank(message = "公告内容为空")
    private String content;

    public AppNotice toAppNotice(Long operatorId){
        AppNotice appNotice=new AppNotice();
        appNotice.setType(type);
        appNotice.setTitle(title);
        appNotice.setContent(content);
        appNotice.setUpdateTime(new Date());
        appNotice.setCreateTime(new Date());
        appNotice.setViewCount(0);
        appNotice.setState(0);
        appNotice.setOperatorId(operatorId);
        return appNotice;
    }

}

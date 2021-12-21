package com.upedge.cms.modules.website.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class WebsiteMessageAllocateRequest {
    @NotNull
    @Size(min = 1)
    private List<Long> ids;
    /**
     *
     */
    @NotBlank
    private String adminUser;


}

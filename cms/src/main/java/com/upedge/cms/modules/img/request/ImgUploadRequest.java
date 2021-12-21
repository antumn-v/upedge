package com.upedge.cms.modules.img.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class ImgUploadRequest {

    @NotNull
    MultipartFile file;

    @NotNull
    Integer type;

}

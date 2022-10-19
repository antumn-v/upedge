package com.upedge.pms.modules.image.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.image.service.ImageUploadRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "图片上传")
@RestController
@RequestMapping("/imageUpload")
public class ImageUploadRecordController {
    @Autowired
    private ImageUploadRecordService imageUploadRecordService;

    @ApiOperation("文件上传")
    @PostMapping("/file")
    public BaseResponse uploadFile(@RequestBody MultipartFile file){
        return imageUploadRecordService.uploadImageByFile(file);
    }


}

package com.upedge.cms.modules.img.controller;


import com.upedge.cms.modules.img.request.ImgUploadRequest;
import com.upedge.cms.modules.img.service.ImgService;
import com.upedge.common.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "图片管理")
@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    ImgService imgService;

    /**
     * 上传图片
     * @return
     */
    @ApiOperation("图片上传")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResponse uploadImg(@RequestParam("file")MultipartFile file) {
        ImgUploadRequest request=new ImgUploadRequest();
        request.setFile(file);
        return imgService.uploadImg(request);
    }

}

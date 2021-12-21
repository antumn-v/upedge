package com.upedge.cms.modules.img.controller;


import com.upedge.cms.modules.img.request.ImgUploadRequest;
import com.upedge.cms.modules.img.service.ImgService;
import com.upedge.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    ImgService imgService;

    /**
     * 上传图片
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResponse uploadImg(@RequestParam("file")MultipartFile file,
                                  @RequestParam("type") Integer type) {
        ImgUploadRequest request=new ImgUploadRequest();
        request.setFile(file);
        request.setType(type);
        return imgService.uploadImg(request);
    }

}

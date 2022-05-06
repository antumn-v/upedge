package com.upedge.cms.modules.img.service;

import com.upedge.cms.modules.img.request.ImgUploadRequest;
import com.upedge.cms.modules.img.response.UploadImgResponse;

public interface ImgService {

    UploadImgResponse uploadImg(ImgUploadRequest request);

}

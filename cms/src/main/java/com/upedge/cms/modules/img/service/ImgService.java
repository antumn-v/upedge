package com.upedge.cms.modules.img.service;

import com.upedge.cms.modules.img.request.ImgUploadRequest;
import com.upedge.common.base.BaseResponse;

public interface ImgService {

    BaseResponse uploadImg(ImgUploadRequest request);

}

package com.upedge.cms.modules.img.service.impl;

import com.upedge.cms.modules.img.request.ImgUploadRequest;
import com.upedge.cms.modules.img.service.ImgService;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImgServiceImpl implements ImgService {

    @Value("${files.image.local}")
    private String localPath;
    @Value("${files.image.prefix}")
    private String imageUrlPrefix;

    @Override
    public BaseResponse uploadImg(ImgUploadRequest request) {
        MultipartFile file=request.getFile();
        //设置图片为唯一的id
        String originalFilename=file.getOriginalFilename();
        String pictureName = IdGenerate.nextId()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //获取上传路径
//        ImgConstant.ImgType imgType=ImgConstant.getImgType(request.getType());
//        if(imgType==null){
//            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
//        }
//        String fileSavePath =imgType.getFileSavePath();
//        String prefix=imgType.getImgUrlPrefix();
        // UploadImgUtil.getMd5()
        try {
            file.transferTo(new File(localPath + pictureName));
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,imageUrlPrefix+pictureName);
    }
}

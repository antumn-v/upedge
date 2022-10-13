package com.upedge.pms.modules.image.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.image.request.ImageUploadRecordAddRequest;
/**
 * @author gx
 */
public class ImageUploadRecordAddResponse extends BaseResponse {
    public ImageUploadRecordAddResponse(int code, String msg, Object data, ImageUploadRecordAddRequest req) {
        super(code,msg,data,req);
    }
}

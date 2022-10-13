package com.upedge.pms.modules.image.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.image.request.ImageUploadRecordListRequest;
/**
 * @author gx
 */
public class ImageUploadRecordListResponse extends BaseResponse {
    public ImageUploadRecordListResponse(int code, String msg, Object data,ImageUploadRecordListRequest req) {
        super(code,msg,data,req);
    }
}

package com.upedge.cms.modules.img.response;

import com.upedge.common.base.BaseResponse;
import lombok.Data;

@Data
public class UploadImgResponse extends BaseResponse {

    private Integer errno;

    public UploadImgResponse(int code, String msg, Object data, Integer errno) {
        super(code, msg, data);
        this.errno = errno;
    }

    public UploadImgResponse(String msg, Integer errno) {
        super(msg);
        this.errno = errno;
    }
}

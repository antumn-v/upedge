package com.upedge.ums.modules.account.request;

import com.upedge.common.config.HostConfig;
import com.upedge.common.utils.FileUtil;
import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import lombok.Data;

/**
 * 银行转账充值申请
 * @author 海桐
 */
@Data
public class TransferRechargeRequest extends ApplyRechargeRequest {

    private TransferRechargeAttr attr;

    @Data
    public class TransferRechargeAttr{
        private String image;

        public RechargeRequestAttr toTransferRequestAttrs(Long requestId){

      //      image = FileUtil.uploadImage(image,"http://192.168.0.80:8686/image/","D:/image/ticket/");
      //      image = FileUtil.uploadImage(image,"http://192.168.0.80:8686/image/","D:/image/ticket/");
            image = FileUtil.uploadImage(image, HostConfig.HOST +"/ums/image/transfer/","/root/files/image/transfer/");

            RechargeRequestAttr requestAttr = new RechargeRequestAttr();
            requestAttr.setAttrName("image");
            requestAttr.setAttrValue(image);
            requestAttr.setRechargeRequestId(requestId);

            return requestAttr;
        }

    }





}

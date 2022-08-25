package com.upedge.thirdparty.shipcompany.cne.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.Base64;

@Data
public class CneRequestBase {

    @JSONField(name = "RequestName")
    private String requestName;
    @JSONField(name = "icID")
    private String icID = "8604585";

    private String apiToken = "5b24fc34322f924731c669bb77c11e7b";
    @JSONField(name = "TimeStamp")
    private Long TimeStamp = System.currentTimeMillis();
    @JSONField(name = "MD5")
//    private String MD5 = messageDigest(icID + TimeStamp + apiToken, "MD5").toLowerCase();
    private String MD5 = DigestUtils.md5Hex(icID + TimeStamp + apiToken).toLowerCase();
//    private String MD5 = messageDigest(icID + TimeStamp + apiToken, "MD5").toLowerCase();


    private String messageDigest(String res, String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = res.getBytes("utf-8");
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String base64(byte[] res){
        return Base64.getEncoder().encodeToString(res);
    }

}

package com.upedge.thirdparty.ali1688.entity.translate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjq on 2018/12/28.
 */
public class TranslateResult {

    Map<String,String> result=new HashMap<>();

    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }
}

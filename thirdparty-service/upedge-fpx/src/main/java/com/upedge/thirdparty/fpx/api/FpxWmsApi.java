package com.upedge.thirdparty.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.fpx.vo.FpxApiResultVo;
import com.upedge.thirdparty.fpx.vo.FpxInbound;
import com.upedge.thirdparty.fpx.vo.FpxMeasureUnit;
import com.upedge.thirdparty.fpx.vo.FpxSku;

import java.util.List;

import static com.upedge.thirdparty.fpx.config.FpxConfig.param;

/**
 * 订单履约api
 */
public class FpxWmsApi {


    public static FpxSku createSku(FpxSku fpxSku) throws Exception {
        param.setMethod(MethodEnum.create_sku.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxSku));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            jsonObject = JSONObject.parseObject(JSON.toJSONString(resultVo.getData()));
            fpxSku = jsonObject.toJavaObject(FpxSku.class);
            return fpxSku;
        }
        throw new Exception(resultVo.getMsg());
    }

    /**
     * 创建入库委托
     * @param fpxInbound
     * @return 委托单号
     */
    public static String createInbound(FpxInbound fpxInbound){
        param.setMethod(MethodEnum.create_inbound.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxInbound));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            jsonObject = JSONObject.parseObject(JSON.toJSONString(resultVo.getData()));
            return jsonObject.getString("consignment_no");
        }
        return null;
    }


}

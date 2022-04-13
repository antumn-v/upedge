package com.upedge.thirdparty.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.fpx.vo.FpxApiResultVo;
import com.upedge.thirdparty.fpx.vo.FpxInbound;
import com.upedge.thirdparty.fpx.vo.FpxSku;

import static com.upedge.thirdparty.fpx.config.FpxConfig.param;

/**
 * 订单履约api
 */
public class FpxWmsApi {


    public static boolean createSku(FpxSku fpxSku){
        param.setMethod(MethodEnum.create_sku.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxSku));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return false;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            return true;
        }
        return false;
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

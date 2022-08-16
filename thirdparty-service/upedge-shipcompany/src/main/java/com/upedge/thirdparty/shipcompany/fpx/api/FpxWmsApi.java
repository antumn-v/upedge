package com.upedge.thirdparty.shipcompany.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import com.upedge.thirdparty.shipcompany.fpx.request.CreateFpxInboundRequest;
import com.upedge.thirdparty.shipcompany.fpx.request.FpxInboundListRequest;
import com.upedge.thirdparty.shipcompany.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.shipcompany.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.shipcompany.fpx.constants.MethodEnum;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxApiResultVo;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxInbound;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxSku;

/**
 * 订单履约api
 */
public class FpxWmsApi {


    public static FpxSku createSku(FpxSku fpxSku) throws Exception {
        FpxConfig.param.setMethod(MethodEnum.create_sku.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxSku));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
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
     * 查询入库委托
     */
    public static FpxInbound selectInboundByOrderId(Long overseaWarehouseOrderId){
        FpxInboundListRequest request = new FpxInboundListRequest();
        request.setRef_no(overseaWarehouseOrderId.toString());
        request.setCreate_time_start(1587368482150L);
        request.setCreate_time_end(4080352109730l);
        FpxConfig.param.setMethod(MethodEnum.select_inbound.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            jsonObject = JSONObject.parseObject(result);
            jsonObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray == null
            || jsonArray.size() == 0){
                return null;
            }
            jsonObject = JSONObject.parseObject(JSON.toJSONString(jsonArray.get(0)));
            return jsonObject.toJavaObject(FpxInbound.class);
        }
        return null;
    }

    /**
     * 创建入库委托
     * @param fpxInbound
     * @return 委托单号
     */
    public static String createInbound(CreateFpxInboundRequest request){
        FpxConfig.param.setMethod(MethodEnum.create_inbound.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
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

    public static void main(String[] args) {
//        Date date = DateUtils.setYears(new Date(),2020);
//        System.out.println(date.getTime());
        System.out.println(selectInboundByOrderId(1516620665144221696L));

    }


}

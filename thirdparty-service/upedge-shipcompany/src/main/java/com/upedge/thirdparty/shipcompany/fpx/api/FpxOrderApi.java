package com.upedge.thirdparty.shipcompany.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import com.upedge.thirdparty.shipcompany.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.shipcompany.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.shipcompany.fpx.constants.MethodEnum;
import com.upedge.thirdparty.shipcompany.fpx.dto.FpxOrderCreateDto;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxCreateOrderSuccessVo;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxOrderErrorDTO;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxPrintLabelVo;

import java.util.List;

public class FpxOrderApi {


    public static FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO createFpxOrder(FpxOrderCreateDto fpxOrderCreateDto) throws Exception {
        FpxConfig.param.setMethod(MethodEnum.order_create.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxOrderCreateDto));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);
        if (null == result){
            return null;
        }
        FpxCreateOrderSuccessVo fpxCreateOrderSuccessVo = JSONObject.parseObject(result,FpxCreateOrderSuccessVo.class);
        if (fpxCreateOrderSuccessVo.getResult().equals("1")){
            return fpxCreateOrderSuccessVo.getData();
        }

        List<FpxOrderErrorDTO> errors = fpxCreateOrderSuccessVo.getErrors();
        String msg = "";
        for (FpxOrderErrorDTO error : errors) {
            msg = error.getErrorMsg() + " ";
        }
        throw new Exception(msg);
    }

    public static String getSinglePackageLabel(String fpxNo) throws Exception {
        FpxConfig.param.setMethod(MethodEnum.single_package_label.getMethod());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request_no",fpxNo);

        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);

        FpxPrintLabelVo fpxPrintLabelVo = JSONObject.parseObject(result,FpxPrintLabelVo.class);
        if (fpxPrintLabelVo.getResult().equals("1")){
            return fpxPrintLabelVo.getData().getLabelUrlInfo().getLogisticsLabel();
        }else {
            List<FpxOrderErrorDTO> errorDTOS = fpxPrintLabelVo.getErrors();

            String msg = "";
            for (FpxOrderErrorDTO error : errorDTOS) {
                msg = error.getErrorMsg() + " ";
            }
            throw new Exception(msg);
        }
    }

    public static String getMultiPackageLabel(List<String> fpxNo,String methodCode) {
        FpxConfig.param.setMethod(MethodEnum.multi_package_label.getMethod());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request_no",fpxNo);
        jsonObject.put("logistics_product_code",methodCode);

        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);

        jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("result") == 1){
            return jsonObject.getString("data");
        }
        return "failed";
    }


}

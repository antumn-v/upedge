package com.upedge.thirdparty.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.fpx.vo.FpxCreateOrderSuccessVo;
import com.upedge.thirdparty.fpx.vo.FpxOrderErrorDTO;
import com.upedge.thirdparty.fpx.vo.FpxPrintLabelVo;

import java.util.List;

import static com.upedge.thirdparty.fpx.config.FpxConfig.param;

public class FpxOrderApi {


    public static FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO createFpxOrder(FpxOrderCreateDto fpxOrderCreateDto) throws Exception {
        param.setMethod(MethodEnum.order_create.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fpxOrderCreateDto));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);
        if (null == result){
            return null;
        }
        FpxCreateOrderSuccessVo fpxCreateOrderSuccessVo = jsonObject.toJavaObject(FpxCreateOrderSuccessVo.class);
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
        param.setMethod(MethodEnum.single_package_label.getMethod());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request_no",fpxNo);

        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);

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
        param.setMethod(MethodEnum.multi_package_label.getMethod());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request_no",fpxNo);
        jsonObject.put("logistics_product_code",methodCode);

        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.SANDBOX_ADDRESS);

        jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("result") == 1){
            return jsonObject.getString("data");
        }
        return "failed";
    }


}

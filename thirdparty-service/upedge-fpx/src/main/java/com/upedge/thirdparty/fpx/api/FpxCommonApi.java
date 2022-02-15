package com.upedge.thirdparty.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.config.FpxConfig;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.fpx.dto.ShipPriceCalculator;
import com.upedge.thirdparty.fpx.model.AffterentParam;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.fpx.vo.PriceCalculatorResultVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FpxCommonApi {


    public static List<PriceCalculatorDTO> priceCalculator(ShipPriceCalculator priceCalculator){
        AffterentParam param = new AffterentParam();
        param.setAppKey(FpxConfig.API_KEY);
        param.setAppSecret(FpxConfig.API_SECRET);
        param.setVersion(FpxConfig.VERSION);
        param.setMethod(MethodEnum.price_calculator.getMethod());
        param.setFormat("json");
        param.setLanguage("cn");
        param.setAccessToken("");

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(priceCalculator));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        PriceCalculatorResultVo resultVo = JSONObject.parseObject(result,PriceCalculatorResultVo.class);
        if (resultVo.getResult().equals("1")){
            return resultVo.getData();
        }

        return null;
    }
}

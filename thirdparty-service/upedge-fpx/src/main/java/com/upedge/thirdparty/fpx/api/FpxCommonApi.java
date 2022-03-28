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


import java.util.ArrayList;
import java.util.List;

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


    public static void main(String[] args) {
        ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
        destinationDTO.setCountry("US");

        List<String> methodCodes = new ArrayList<>();
        methodCodes.add("F129");

        ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
        priceCalculator.setHeight("1");
        priceCalculator.setLength("1");
        priceCalculator.setWidth("1");
        priceCalculator.setWeight("100");
        priceCalculator.setService_code("FB4");
        priceCalculator.setProduct_codes(methodCodes);
        priceCalculator.setWarehouse_code("USLAXA");
        priceCalculator.setBilling_time(System.currentTimeMillis());
        priceCalculator.setDestination(destinationDTO);

        List<PriceCalculatorDTO> priceCalculatorDTOS = FpxCommonApi.priceCalculator(priceCalculator);

        System.out.println(priceCalculatorDTOS);

    }
}

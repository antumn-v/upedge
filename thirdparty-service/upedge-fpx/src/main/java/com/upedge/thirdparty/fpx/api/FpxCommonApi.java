package com.upedge.thirdparty.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.config.FpxConfig;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.fpx.dto.ShipPriceCalculator;
import com.upedge.thirdparty.fpx.model.AffterentParam;
import com.upedge.thirdparty.fpx.request.FpxWarehouseMethodListRequest;
import com.upedge.thirdparty.fpx.request.GetFpxWarehouseRequest;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.fpx.vo.FpxApiResultVo;
import com.upedge.thirdparty.fpx.vo.FpxMethodVo;
import com.upedge.thirdparty.fpx.vo.FpxWarehouseVo;

import java.util.List;

public class FpxCommonApi {

    static AffterentParam param = new AffterentParam();

    static {
        param.setAppKey(FpxConfig.API_KEY);
        param.setAppSecret(FpxConfig.API_SECRET);
        param.setVersion(FpxConfig.VERSION);
        param.setFormat("json");
        param.setLanguage("cn");
        param.setAccessToken("");
    }


    /**
     * 运费试算
     * @param priceCalculator
     * @return
     */
    public static List<PriceCalculatorDTO> priceCalculator(ShipPriceCalculator priceCalculator){
        param.setMethod(MethodEnum.price_calculator.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(priceCalculator));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            return (List<PriceCalculatorDTO>) resultVo.getData();
        }

        return null;
    }

    /**
     * 查询仓库信息
     * @param request
     * @return
     */
    public static List<FpxWarehouseVo> getFpxWarehouse(GetFpxWarehouseRequest request){
        param.setMethod(MethodEnum.get_warehouse.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            return (List<FpxWarehouseVo>) resultVo.getData();
        }
        return null;
    }

    /**
     * 查询FPX运输方式
     * @param request
     * @return
     */
    public static List<FpxMethodVo> getFpxMethods(FpxWarehouseMethodListRequest request){
        param.setMethod(MethodEnum.get_methods.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            return (List<FpxMethodVo>) resultVo.getData();
        }
        return null;
    }




    public static void main(String[] args) {
//        ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
//        destinationDTO.setCountry("US");

//        List<String> methodCodes = new ArrayList<>();
//        methodCodes.add("F129");

//        ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
//        priceCalculator.setHeight("1");
//        priceCalculator.setLength("1");
//        priceCalculator.setWidth("1");
//        priceCalculator.setWeight("100");
//        priceCalculator.setService_code("FB4");
//        priceCalculator.setProduct_codes(methodCodes);
//        priceCalculator.setWarehouse_code("USLAXA");
//        priceCalculator.setBilling_time(System.currentTimeMillis());
//        priceCalculator.setDestination(destinationDTO);
//
//        List<PriceCalculatorDTO> priceCalculatorDTOS = FpxCommonApi.priceCalculator(priceCalculator);
//
//        System.out.println(priceCalculatorDTOS);

//        GetFpxWarehouseRequest getFpxWarehouseRequest = new GetFpxWarehouseRequest();
//        getFpxWarehouseRequest.setCountry("US");
//        getFpxWarehouseRequest.setServiceCode("F");
//        System.out.println(getFpxWarehouse(getFpxWarehouseRequest));

        FpxWarehouseMethodListRequest fpxWarehouseMethodListRequest = new FpxWarehouseMethodListRequest();
        fpxWarehouseMethodListRequest.setSourcePositionCode("USLAXA");
        fpxWarehouseMethodListRequest.setCategoryCode("end");
        fpxWarehouseMethodListRequest.setServiceCode("F");
        fpxWarehouseMethodListRequest.setDestCountryCode("US");
        System.out.println(getFpxMethods(fpxWarehouseMethodListRequest));

    }
}

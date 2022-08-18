package com.upedge.thirdparty.shipcompany.fpx.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import com.upedge.thirdparty.shipcompany.fpx.utils.ApiHttpClientUtils;
import com.upedge.thirdparty.shipcompany.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.shipcompany.fpx.constants.MethodEnum;
import com.upedge.thirdparty.shipcompany.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.shipcompany.fpx.dto.ShipPriceCalculator;
import com.upedge.thirdparty.shipcompany.fpx.request.FpxWarehouseMethodListRequest;
import com.upedge.thirdparty.shipcompany.fpx.request.GetFpxWarehouseRequest;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxApiResultVo;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxMeasureUnit;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxMethodVo;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxWarehouseVo;

import java.util.List;

public class FpxCommonApi {




    /**
     * 运费试算
     * @param priceCalculator
     * @return
     */
    public static List<PriceCalculatorDTO> priceCalculator(ShipPriceCalculator priceCalculator){
        FpxConfig.param.setMethod(MethodEnum.price_calculator.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(priceCalculator));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            jsonObject = JSON.parseObject(JSON.toJSONString(resultVo));
            List<PriceCalculatorDTO> priceCalculatorDTOS = jsonObject.getJSONArray("data").toJavaList(PriceCalculatorDTO.class);
            return priceCalculatorDTOS;
        }

        return null;
    }

    /**
     * 查询仓库信息
     * @param request
     * @return
     */
    public static List<FpxWarehouseVo> getFpxWarehouse(GetFpxWarehouseRequest request){
        FpxConfig.param.setMethod(MethodEnum.get_warehouse.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
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
        FpxConfig.param.setMethod(MethodEnum.get_methods.getMethod());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            return (List<FpxMethodVo>) resultVo.getData();
        }
        return null;
    }

    public static List<FpxMethodVo> getTransportMethods(Integer transportMode){
        FpxConfig.param.setMethod(MethodEnum.get_xms_methods.getMethod());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transport_mode",transportMode);
        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,jsonObject, AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            List<FpxMethodVo> fpxMethodVos = JSONArray.parseArray(JSON.toJSONString(resultVo.getData()),FpxMethodVo.class);
            return fpxMethodVos;
        }
        return null;
    }

    /**
     * 选择计量单位
     */
    public static List<FpxMeasureUnit> selectMeasureUnit(){
        FpxConfig.param.setMethod(MethodEnum.measure_unit_list.getMethod());

        String result = ApiHttpClientUtils.apiJsongPost(FpxConfig.param,new JSONObject(), AmbientEnum.FORMAT_ADDRESS);
        if (null == result){
            return null;
        }
        FpxApiResultVo resultVo = JSONObject.parseObject(result,FpxApiResultVo.class);
        if (resultVo.getResult().equals("1")){
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(resultVo.getData()));
            return jsonArray.toJavaList(FpxMeasureUnit.class);
        }
        return null;
    }




    public static void main(String[] args) {

        FpxWarehouseMethodListRequest fpxWarehouseMethodListRequest = new FpxWarehouseMethodListRequest();
        fpxWarehouseMethodListRequest.setSourcePositionCode("USLAXA");
        fpxWarehouseMethodListRequest.setCategoryCode("end");
        fpxWarehouseMethodListRequest.setServiceCode("F");
        List<FpxMethodVo> fpxMethodVos = FpxCommonApi.getFpxMethods(fpxWarehouseMethodListRequest);
        System.out.println(fpxMethodVos);

//        List<String> methodCodes = new ArrayList<>();
//        methodCodes.add("F129");
//
//        ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
//        destinationDTO.setCountry("DE");
//        destinationDTO.setPost_code("67808");
//        ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
//        priceCalculator.setHeight("1");
//        priceCalculator.setLength("1");
//        priceCalculator.setWidth("1");
//        priceCalculator.setWeight("400");
//        priceCalculator.setService_code("FB4");
//        priceCalculator.setProduct_codes(methodCodes);
//        priceCalculator.setWarehouse_code("DEFRAA");
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

//        FpxWarehouseMethodListRequest fpxWarehouseMethodListRequest = new FpxWarehouseMethodListRequest();
//        fpxWarehouseMethodListRequest.setSourcePositionCode("USLAXA");
//        fpxWarehouseMethodListRequest.setCategoryCode("end");
//        fpxWarehouseMethodListRequest.setServiceCode("F");
//        fpxWarehouseMethodListRequest.setDestCountryCode("US");
//        System.out.println(getFpxMethods(fpxWarehouseMethodListRequest));

    }
}

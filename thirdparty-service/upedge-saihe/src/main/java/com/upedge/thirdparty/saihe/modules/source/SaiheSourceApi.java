package com.upedge.thirdparty.saihe.modules.source;

import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApRequest;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceRequest;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceResponse;
import com.upedge.thirdparty.saihe.request.SaiheRequestBody;
import com.upedge.thirdparty.saihe.request.SaiheRequestEntity;
import com.upedge.thirdparty.saihe.response.SaiheResponseEntity;
import com.upedge.thirdparty.saihe.utils.PostUtils;
import com.upedge.thirdparty.saihe.utils.XmlAndJavaObjectConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;

@Slf4j
public class SaiheSourceApi {

    /**
     * 获取订单来源渠道列表
     */
    public static ApiGetOrderSourceResponse getOrderSourceList(Integer orderSourceType){
        ApiGetOrderSourceResponse apiGetOrderSourceResponse=new ApiGetOrderSourceResponse("Error");
        try {
            ApiGetOrderSourceRequest apiGetOrderSourceRequest=new ApiGetOrderSourceRequest();

            ApRequest apRequest=new ApRequest();

            apRequest.setUserName(SaiheConfig.USERNAME);//		必填	用户名
            apRequest.setPassword(SaiheConfig.PASSWORD);//必填	密码
            apRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);//必填	客户号
            apRequest.setOrderSourceType(orderSourceType);//来源渠道类型
            apiGetOrderSourceRequest.setApRequest(apRequest);

            SaiheRequestEntity requestEntity=new SaiheRequestEntity();
            SaiheRequestBody requestBody=new SaiheRequestBody();
            requestBody.setApiGetOrderSourceRequest(apiGetOrderSourceRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            log.debug("result:{}",result);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class,result);
            return responseEntity.getBody().getApiGetOrderSourceResponse();
        }catch (Exception e){
            return apiGetOrderSourceResponse;
        }
    }



}

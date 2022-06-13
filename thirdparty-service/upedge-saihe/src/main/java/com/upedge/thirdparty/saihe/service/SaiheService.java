package com.upedge.thirdparty.saihe.service;

import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ApiGetProductInventoryRequest;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.InventoryRequest;
import com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList.ApiGetInPurchaseDetailRequest;
import com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList.ApiGetInPurchaseDetailResponse;
import com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList.PurchaseDetailRequest;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.*;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.createProcurement.*;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderRequest;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.OrderRequest;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiGetPackagesRequest;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiGetPackagesResponse;
import com.upedge.thirdparty.saihe.entity.getPackages.PackagesRequest;
import com.upedge.thirdparty.saihe.entity.getProcurementList.GetProcurementList;
import com.upedge.thirdparty.saihe.entity.getProcurementList.GetProcurementListRequest;
import com.upedge.thirdparty.saihe.entity.getProcurementList.GetProcurementListResponse;
import com.upedge.thirdparty.saihe.entity.getProducts.*;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiGetTransportRequest;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiGetTransportResponse;
import com.upedge.thirdparty.saihe.entity.getTransportList.TrRequest;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiGetWareHouseRequest;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiGetWareHouseResponse;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.WhRequest;
import com.upedge.thirdparty.saihe.entity.processUpdateProduct.*;
import com.upedge.thirdparty.saihe.entity.uploadOrder.ApiUploadOrderInfo;
import com.upedge.thirdparty.saihe.entity.uploadOrder.ApiUploadOrderRequest;
import com.upedge.thirdparty.saihe.entity.uploadOrder.Request;
import com.upedge.thirdparty.saihe.entity.uploadOrder.UpLoadOrderV2Response;
import com.upedge.thirdparty.saihe.request.SaiheRequestBody;
import com.upedge.thirdparty.saihe.request.SaiheRequestEntity;
import com.upedge.thirdparty.saihe.response.GetProductInventoryResponse;
import com.upedge.thirdparty.saihe.response.RequestBody1;
import com.upedge.thirdparty.saihe.response.RequestEntity1;
import com.upedge.thirdparty.saihe.response.SaiheResponseEntity;
import com.upedge.thirdparty.saihe.utils.PostUtils;
import com.upedge.thirdparty.saihe.utils.XmlAndJavaObjectConvert;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class SaiheService {

    /**
     * 根据赛盒SKU获取产品
     * @param SKU
     * @return
     */
    public static ApiGetProductResponse getProductBySKU(String SKU){

        ApiGetProductResponse apiGetProductResponse=new ApiGetProductResponse("Error");
        try {
            ApiGetProductRequest apiGetProductRequest = new ApiGetProductRequest();

            ProductRequest productRequest = new ProductRequest();

            productRequest.setUserName(SaiheConfig.USERNAME);//		必填	用户名
            productRequest.setPassword(SaiheConfig.PASSWORD);//必填	密码
            productRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);//必填	客户号
            productRequest.setSKU(SKU);//选填	产品SKU
            apiGetProductRequest.setProductRequest(productRequest);

            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setGetProducts(apiGetProductRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV(SaiheConfig.PRODUCT_URL, xmlStr);
            log.debug("result:{}",result);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetProductResponse();
        }catch (Exception e){
            return apiGetProductResponse;
        }

    }

    /**
     * 根据自定义SKU获取产品
     * 自定义SKU，多选用英文逗号隔开
     * @param ClientSKUs
     * @param token
     * @return
     */
    public static ApiGetProductResponse getProductsByClientSKUs(String ClientSKUs,Integer token){
        ApiGetProductResponse apiGetProductResponse=new ApiGetProductResponse("Error");
        try {
            ApiGetProductRequest apiGetProductRequest = new ApiGetProductRequest();
            ProductRequest productRequest = new ProductRequest();
            productRequest.setUserName(SaiheConfig.USERNAME);//		必填	用户名
            productRequest.setPassword(SaiheConfig.PASSWORD);//必填	密码
            productRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);//必填	客户号
            productRequest.setClientSKUs(ClientSKUs);//选填	产品母体ID
            productRequest.setProductUpdateStartTime("2018-01-01 00:00:00");//选填	请求更新开始时间
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            productRequest.setProductUpdateEndTime(format.format(new Date()));//选填	请求更新结束时间
            productRequest.setNextToken(token);//第二次回传时填写(用于分页)	NextToken
            apiGetProductRequest.setProductRequest(productRequest);
            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setGetProducts(apiGetProductRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.PRODUCT_URL, xmlStr);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetProductResponse();
        }catch (Exception e){
            return apiGetProductResponse;
        }
    }

    /**
     * 根据母体SKU获取产品
     * @param groupSKU
     * @param token
     * @return
     */
    public static ApiGetProductResponse getProductsByGroupSKU(String groupSKU,Integer token){

        ApiGetProductResponse apiGetProductResponse=new ApiGetProductResponse("Error");
        try {
            ApiGetProductRequest apiGetProductRequest = new ApiGetProductRequest();

            ProductRequest productRequest = new ProductRequest();

            productRequest.setUserName(SaiheConfig.USERNAME);//		必填	用户名
            productRequest.setPassword(SaiheConfig.PASSWORD);//必填	密码
            productRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);//必填	客户号
            productRequest.setProductGroupSKU(groupSKU);//选填	产品母体ID
            productRequest.setProductUpdateStartTime("2018-01-01 00:00:00");//选填	请求更新开始时间
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            productRequest.setProductUpdateEndTime(format.format(new Date()));//选填	请求更新结束时间
            productRequest.setNextToken(token);//第二次回传时填写(用于分页)	NextToken
            apiGetProductRequest.setProductRequest(productRequest);

            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setGetProducts(apiGetProductRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV(SaiheConfig.PRODUCT_URL, xmlStr);
            log.debug("result:{}",result);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetProductResponse();
        }catch (Exception e){
            return apiGetProductResponse;
        }

    }

    /**
     * 获取赛盒运输方式 对应 仓库ID
     */
    public static ApiGetTransportResponse getTransportList(Integer wareHouseID){
        ApiGetTransportResponse apiGetTransportResponse=new ApiGetTransportResponse("Error");
        try {
            ApiGetTransportRequest apiGetTransportRequest = new ApiGetTransportRequest();
            TrRequest trRequest = new TrRequest();
            trRequest.setUserName(SaiheConfig.USERNAME);
            trRequest.setPassword(SaiheConfig.PASSWORD);
            trRequest.setCustomer_ID(SaiheConfig.CUSTOMER_ID);
            trRequest.setWareHouseID(wareHouseID);
            apiGetTransportRequest.setTrRequest(trRequest);
            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setApiGetTransportRequest(apiGetTransportRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            log.debug("result:{}",result);
            SaiheResponseEntity responseEntity = (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetTransportResponse();
        }catch (Exception e){
            return apiGetTransportResponse;
        }
    }

    /**
     *获取仓库列表
     */
    public static ApiGetWareHouseResponse getWareHouseList(){
        ApiGetWareHouseResponse apiGetWareHouseResponse=new ApiGetWareHouseResponse("Error");
        try {
            ApiGetWareHouseRequest apiGetWareHouseRequest = new ApiGetWareHouseRequest();
            WhRequest request = new WhRequest();
            request.setUserName(SaiheConfig.USERNAME);
            request.setPassword(SaiheConfig.PASSWORD);
            request.setCustomer_ID(SaiheConfig.CUSTOMER_ID);
            //仓库类型（默认值：1）
            request.setWareHouseType(1);

            apiGetWareHouseRequest.setRequest(request);
            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setApiGetWareHouseRequest(apiGetWareHouseRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class,result);
            return responseEntity.getBody().getApiGetWareHouseResponse();
        }catch (Exception e){
            return apiGetWareHouseResponse;
        }
    }


    /**
     * 上传产品
     * @param list
     * @return
     */
    public static ApiUploadProductsResponse processUpdateProduct(List<ApiImportProductInfo> list){
        ApiUploadProductsResponse apiUploadProductsResponse=new ApiUploadProductsResponse("Error");
        try {
            Prequest request = new Prequest();
            request.setUserName(SaiheConfig.USERNAME);
            request.setPassword(SaiheConfig.PASSWORD);
            request.setCustomerID(SaiheConfig.CUSTOMER_ID);

            ImportProductList importProductList=new ImportProductList();
            importProductList.setImportProductList(list);
            request.setImportProductList(importProductList);
            ApiUploadProductsRequest apiUploadProductsRequest = new ApiUploadProductsRequest();
            apiUploadProductsRequest.setRequest(request);

            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody= new SaiheRequestBody();
            requestBody.setApiUploadProductsRequest(apiUploadProductsRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.PRODUCT_URL,xmlStr);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiUploadProductsResponse();
        }catch (Exception e){
            return apiUploadProductsResponse;
        }
    }


    /**
     * 创建备库订单
     * @param procurementProductList
     * @return
     */
    public static ApiCreateProcurementResponse createProcurement(List<ApiCreateProcurementProductList> procurementProductList){

        ApiCreateProcurementResponse apiCreateProcurementResponse=new ApiCreateProcurementResponse("Error");
        try {
            ApiCreateProcurementRequest apiCreateProcurementRequest=new ApiCreateProcurementRequest();
            ProcurementRequest procurementRequest=new ProcurementRequest();
            procurementRequest.setUserName(SaiheConfig.USERNAME);
            procurementRequest.setPassword(SaiheConfig.PASSWORD);
            procurementRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            procurementRequest.setWareHouseID(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID);
            procurementRequest.setRemark("");
            ProductList productList=new ProductList();
            productList.setProductList(procurementProductList);
            procurementRequest.setProductList(productList);
            apiCreateProcurementRequest.setProcurementRequest(procurementRequest);

            SaiheRequestEntity requestEntity=new SaiheRequestEntity();
            SaiheRequestBody requestBody=new SaiheRequestBody();
            requestBody.setApiCreateProcurementRequest(apiCreateProcurementRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiCreateProcurementResponse();
        }catch (Exception e){
            return apiCreateProcurementResponse;
        }

    }

    /**
     * 根据赛盒orderCode获取订单信息
     * @param orderCode
     * @return
     */
    public static ApiGetOrderResponse getOrderByCode(String orderCode){
        ApiGetOrderResponse apiGetOrderResponse=new ApiGetOrderResponse("Error");
        try {
            ApiGetOrderRequest apiGetOrderRequest = new ApiGetOrderRequest();
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            orderRequest.setUserName(SaiheConfig.USERNAME);
            orderRequest.setPassword(SaiheConfig.PASSWORD);
            //系统订单号
            orderRequest.setOrderCode(orderCode);
            //orderRequest.setOrderSourceType();
            //orderRequest.setNextToken(0);
            //orderRequest.setClientOrderCode("1");
            //订单产品状态(All:返回全部产品,Updated:返回修改后的产品)
            orderRequest.setOrderListStatus("All");

            apiGetOrderRequest.setOrderRequest(orderRequest);

            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setApiGetOrderRequest(apiGetOrderRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            SaiheResponseEntity responseEntity = (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetOrderResponse();
        }catch (Exception e){
            return apiGetOrderResponse;
        }
    }

    /**
     * 作废赛盒订单
     * @param orderCode
     * @return
     */
    public static ApiCancelOrderResponse cancelOrderInfo(String orderCode){
        ApiCancelOrderResponse apiCancelOrderResponse=new ApiCancelOrderResponse("Error");
        try{
            SaiheRequestEntity requestEntity=new SaiheRequestEntity();
            SaiheRequestBody requestBody=new SaiheRequestBody();

            ApiCancelOrderRequest apiCancelOrderRequest=new ApiCancelOrderRequest();
            CancelOrderInfo cancelOrderInfo=new CancelOrderInfo();
            cancelOrderInfo.setUserName(SaiheConfig.USERNAME);
            cancelOrderInfo.setPassword(SaiheConfig.PASSWORD);
            cancelOrderInfo.setCustomerID(SaiheConfig.CUSTOMER_ID);
            cancelOrderInfo.setOrderCode(orderCode);
            cancelOrderInfo.setOrderCancelReason(4);
            apiCancelOrderRequest.setCancelOrderInfo(cancelOrderInfo);
            requestBody.setApiCancelOrderRequest(apiCancelOrderRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL,xmlStr);
            SaiheResponseEntity responseEntity= (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiCancelOrderResponse();
        }catch (Exception e){
            return apiCancelOrderResponse;
        }
    }

    /**
     * 根据平台订单号获取订单信息
     * @param clientOrderCode
     * @return
     */
    public static ApiGetOrderResponse getOrderByClientOrderCode(String clientOrderCode){
        ApiGetOrderResponse apiGetOrderResponse=new ApiGetOrderResponse("Error");
        try {
            ApiGetOrderRequest apiGetOrderRequest = new ApiGetOrderRequest();
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            orderRequest.setUserName(SaiheConfig.USERNAME);
            orderRequest.setPassword(SaiheConfig.PASSWORD);
            //系统订单号
            //orderRequest.setOrderCode(orderCode);
            //orderRequest.setOrderSourceType();
            //orderRequest.setNextToken(0);
            orderRequest.setClientOrderCode(clientOrderCode);
            //订单产品状态(All:返回全部产品,Updated:返回修改后的产品)
            orderRequest.setOrderListStatus("All");
            apiGetOrderRequest.setOrderRequest(orderRequest);
            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setApiGetOrderRequest(apiGetOrderRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            SaiheResponseEntity responseEntity = (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            return  responseEntity.getBody().getApiGetOrderResponse();
        }catch (Exception e){
            return apiGetOrderResponse;
        }
    }


    /**
     * 上传订单
     */
    public static UpLoadOrderV2Response upLoadOrderV2(ApiUploadOrderInfo apiUploadOrderInfo){
        UpLoadOrderV2Response upLoadOrderV2Response=new UpLoadOrderV2Response("Error");
        try {
            ApiUploadOrderRequest apiUploadOrderRequest = new ApiUploadOrderRequest();
            Request request = new Request();
            request.setUserName(SaiheConfig.USERNAME);
            request.setPassword(SaiheConfig.PASSWORD);
            request.setCustomer_ID(SaiheConfig.CUSTOMER_ID);
            request.setApiUploadOrderInfo(apiUploadOrderInfo);
            apiUploadOrderRequest.setRequest(request);
            SaiheRequestEntity requestEntity = new SaiheRequestEntity();
            SaiheRequestBody requestBody = new SaiheRequestBody();
            requestBody.setApiUploadOrderRequest(apiUploadOrderRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
            String result = PostUtils.sendPostV(SaiheConfig.ORDER_URL, xmlStr);
            SaiheResponseEntity responseEntity = (SaiheResponseEntity) XmlAndJavaObjectConvert.convertXmlStrToObject(SaiheResponseEntity.class, result);
            upLoadOrderV2Response = responseEntity.getBody().getUpLoadOrderV2Response();
            return upLoadOrderV2Response;
        }catch (Exception e){
            return upLoadOrderV2Response;
        }
    }

    /**
     * 获取赛盒包裹信息
     * @param ShipTimeBegin
     * @param ShipTimeEnd
     * @param NextToken
     * @return
     */
    public static ApiGetPackagesResponse getPackages(Date ShipTimeBegin, Date ShipTimeEnd, Integer NextToken){
        ApiGetPackagesResponse apiGetPackagesResponse=new ApiGetPackagesResponse("Error");
        try {
            RequestEntity1 requestEntity=new RequestEntity1();
            RequestBody1 requestBody=new RequestBody1();
            ApiGetPackagesRequest apiGetPackagesRequest=new ApiGetPackagesRequest();
            PackagesRequest packagesRequest=new PackagesRequest();
            packagesRequest.setUserName(SaiheConfig.USERNAME);
            packagesRequest.setPassword(SaiheConfig.PASSWORD);
            packagesRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            packagesRequest.setShipTimeBegin(ShipTimeBegin);
            packagesRequest.setShipTimeEnd(ShipTimeEnd);
            packagesRequest.setOrderSourceType(SaiheConfig.UPEDGE_ORDER_SOURCE_TYPE);
            packagesRequest.setNextToken(NextToken);
            apiGetPackagesRequest.setRequest(packagesRequest);
            requestBody.setApiGetPackagesRequest(apiGetPackagesRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV2(SaiheConfig.ORDER_URL, xmlStr,"http://tempuri.org/GetPackages");
//            log.debug("result:{}",result);
            RequestEntity1 responseEntity= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity.getBody().getApiGetPackagesResponse();
        }catch (Exception e){
            e.printStackTrace();
            return apiGetPackagesResponse;
        }
    }

    /**
     * 查询采购入库记录
     * @return
     */
    public static GetProcurementListResponse getProcurementListRequest(Date startTime,Date endTime,Integer nextToken){

        GetProcurementListResponse getProcurementListResponse=new GetProcurementListResponse("Error");
        try {

            RequestEntity1 requestEntity = new RequestEntity1();
            RequestBody1 requestBody = new RequestBody1();

            GetProcurementListRequest apiGetInPurchaseDetailRequest = new GetProcurementListRequest();
            GetProcurementList getProcurementList = new GetProcurementList();
            getProcurementList.setUserName(SaiheConfig.USERNAME);
            getProcurementList.setPassword(SaiheConfig.PASSWORD);
            getProcurementList.setCustomerID(SaiheConfig.CUSTOMER_ID);
//            getProcurementList.setP_Code(P_Code);
            getProcurementList.setStartTime(startTime);
            getProcurementList.setEndTime(endTime);
            getProcurementList.setNextToken(nextToken);
            apiGetInPurchaseDetailRequest.setGetProcurementList(getProcurementList);
            requestBody.setGetProcurementListRequest(apiGetInPurchaseDetailRequest);
            requestEntity.setBody(requestBody);
            String xmlStr = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            log.debug("xmlStr:{}",xmlStr);
            String result = PostUtils.sendPostV2(SaiheConfig.ORDER_URL, xmlStr,"http://tempuri.org/GetProcurementList");
//            log.debug("result:{}",result);
            RequestEntity1 responseEntity1= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity1.getBody().getGetProcurementListResponse();

        }catch (Exception e){
            return getProcurementListResponse;
        }

    }

    public static void listP(Integer nextToken) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime="2021-03-08 00:00:00";
        String endTime="2021-03-08 16:00:00";
        GetProcurementListResponse response=SaiheService.getProcurementListRequest(format.parse(startTime),format.parse(endTime),nextToken);
        if(response.getGetProcurementListResult().getStatus().equals("OK")){

            if(response.getGetProcurementListResult().getNextToken()!=null
                    &&response.getGetProcurementListResult().getNextToken()!=-1){
                listP(response.getGetProcurementListResult().getNextToken());
            }
        }
    }


    //查询采购入库记录
    public static ApiGetInPurchaseDetailResponse getInPurchaseDetailRequest(String P_Code, String ClientSKU){

        ApiGetInPurchaseDetailResponse getInPurchaseDetailResponse=new ApiGetInPurchaseDetailResponse("Error");
        try {

            RequestEntity1 requestEntity = new RequestEntity1();
            RequestBody1 requestBody = new RequestBody1();

            ApiGetInPurchaseDetailRequest apiGetInPurchaseDetailRequest = new ApiGetInPurchaseDetailRequest();
            PurchaseDetailRequest purchaseDetailRequest = new PurchaseDetailRequest();
            purchaseDetailRequest.setUserName(SaiheConfig.USERNAME);
            purchaseDetailRequest.setPassword(SaiheConfig.PASSWORD);
            purchaseDetailRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            purchaseDetailRequest.setWareHouseID(0);
            purchaseDetailRequest.setP_Code(P_Code);
            purchaseDetailRequest.setClientSKU(ClientSKU);


            purchaseDetailRequest.setStartTime("2019-01-01 00:00:00");//选填	请求更新开始时间

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            purchaseDetailRequest.setEndTime(format.format(calendar.getTime()));//选填	请求更新结束时间

            apiGetInPurchaseDetailRequest.setPurchaseDetailRequest(purchaseDetailRequest);
            requestBody.setApiGetInPurchaseDetailRequest(apiGetInPurchaseDetailRequest);
            requestEntity.setBody(requestBody);

            String xmlStr2 = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            System.out.println(xmlStr2);
            String result = PostUtils.sendPost1(SaiheConfig.ORDER_URL, "http://tempuri.org/GetPurchasePutInLogList", xmlStr2);
//            System.out.println(result);
            RequestEntity1 responseEntity1= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity1.getBody().getApiGetInPurchaseDetailResponse();

        }catch (Exception e){
            return getInPurchaseDetailResponse;
        }

    }

    /**
     * 获取库存信息
     * @param warehouseID
     * @param nextToken
     * @return
     */
    public static GetProductInventoryResponse listProductInventory(Integer warehouseID, Integer nextToken){

        GetProductInventoryResponse getProductInventoryResponse=new GetProductInventoryResponse("Error");
        try {

            RequestEntity1 requestEntity = new RequestEntity1();
            RequestBody1 requestBody = new RequestBody1();

            ApiGetProductInventoryRequest apiGetProductInventoryRequest = new ApiGetProductInventoryRequest();
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setUserName(SaiheConfig.USERNAME);
            inventoryRequest.setPassword(SaiheConfig.PASSWORD);
            inventoryRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            inventoryRequest.setWarehouseID(warehouseID);
            inventoryRequest.setNextToken(nextToken);

            apiGetProductInventoryRequest.setInventoryRequest(inventoryRequest);
            requestBody.setApiGetProductInventoryRequest(apiGetProductInventoryRequest);
            requestEntity.setBody(requestBody);

            String xmlStr2 = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            System.out.println(xmlStr2);
            String result = PostUtils.sendPost1(SaiheConfig.PRODUCT_URL, "http://tempuri.org/GetProductInventory", xmlStr2);
//            System.out.println(result);
            RequestEntity1 responseEntity1= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity1.getBody().getGetProductInventoryResponse();

        }catch (Exception e){
            return getProductInventoryResponse;
        }

    }


    public static GetProductInventoryResponse getProductInventory(Integer warehouseID,String ClientSKU){

        GetProductInventoryResponse getProductInventoryResponse=new GetProductInventoryResponse("Error");
        try {

            RequestEntity1 requestEntity = new RequestEntity1();
            RequestBody1 requestBody = new RequestBody1();

            ApiGetProductInventoryRequest apiGetProductInventoryRequest = new ApiGetProductInventoryRequest();
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setUserName(SaiheConfig.USERNAME);
            inventoryRequest.setPassword(SaiheConfig.PASSWORD);
            inventoryRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            inventoryRequest.setWarehouseID(warehouseID);
            inventoryRequest.setClientSKU(ClientSKU);

            apiGetProductInventoryRequest.setInventoryRequest(inventoryRequest);
            requestBody.setApiGetProductInventoryRequest(apiGetProductInventoryRequest);
            requestEntity.setBody(requestBody);

            String xmlStr2 = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            System.out.println(xmlStr2);
            String result = PostUtils.sendPost1(SaiheConfig.PRODUCT_URL, "http://tempuri.org/GetProductInventory", xmlStr2);
//            System.out.println(result);
            RequestEntity1 responseEntity1= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity1.getBody().getGetProductInventoryResponse();

        }catch (Exception e){
            return getProductInventoryResponse;
        }

    }

    public static void main(String[] args) throws ParseException {
//        ApiGetOrderResponse apiGetOrderResponse =  getOrderByClientOrderCode("1427899953854398464");
//        System.out.println(apiGetOrderResponse);
//        ApiGetProductResponse apiGetProductResponse = getProductsByClientSKUs("613372243815,cj4lGkH8IM4Q,4600031153606",null);
//        System.out.println(apiGetProductResponse);


//        ApiGetOrderResponse apiGetOrderResponse =  getOrderByClientOrderCode("1504079038278926336 ");
//        System.out.println(apiGetOrderResponse);
//        System.out.println(getProductsByClientSKUs("The Beard Roller:1646923580001",null));

//        ApiGetWareHouseResponse apiGetWareHouseResponse = getWareHouseList();
//        System.out.println(apiGetWareHouseResponse.getGetWareHouseListResult().getWareHouseList());

        ApiGetProductResponse response = getProductsByClientSKUs(null,null);
        GetProductsResult result = response.getGetProductsResult();
        if (result.getStatus().equals("OK")){
            Integer nextToken = result.getNextToken();
            List<ApiProductInfo> apiProductInfos = result.getProductInfoList().getProductInfoList();
            for (ApiProductInfo apiProductInfo : apiProductInfos) {

            }
        }

        System.out.println(response);
    }


    /**
     * 根据packageId查询包裹
     * @param packageID
     * @return
     */
    public static ApiGetPackagesResponse getPackageById(Integer packageID){
        ApiGetPackagesResponse apiGetPackagesResponse=new ApiGetPackagesResponse("Error");
        try {
            RequestEntity1 requestEntity=new RequestEntity1();
            RequestBody1 requestBody=new RequestBody1();


            ApiGetPackagesRequest apiGetPackagesRequest=new ApiGetPackagesRequest();
            PackagesRequest packagesRequest=new PackagesRequest();
            packagesRequest.setUserName(SaiheConfig.USERNAME);
            packagesRequest.setPassword(SaiheConfig.PASSWORD);
            packagesRequest.setCustomerID(SaiheConfig.CUSTOMER_ID);
            packagesRequest.setPackageID(packageID);
            packagesRequest.setOrderSourceType(4);


            apiGetPackagesRequest.setRequest(packagesRequest);
            requestBody.setApiGetPackagesRequest(apiGetPackagesRequest);
            requestEntity.setBody(requestBody);

            String xmlStr2 = XmlAndJavaObjectConvert.convertToXml(requestEntity);
//            System.out.println(xmlStr2);

            String result = PostUtils.sendPost1(SaiheConfig.ORDER_URL, "http://tempuri.org/GetPackages", xmlStr2);
//            System.out.println(result);

            RequestEntity1 responseEntity1= (RequestEntity1) XmlAndJavaObjectConvert.convertXmlStrToObject(RequestEntity1.class, result);
            return  responseEntity1.getBody().getApiGetPackagesResponse();
        }catch (Exception e){
            return apiGetPackagesResponse;
        }
    }
}

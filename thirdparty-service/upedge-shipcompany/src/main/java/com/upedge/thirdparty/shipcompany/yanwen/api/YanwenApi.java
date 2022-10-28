package com.upedge.thirdparty.shipcompany.yanwen.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.exception.CustomerException;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenApiRequestBase;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenCreateExpressResponse;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenExpressDto;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenGetShipMethodDto;
import com.upedge.thirdparty.shipcompany.yanwen.vo.YanwenShipMethodVo;
import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YanwenApi {

//    public static String ServicePoint = "http://47.96.220.163:802/service";
    public static String ServicePoint = "http://online.yw56.com.cn/service";

    public static String userId = "20074603";

    public static String apiToken = "55D9B8F3C8AE85644AC393B92C159CF9";


    public static List<YanwenShipMethodVo> getYanwenShipMethods(){
        String url = ServicePoint +  "/Users/"+userId+"/GetChannels";

        try {
            String result = commonRequest(url,HttpMethod.GET,null);
            JSONObject jsonObject = xmltoJson(result);
            YanwenGetShipMethodDto yanwenGetShipMethodDto = JSONObject.toJavaObject(jsonObject,YanwenGetShipMethodDto.class);
            return yanwenGetShipMethodDto.getGetChannelCollectionResponseType().getChannelCollection().getYanwenShipMethodVos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTrackLabel(String epCode,String path) throws CustomerException {
//        String url = ServicePoint + "/Users/"+userId+"/Expresses/"+epCode+"/A10x10LLabel";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("waybillNumber",epCode);

        YanwenApiRequestBase requestBase = new YanwenApiRequestBase();
        requestBase.setData(null);
        requestBase.initSign(jsonObject.toJSONString(), "express.order.label.get");

        String url = "https://open.yw56.com.cn/api/order?user_id="+requestBase.getUser_id()+"&method=express.order.label.get&format=json&timestamp="+requestBase.getTimestamp()+"&sign="+requestBase.getSign()+"&version=V1.0";


        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        String s= jsonObject.toJSONString();

        RequestBody body = RequestBody.create(mediaType,s);


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/json")
                .method(HttpMethod.POST.toString(), body)
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            jsonObject = JSONObject.parseObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer code = jsonObject.getInteger("code");
        if (code != 0){
            throw new CustomerException(jsonObject.getString("message"));
        }

        return jsonObject.getJSONObject("data").getString("base64String");

    }

    public static YanwenCreateExpressResponse.YanwenCreateExpressResponseDataDTO createExpress(YanwenExpressDto yanwenExpressDto) throws CustomerException {


        YanwenApiRequestBase requestBase = new YanwenApiRequestBase();
        requestBase.setData(yanwenExpressDto);
        requestBase.initSign(JSONObject.toJSONString(yanwenExpressDto),"express.order.create");

        String url = "https://open.yw56.com.cn/api/order?user_id="+requestBase.getUser_id()+"&method=express.order.create&format=json&timestamp="+requestBase.getTimestamp()+"&sign="+requestBase.getSign()+"&version=V1.0";

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("application/json");

            String s= JSONObject.toJSONString(yanwenExpressDto);

            RequestBody body = RequestBody.create(mediaType,s);


            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type","application/json")
                    .method(HttpMethod.POST.toString(), body)
                    .build();
            Response response = null;

            response = client.newCall(request).execute();
            JSONObject jsonObject = JSONObject.parseObject(response.body().string());
            YanwenCreateExpressResponse yanwenCreateExpressResponse = jsonObject.toJavaObject(YanwenCreateExpressResponse.class);
            if (yanwenCreateExpressResponse.getSuccess()){
                return yanwenCreateExpressResponse.getData();
            }
            throw new CustomerException(yanwenCreateExpressResponse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }

    }


    static String commonRequest(String url, HttpMethod method, String data) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if (null != data) {
            body = RequestBody.create(mediaType,data);
        }

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addHeader("Authorization","basic " + apiToken)
                .addHeader("Accept","application/xml")
                .method(method.toString(), body)
                .build();
        Response response = null;

        response = client.newCall(request).execute();

        return response.body().string();

    }


    public static JSONObject xmltoJson(String xml) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Document document = DocumentHelper.parseText(xml);
        //获取根节点元素对象
        Element root = document.getRootElement();
        iterateNodes(root, jsonObject);
        return jsonObject;
    }

    /**
     * 遍历元素
     *
     * @param node 元素
     * @param json 将元素遍历完成之后放的JSON对象
     */
    @SuppressWarnings("unchecked")
    public static void iterateNodes(Element node, JSONObject json) {
        //获取当前元素的名称
        String nodeName = node.getName();
        //判断已遍历的JSON中是否已经有了该元素的名称
        if (json.containsKey(nodeName)) {
            //该元素在同级下有多个
            Object Object = json.get(nodeName);
            JSONArray array = null;
            if (Object instanceof JSONArray) {
                array = (JSONArray) Object;
            } else {
                array = new JSONArray();
                array.add(Object);
            }
            //获取该元素下所有子元素
            List<Element> listElement = node.elements();
            if (listElement.isEmpty()) {
                //该元素无子元素，获取元素的值
                String nodeValue = node.getTextTrim();
                array.add(nodeValue);
                json.put(nodeName, array);
                return;
            }
            //有子元素
            JSONObject newJson = new JSONObject();
            //遍历所有子元素
            for (Element e : listElement) {
                //递归
                iterateNodes(e, newJson);
            }
            array.add(newJson);
            json.put(nodeName, array);
            return;
        }
        //该元素同级下第一次遍历
        //获取该元素下所有子元素
        List<Element> listElement = node.elements();
        if (listElement.isEmpty()) {
            //该元素无子元素，获取元素的值
            String nodeValue = node.getTextTrim();
            json.put(nodeName, nodeValue);
            return;
        }
        //有子节点，新建一个JSONObject来存储该节点下子节点的值
        JSONObject object = new JSONObject();
        //遍历所有一级子节点
        for (Element e : listElement) {
            //递归
            iterateNodes(e, object);
        }
        json.put(nodeName, object);
        return;
    }


    public static String uploadLabelPdf(String epCode,String path)  {
        HttpURLConnection urlConnection = null;
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        String fileName = epCode + ".pdf";
        try {
            URL url = new URL(ServicePoint + "/Users/"+userId+"/Expresses/"+epCode+"/A10x10LLabel");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(20000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Authorization", "basic "+ apiToken);
            urlConnection.connect();

            File file = new File(path +fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            fileOutputStream = new FileOutputStream(path + fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] buf = new byte[4096];
            int length = bufferedInputStream.read(buf);
            while (-1 != length) {
                bufferedOutputStream.write(buf, 0, length);
                length = bufferedInputStream.read(buf);
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            return fileName;
        } catch (Exception e) {
            System.out.println("getFile error: " + e);
        } finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


}

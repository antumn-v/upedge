package com.upedge.thirdparty.shipcompany.yanwen.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.yanwen.YanwenShipMethodVo;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenGetShipMethodDto;
import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class YanwenApi {

    public static String apiToken = "D6140AA383FD8515B09028C586493DDB";


    public static List<YanwenShipMethodVo> getYanwenShipMethods(){
        String url = "http://47.96.220.163:802/service/Users/100000/GetChannels";

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

    public static void main(String[] args) {
        System.out.println(getYanwenShipMethods());
    }
}

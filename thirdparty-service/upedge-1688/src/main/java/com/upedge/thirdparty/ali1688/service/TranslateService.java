package com.upedge.thirdparty.ali1688.service;

import com.alibaba.fastjson.JSON;
import com.upedge.thirdparty.ali1688.config.TranslateConfig;
import com.upedge.thirdparty.ali1688.entity.translate.TranslateRes;
import com.upedge.thirdparty.ali1688.entity.translate.TranslateResult;
import com.upedge.thirdparty.ali1688.entity.translate.Translation;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TranslateService {

    private static String sendTranslate(String url, List<String> paramList) {
        String result = "";
        CloseableHttpResponse response=null;
        try {
            //1、创建httpClient
            CloseableHttpClient client = HttpClients.createDefault();
            //2、封装请求参数
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

            list.add(new BasicNameValuePair("key", "AIzaSyACb5jC3UatfkfNVfK9P0kI1EN19SRklaE"));
            list.add(new BasicNameValuePair("model", "nmt"));
            list.add(new BasicNameValuePair("source", "zh-CN"));
            list.add(new BasicNameValuePair("format", "text"));
            list.add(new BasicNameValuePair("target", "en"));

            for(String param:paramList){
                list.add(new BasicNameValuePair("q", param));
            }

            //3、转化参数
            String params = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
            //4、创建HttpGet请求
            HttpGet httpGet = new HttpGet(url + "?" + params);
            response = client.execute(httpGet);

            //5、获取实体
            HttpEntity entity = response.getEntity();
            //将实体装成字符串
            result = EntityUtils.toString(entity);
            return result;

        }catch (Exception e){
            return "";
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static TranslateResult translate(List<String> params) {
        String res=sendTranslate(TranslateConfig.TRANSLATE_URL,params);
        TranslateResult translateResult=new TranslateResult();
        try {
            TranslateRes translateRes = JSON.parseObject(res, TranslateRes.class);
            if(translateRes!=null&&translateRes.getData()!=null&&translateRes.getData().getTranslations()!=null){
                List<Translation> translationList=translateRes.getData().getTranslations();
                HashMap map=new HashMap();
                for(int i=0;i<translationList.size();i++){
                    String param=params.get(i);
                    String enParam=translationList.get(i).getTranslatedText();
                    map.put(param,enParam);
                }
                translateResult.setResult(map);
            }
        }catch (Exception e){
            return translateResult;
        }
        return translateResult;
    }
}

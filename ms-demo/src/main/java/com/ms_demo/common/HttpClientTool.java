package com.ms_demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpClientTool {

    //通用HTTP GET請求URL合并參數方法
    public  String concatenateGetParameters(String baseUrl, List<String> parameterNames, List<String> parameterValues) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (parameterNames.size()!= parameterValues.size()) {
            throw new IllegalArgumentException("参数名和参数值的数量不匹配");
        }

        boolean firstParameter = true;
        for (int i = 0; i < parameterNames.size(); i++) {
            try {
                if (firstParameter) {
                    urlBuilder.append("?");
                    firstParameter = false;
                } else {
                    urlBuilder.append("&");
                }
                // 对参数值进行URL编码，防止特殊字符导致问题
                String encodedValue = URLEncoder.encode(parameterValues.get(i), StandardCharsets.UTF_8.name());
                urlBuilder.append(parameterNames.get(i)).append("=").append(encodedValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return urlBuilder.toString();
    }

    //通用HTTP GET請求方法
    public HttpResponse httpGetMethod(String url, List<BasicHeader> listBasicHeader) {
        // 创建HttpClient实例
        HttpClient httpClient = HttpClientBuilder.create().build();

        // 创建HttpGet请求对象，指定请求的URL
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response=null;

        // 初始化應答報文内容
        String responseBody = "";

        // 设置请求头
        for (BasicHeader header : listBasicHeader) {
            httpGet.setHeader(header);
        }

        try {
            // 执行请求，获取响应
            response = httpClient.execute(httpGet);

            // 获取响应状态码，判断请求是否成功
            //int statusCode = response.getStatusLine().getStatusCode();
            //if (statusCode >= 200 && statusCode < 300) {
                // 获取响应体内容
                //System.out.println("GET請求获取到的响应内容: " + responseBody);
            //} else {
                //System.out.println("GET请求失败，状态码: " + statusCode);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    //通用HTTP POST請求方法
    public String httpPostMethod(String url, List<BasicHeader> listBasicHeader, Map<String, Object> data) {

        String content = "";
        // 创建HttpClient实例
        HttpClient httpClient = HttpClientBuilder.create().build();

        // 创建HttpPost请求对象，指定提交数据的URL
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        for (BasicHeader header : listBasicHeader) {
            httpPost.setHeader(header);
        }

        // 解析JSON報文躰
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 设置JSON報文數據
        StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

        try {
            // 执行请求，获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应状态码，判断请求是否成功
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                // 获取响应体内容
                HttpEntity respEntity = response.getEntity();
                content = EntityUtils.toString(respEntity);
                //System.out.println("POST請求获取到的响应内容: " + content);
            } else {
                //System.out.println("POST请求失败，状态码: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    //通用获取token方法
    public String getToken()
    {
        // HTTP POST请求URL
        String httpPostUrl = "https://api-demo.airwallex.com/api/v1/authentication/login";
        // HTTP POST请求Header LIST
        List<BasicHeader> httpPostListBasicHeader = new ArrayList<BasicHeader>();
        httpPostListBasicHeader.add(new BasicHeader("Content-Type", "application/json"));
        httpPostListBasicHeader.add(new BasicHeader("x-client-id", "CVwQh88xTUeqYV-zMpLahg"));
        httpPostListBasicHeader.add(new BasicHeader("x-api-key", "194dcb42f76d6c19ee0bd52812320d3d20eb69288832197802b31e2d03379bfaf044ccf348ddf46a290f87114e0b84aa"));
        // JSON转换工具类
        ObjectMapper objectMapper = new ObjectMapper();
        // HTTP POST 请求JSON body体
        Map<String, Object> httpPostReqData = new HashMap<>();
        Map<String, Object> httpPostResData = new HashMap<>();
        try {
            httpPostResData = objectMapper.readValue(httpPostMethod(httpPostUrl, httpPostListBasicHeader, httpPostReqData),Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return httpPostResData.get("token").toString();
    }
}
package com.ms_demo.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_demo.common.HttpClientTool;
import com.ms_demo.model.RateModel;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TestRateDetailsCheck {
    @Test
    public void testRateDetailsCheck() {
        //初始化HTTP工具類
        HttpClientTool httpClientTool = new HttpClientTool();
        // HTTP POST請求URL
        String httpPostUrl = "https://api-demo.airwallex.com/api/v1/authentication/login";
        // HTTP POST請求Header LIST
        List<BasicHeader> httpPostListBasicHeader = new ArrayList<BasicHeader>();
        // 對HTTP POST Header 賦值，可優化調整爲從excel，數據庫或者文件讀取
        httpPostListBasicHeader.add(new BasicHeader("Content-Type", "application/json"));
        httpPostListBasicHeader.add(new BasicHeader("x-client-id", "CVwQh88xTUeqYV-zMpLahg"));
        httpPostListBasicHeader.add(new BasicHeader("x-api-key", "194dcb42f76d6c19ee0bd52812320d3d20eb69288832197802b31e2d03379bfaf044ccf348ddf46a290f87114e0b84aa"));
        // JSON轉換工具類
        ObjectMapper objectMapper = new ObjectMapper();
        // HTTP POST 請求JSON body體
        Map<String, Object> httpPostReqData = new HashMap<>();
        // HTTP POST 請求獲取的應答報文JSON報文躰
        Map<String, Object> httpPostResData = new HashMap<>();
        try {
            httpPostResData = objectMapper.readValue(httpClientTool.httpPostMethod(httpPostUrl, httpPostListBasicHeader, httpPostReqData),Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String token =httpPostResData.get("token").toString();
        String httpGetBaseUrl = "https://api-demo.airwallex.com/api/v1/fx/rates/current";
        //System.out.println("POST請求的URL地址:"+httpPostUrl);
        // 對HTTP GET 參數 賦值，可優化調整爲從excel，數據庫或者文件讀取
        List<String> httpGetParameterNames = new ArrayList<>();
        httpGetParameterNames.add("buy_currency");
        httpGetParameterNames.add("buy_amount");
        httpGetParameterNames.add("sell_currency");
        List<String> httpGetParameterValues = new ArrayList<>();
        httpGetParameterValues.add("AUD");
        httpGetParameterValues.add("0");
        httpGetParameterValues.add("USD");
        // HTTP GET請求URL
        String httpGetUrl = httpClientTool.concatenateGetParameters(httpGetBaseUrl, httpGetParameterNames, httpGetParameterValues);
        System.out.println("GET請求的URL地址:"+httpGetUrl);

        httpPostListBasicHeader.add(new BasicHeader("Authorization", "Bearer "+token));
        HttpResponse httpResponse = httpClientTool.httpGetMethod(httpGetUrl, httpPostListBasicHeader);
        RateModel rateModel = new RateModel();
        String httpResBody = null;
        try {
            httpResBody = EntityUtils.toString(httpResponse.getEntity());
        int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("返回CODE"+httpStatusCode);
        System.out.println("返回报文"+httpResBody);
            rateModel = objectMapper.readValue(httpResBody, RateModel.class);
        } catch (Exception e) {
            rateModel =null;
        }
        //System.out.println("响应getRate_details:"+rateModel.getRate_details());
        //Assert.assertEquals(rateModel.getBuy_currency(),"AUD");
        //Assert.assertEquals(httpResBody,"{\"code\":\"invalid_argument\",\"message\":\"The currency code is not 3-letter ISO-4217 code\",\"source\":\"buy_currency\"}");
        Assert.assertTrue(httpResBody.contains("invalid_parameter"));
        //Assert.assertNotEquals(rateModel.getRate_details().get(0).getSell_amount().toString(),"100.00");
    }
}

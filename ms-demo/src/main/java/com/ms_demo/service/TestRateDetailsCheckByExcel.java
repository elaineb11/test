package com.ms_demo.service;

import com.ms_demo.common.DataFile;
import com.ms_demo.common.ExcelDataHeleper;
import com.ms_demo.common.HttpClientTool;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestRateDetailsCheckByExcel extends ExcelDataHeleper {

    @Test(dataProvider = "excel",dataProviderClass = ExcelDataHeleper.class)
    @DataFile(path = "src/main/resources/testRateDetails.xlsx", sheet = "Sheet0")
    public void testRateDetailsCheckByExcel(Map param) {
        String getUrl=param.get("getUrl").toString();
        String expectCode=param.get("expectCode").toString();
        String expectResult=param.get("expectResult").toString();

        //初始化HTTP工具类
        HttpClientTool httpClientTool = new HttpClientTool();
        String token = httpClientTool.getToken();
        // HTTP POST请求Header LIST
        List<BasicHeader> httpPostListBasicHeader = new ArrayList<BasicHeader>();
        httpPostListBasicHeader.add(new BasicHeader("Authorization", "Bearer " + token));
        //System.out.println("url"+getUrl);
        HttpResponse httpResponse = httpClientTool.httpGetMethod(getUrl, httpPostListBasicHeader);
        String httpResBody = null;
        int httpStatusCode = 0;
        try {
            // JSON转换工具类
            httpResBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("httpResBody:" + httpResBody);
            httpStatusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("httpStatusCode:" + httpStatusCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(httpStatusCode + "", expectCode);
        Assert.assertTrue(httpResBody.contains(expectResult));
    }
}

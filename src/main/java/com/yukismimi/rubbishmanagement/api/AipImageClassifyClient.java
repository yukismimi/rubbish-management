package com.yukismimi.rubbishmanagement.api;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.util.Util;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

/**
 * 接口调用类 单例模式
 */
public class AipImageClassifyClient {

    //设置APPID/AK/SK
    public static final String APP_ID = "17975475";
    public static final String API_KEY = "zedmY5rsFpIjLoKT4xbfddU8";
    public static final String SECRET_KEY = "M1mtrkQGnoxjv0ysnEkw6tV4SW41T7ie";

    //接口调用类
    public static AipImageClassify client;

    private static AipImageClassifyClient instance = new AipImageClassifyClient();
    private AipImageClassifyClient (){
        client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
//        client.setHttpProxy("10.167.196.133", 8080);
    }
    public static AipImageClassifyClient getInstance() {
        return instance;
    }

    public AipImageClassify getClient() {
        return client;
    }

    public void setClient(AipImageClassify client) {
        AipImageClassifyClient.client = client;
    }
    
    /**
     * 使用本地图片
     * @throws JSONException 
     */
    public String getRecognitionResultByLocalPic(String picPath) throws JSONException {
        // 初始化一个AipImageClassifyClient
        AipImageClassify client = AipImageClassifyClient.getInstance().getClient();

        // 调用接口
        org.json.JSONObject res = client.advancedGeneral(picPath, new HashMap<String, String>());
        return res.toString(2);
    }

    /**
     * 使用图片字节流
     * @throws JSONException 
     */
    public String getRecognitionResultByPicBytes(String path) throws JSONException {
    	 byte[] data = new byte[0];
         try {
             data = Util.readFileByBytes(path);
         } catch (IOException e) {
             e.printStackTrace();
         }
        AipImageClassify client = AipImageClassifyClient.getInstance().getClient();
        // 调用接口
        org.json.JSONObject res = client.advancedGeneral(data, new HashMap<String, String>());
        JSONArray jsonArray = res.getJSONArray("result");
        JSONObject bestMatch = (JSONObject) jsonArray.get(0);
        return bestMatch.get("keyword").toString();
    }
}

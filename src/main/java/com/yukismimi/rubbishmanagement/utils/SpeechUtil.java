package com.yukismimi.rubbishmanagement.utils;

import org.apache.commons.lang3.StringUtils;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

public class SpeechUtil {
  
  public static final String APP_ID = "18005055";
  public static final String API_KEY = "0Iq7um4hhrtgK4wpTRlY2zSr";
  public static final String SECRET_KEY = "QqFbIafn77Cxx20KDTjFG01qiKQmzXFb";
  private static AipSpeech client;
  private JSONObject asrRes;
  
  private SpeechUtil(){
    client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    client.setHttpProxy("10.167.196.133", 8080);
  }
  
  public static SpeechUtil getInstance(){  
    return Inner.instance;  
  }
  
  private static class Inner {  
    private static final SpeechUtil instance = new SpeechUtil();  
  } 
  
  public String asrLocalSpeech(String path)
  {
    // 对本地语音文件进行识别
    if(StringUtils.isNotEmpty(path)) {
      asrRes = client.asr(path, "wav", 16000, null);
    } 
    return asrRes.toString();
  }
  
}

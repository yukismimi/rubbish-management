package com.yukismimi.rubbishmanagement.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;

public class SpeechUtil {

  public static final String APP_ID = "18005055";
  public static final String API_KEY = "0Iq7um4hhrtgK4wpTRlY2zSr";
  public static final String SECRET_KEY = "QqFbIafn77Cxx20KDTjFG01qiKQmzXFb";
  private static AipSpeech client;
  private JSONObject asrRes;

  private SpeechUtil(){
    client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
//    client.setHttpProxy("10.167.196.133", 8080);
  }

  public static SpeechUtil getInstance(){
    return Inner.instance;
  }

  private static class Inner {
    private static final SpeechUtil instance = new SpeechUtil();
  }

  public String asrLocalSpeech(String path) throws UnsupportedAudioFileException, IOException
  {
    // 对本地语音文件进行识别
    if(StringUtils.isNotEmpty(path)) {
      HashMap<String, Object> map = new HashMap<String,Object>();
      map.put("dev_pid", 1536);
      String newFilePath = path.replace(".", "-")+".wav";
      File f=new File(path);
      InputStream inputStream = new FileInputStream(f);
      BufferedInputStream zipTest=new BufferedInputStream(inputStream);
      AudioInputStream inStream = AudioSystem.getAudioInputStream(zipTest);
      AudioFormat outDataFormat = new AudioFormat(16000.0f, 16, 1, true, false);
      AudioInputStream lowResAIS = AudioSystem.getAudioInputStream(outDataFormat, inStream);
      AudioSystem.write(lowResAIS, AudioFileFormat.Type.WAVE, new File(newFilePath));
      asrRes = client.asr(newFilePath, "wav", 16000, map);
    }
    return asrRes.toString();
  }

}

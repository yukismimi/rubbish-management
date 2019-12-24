package com.yukismimi.rubbishmanagement.speechRecognition;

import java.io.IOException;

import com.yukismimi.rubbishmanagement.utils.FileUtils;
import com.yukismimi.rubbishmanagement.utils.SpeechUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.sound.sampled.UnsupportedAudioFileException;

@RestController
@RequestMapping("/ai/speech")
@Api("语音识别")
@CrossOrigin
public class SpeechRecognitionController {
  @Value(value = "${localPath.speech}")
  private String localPath;
  @RequestMapping(value = "getRecordString", method = RequestMethod.POST)
  @ApiOperation("识别接口")
  public String recongnition(MultipartFile file) throws IOException, InterruptedException, UnsupportedAudioFileException {
    // 要上传的目标文件存放路径
    String uploadedFilePath = FileUtils.upload(file, localPath, file.getOriginalFilename());
    JSONObject result = JSONObject.parseObject(SpeechUtil.getInstance().asrLocalSpeech(uploadedFilePath));
    JSONArray jsonArray = result.getJSONArray("result");
    String name = jsonArray.getString(0);
    return name;
  }
}

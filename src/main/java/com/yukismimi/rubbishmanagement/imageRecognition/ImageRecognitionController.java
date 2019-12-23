package com.yukismimi.rubbishmanagement.imageRecognition;

import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yukismimi.rubbishmanagement.api.AipImageClassifyClient;
import com.yukismimi.rubbishmanagement.rubbsh.RubbishServiceImpl;
import com.yukismimi.rubbishmanagement.utils.FileUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ai/image")
@Api("图像识别")
@CrossOrigin
public class ImageRecognitionController {
  @Autowired
  private RubbishServiceImpl rubbishService;
  @Value(value = "${localPath.image}")
  private String localPath;
  @RequestMapping(value = "getRecordString", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  @ApiOperation("识别接口")
  public String recongnition(MultipartFile file) throws IOException, InterruptedException, JSONException {
    // 要上传的目标文件存放路径
    String uploadedFilePath = FileUtils.upload(file, localPath, file.getOriginalFilename());
    JSONObject result = JSONObject.parseObject(AipImageClassifyClient.getInstance().getRecognitionResultByLocalPic(uploadedFilePath));
    JSONArray jsonArray = result.getJSONArray("result");
    JSONObject jsonObject = jsonArray.getJSONObject(0);
    String name = jsonObject.getString("keyword");
    return name;
  }
  
}

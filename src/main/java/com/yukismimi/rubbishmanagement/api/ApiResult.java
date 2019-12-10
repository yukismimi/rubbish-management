package com.yukismimi.rubbishmanagement.api;

import com.yukismimi.rubbishmanagement.rubbsh.Rubbish;
import lombok.Data;

import java.util.List;

@Data
public class ApiResult {

    int code;

    String msg;

    List<Rubbish> newslist;
}

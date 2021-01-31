package com.example.mpvtest.config;

import com.example.mpvtest.model.BaseApi;
import com.example.mpvtest.model.Method;

import java.util.HashMap;
import java.util.Map;

public class ApiConstant {
    public static int REQUEST_TIMEOUT = 60;
    private static String rootUrl = "http://192.168.1.106:9000";
    public static  String masterKey = "0GpiUBqc6oj9l9f70nYc1kG8TNYPHVNQ";
    public static BaseApi login = new BaseApi(rootUrl + "/auth", Method.POST);
}

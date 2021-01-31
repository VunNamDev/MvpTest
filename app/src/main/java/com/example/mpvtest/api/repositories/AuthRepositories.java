package com.example.mpvtest.api.repositories;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.mpvtest.api.ApiClient;
import com.example.mpvtest.config.ApiConstant;
import com.example.mpvtest.interfaces.OnTaskCompleted;
import com.example.mpvtest.util.GsonUtil;
import com.google.gson.Gson;

import java.util.HashMap;

public class AuthRepositories implements _AuthRepositories {

    OnTaskCompleted onTaskCompleted;

    public AuthRepositories() {
    }

    public AuthRepositories(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }


    @Override
    public void login(String userName, String password, Context context) {

        HashMap params = new HashMap<String, String>() {{
            put("Content-Type", "application/x-www-form-urlencoded");
            put("access_token", ApiConstant.masterKey);

        }};
        final String basicAuth = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP);

        HashMap header = new HashMap<String, String>() {{
            put("Authorization", basicAuth);
        }};
        ApiClient.request(ApiConstant.login, params, header, this.onTaskCompleted,context);


    }

    @Override
    public void register() {

    }

    @Override
    public void forgotPassword() {

    }

    @Override
    public void logout() {

    }
}

interface _AuthRepositories {
    void login(String userName, String password, Context context);

    void register();

    void forgotPassword();

    void logout();
}
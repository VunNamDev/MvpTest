package com.example.mpvtest.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mpvtest.config.ApiConstant;
import com.example.mpvtest.interfaces.OnTaskCompleted;
import com.example.mpvtest.model.BaseApi;
import com.example.mpvtest.model.Method;
import com.example.mpvtest.util.GlobalFun;
import com.example.mpvtest.util.GsonUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {

    private static OkHttpClient okHttpClient;
    private static GsonUtil gsonUtil = new GsonUtil();

    public static void  request(BaseApi baseApi,
                                  HashMap<String, String> params,
                                  HashMap<String, String> headers, OnTaskCompleted onTaskCompleted,Context context) {
        initOkHttp(headers);
        ANRequest request = null;
        if (baseApi.getMethod() == Method.GET) {
            request = AndroidNetworking.get(baseApi.getUrl())
//                    .addPathParameter(params)
                    .addQueryParameter(params)//add data to url
                    .addHeaders(headers)
                    .setTag("test")

                    .setPriority(Priority.LOW)
                    .setOkHttpClient(okHttpClient)
                    .build();
        } else if (baseApi.getMethod() == Method.POST) {
            request = AndroidNetworking.post(baseApi.getUrl())
                    .addBodyParameter(params)
                    .addHeaders(headers)
                    .setTag("test")
                    .setPriority(Priority.LOW)
                    .setOkHttpClient(okHttpClient)
                    .build();
        } else if (baseApi.getMethod() == Method.PUT) {
            request = AndroidNetworking.put(baseApi.getUrl())
                    .addPathParameter(params)
                    .addHeaders(headers)
                    .setTag("test")
                    .setPriority(Priority.LOW)
                    .setOkHttpClient(okHttpClient)
                    .build();
        } else if (baseApi.getMethod() == Method.DELETE) {
            request = AndroidNetworking.delete(baseApi.getUrl())
                    .addPathParameter(params)
                    .addHeaders(headers)
                    .setTag("test")
                    .setPriority(Priority.LOW)
                    .setOkHttpClient(okHttpClient)
                    .build();
        }
        Task task = new Task(onTaskCompleted, context);
        task.execute(request);

    }

    private static void initOkHttp(HashMap<String, String> map) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(ApiConstant.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiConstant.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConstant.REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")

                        .addHeader("Content-Type", "application/json");


                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();
    }

    private static class Task extends AsyncTask<ANRequest, Void, HashMap<String, String>> {//param, progress, resrult

        private Exception exception;
        private OnTaskCompleted onTaskCompleted;
        private  Context context;
        private ProgressDialog mProgressDialog;
        private Task() {
        }

        private Task(OnTaskCompleted onTaskCompleted, Context context) {
            this.onTaskCompleted = onTaskCompleted;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog("Wait...");
        }

        @Override
        protected HashMap<String, String> doInBackground(ANRequest... anRequests) {
            HashMap<String, String> map;

            ANResponse response = anRequests[0].executeForJSONObject();
            if (response.isSuccess()) {
                map = new HashMap<String, String>();
                map.put("status", gsonUtil.parseBool(true));
                map.put("data", gsonUtil.parseString(String.valueOf(response.getResult())));
            } else {
                map = new HashMap<String, String>();
                ANError error = response.getError();
                map.put("status", gsonUtil.parseBool(false));
                map.put("errorCode", gsonUtil.parseInt(error.getErrorCode()));
//                getErrorCode = 0 => TIMEOUT
                map.put("errorBody", gsonUtil.parseString(error.getErrorBody()));
            }

            return map;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
            super.onPostExecute(stringStringHashMap);
            hideDialog();
            onTaskCompleted.onTaskCompleted(stringStringHashMap);

        }
        protected void showDialog(String message) {
            if (!isShowingProgressDialog()) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
            }
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }

        public void updateProgressDialogMessage(String message) {
            mProgressDialog.setMessage(message);
        }

        protected void hideDialog() {
            if (isShowingProgressDialog()) {
                mProgressDialog.dismiss();
            }
        }

        protected boolean isShowingProgressDialog() {
            if (mProgressDialog == null) {
                return false;
            }
            return mProgressDialog.isShowing();
        }
    }
}

package com.example.mpvtest.activity.signin;

import android.content.Context;
import android.util.Log;

import com.example.mpvtest.api.repositories.AuthRepositories;
import com.example.mpvtest.interfaces.OnTaskCompleted;
import com.example.mpvtest.util.GsonUtil;

import java.util.HashMap;

public class SignInPresenter implements SignInContract.Presenter {

    SignInPresenter(){

    }
    SignInPresenter(Context context){
        this.context = context;
    }
    AuthRepositories authRepositories;
    GsonUtil gsonUtil = new GsonUtil();
    Context context;
    @Override
    public void handleSignIn(String username, String password) {
        OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(HashMap<String, String> result) {
                if (gsonUtil.getBool(result.get("status"))) {
                    mView.signInSuccess();
                } else {

                    mView.signInFailure("Username or Password not true!");
                }
            }
        };
        authRepositories = new AuthRepositories(onTaskCompleted);
        authRepositories.login(username, password,context);


    }

    private SignInContract.View mView;

    public void setView(SignInContract.View view) {
        mView = view;
    }
}

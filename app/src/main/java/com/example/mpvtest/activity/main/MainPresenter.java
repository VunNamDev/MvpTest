package com.example.mpvtest.activity.main;

public class MainPresenter {
    interface View {
        void signInSuccess();

        void signInFailure(String error);
    }

    interface Presenter {
        void handleSignIn(String username, String password);
    }
}
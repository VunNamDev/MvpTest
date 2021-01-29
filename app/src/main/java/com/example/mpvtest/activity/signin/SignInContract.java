package com.example.mpvtest.activity.signin;

/**
 * Created by TuanNM on 28/10/2018.
 * descripsiont:
 */
public interface SignInContract {
    interface View {
        void signInSuccess();

        void signInFailure(String error);
    }

    interface Presenter {
        void handleSignIn(String username, String password);
    }
}

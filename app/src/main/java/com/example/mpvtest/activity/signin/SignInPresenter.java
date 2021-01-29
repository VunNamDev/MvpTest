package com.example.mpvtest.activity.signin;

/**
 * Created by TuanNM on 28/10/2018.
 * descripsiont:
 */
public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View mView;

    public void setView(SignInContract.View view) {
        mView = view;
    }

    @Override
    public void handleSignIn(String username, String password) {
        if (username.equals("mvpexample") && password.equals("1234")) {
            mView.signInSuccess();
            return;
        }

        mView.signInFailure("Username or Password not true!");
    }
}

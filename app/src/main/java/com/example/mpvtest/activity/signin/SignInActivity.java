package com.example.mpvtest.activity.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mpvtest.R;
import com.example.mpvtest.activity.main.MainActivity;
import com.example.mpvtest.base.BaseActivity;

public class SignInActivity extends BaseActivity implements SignInContract.View,
        View.OnClickListener {

    private EditText mTextUsername;
    private EditText mTextPassword;
    private LinearLayout mButtonSignIn;
    private TextView mButtonSignUp;
    private SignInPresenter mSignInPresenter;
    TextView txtError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initView();
        registerListener();
        initPresenter();
    }

    private void initView() {
        mTextUsername = findViewById(R.id.txtUserName);
        mTextPassword = findViewById(R.id.txtPassword);
        mButtonSignIn = findViewById(R.id.btnLogin);
        mButtonSignUp = findViewById(R.id.btnLogout);
        txtError = findViewById(R.id.txtError);
    }

    private void registerListener() {
        mButtonSignIn.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    private void initPresenter() {
        mSignInPresenter = new SignInPresenter();
        mSignInPresenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnLogout:
//                startActivity(new Intent(this, SignUpActivity.class));
                break;
            default:
                break;
        }
    }

    private void login() {
        String username = mTextUsername.getText().toString();
        String password = mTextPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Username or Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        mSignInPresenter.handleSignIn(username, password);
    }

    @Override
    public void signInSuccess() {
        Toast.makeText(this, "Sign In Success!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void signInFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        txtError.setText(getString(R.string.login_error));
    }
}

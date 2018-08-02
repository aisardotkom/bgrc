package com.bismillah.employee;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bismillah.employee.retrofit.BaseApiService;
import com.bismillah.employee.retrofit.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    BaseApiService baseApiService;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        baseApiService = UtilsApi.getApiService();

    }

    public void loginbtn(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String level = "";

        if (validateLogin(username, password)) {
            doLogin(username, password, level);
        }
    }

    private boolean validateLogin(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username, final String password, final String level) {
        baseApiService.loginRequest(etUsername.getText().toString(), etPassword.getText().toString(), level)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                /*if (jsonObject.optString("admin")) {
                                    Toast.makeText(LoginActivity.this, "ini admin", Toast.LENGTH_LONG).show();

                                     *//*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);*//*
                                } else if (jsonObject.optBoolean("guest", true)) {
                                    Toast.makeText(LoginActivity.this, "ini guest", Toast.LENGTH_LONG).show();
                                }*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }


}
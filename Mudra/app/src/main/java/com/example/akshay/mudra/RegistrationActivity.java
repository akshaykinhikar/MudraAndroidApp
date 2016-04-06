package com.example.akshay.mudra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        Register
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "register Button Clicked");

                JSONObject userRegisterDetail = new JSONObject();
                try {
                    userRegisterDetail.put("userName", ((EditText) findViewById(R.id.et_username)).getText().toString());
                    userRegisterDetail.put("password", ((EditText) findViewById(R.id.et_password)).getText().toString());
                    userRegisterDetail.put("confirmPassword",((EditText) findViewById(R.id.et_password1)).getText().toString());
                    userRegisterDetail.put("firstName",((EditText) findViewById(R.id.et_fname)).getText().toString());
                    userRegisterDetail.put("lastName",((EditText) findViewById(R.id.et_lname)).getText().toString());
                    userRegisterDetail.put("addressLine1",((EditText) findViewById(R.id.et_add_line_1)).getText().toString());
                    userRegisterDetail.put("addressLine2",((EditText) findViewById(R.id.et_add_line_2)).getText().toString());
                    userRegisterDetail.put("city",((EditText) findViewById(R.id.et_city)).getText().toString());
                    userRegisterDetail.put("state",((EditText) findViewById(R.id.et_state)).getText().toString());
                    userRegisterDetail.put("pincode",((EditText) findViewById(R.id.et_pin)).getText());
                    userRegisterDetail.put("email",((EditText) findViewById(R.id.et_email)).getText().toString());
                    userRegisterDetail.put("country",((EditText) findViewById(R.id.et_country)).getText().toString());
                    userRegisterDetail.put("mobileNo0",((EditText) findViewById(R.id.et_mobileNo)).getText());
                    userRegisterDetail.put("mobileNo1",((EditText) findViewById(R.id.et_alternateMobileNo)).getText());

                    AsyncHttpClient registerUser = new AsyncHttpClient();
                    try {
                        JSONObject userInfo = new JSONObject();
                        userInfo.put("userInfo", userRegisterDetail);
                        if(Utility.isNetConnected(getApplicationContext())){
                            registerUser.post(RegistrationActivity.this, "http://192.168.1.113:8080/register_new_user/",
                                    new StringEntity(userInfo.toString()),"application/json",
                                    new JsonHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            super.onSuccess(statusCode, headers, response);
                                            Log.d("msg", "response" + response);
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            super.onFailure(statusCode, headers, responseString, throwable);
                                            Log.d("msg", "error Response" +responseString);
                                        }
                                    });
                        }


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

package com.example.akshay.mudra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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

                JSONObject userInfo = new JSONObject();
                try {
                    userInfo.put("userName", ((EditText) findViewById(R.id.et_username)).getText().toString());
                    userInfo.put("password", ((EditText) findViewById(R.id.et_password)).getText().toString());
                    userInfo.put("confirmPassword",((EditText) findViewById(R.id.et_password1)).getText().toString());
                    userInfo.put("firstName",((EditText) findViewById(R.id.et_fname)).getText().toString());
                    userInfo.put("lastName",((EditText) findViewById(R.id.et_lname)).getText().toString());
                    userInfo.put("addressLine1",((EditText) findViewById(R.id.et_add_line_1)).getText().toString());
                    userInfo.put("addressLine2",((EditText) findViewById(R.id.et_add_line_2)).getText().toString());
                    userInfo.put("city",((EditText) findViewById(R.id.et_city)).getText().toString());
                    userInfo.put("state",((EditText) findViewById(R.id.et_state)).getText().toString());
                    userInfo.put("pincode",((EditText) findViewById(R.id.et_pin)).getText());
                    userInfo.put("email",((EditText) findViewById(R.id.et_email)).getText().toString());
                    userInfo.put("country",((EditText) findViewById(R.id.et_country)).getText().toString());
                    userInfo.put("mobileNo0",((EditText) findViewById(R.id.et_mobileNo)).getText());
                    userInfo.put("mobileNo1",((EditText) findViewById(R.id.et_alternateMobileNo)).getText());

                    AsyncHttpClient registerUser = new AsyncHttpClient();
                    try {
                        registerUser.post(RegistrationActivity.this, "http://192.168.1.125:8000/register_new_user/", new StringEntity(userInfo.toString()),"application/json", new JsonHttpResponseHandler(
                        ));
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

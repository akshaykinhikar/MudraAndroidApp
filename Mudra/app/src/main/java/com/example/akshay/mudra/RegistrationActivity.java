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


                String userName = ((EditText) findViewById(R.id.et_username)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.et_password1)).getText().toString();
                String firstName = ((EditText) findViewById(R.id.et_fname)).getText().toString();
                String lastName = ((EditText) findViewById(R.id.et_lname)).getText().toString();
                String addressLine1 = ((EditText) findViewById(R.id.et_add_line_1)).getText().toString();
                String addressLine2 = ((EditText) findViewById(R.id.et_add_line_2)).getText().toString();
                String city = ((EditText) findViewById(R.id.et_city)).getText().toString();
                String state = ((EditText) findViewById(R.id.et_state)).getText().toString();
                String pincode = ((EditText) findViewById(R.id.et_pin)).getText().toString();
                String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
                String country = ((EditText) findViewById(R.id.et_country)).getText().toString();
                String mobileNo0 = ((EditText) findViewById(R.id.et_mobileNo)).getText().toString();
                String mobileNo1 = ((EditText) findViewById(R.id.et_alternateMobileNo)).getText().toString();
                Log.d("msg", "register Button Clicked " + userName);

                JSONObject registrationobj = new JSONObject();
                try {
                    registrationobj.put("userName", userName);
                    registrationobj.put("password", password);
                    registrationobj.put("confirmPassword",confirmPassword);
                    registrationobj.put("firstName",firstName);
                    registrationobj.put("lastName",lastName);
                    registrationobj.put("addressLine1",addressLine1);
                    registrationobj.put("addressLine2",addressLine2);
                    registrationobj.put("city",city);
                    registrationobj.put("state",state);
                    registrationobj.put("pincode",pincode);
                    registrationobj.put("email",email);
                    registrationobj.put("country",country);
                    registrationobj.put("mobileNo0",mobileNo0);
                    registrationobj.put("mobileNo1",mobileNo1);



                    AsyncHttpClient login = new AsyncHttpClient();
                    try {
                        login.post(RegistrationActivity.this, "http://192.168.1.125:8000/user_login/", new StringEntity(registrationobj.toString()),"application/json", new JsonHttpResponseHandler(
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

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


//                String userName = ;
//                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
//                String confirmPassword = ((EditText) findViewById(R.id.et_password1)).getText().toString();
//                String firstName = ((EditText) findViewById(R.id.et_fname)).getText().toString();
//                String lastName = ((EditText) findViewById(R.id.et_lname)).getText().toString();
//                String addressLine1 = ((EditText) findViewById(R.id.et_add_line_1)).getText().toString();
//                String addressLine2 = ((EditText) findViewById(R.id.et_add_line_2)).getText().toString();
//                String city = ((EditText) findViewById(R.id.et_city)).getText().toString();
//                String state = ((EditText) findViewById(R.id.et_state)).getText().toString();
//                String pincode = ((EditText) findViewById(R.id.et_pin)).getText().toString();
//                String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
//                String country = ((EditText) findViewById(R.id.et_country)).getText().toString();
//                String mobileNo0 = ((EditText) findViewById(R.id.et_mobileNo)).getText().toString();
//                String mobileNo1 = ((EditText) findViewById(R.id.et_alternateMobileNo)).getText().toString();
//                Log.d("msg", "register Button Clicked " + userName);

                JSONObject registrationobj = new JSONObject();
                try {
                    registrationobj.put("userName", ((EditText) findViewById(R.id.et_username)).getText().toString());
                    registrationobj.put("password", ((EditText) findViewById(R.id.et_password)).getText().toString());
                    registrationobj.put("confirmPassword",((EditText) findViewById(R.id.et_password1)).getText().toString());
                    registrationobj.put("firstName",((EditText) findViewById(R.id.et_fname)).getText().toString());
                    registrationobj.put("lastName",((EditText) findViewById(R.id.et_lname)).getText().toString());
                    registrationobj.put("addressLine1",((EditText) findViewById(R.id.et_add_line_1)).getText().toString());
                    registrationobj.put("addressLine2",((EditText) findViewById(R.id.et_add_line_2)).getText().toString());
                    registrationobj.put("city",((EditText) findViewById(R.id.et_city)).getText().toString());
                    registrationobj.put("state",((EditText) findViewById(R.id.et_state)).getText().toString());
                    registrationobj.put("pincode",((EditText) findViewById(R.id.et_pin)).getText().toString());
                    registrationobj.put("email",((EditText) findViewById(R.id.et_email)).getText().toString());
                    registrationobj.put("country",((EditText) findViewById(R.id.et_country)).getText().toString());
                    registrationobj.put("mobileNo0",((EditText) findViewById(R.id.et_mobileNo)).getText().toString());
                    registrationobj.put("mobileNo1",((EditText) findViewById(R.id.et_alternateMobileNo)).getText().toString());

                    AsyncHttpClient registerUser = new AsyncHttpClient();
                    try {
                        registerUser.post(RegistrationActivity.this, "http://192.168.1.125:8000/user_login/", new StringEntity(registrationobj.toString()),"application/json", new JsonHttpResponseHandler(
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

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

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName = (EditText) findViewById(R.id.et_username);
        final EditText passwd = (EditText) findViewById(R.id.et_password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "login Button Clicked");
                String username = userName.getText().toString();
                String password = passwd.getText().toString();

                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("username", username);
                    jsonobj.put("password", password);

                    AsyncHttpClient login = new AsyncHttpClient();
                    try {
                        login.post(LoginActivity.this, "http://192.168.1.125:8000/user_login/", new StringEntity(jsonobj.toString()),"application/json", new JsonHttpResponseHandler(
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

package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akshay.mudra.HomeActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class EditAccountsFormFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public EditAccountsFormFragment() {
        // Required empty public constructor
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String strtext = getArguments().getString("id");
        String newid = null;
        Log.d("EditAcc1", ""+strtext);
        try {
            JSONObject idObj = new JSONObject(strtext);
            newid = (String) idObj.get("id");
            Log.d("editAcc","new id is" +newid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ------------------ Requests  ------------------
        AsyncHttpClient editAccDetails = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getActivity());
        editAccDetails.setCookieStore(myCookieStore);

        if(Utility.isNetConnected(getContext())){
            JSONObject jsonobj = new JSONObject();
            try {
                jsonobj.put("account_id", newid);

//                    ----------------- post id ---------------------

                    editAccDetails.post(getContext(), "http://192.168.1.125:8000/get_account_details/", new StringEntity(jsonobj.toString()),
                            "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    Log.d("EditAcc", "on Success of POST" + response);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                    Log.d("EditAcc", "on Failure of " + responseString);
                                }
                            });

//                    --------------- get account type ----------------
                editAccDetails.get(getContext(), "http://192.168.1.125:8000/get_accounttype_from_db/", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("EditAcc"," get Acc type from db Suucess" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("EditAcc","get Acc type from db failure" +responseString);
                    }
                });

//                ------------------ get groups -----------------------
                editAccDetails.get(getContext(), "http://192.168.1.125:8000/get_groups_from_db/", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("EditAcc"," get groups from db Suucess" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("EditAcc","get groups from db failure" +responseString);
                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        return inflater.inflate(R.layout.popup_add_new_acc, container, false);


    }

      @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

      public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }
}

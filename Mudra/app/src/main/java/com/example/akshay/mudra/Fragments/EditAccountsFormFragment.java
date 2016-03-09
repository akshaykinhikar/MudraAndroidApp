package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akshay.mudra.HomeActivity;
import com.example.akshay.mudra.MainActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class EditAccountsFormFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Spinner spinner_group,spinner_acc;
    View view;
    String newid = null;

    List<String> groupNameList = new ArrayList<String>();
    List<String> accountNameList = new ArrayList<String>();
    Button saveAccountDetail;
    EditText acc_name,alias,fname,lname,addLine1,addLine2,city,state,country,pin,email,mob_no,alt_mob_no, opening_bal;
    JSONObject accountInfo = new JSONObject();

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

        view =  inflater.inflate(R.layout.popup_add_new_acc, container, false);
        acc_name = (EditText) view.findViewById(R.id.et_new_user_acc_name);
        alias = (EditText) view.findViewById(R.id.et_alias_acc);
        fname = (EditText) view.findViewById(R.id.et_fname);
        lname = (EditText) view.findViewById(R.id.et_lname);
        addLine1 = (EditText) view.findViewById(R.id.et_line1);
        addLine2 = (EditText) view.findViewById(R.id.et_line2);
        city = (EditText) view.findViewById(R.id.et_city);
        state = (EditText) view.findViewById(R.id.et_state);
        country= (EditText) view.findViewById(R.id.et_country);
        pin = (EditText) view.findViewById(R.id.et_pin);
        email = (EditText) view.findViewById(R.id.et_email);
        mob_no = (EditText) view.findViewById(R.id.et_mob0);
        alt_mob_no= (EditText) view.findViewById(R.id.et_alter_mob_no);
        opening_bal= (EditText) view.findViewById(R.id.et_opening_balance);


        //      ++++++++++++++ post Info to save +++++++++++++++++++
        saveAccountDetail = (Button) view.findViewById(R.id.saveEditAccForm);

        saveAccountDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient saveEditedAccForm = new AsyncHttpClient();
                PersistentCookieStore myCookieStore = new PersistentCookieStore(getActivity());
                saveEditedAccForm.setCookieStore(myCookieStore);


                if(Utility.isNetConnected(getContext())){

                    try {
                        accountInfo.put("account_name", acc_name.getText().toString());
                        accountInfo.put("alias", alias.getText().toString());
                        accountInfo.put("firstName", fname.getText().toString());
                        accountInfo.put("lastName", lname.getText().toString());
                        accountInfo.put("addressLine1", addLine1.getText().toString());
                        accountInfo.put("addressLine2", addLine2.getText().toString());
                        accountInfo.put("city", city.getText().toString());
                        accountInfo.put("state", state.getText().toString());
                        accountInfo.put("country", country.getText().toString());
                        accountInfo.put("pincode",pin.getText().toString());
                        accountInfo.put("email", email.getText().toString());
                        accountInfo.put("mobileNo0", mob_no.getText().toString());
                        accountInfo.put("mobileNo1", alt_mob_no.getText().toString());
                        accountInfo.put("openingBalance", opening_bal.getText().toString());
                        accountInfo.put("account_id", newid);
                        accountInfo.put("start_date", JSONObject.NULL);
                        accountInfo.put("end_date", JSONObject.NULL);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        //        +++++++++++ Spinner Starts ++++++++
        spinner_group = (Spinner) view.findViewById(R.id.spinner_groupname);
        spinner_acc = (Spinner) view.findViewById(R.id.spinner_acc_type);
        // Spinner click listener
        // spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) spinner);

        // Data from Activity -- id for acc to edit
        String strtext = getArguments().getString("id");

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
                    public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("EditAcc"," get Acc type from db Success" + response);
                        try {
                            Log.d("length", "length is " +response.getJSONArray("accTypeList").length());
                            for(int i = 0; i < response.getJSONArray("accTypeList").length(); i++){
                                accountNameList
                                        .add(response.getJSONArray("accTypeList").getJSONObject(i).getString("choice_name"));
                            Log.d("EditAcc","accountNameList is"+ accountNameList);
                            }
                            // Creating adapter for spinner
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, accountNameList);


                            // Drop down layout style - list view with radio button
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            spinner_acc.setAdapter(dataAdapter);

                            spinner_acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    try {
                                        accountInfo.put("accounttype", response.getJSONArray("accTypeList").getString(position));
                                        Toast.makeText(getActivity(), "" + response.getJSONArray("accTypeList").getString(position).toString(), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                    public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {

                            Log.d("EditAcc", " get groups " + response.getJSONArray("accGroupList"));
                            for(int i = 0; i <response.getJSONArray("accGroupList").length(); i++){
                                groupNameList
                                        .add(response.getJSONArray("accGroupList").getJSONObject(i).getString("choice_name"));
                            }
                            // Creating adapter for spinner
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, groupNameList);

                            // Drop down layout style - list view with radio button
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            spinner_group.setAdapter(dataAdapter);

                            spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
//                                    Toast.makeText(getActivity(), "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                                    try {
                                        Toast.makeText(getActivity(), "" + response.getJSONArray("accGroupList").getString(position), Toast.LENGTH_SHORT).show();
                                        accountInfo.put("group", response.getJSONArray("accGroupList").getString(position));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            //++++++++++++ Spinner Ends +++++++++

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d("EditAcc"," get groups from db Success" + response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        return view;
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

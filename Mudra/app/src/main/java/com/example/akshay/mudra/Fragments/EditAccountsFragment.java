package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.akshay.mudra.HomeActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EditAccountsFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    List<String> account_name = new ArrayList<>();
    List<String> account_amount = new ArrayList<>();

    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    public EditAccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AsyncHttpClient accountsReq = new AsyncHttpClient();
        PersistentCookieStore account_cookies = new PersistentCookieStore(getContext());
        accountsReq.setCookieStore(account_cookies);

        try {
            if(Utility.isNetConnected(getContext())){
                accountsReq.get("http://192.168.1.125:8000/show_account_details/", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            int arrayLength = response.getJSONArray("accountList").length();
                            for(int i = 0; i < arrayLength; i++){
                                account_name.add(response.getJSONArray("accountList").getJSONObject(i).getString("account_name"));
                                account_amount.add(response.getJSONArray("accountList").getJSONObject(i).getString("amount"));
                                Log.d("edit_acc_fra", "" + account_name + account_amount);
                            }
                            setAccountsDetail();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("msg","responseString" +responseString);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_accounts, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position).get("acc"), Toast.LENGTH_SHORT).show();
                ((HomeActivity)getActivity()).editAccountFragmentInterface();
            }
        });
    }

    public interface EditAccountFragmentInterface{
        void editAccountFragmentInterface();
    }

    public void setAccountsDetail(){
        HashMap<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < account_name.size(); i++ ){
            map = new HashMap<String, String>();
            map.put("acc_name",account_name.get(i));
            map.put("acc_amount",account_amount.get(i));
            data.add(map);
        }
        //keys in map
        String[] from = {"acc_name", "acc_amount"};
        int[] to = {R.id.acc_name, R.id.acc_amount};
        adapter = new SimpleAdapter(getActivity(), data, R.layout.account_list_for_edit, from, to);
        setListAdapter(adapter);
    }


     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }
}

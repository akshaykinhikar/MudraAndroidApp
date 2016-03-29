package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.mudra.HomeActivity;
import com.example.akshay.mudra.LoginActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.entity.StringEntity;


public class TransactionDetailFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strtext = getArguments().getString("data");
        try {
            JSONObject ObjectFromActivity = new JSONObject(strtext);
            String id = (String) ObjectFromActivity.get("id");
            Log.d("msg", "data from activity" + id);
            JSONObject getTransacDetailObj = new JSONObject();
            getTransacDetailObj.put("account_id", id);
            getTransacDetailObj.put("start_date", JSONObject.NULL);

//            +++++++++++++++++++++++++++++++++++++++++++++++
//            ++++++ REQ For Getting Transaction Data +++++++
//            +++++++++++++++++++++++++++++++++++++++++++++++
            //for cookies
            AsyncHttpClient login = new AsyncHttpClient();
            PersistentCookieStore tranCookies = new PersistentCookieStore(getContext());
            login.setCookieStore(tranCookies);
//            myCookieStore = new PersistentCookieStore(getActivity());
//            login.setCookieStore(myCookieStore);
            try {
                if (Utility.isNetConnected(getContext())) {
                    login.post(getActivity(), "http://192.168.1.125:8000//show_transactions_of_single_account/",
                            new StringEntity(getTransacDetailObj.toString()),
                            "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try {
                                        setData(response.getJSONArray("transactionList"));
                                        Log.d("msg", "onSuccess" + response.toString(4));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                    Log.d("msg", "onFailure" + responseString + statusCode);
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            +++++++++++++++++++++++++++++++++++++++++++++++++


       } catch (JSONException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        //prepareListData();

        // Inflate the layout for this fragment
        return view;
    }

    public void setData(JSONArray data){
        listAdapter = new ExpandableListAdapter(data);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public class ExpandableListAdapter extends BaseExpandableListAdapter {

     private JSONArray data;



        public ExpandableListAdapter(JSONArray data) {
            this.data = data;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, null);
            }

            TextView accnt = (TextView) convertView
                    .findViewById(R.id.lblListItem_acc);

            TextView amt = (TextView) convertView.findViewById(R.id.lblListItem_amt);

            TextView credit_debit = (TextView) convertView.findViewById(R.id.lblListItem_deb_cred);

            try {
                accnt.setText(
                        data.getJSONObject(groupPosition).getJSONArray("transaction_record_list")
                .getJSONObject(childPosition).getString("account_name"));

                amt.setText(
                        data.getJSONObject(groupPosition).getJSONArray("transaction_record_list")
                                .getJSONObject(childPosition).getInt("amount")+""
                );

                credit_debit.setText(
                        data.getJSONObject(groupPosition).getJSONArray("transaction_record_list")
                                .getJSONObject(childPosition).getBoolean("is_debit")
                        ? "True" : "False"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public int getGroupCount() {
            return data.length();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            try {
                return data.getJSONObject(groupPosition).getJSONArray("transaction_record_list").length();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.transaction_list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader_t_type);
            TextView date = (TextView) convertView.findViewById(R.id.lblListHeader_t_date);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            try {
                lblListHeader.setText(data.getJSONObject(groupPosition).getString("transactiontype"));
                date.setText(new Date(data.getJSONObject(groupPosition).getLong("transaction_date")).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }




}
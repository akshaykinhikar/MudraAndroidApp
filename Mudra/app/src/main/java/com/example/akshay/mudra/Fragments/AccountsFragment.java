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
import android.widget.ListView;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AccountsFragment extends ListFragment {
    ListView listView;
//    private OnFragmentInteractionListener mListener;
    List<String> account_name = new ArrayList<>();
    List<String> account_amount = new ArrayList<>();
    List<Integer> id = new ArrayList<>();


    ArrayList<HashMap <String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AsyncHttpClient accountsReq = new AsyncHttpClient();
        PersistentCookieStore accounts_cookies = new PersistentCookieStore(getContext());
        accountsReq.setCookieStore(accounts_cookies);
        try {
            if (Utility.isNetConnected(getContext())) {
                accountsReq.get("http://192.168.1.116:8001/show_account_details/", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("acc_fra", "accountsReq Res " + response);
                        try {
                            int arrayLength = response.getJSONArray("accountList").length();
                            if (arrayLength != 0) {
                                for (int i = 0; i < arrayLength; i++) {
                                    account_name.add(response.getJSONArray("accountList").getJSONObject(i).getString("account_name"));
                                    account_amount.add(response.getJSONArray("accountList").getJSONObject(i).getString("amount"));
                                    id.add((Integer) response.getJSONArray("accountList").getJSONObject(i).get("id"));

                                    Log.d("acc_fra", "in For " + account_name + account_amount + id);
                                }
                                setAccountsDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("acc_fra", "accountReq ErrRes");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_accounts, container, false);

        return v;

    }


    public void setAccountsDetail(){
        HashMap<String, String> map = new HashMap<String, String>();

        for(int i = 0; i < account_name.size(); i++ ){
            map = new HashMap<String, String>();
            map.put("acc_name",account_name.get(i));
            map.put("acc_amount",account_amount.get(i));
            map.put("id",id.get(i).toString());

            data.add(map);
        }

        //keys in map
        String[] from = {"acc_name", "acc_amount"};

        int[] to = {R.id.years, R.id.years1};

        adapter = new SimpleAdapter(getActivity(), data, R.layout.model, from, to);
        setListAdapter(adapter);
    }

 
    @Override
    public void onStart() {

        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), data.get(position).get("id") , Toast.LENGTH_SHORT).show();
                JSONObject ObjToActivity =  new JSONObject();
                try {
                    ObjToActivity.put("id",data.get(position).get("id"));
                    ObjToActivity.put("start_date",data.get(position).get("start_date"));
                } catch (JSONException e) {
                    Log.i("acc_frag","data empty");
                    e.printStackTrace();
                }
                Log.d("acc_fra", "myAccount");
                ((HomeActivity)getActivity()).onFragmentInteraction(ObjToActivity.toString());
                ((HomeActivity) getActivity()).fragmentTransactionInterface();

            }
        });
    }

    public interface FragmentTransactionInterface{
        void fragmentTransactionInterface();
    }

        public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }
}

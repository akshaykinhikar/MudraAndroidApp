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

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeFragment extends ListFragment {

    ListView listView;
    List< Long> accStartYear = new ArrayList<>();
    List< Long> accEndYear = new ArrayList<>();

//    String[] accStartYear = ;
//    String[] accEndYear;
    ArrayList<HashMap<String, Date>> data = new ArrayList<>();
    SimpleAdapter adapter;
    Context context;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AsyncHttpClient financial_year = new AsyncHttpClient();
        PersistentCookieStore financial_yearCookies = new PersistentCookieStore(getContext());
        financial_year.setCookieStore(financial_yearCookies);
        try {
            if (Utility.isNetConnected(getContext())){
                financial_year.get("http://192.168.1.125:8000/list_of_accounting_years/",new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("accYear", "response" + response);
                        try {
                            int arrayLength = response.getJSONArray("AccYearsList").length();
                            if (arrayLength != 0){

                                for(int i=0;i< arrayLength ;i++)
                                {
                                    accStartYear.add(response.getJSONArray("AccYearsList").getJSONObject(i).getLong("start_date"));
                                    accEndYear.add(response.getJSONArray("AccYearsList").getJSONObject(i).getLong("end_date"));

                                    Log.d("accStartYear", "" + accStartYear + accEndYear);
                                    // process data here
                                }
                                setAccountYear();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("msg","Error Response" +responseString);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void setAccountYear(){
        HashMap<String, Date> map =  new HashMap<>();
        map = new HashMap<>();
        Log.d("acc","Size " + accStartYear.size());
        for(int i = 0; i < accStartYear.size(); i++){
            map.put("startYear", new Date(accStartYear.get(i)));
            map.put("endYear", new Date(accEndYear.get(i)));
//            map.put("endYear",accEndYear.get(i));
            data.add(map);
        }

        String[] from = {"startYear", "endYear"};
        int[] to = {R.id.start_date, R.id.end_date};
        adapter = new SimpleAdapter(getActivity(), data, R.layout.accounting_year_item, from, to);
        setListAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), ""+data.get(position).get("startYear"), Toast.LENGTH_SHORT).show();
                ((HomeActivity)getActivity()).fragmentAccountsInterface();
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface FragmentAccountsInterface{
        void fragmentAccountsInterface();
    }
}

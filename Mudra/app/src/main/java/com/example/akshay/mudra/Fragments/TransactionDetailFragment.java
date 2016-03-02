package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.akshay.mudra.R;

import java.util.ArrayList;
import java.util.HashMap;


public class TransactionDetailFragment extends ListFragment {
    ListView listView;
    private OnFragmentInteractionListener mListener;
    String[] transaction_type = {"1","2","3","4"};
    String[] transaction_date = {"1","2","3","4"};
    String[] transaction_description = {"1","2","3","4"};
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
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
        HashMap<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < transaction_date.length; i++ ){
            map = new HashMap<String, String>();
            map.put("transaction_type",transcd action_type[i]);
            map.put("transaction_date",transaction_date[i]);
            map.put("transaction_description",transaction_description[i]);
            data.add(map);
        }
        //keys in map
        String[] from = {"transaction_type", "transaction_date","transaction_description"};
        int[] to = {R.id.tv_tra, R.id.tv_date,R.id.tv_description_holder};
        adapter = new SimpleAdapter(getActivity(), data, R.layout.transaction_item, from, to);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position).get("transaction_date"), Toast.LENGTH_SHORT).show();
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
}

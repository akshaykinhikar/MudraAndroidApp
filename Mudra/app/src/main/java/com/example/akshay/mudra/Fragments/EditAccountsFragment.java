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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.akshay.mudra.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EditAccountsFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    String[] Acc = {"a","b","c","d"};
    String[] Amount = {"A", "B", "C", "D"};

    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    public EditAccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HashMap<String, String> map = new HashMap<String, String>();
        for(int i=0; i<Acc.length; i++){
            map = new HashMap<String, String>();
            map.put("acc", Acc[i]);
            map.put("amount", Amount[i]);
            data.add(map);
        }

        String[] from = {"acc", "amount"};
        int[] to ={R.id.acc_name, R.id.acc_amount};
        adapter = new SimpleAdapter(getActivity(), data, R.layout.account_list_for_edit, from, to);
        setListAdapter(adapter);

      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_accounts, container, false);
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
                Toast.makeText(getActivity(), data.get(position).get("acc"), Toast.LENGTH_SHORT).show();
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

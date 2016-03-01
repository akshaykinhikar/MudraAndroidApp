package com.example.akshay.mudra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.akshay.mudra.HomeActivity;
import com.example.akshay.mudra.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeFragment extends ListFragment {

    ListView listView;
    String[] accStartYear = {"1","2", "3","4"};
    String[] accEndYear = {"1","2", "3","4"};
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;


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
        HashMap<String, String> map =  new HashMap<String,String>();
        for(int i =0; i<accStartYear.length; i++){
            map = new HashMap<String, String>();
            map.put("startYear",accStartYear[i]);
            map.put("endYear",accEndYear[i]);
            data.add(map);
        }

        String[] from = {"startYear", "endYear"};
        int[] to = {R.id.start_date, R.id.end_date};
        adapter = new SimpleAdapter(getActivity(), data, R.layout.accounting_year_item, from, to);
        setListAdapter(adapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position).get("startYear"), Toast.LENGTH_SHORT).show();
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

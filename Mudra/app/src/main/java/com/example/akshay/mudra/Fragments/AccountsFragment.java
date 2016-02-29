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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AccountsFragment extends ListFragment {
    ListView listView;
    private OnFragmentInteractionListener mListener;
    String[] years = {"1","2","3","4"};
    String[] years1 = {"1","2","3","4"};
    ArrayList<HashMap <String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       HashMap<String, String> map = new HashMap<String, String>();

        for(int i = 0; i < years.length; i++ ){
            map = new HashMap<String, String>();
            map.put("years",years[i]);
            map.put("years1",years1[i]);
            data.add(map);
        }

        //keys in map
        String[] from = {"years", "years1"};

        int[] to = {R.id.years, R.id.years1};

        adapter = new SimpleAdapter(getActivity(), data, R.layout.model, from, to);
        setListAdapter(adapter);

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_accounts, container, false);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {

        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position).get("years") , Toast.LENGTH_SHORT).show();
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

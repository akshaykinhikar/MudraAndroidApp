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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akshay.mudra.LoginActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EventAccountingFragment extends Fragment {

    Spinner spinner_transaction_mode,spinner_account_action_credit_debit, spinner_select_account;
    EditText enter_ammount;

    private OnFragmentInteractionListener mListener;

    // Spinner Drop down elements
    List<String> transaction_modeList = new ArrayList<String>();
    List<String> action_credit_debit =  new ArrayList<>();
    List<String> account_list =  new ArrayList<>();

    public EventAccountingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_accounting, container, false);

        spinner_transaction_mode = (Spinner) view.findViewById(R.id.spinner_transaction_mode);
        spinner_account_action_credit_debit = (Spinner) view.findViewById(R.id.spinner_credit_debit);
        spinner_select_account = (Spinner) view.findViewById(R.id.spinner_account_name);

        enter_ammount = (EditText) view.findViewById(R.id.et_enter_ammount);
        

        Log.d("eventAcc"," spinner to show" +transaction_modeList);

//        =================================================================
//        ================= GET Transaction Mode===========================
//        =================================================================

        //for cookies
        AsyncHttpClient eventAcc = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getActivity());
        eventAcc.setCookieStore(myCookieStore);

        if (Utility.isNetConnected(getContext())) {
               eventAcc.get(getActivity(), "http://192.168.1.125:8000/get_transactiontype_from_db/", new JsonHttpResponseHandler() {
                   @Override
                   public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                       super.onSuccess(statusCode, headers, response);

                       Log.d("eventAcc", "Transaction Mode Success" + response);
                       try {
                           Log.d("eventAcc", "Transaction Mode Success" + response.getJSONArray("TransactionTypeList"));
                           int array_tran_list = response.getJSONArray("TransactionTypeList").length();
                           for (int i = 0; i < array_tran_list; i++) {
                               transaction_modeList.add(response.getJSONArray("TransactionTypeList").getJSONObject(i).getString("choice_name"));
                           }
                           Log.d("array", "" + transaction_modeList);
                           ArrayAdapter<String> transaction_mode_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, transaction_modeList);

                           transaction_mode_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                           spinner_transaction_mode.setAdapter(transaction_mode_adapter);

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }

                   @Override
                   public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                       super.onFailure(statusCode, headers, responseString, throwable);
                       Log.d("entAcc", "Transaction mode failure" + responseString);
                   }
               });
//            =============================================
//            ======   GET ACCOUNT   ======================
//            =============================================
            eventAcc.get(getActivity(), "http://192.168.1.125:8000/show_account_details/", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Log.d("eventAcc", "Acc name Success" + response);
                    try {
                        int acc_list_array = response.getJSONArray("accountList").length();
                        for (int i = 0; i < acc_list_array; i++) {
                            account_list.add(response.getJSONArray("accountList").getJSONObject(i).getString("account_name"));
                        }
                        Log.d("eventAcc", "acc list in array" + account_list);
                        ArrayAdapter<String> selected_acc_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, account_list);

                        selected_acc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner_select_account.setAdapter(selected_acc_adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("entAcc", "Acc mode failure" + responseString);
                }
            });
        }
//        ++++++++++++++++++++++++++++++++++++++++++++++++
//        ++++++++++++++++++++++++++++++++++++++++++++++++
//        ++++++++++++++++++++++++++++++++++++++++++++++++
//        listener for spinner_transaction_mode
        spinner_transaction_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(getActivity(), "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        LIstener for account
        spinner_select_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner credit or Debit
        action_credit_debit.add("Credit");
        action_credit_debit.add("debit");

        ArrayAdapter<String> adapter_credit_debit = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, action_credit_debit);

        adapter_credit_debit.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_account_action_credit_debit.setAdapter(adapter_credit_debit);


        return view;
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

     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }
}

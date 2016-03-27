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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akshay.mudra.LoginActivity;
import com.example.akshay.mudra.R;
import com.example.akshay.mudra.Utility.Utility;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static com.example.akshay.mudra.R.id.btn_add_more_acc;

public class EventAccountingFragment extends ListFragment {

    Spinner spinner_transaction_mode,spinner_account_action_credit_debit, spinner_select_account;
    EditText enter_ammount, description;
    Button selectDate, btn_save_acc_tran, btn_save_current_transaction;
    public String value_selected_amount,value_is_credit_debit;
    JSONArray acc_list_to_send = new JSONArray();
    int count_acc_list_to_send = 0;

    JSONObject singleAccountTransaction = new JSONObject();

    private OnFragmentInteractionListener mListener;

    // Spinner Drop down elements
    List<String> transaction_modeList = new ArrayList<String>();
    List<String> action_credit_debit =  new ArrayList<>();
    List<String> account_list =  new ArrayList<>();

    private String TAG_DATE_PICKER_DIALOG = "TAG_DATE_PICKER_DIALOG"  ;

    ArrayList<String> accountArrayListSingleObj = new ArrayList<>();

    JSONObject selectedAcc = new JSONObject();

    JSONObject objAccResponseServer;


//    =============================
//    =======  List ===========
//    =============================

    String[] accountActionCD = new String[] {
            "Debit",
            "Credit"
    };

    String[] accountName = new String[]{
            "Cash",
            "cash1"
    };

    String[] accountAmmount = new String[]{
            "1000",
            "2000"
    };


    public EventAccountingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_accounting, container, false);

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm = new HashMap<String, String>();
        for(int i= 0; i<2; i++){
            hm = new HashMap<String, String>();
            hm.put("txt", accountActionCD[i]);
            hm.put("cur",accountName[i]);
            hm.put("amount",accountAmmount[i]);
            data.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "txt","cur", "amount"};

        // Ids of views in listview_layout
        int[] to = { R.id.textView1,R.id.textView2, R.id.textView3};

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.transaction_acc_item, from, to);

        setListAdapter(adapter);

        //list for added account transaction

        spinner_transaction_mode = (Spinner) view.findViewById(R.id.spinner_transaction_mode);
        spinner_account_action_credit_debit = (Spinner) view.findViewById(R.id.spinner_credit_debit);
        spinner_select_account = (Spinner) view.findViewById(R.id.spinner_account_name);

        enter_ammount = (EditText) view.findViewById(R.id.et_enter_ammount);
//        value_selected_amount = enter_ammount.getText().toString();

        selectDate = (Button) view.findViewById(R.id.btn_select_date);
        description = (EditText) view.findViewById(R.id.et_description);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) getActivity(),2015,9,5);
                datePickerDialog.show(getFragmentManager(), TAG_DATE_PICKER_DIALOG);
            }


        });

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
                    objAccResponseServer = response;

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
                Log.d("eventAcc", "transactionMode" + parent.getItemAtPosition(position).toString());
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
                Toast.makeText(getContext(), "" + parent, Toast.LENGTH_SHORT).show();
                try {
                    Log.d("eventAcc", "objAccResponseServer array" + objAccResponseServer.getJSONArray("accountList").getJSONObject(position));
//                    selectedAcc.put("account", objAccResponseServer.getJSONArray("accountList").getJSONObject(position));
                    singleAccountTransaction.put("account", objAccResponseServer.getJSONArray("accountList").getJSONObject(position));

//                          account: {amount: "1878 Cr", id: 1, account_name: "My Bank Account"}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                selectedAcc.put("account", )
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

        spinner_account_action_credit_debit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("eventAcc", "Selected Item" + parent.getItemAtPosition(position));
                if (position == 0) {
                    value_is_credit_debit = "C";
                    Log.d("eventAcc", "Credit");
                } else {
                    value_is_credit_debit = "D";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_save_current_transaction = (Button) view.findViewById(R.id.btn_add_event_acc);
        btn_save_current_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("eventAcc","save tran btn");
                JSONObject tranObjWithDateTime = new JSONObject();
                try {
                    tranObjWithDateTime.put("Acc_list",singleAccountTransaction);
                    tranObjWithDateTime.put("transaction_date","2012012");
                    tranObjWithDateTime.put("description","lol");
                    Log.d("eventAcc", "tranObjWithDateTime is "+tranObjWithDateTime);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

//      Add obj to Acc Transaction List
        btn_save_acc_tran = (Button) view.findViewById(btn_add_more_acc);

        btn_save_acc_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    singleAccountTransaction.put("is_debit",value_is_credit_debit);
                    singleAccountTransaction.put("amount", enter_ammount.getText().toString());
                    Log.d("eventAcc", "amount is " + enter_ammount.getText().toString());
                    //singleAccountTransaction.put("account", selectedAcc);
                    Log.d("eventAcc", "obj2" + singleAccountTransaction);

                    if( enter_ammount.getText().toString() != null && Integer.parseInt(enter_ammount.getText().toString())> -1){
                        acc_list_to_send.put(singleAccountTransaction);
                        Log.d("eventAcc", "add button" + count_acc_list_to_send + acc_list_to_send.toString(4));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                accountArrayListSingleObj.add("Acc_list", "");




            }
        });


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

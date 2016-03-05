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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
                    login.post(getActivity(), "http://192.168.1.125:8000/show_transactions_of_single_account/", new StringEntity(getTransacDetailObj.toString()),
                            "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                     Log.d("msg", "onSuccess" + response + statusCode);
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


        List<ParentClass> parentClasses = new ArrayList<>();
        parentClasses.add(new ParentClass("Saving","12 JAN 2014"));
        parentClasses.add(new ParentClass("Current","15 JAN 2014"));
        parentClasses.add(new ParentClass("Saving","22 JAN 2014"));

        List<ChildClass> childClasses = new ArrayList<>();
        List<AssociatedAccounts> associatedAccountses = new ArrayList<>();
        associatedAccountses.add(new AssociatedAccounts("Nishant's Account","1000","Debit"));
        associatedAccountses.add(new AssociatedAccounts("Vinay's Account","2000","Debit"));
        associatedAccountses.add(new AssociatedAccounts("Akshay's Account","3000","Credit"));

        childClasses.add(new ChildClass(associatedAccountses));
        childClasses.add(new ChildClass(associatedAccountses));
        childClasses.add(new ChildClass(associatedAccountses));


        listAdapter = new ExpandableListAdapter(parentClasses,childClasses);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Inflate the layout for this fragment
        return view;
    }

    


    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String obj);
    }


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private List<ParentClass> parentClasses;
        private List<ChildClass> childClasses;



        public ExpandableListAdapter(List<ParentClass> parentClasses,List<ChildClass> childClasses) {
            this.parentClasses = parentClasses;
            this.childClasses = childClasses;

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

            accnt.setText(childClasses.get(groupPosition).associatedAccountsList.get(childPosition).getAccount());

            amt.setText(childClasses.get(groupPosition).associatedAccountsList.get(childPosition).getAmount());

            credit_debit.setText(childClasses.get(groupPosition).associatedAccountsList.get(childPosition).getCredit_debit());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public int getGroupCount() {
            return parentClasses.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childClasses.size();
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
            lblListHeader.setText(parentClasses.get(groupPosition).getTranAccountType());
            date.setText(parentClasses.get(groupPosition).getTranAccountDate());

            return convertView;
        }
    }

    public class ParentClass{
        public String getTranAccountType() {
            return tranAccountType;
        }

        public ParentClass(String tranAccountType,String tranAccountDate ){
            this.tranAccountDate = tranAccountDate;
            this.tranAccountType = tranAccountType;
        }

        public String getTranAccountDate() {
            return tranAccountDate;
        }

        private String tranAccountType;
        private String tranAccountDate;
    }

//    ++++++++++++++++++++++++++++++++++++++++++
    public class ChildClass{

    private List<AssociatedAccounts> associatedAccountsList = new ArrayList<>();

    public ChildClass(List<AssociatedAccounts> associatedAccountses){
        this.associatedAccountsList = associatedAccountses;
    }
    }

    public class AssociatedAccounts{
        private String account;
        private  String amount;
        private  String credit_debit;

        public String getAccount() {
            return account;
        }

        public String getAmount() {
            return amount;
        }

        public String getCredit_debit() {
            return credit_debit;
        }

        public AssociatedAccounts(String account, String amount, String credit_debit ){
            this.account = account;
            this.amount = amount;
            this.credit_debit = credit_debit;


        }
    }


}
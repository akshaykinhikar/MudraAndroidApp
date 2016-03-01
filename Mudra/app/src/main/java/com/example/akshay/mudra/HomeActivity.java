package com.example.akshay.mudra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.akshay.mudra.Fragments.AccountsFragment;
import com.example.akshay.mudra.Fragments.ActivitiesFragment;
import com.example.akshay.mudra.Fragments.EditAccountsFormFragment;
import com.example.akshay.mudra.Fragments.EditAccountsFragment;
import com.example.akshay.mudra.Fragments.EventAccountingFragment;
import com.example.akshay.mudra.Fragments.HomeFragment;
import com.example.akshay.mudra.Fragments.TransactionDetailFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,AccountsFragment.OnFragmentInteractionListener, ActivitiesFragment.OnFragmentInteractionListener,EditAccountsFragment.OnFragmentInteractionListener,EventAccountingFragment.OnFragmentInteractionListener, AccountsFragment.FragmentTransactionInterface, EditAccountsFragment.EditAccountFragmentInterface {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Fragment attached
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeFragment, new HomeFragment(), "homeFragment");
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menu_addAccount){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add New Account");
            builder.setView(getLayoutInflater().inflate(R.layout.popup_add_new_acc,null,false));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "okk button clicked");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "Cancel button clicked");
                }
            });
            builder.create().show();


        }
        if (id == R.id.menu_addGroup){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Group");
            builder.setView(getLayoutInflater().inflate(R.layout.popup_add_group,null,false));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "okk button clicked");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "Cancel button clicked");
                }
            });
            builder.create().show();

        }
        if (id == R.id.menu_addYear){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Year");
            builder.setView(getLayoutInflater().inflate(R.layout.popup_year,null,false));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "okk button clicked");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("msg", "Cancel button clicked");
                }
            });
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myAccount) {
            Log.d("msg","myAccount");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, new HomeFragment(), "homeFragment");
            fragmentTransaction.commit();

            // Handle the camera action
        } else if (id == R.id.accounts) {
            Log.d("msg","Accounts");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, new AccountsFragment(), "accountsFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.editAccounts) {
            Log.d("msg","myeditAccounts");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, new EditAccountsFragment(), "editAccountsFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.eventAccounting) {
            Log.d("msg","myeventAccounting");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, new EventAccountingFragment(), "eventAccountingFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.activities) {
            Log.d("msg","myActivities");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, new ActivitiesFragment(), "activitiesFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void fragmentTransactionInterface() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFragment, new TransactionDetailFragment(), "homeFragment");
        fragmentTransaction.commit();

    }

    @Override
    public void editAccountFragmentInterface() {
        Log.d("msg", "editAccountFragmentInterface");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFragment, new EditAccountsFormFragment(), "homeFragment");
        fragmentTransaction.commit();
        Log.d("msg", "Afteer Ataching editAccountFragmentInterface");

    }
}

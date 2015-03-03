package com.example.lin.lin_homework_v01;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DisplayMessageActivity extends ActionBarActivity implements DetailFragment.OnFragmentInteractionListener{

    private static final String TAG = "DisplayMessageActivity";
    int cur_p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "Display Message Activity starts...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        /*receive message coming from other activity, main activity or Display Post List Activity*/
        Intent intent = getIntent();
        cur_p = (int)intent.getSerializableExtra(MainActivity.SHOW_POST_MESSAGE);

        /* start detail fragment, replace detail_Container with detailFragment */
        DetailFragment detailFrag = new DetailFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.detail_Container, detailFrag);

        /* transmit the position to detail fragment*/
        Bundle bundle = new Bundle();
        bundle.putInt("position_communication", cur_p);
        detailFrag.setArguments(bundle);

        fragmentTransaction.commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) {

    }

}

package com.example.lin.lin_homework_v01;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity
        implements ListFragment.OnFragmentInteractionListener,
                   DetailFragment.OnFragmentInteractionListener,
                   AddFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActitivity";

    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static boolean Update = true;

    static boolean isTablet = false;
    ListFragment listFrag = new ListFragment();
    //DetailFragment detailFragment = new DetailFragment();
    PostReader post_reader = new PostReader();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {

            this.update();
            handler.postDelayed(this, 1000);// update every 1000 ms


        }
        void update() {
            /*when posts are refreshed, Update will be set true*/
            //Log.d(TAG, "posts size: " + posts.size());
            if(Update == true)
            {
                //posts = post_reader.GetPosts();
                Log.d(TAG, "update begins...post size: " + posts.size() );
                //listFrag.update(posts);
                Log.d(TAG,"trying to get data from server...");
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                //Log.d(TAG, "ConnectivityManager getActiveNetworkInfo done");

                if (networkInfo != null && networkInfo.isConnected()) {

                    Log.d(TAG, "connected to the Internet!");
                    ArrayList<Post> m_posts = new ArrayList<Post>();
                    m_posts  = post_reader.GetPosts("http://forum.openium.fr/api/posts");
                    Log.d(TAG, "get posts: " + m_posts.size());

                    if(m_posts.size() > 0)
                    {

                        posts = m_posts;
                        listFrag.update(posts);
                        Update = false;
                    }


                } else {
                    Log.d(TAG, "Failed to connect to the Internet!");
                    // display error
                }
                Log.d(TAG, "update ends...");
            }

        }
    };


    public final static String SHOW_POST_MESSAGE = "show_post_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IsiForum_v3");

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.listContainer,listFrag);


        if(findViewById(R.id.detailContainer) == null)
        {
            Log.d(TAG, "this is phone");
            isTablet = false;

        }else
        {
            Log.d(TAG, "this is tablet");
            isTablet = true;
            /*start add fragment*/
            AddFragment addFragment = new AddFragment();
            fragmentTransaction.replace(R.id.detail_Container,addFragment);

        }
        fragmentTransaction.commit();
        Update = true;
        handler.postDelayed(runnable, 100);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //stop refresh
        super.onDestroy();
    }

    /* receive position from list fragment*/
    public void OnItemClick(int pos)
    {

        Log.d(TAG, "MainActivity receive: " + pos + " from ListFragment");

        if(!isTablet) // phone
        {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(MainActivity.SHOW_POST_MESSAGE, pos);
            startActivity(intent);

        }else {
            Log.d(TAG, "position: " + pos + ", start creating detail fragment...");

            DetailFragment detailFragment = new DetailFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putInt("position_communication", pos);
            detailFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.detailContainer, detailFragment);
            fragmentTransaction.commit();

        }

    }


    public void onFragmentInteraction(Uri uri) {

    }

}

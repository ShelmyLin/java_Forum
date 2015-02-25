package com.example.lin.lin_homework_v01;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActitivity";

    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static boolean Update = true;
    private static boolean readJsonDone = false;
    private ListView list_view;

    PostReader post_reader = new PostReader();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            //Log.d(TAG, "Runnable run() starts...");

            this.update();
            handler.postDelayed(this, 1000*2);// update every 0.5 seconds

            //Log.d(TAG, "Runnable run() ends...");

        }
        void update() {
            /*when posts are refreshed, Update will be set true*/
            Log.d(TAG, "posts size: " + posts.size());
            if(Update == true) {
                //posts = post_reader.GetPosts();
                Log.d(TAG, "update begins...post size: " + posts.size() );
                Set_Posts_To_List_View(posts);
                Log.d(TAG,"trying to get data from server...");
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                //Log.d(TAG, "ConnectivityManager getActiveNetworkInfo done");

                if (networkInfo != null && networkInfo.isConnected()) {

                    Log.d(TAG, "connected to the Internet!");
                    ArrayList<Post> m_posts = new ArrayList<Post>();
                    m_posts  = post_reader.GetPosts("http://forum.openium.fr/api/posts");

                    if(m_posts.size() > 0)
                    {
                        posts = m_posts;
                        Set_Posts_To_List_View(posts);
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
    public final static String SHOW_POST_LIST    = "show_post_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IsiForum_v3");

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
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }

    public ArrayList<Post> readPostsFromJsonFile(int resourceId) {

        Log.d(TAG, "Reading posts from JSON file...");
        ArrayList<Post> posts = new ArrayList();

        // read and deserialize the file
        BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(resourceId)));

        //get json array from buffered reader
        JsonElement jsonElement = new JsonParser().parse(br);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        // create posts from JSON elements and add it to the list
        Gson gson = new Gson();
        int i = 0;
        for (JsonElement e : jsonArray) {

            posts.add(gson.fromJson(e, Post.class));
            //posts.get(i).setId(i);
            i++;
        }

        Log.d(TAG, "Read JSON file successfully.");

        // return a list with the deserialized posts
        return posts;

    }

    /*listen to user's command and respond*/
    private final class ItemClickEvent implements AdapterView.OnItemClickListener
    {
        /*arg2 is where user clicks*/
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            String str = (String)list_view.getItemAtPosition(arg2);
            Log.d(TAG, arg2 + " was clicked: " + str);

            /*create a DisplayMessageActivity to show the detail message*/
            //Post p = posts.get(arg2);
            Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainActivity.SHOW_POST_MESSAGE, arg2);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void Add_Message(View v)
    {
        Log.d(TAG, "you click Add Message Button");
        Intent intent = new Intent(getApplicationContext(), AddMessageActivity.class);
        startActivity(intent);
    }

    public void Set_Posts_To_List_View(ArrayList<Post> posts)
    {
        /*add the authors to each ListView item using Adapter*/
        Log.d(TAG, "Set_Posts_To_List_View starts...");
        ArrayList<String> authors = new ArrayList<String>();
        for (int i = 0; i < posts.size(); i++)
        {
            authors.add(posts.get(i).getAuthor());
        }

        list_view = (ListView)findViewById(R.id.listView_Main);
        list_view.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, authors));

        /*listen to user's command and respond*/
        list_view.setOnItemClickListener(new ItemClickEvent());
        Log.d(TAG, "Set_Posts_To_List_View ends...");
    }



}

package com.example.lin.lin_homework_v01;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class AddMessageActivity extends ActionBarActivity implements AddFragment.OnFragmentInteractionListener{

    private static final String TAG = "AddMessageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);


        AddFragment addFragment = new AddFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.new_post_Container, addFragment);
        fragmentTransaction.commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_message, menu);
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

//    public boolean Add_Message(View v)
//    {
//        Post p = new Post();
//
//        EditText et = (EditText)findViewById(R.id.editText_Author);
//        String str = et.getText().toString();
//        if(str.length() > 0 && str != " ")
//        {
//            p.setAuthor(str);
//        }else
//        {
//            Log.d(TAG, "author is empty");
//            Toast.makeText(getApplicationContext(), "Please input author",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        et = (EditText)findViewById(R.id.editText_Title);
//        str = et.getText().toString();
//        if(str.length() > 0 && str != " ")
//        {
//            p.setTitle(str);
//        }else
//        {
//            Log.d(TAG, "title is empty");
//            Toast.makeText(getApplicationContext(), "Please input title",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        et = (EditText)findViewById(R.id.editText_Message);
//        str = et.getText().toString();
//        if(str.length() > 0 && str != " ")
//        {
//            p.setMessage(str);
//        }else
//        {
//            Log.d(TAG, "message is empty");
//            Toast.makeText(getApplicationContext(), "Please input message",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Long tsLong = System.currentTimeMillis()/1000;
//        String ts = tsLong.toString();
//        p.setTimestamp(ts);
//
//        //send the data to webservice
//        new PostSender(p).execute();
//
//        int id = MainActivity.posts.size();
//        //p.setId(id);
//        MainActivity.posts.add(p);
//        MainActivity.Update = true;
//        Log.d(TAG, "add message successfully! posts size: " +MainActivity.posts.size());
//        Toast.makeText(getApplicationContext(), "add message successfully",Toast.LENGTH_SHORT).show();
//
//
//        Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(MainActivity.SHOW_POST_MESSAGE, id);
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//        return true;
//
//
//    }

    public void onFragmentInteraction(Uri uri) {

    }
}

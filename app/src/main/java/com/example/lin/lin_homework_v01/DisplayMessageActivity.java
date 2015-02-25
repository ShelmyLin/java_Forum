package com.example.lin.lin_homework_v01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DisplayMessageActivity extends ActionBarActivity {

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

        SetMessage(cur_p);
        Set_Button();


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

    public void SetMessage(int position)
    {
          /*set the values to each item*/
        Post p = MainActivity.posts.get(position);
        cur_p = position;
        TextView t_v = (TextView)findViewById(R.id.textView_Author);
        t_v.setText(p.getAuthor());
        t_v = (TextView)findViewById(R.id.textView_Title);
        t_v.setText(p.getTitle());
        t_v = (TextView)findViewById(R.id.textView_Date);
        t_v.setText(p.getTimestamp());
        t_v = (TextView)findViewById(R.id.textView_Message);
        t_v.setText(p.getMessage());
    }

    public void Set_Button()
    {
        int prev_id = cur_p - 1;
        Button m_button = (Button)findViewById(R.id.button_Prev);
        if(prev_id < 0)
        {
            m_button.setEnabled(false);
        }else
        {
            m_button.setEnabled(true);
        }

        int Next_id = cur_p + 1;
        m_button = (Button)findViewById(R.id.button_Next);
        if(Next_id > MainActivity.posts.size() - 1)
        {
            m_button.setEnabled(false);
        }else
        {
            m_button.setEnabled(true);
        }

    }
    public void Show_Prev_Message(View v)
    {
        Log.d(TAG, "Show_Prev_Message() starts...");
        cur_p = cur_p - 1;

        Log.d(TAG, "current id :" + cur_p + "/" + MainActivity.posts.size());

        SetMessage(cur_p);
        Set_Button();
        Log.d(TAG, "Show_Prev_Message() ends...");

    }
    public void Show_Next_Message(View v)
    {
        Log.d(TAG, "Show_Next_Message() starts...");
        cur_p = cur_p + 1;

        Log.d(TAG, "current id :" + cur_p+ "/" + MainActivity.posts.size());

        SetMessage(cur_p);
        Set_Button();
        Log.d(TAG, "Show_Next_Message() ends...");

    }

    public void Add_Message(View v)
    {

        Log.d(TAG, "you click Add Message Button");
        Intent intent = new Intent(getApplicationContext(), AddMessageActivity.class);
        startActivity(intent);
    }

}

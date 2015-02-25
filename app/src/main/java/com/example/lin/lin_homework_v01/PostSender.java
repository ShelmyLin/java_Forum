package com.example.lin.lin_homework_v01;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by lin on 2/20/15.
 */
public class PostSender extends AsyncTask<String, Integer, Boolean > {


    static  String TAG = "PostSender";
    private Post post;

    protected Boolean doInBackground(String... params) {

        String url = "http://forum.openium.fr:80/api/posts";

        boolean IsSucess = false;
        String json_str;
        // Open the resource and get the data
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        Log.d(TAG, "get url: "+ url);
        try{
            HttpPost httpPost = new HttpPost(url);
            Gson json = new Gson();
            json_str = json.toJson(post);
            System.out.println(json_str);
            StringEntity m_params = new StringEntity(json_str);

            httpPost.addHeader("Content-Type","application/json; charset=utf-8");
            httpPost.setEntity(m_params);
            HttpResponse response = client.execute(httpPost);

            Log.d(TAG,response.getStatusLine().toString());

            IsSucess = true;

        }
        catch (Exception e) {
            Log.d(TAG,"Exception when send post");
            e.printStackTrace();
            IsSucess = false;
        }

        return IsSucess;
    }

    public PostSender(Post p) {
        post = p;
    }







}

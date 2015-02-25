package com.example.lin.lin_homework_v01;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by lin on 2/17/15.
 */

public class PostReader {

        private static String TAG = "PostReader";
        public static String WebStr = new String();


        public ArrayList<Post> GetPosts(String url)
        {
            Log.d(TAG, "GetPost starts...");
            boolean IsSucc = false;

            ArrayList<Post> m_posts = new ArrayList<Post>();
            new DownloadWebpageTask().execute("http://forum.openium.fr/api/posts");
            //Log.d(TAG, "get web string: " + WebStr);
            //
            if(WebStr.length() > 1)
            {
                IsSucc = true;
                // create posts from JSON elements and add it to the list
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Post>>() {}.getType();
                try {
                    m_posts = gson.fromJson(WebStr, listType);
                    //Log.d(TAG, "parse json, size: " + m_posts.size());
                }catch (Exception e)
                {
                    Log.d(TAG, "some errors occur when parse json: " + e.getMessage());

                }
            }
            Log.d(TAG, "GetPost ends...");
            return  m_posts;
        }



        public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        public String str = new String();
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {

                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.

        protected void onPostExecute(String result) {

            //Log.d(TAG, "result from web page: " + result);
            WebStr = result;
            //Log.d(TAG, "copy string from result: " + WebStr);
            //textView.setText(result);
        }

            public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
                Reader reader = null;
                reader = new InputStreamReader(stream, "UTF-8");
                char[] buffer = new char[len];
                for(int i = 0; i < len; i++)
                {
                    buffer[i] = '\0';
                }
                reader.read(buffer);
                return new String(buffer);
            }

            // Given a URL, establishes an HttpUrlConnection and retrieves
            // the web page content as a InputStream, which it returns as
            // a string.
            private String downloadUrl(String myurl) throws IOException {
                InputStream is = null;
                // Only display the first 500 characters of the retrieved
                // web page content.
                int len = 500000;

                try {
                    URL url = new URL(myurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String contentAsString = readIt(is, len);
                    int end = contentAsString.lastIndexOf("]") + 1;
                    return contentAsString.substring(0, end);

                    // Makes sure that the InputStream is closed after the app is
                    // finished using it.
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }


    }





}

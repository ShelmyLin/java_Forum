package com.example.lin.lin_homework_v01;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
/**
 * Created by lin on 2/2/15.
 */
public class Post implements Serializable{
    private String title;
    private String author;
    private String message;
    private String timestamp;
    //private int id;

    //public void setId(int id){this.id = id;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    //public int getId(){return id;}

    @Override
    public String toString() {
        return super.toString();
    }



}

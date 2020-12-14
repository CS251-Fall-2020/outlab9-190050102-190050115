package com.example.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Object> viewItems = new ArrayList<>();
    private TextView textView;

    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        volleyGet1(this);
    }

    public void volleyGet1(Context context){
        String url = "https://google-news.p.rapidapi.com/v1/topic_headlines?lang=en&country=US&topic=technology";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    for(int i=0; i< jsonArray.length(); i++){
                        JSONObject article = jsonArray.getJSONObject(i);
                        String name = article.getString("title");
                        viewItems.add(new news(name));
                    }

                    // specify an adapter (see also next example)
                    mAdapter = new RecyclerAdapter(context, viewItems);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @org.jetbrains.annotations.NotNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("x-bingapis-sdk", "true");
                params.put("x-rapidapi-key", "623994d80emsh75e99be54b62f74p1cd3b7jsndcfb41111e2c");
                params.put("x-rapidapi-host", "google-news.p.rapidapi.com");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void volleyGet2(Context context){
        String url = "https://bing-news-search1.p.rapidapi.com/news?safeSearch=Off&textFormat=Raw";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    for(int i=0; i< jsonArray.length(); i++){
                        JSONObject article = jsonArray.getJSONObject(i);
                        String name = article.getString("title");
                        viewItems.add(new news(name));
                    }

                    // specify an adapter (see also next example)
                    mAdapter = new RecyclerAdapter(context, viewItems);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @org.jetbrains.annotations.NotNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("x-bingapis-sdk", "true");
                params.put("x-rapidapi-key", "623994d80emsh75e99be54b62f74p1cd3b7jsndcfb41111e2c");
                params.put("x-rapidapi-host", "bing-news-search1.p.rapidapi.com");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
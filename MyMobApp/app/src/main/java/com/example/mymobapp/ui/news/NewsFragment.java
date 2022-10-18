package com.example.mymobapp.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymobapp.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import com.example.mymobapp.model.Article;
import com.example.mymobapp.model.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private String URL = "https://newsapi.org/v2/top-headlines?country=ie&pageSize=20&apiKey=";
    private String api_key = "97fdc077d1ed4371add402633bfdd34f";
    private NewsViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = NewsFragment.class.getSimpleName();
    private Context thisContext;
    private View v;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @SuppressLint("StaticFieldLeak")
    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Toast.makeText(thisContext,"No results", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            News news = gson.fromJson(s, News.class);
            articles = news.getArticles();
            adapter.setArticles(articles);
            adapter.notifyDataSetChanged();

        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        assert container != null;
        this.thisContext = container.getContext();

        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    void onClick(View v){

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new Adapter(articles, thisContext);
        recyclerView.setAdapter(adapter);
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(URL+api_key);

        // TODO: Use the ViewModel
    }




}
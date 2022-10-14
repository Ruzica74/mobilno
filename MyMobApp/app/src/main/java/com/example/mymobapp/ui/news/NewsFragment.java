package com.example.mymobapp.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymobapp.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    private String URL = "https://newsapi.org/v2/top-headlines?country=ie&pageSize=3&apiKey=";
    private String api_key = "97fdc077d1ed4371add402633bfdd34f";
    private NewsViewModel mViewModel;

    TextView txtString;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

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
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("****Vijestii: "+s);
            txtString = (TextView) getView().findViewById(R.id.textView1);
            txtString.setMovementMethod(new ScrollingMovementMethod());
            txtString.setText(s);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(URL+api_key);
        return inflater.inflate(R.layout.fragment_news, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        // TODO: Use the ViewModel
    }

}
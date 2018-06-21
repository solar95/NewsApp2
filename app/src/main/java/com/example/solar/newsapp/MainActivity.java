package com.example.solar.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String USGS_REQUEST_URL = "http://content.guardianapis.com/search?api-key=ebecd602-f289-44fb-9f50-ff28ffab0a44&show-fields=byline";
    private NewsAdapter adapter;
    ListView lv01;
    TextView noNewsArticleText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv01 = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress_bar);
        noNewsArticleText = findViewById(R.id.empty_view);
        adapter = new NewsAdapter(this, new ArrayList<NewsArticle>());
        lv01.setAdapter(adapter);
        lv01.setEmptyView(noNewsArticleText);



        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            noNewsArticleText.setText(R.string.no_internet);
        }




        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e(LOG_TAG,"Si funciono!");
            }
        });

    }



    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args) {
        String TAG = "URI";
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        Log.d(TAG, "uriBuilder: " + uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> NewsArticles) {
        noNewsArticleText.setText(R.string.no_article);
        adapter.clear();

        if (NewsArticles != null && !NewsArticles.isEmpty()) {
            adapter.addAll(NewsArticles);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        adapter.clear();
        getLoaderManager().restartLoader(0, null, this);
    }





}

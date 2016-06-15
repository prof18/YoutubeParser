package com.prof.youtubeparser.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.Video;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VideoAdapter vAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                vAdapter.clearData();
                vAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(true);
                loadVideo();

            }
        });

        ConnectionDetector cd = new ConnectionDetector(this);

        Boolean isInternetPresent = cd.isNetworkAvailable();
        if (!isInternetPresent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        } else if (isInternetPresent) {

            loadVideo();

        }


    }

    public void loadVideo() {

      /*  if (vAdapter != null)
            vAdapter.clearData();
*/
        if (!mSwipeRefreshLayout.isRefreshing())
            progressWheel.setVisibility(View.VISIBLE);

        Parser parser = new Parser();
        String url = parser.generateRequest("UCVHFbqXqoYvEWM1Ddxl0QDg", 20, "AIzaSyDdfElmBLygd3Q3Pld1Xb2GaHoUGDu8RUE");
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Video> list) {
                vAdapter = new VideoAdapter(list, R.layout.yt_row, MainActivity.this );
                mRecyclerView.setAdapter(vAdapter);
                vAdapter.notifyDataSetChanged();

                progressWheel.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressWheel.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Impossibile caricare i video. Swipe in giù per riprovare",
                                Toast.LENGTH_SHORT).show();
                        Log.i("Unable to load ", "videos");
                    }
                });

            }
        });

    }



    @Override
    public void onResume() {

        super.onResume();

        if (vAdapter != null) {

            vAdapter.notifyDataSetChanged();
          //  vAdapter.clearData();


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vAdapter != null)
            vAdapter.clearData();
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
}

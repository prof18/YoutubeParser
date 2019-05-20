package com.prof.youtubeparser.sample.java;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.VideoStats;
import com.prof.youtubeparser.models.stats.Statistics;
import com.prof.youtubeparser.models.videos.Video;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Video>> mVideoLiveList = new MutableLiveData<>();
    private MutableLiveData<String> mSnackbar = new MutableLiveData<>();
    private MutableLiveData<Statistics> mLiveStats = new MutableLiveData<>();

    private String mNextToken = null;
    private Parser mParser = new Parser();
    private ArrayList<Video> mVideoList = new ArrayList<>();
    private final String CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg";

    public MutableLiveData<List<Video>> getVideoLiveList() {
        return mVideoLiveList;
    }

    private void setVideoLiveList(List<Video> videoList) {
        this.mVideoLiveList.postValue(videoList);
    }

    public MutableLiveData<String> getSnackbar() {
        return mSnackbar;
    }

    public void onSnackbarShowed() {
        this.mSnackbar.setValue(null);
    }

    public MutableLiveData<Statistics> getLiveStats() {
        return mLiveStats;
    }

    private void setLiveStats(Statistics stats) {
        this.mLiveStats.postValue(stats);
    }

    private void setNextToken(String nextToken) {
        this.mNextToken = nextToken;
    }

    public void fetchVideos() {

        mParser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(@NonNull List<Video> list, @NonNull String nextPageToken) {
                setNextToken(nextPageToken);
                mVideoList.addAll(list);
                setVideoLiveList(mVideoList);
            }

            @Override
            public void onError(@NonNull Exception e) {
                setVideoLiveList(new ArrayList<Video>());
                e.printStackTrace();
                mSnackbar.postValue("An error has occurred. Please try again");
            }
        });

        String requestUrl = mParser.generateRequest(
                CHANNEL_ID,
                20,
                Parser.ORDER_DATE,
                BuildConfig.KEY
        );

        mParser.execute(requestUrl);

    }

    public void fetchNextVideos() {
        if (mNextToken != null) {
            mParser.onFinish(new Parser.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(@NonNull List<Video> list, @NonNull String nextPageToken) {
                    setNextToken(nextPageToken);
                    mVideoList.addAll(list);
                    setVideoLiveList(mVideoList);
                }

                @Override
                public void onError(@NonNull Exception e) {
                    e.printStackTrace();
                    mSnackbar.postValue("An error has occurred. Please try again");
                }
            });

            String requestUrl = mParser.generateMoreDataRequest(
                    CHANNEL_ID,
                    20,
                    Parser.ORDER_DATE,
                    BuildConfig.KEY,
                    mNextToken
            );

            mParser.execute(requestUrl);
        }
    }

    public void fetchStatistics(String videoId) {

        VideoStats videoStats = new VideoStats();
        videoStats.onFinish(new VideoStats.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(@NonNull Statistics stats) {
                setLiveStats(stats);
            }

            @Override
            public void onError(@NonNull Exception e) {
                e.printStackTrace();
                mSnackbar.postValue("An error has occurred. Please try again");
            }
        });

        String requestUrl = videoStats.generateStatsRequest(videoId, BuildConfig.KEY);
        videoStats.execute(requestUrl);
    }
}

package com.example.shortvideoapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private List<VideoInfo> videoList = new ArrayList<>();
    private int flag = 0;
    private RecyclerView mVideoListView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoListView = findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVideoListView.setLayoutManager(layoutManager);
        mVideoListView.setHasFixedSize(true);

        okHttpData();
        while (flag == 0) ;

        itemAdapter = new ItemAdapter(videoList, this);
        mVideoListView.setAdapter(itemAdapter);
    }

    //获取json内容
    private void okHttpData() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://beiyou.bytedance.com/api/invoke/video/invoke/video").build();
                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    parseJsonString(data);
                    flag = 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //解析json内容
    private void parseJsonString(String data) {
        if (data != null) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getJSONObject(i).getString("_id");
                    String feedUrl = jsonArray.getJSONObject(i).getString("feedurl");
                    String nickName = jsonArray.getJSONObject(i).getString("nickname");
                    String description = jsonArray.getJSONObject(i).getString("description");
                    String likeCount = jsonArray.getJSONObject(i).getString("likecount");
                    String avatar = jsonArray.getJSONObject(i).getString("avatar");

                    //放进list
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setId(id);
                    videoInfo.setFeedUrl(feedUrl);
                    videoInfo.setNickName(nickName);
                    videoInfo.setDescription(description);
                    videoInfo.setLikeCount(likeCount);
                    videoInfo.setAvatar(avatar);
                    videoList.add(videoInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

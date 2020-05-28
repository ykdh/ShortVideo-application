package com.example.shortvideoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView ivAvatar;
    private ImageView ivHeart;
    private TextView tvNickName;
    private TextView tvDescription;
    private TextView tvLikeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_page);

        videoView = findViewById(R.id.video);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivHeart = findViewById(R.id.ivHeart);
        tvNickName = findViewById(R.id.tvNickName);
        tvDescription = findViewById(R.id.tvDescription);
        tvLikeCount = findViewById(R.id.tvLikeCount);

        //接收数据
        Intent intent = getIntent();
        String feedUrl = intent.getStringExtra("feedUrl");
        String avatar = intent.getStringExtra("avatar");
        String nickName = intent.getStringExtra("nickName");
        String description = intent.getStringExtra("description");
        String likeCount = intent.getStringExtra("likeCount");

        Log.d("接收intent", nickName);

        //显示
        tvNickName.setText("@" + nickName);
        tvDescription.setText(description);
        tvLikeCount.setText(likeCount);
        ivHeart.setImageResource(R.mipmap.like0);
        Glide.with(this).load(avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .into(ivAvatar);
    }

}
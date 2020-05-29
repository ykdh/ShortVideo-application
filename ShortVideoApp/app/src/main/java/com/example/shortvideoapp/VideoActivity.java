package com.example.shortvideoapp;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import android.view.GestureDetector.OnGestureListener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView ivAvatar;
    private ImageView ivHeart;
    private ImageView pause;
    private TextView tvNickName;
    private TextView tvDescription;
    private TextView tvLikeCount;
    private FrameLayout frameLayout;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_page);

        videoView = findViewById(R.id.video);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivHeart = findViewById(R.id.ivHeart);
        pause = findViewById(R.id.pause);
        tvNickName = findViewById(R.id.tvNickName);
        tvDescription = findViewById(R.id.tvDescription);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        frameLayout = findViewById(R.id.thisVideo);

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
        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivHeart.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.mipmap.like0).getConstantState())) {
                    ivHeart.setImageResource(R.mipmap.like1);
                } else {
                    ivHeart.setImageResource(R.mipmap.like0);
                }
            }
        });

        Glide.with(this).load(avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .into(ivAvatar);

        videoView.setVideoPath(feedUrl);
        videoView.requestFocus();
        videoView.start();
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pause.setVisibility(View.VISIBLE);
                } else {
                    videoView.start();
                    pause.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

}

package com.example.shortvideoapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;


public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView ivAvatar;
    private ImageView ivHeart;
    private ImageView pause;
    private TextView tvNickName;
    private TextView tvDescription;
    private TextView tvLikeCount;
    private FrameLayout frameLayout;
    private int clickCount = 0;
    private Handler handler;
    private SpinKitView spinKitView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        spinKitView = findViewById(R.id.spinKit);

        //接收数据
        Intent intent = getIntent();
        String feedUrl = intent.getStringExtra("feedUrl");
        String avatar = intent.getStringExtra("avatar");
        String nickName = intent.getStringExtra("nickName");
        String description = intent.getStringExtra("description");
        String likeCount = intent.getStringExtra("likeCount");

        //显示用户名和描述
        tvNickName.setText("@" + nickName);
        tvDescription.setText(description);

        //喜欢数
        int tmpLike = Integer.parseInt(likeCount);
        if (tmpLike >= 10000) {
            int like1 = tmpLike / 10000;
            int like2 = (tmpLike % 10000) / 1000;
            String text = like1 + "." + like2 + "w";
            tvLikeCount.setText(text);
        } else {
            tvLikeCount.setText(likeCount);
        }

        //爱心
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

        //头像
        Glide.with(this).load(avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .into(ivAvatar);

        //视频
        videoView.setVideoPath(feedUrl);
        videoView.requestFocus();
        //加载动画
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    //动画
                    spinKitView.setVisibility(SpinKitView.VISIBLE);
                } else {
                    //停止
                    spinKitView.setVisibility(SpinKitView.GONE);
                }
                return true;
            }
        });
        //循环播放
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        //单击暂停/播放，双击出现爱心
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickCount == 1) {
                            if (videoView.isPlaying()) {
                                videoView.pause();
                                pause.setVisibility(View.VISIBLE);
                            } else {
                                videoView.start();
                                pause.setVisibility(View.INVISIBLE);
                            }
                        } else if (clickCount == 2) {
                            ivHeart.setImageResource(R.mipmap.like1);
                        }
                        handler.removeCallbacksAndMessages(null);
                        clickCount = 0;
                    }
                }, 500);
            }
        });
    }
}

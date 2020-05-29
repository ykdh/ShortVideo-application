package com.example.shortvideoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<VideoInfo> videoList;
    private Context context;

    public ItemAdapter(List<VideoInfo> list, Context context) {
        this.videoList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final VideoInfo videoInfo = videoList.get(i);
        final String feedUrl = videoInfo.getFeedUrl();

        //描述
        viewHolder.description.setText(videoInfo.getDescription());

        //喜欢数
        int tmpLike = Integer.parseInt(videoInfo.getLikeCount());
        if (tmpLike >= 10000) {
            int like1 = tmpLike / 10000;
            int like2 = (tmpLike % 10000) / 1000;
            String text = like1 + "." + like2 + "w";
            viewHolder.like.setText(text);
        } else {
            viewHolder.like.setText(videoInfo.getLikeCount());
        }

        //封面图
        Glide.with(context).setDefaultRequestOptions(new RequestOptions()
                .frame(1000000).fitCenter().error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .load(feedUrl).into(viewHolder.cover);

        //点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(context, VideoActivity.class);

                intent.putExtra("feedUrl", feedUrl);
                intent.putExtra("avatar", videoInfo.getAvatar());
                intent.putExtra("nickName", videoInfo.getNickName());
                intent.putExtra("description", videoInfo.getDescription());
                intent.putExtra("likeCount", videoInfo.getLikeCount());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cover;
        private final TextView description;
        private final TextView like;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            description = (TextView) itemView.findViewById(R.id.description);
            like = (TextView) itemView.findViewById(R.id.like);
        }
    }
}

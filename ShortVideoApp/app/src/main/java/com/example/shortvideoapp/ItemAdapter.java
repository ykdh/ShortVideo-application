package com.example.shortvideoapp;

import android.content.Context;
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
    private static final String TAG = "DouAdapter";
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final VideoInfo videoInfo = videoList.get(i);
        viewHolder.description.setText("  " + videoInfo.getDescription());
        viewHolder.like.setText("♥ " + videoInfo.getLikeCount() + "  ");
        String feedUrl = videoInfo.getFeedUrl();

        //封面图
        Glide.with(context).setDefaultRequestOptions(new RequestOptions()
                .frame(1000000).fitCenter().error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .load(feedUrl).into(viewHolder.cover);

        //点击事件

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //封面图、描述、喜欢数
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

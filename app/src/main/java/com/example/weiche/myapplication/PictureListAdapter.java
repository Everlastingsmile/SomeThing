package com.example.weiche.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.weiche.myapplication.bean.PhotoGirl;
import com.example.weiche.myapplication.widget.photoview.PhotoView;
import com.example.weiche.myapplication.util.DisplayUtils;
import com.example.weiche.myapplication.util.GlideHelper;

import java.util.ArrayList;
import java.util.List;

public class PictureListAdapter extends RecyclerView.Adapter {

    private List<PhotoGirl> mDatas;
    private Context mContext;
    private ItemOnClickListener mItemOnClickListener;
    private int mScreenWidth;

    public PictureListAdapter(Context context){
        mContext = context;
        mDatas = new ArrayList<>();
        mScreenWidth = DisplayUtils.getScreenWidth(context);
    }

    public List<PhotoGirl> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<PhotoGirl> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void appendData(List<PhotoGirl> datas){
        int footPosition = mDatas.size()-1;
        this.mDatas.addAll(datas);
        notifyItemInserted(footPosition);
        notifyItemRangeChanged(footPosition,mDatas.size() - footPosition);
    }

    public void setmItemOnClickListener(ItemOnClickListener mItemOnClickListener) {
        this.mItemOnClickListener = mItemOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        GlideHelper.get(mContext,mDatas.get(position).getUrl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                if (o instanceof Drawable) {
                    int imageWidth = ((Drawable) o).getIntrinsicWidth();
                    int imageHeight = ((Drawable) o).getIntrinsicHeight();
                    int width = mScreenWidth/2 - 5;
                    int height = (int) (width * imageHeight / imageWidth*1f);
                    ViewGroup.LayoutParams params = myViewHolder.photoView.getLayoutParams();
                    params.height = height;
                    params.width = width;
                    myViewHolder.photoView.setLayoutParams(params);
                }
                return false;
            }
        }).into(myViewHolder.photoView);
        myViewHolder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null){
                    mItemOnClickListener.onItemClick(v,position,mDatas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private PhotoView photoView;
        public MyViewHolder(View itemView) {
            super(itemView);
            photoView = (PhotoView)itemView.findViewById(R.id.image_view);
        }
    }

    interface ItemOnClickListener{
        void onItemClick(View view, int position, PhotoGirl photoGirl);
        void onItemLongClick(View view, int position, PhotoGirl photoGirl);
    }
}

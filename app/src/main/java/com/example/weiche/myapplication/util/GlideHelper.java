package com.example.weiche.myapplication.util;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weiche.myapplication.MyApplication;
import com.example.weiche.myapplication.R;

public class GlideHelper {

    private static RequestManager with(Context context) {
        try {
            if (context == null) {
                throw new NullPointerException("RequestManager with(Context context): context == null");
            }
            return Glide.with(context);
        } catch (Exception e) {
            return Glide.with(MyApplication.getAppContext());
        }
    }

    /**
     * 如果发生异常，则使用ApplicationContext加载图片（取材于Glide源码，Glide源码里对于应用处于后台等无法使用
     * fragment生命周期来监测页面生命周期的情况下一律使用ApplicationContext，相关源码位置
     * {@link com.bumptech.glide.manager.RequestManagerRetriever#get(android.app.Fragment)}）
     *
     * @param fragment
     * @return
     */
    private static RequestManager with(Fragment fragment) {
        try {
            if (fragment == null) {
                throw new NullPointerException("RequestManager with(Context context): fragment == null");
            }
            if (fragment.getActivity() == null) {
                throw new NullPointerException("RequestManager with(Context context): fragment.getActivity() == null");
            }
            return Glide.with(fragment);
        } catch (Exception e) {
            return Glide.with(MyApplication.getAppContext());
        }
    }

    public static RequestBuilder<Drawable> get(Context context, String url) {
        boolean isGif = url != null && url.endsWith(".gif");
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(isGif ? DiskCacheStrategy.DATA : DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_empty_picture).centerCrop();
        return with(context).load(url);
    }

    public static RequestBuilder<Drawable> get(Fragment fragment, String url) {
        boolean isGif = url != null && url.endsWith(".gif");
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(isGif ? DiskCacheStrategy.DATA : DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading).error(R.drawable.ic_empty_picture).centerCrop();
        return with(fragment).load(url);
    }

}

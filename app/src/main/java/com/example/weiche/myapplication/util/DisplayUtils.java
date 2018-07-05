package com.example.weiche.myapplication.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * utils for screen display
 */
public class DisplayUtils {

    private static float mCachedDensity = -1f;

    public static int getPixel(Context context, float dp) {
        float density = getDensity(context);
        return (int) (dp * density);
    }

    public static float getDensity(Context context) {
        if (mCachedDensity > 0)
            return mCachedDensity;
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null)
            mCachedDensity = metrics.density;
        return mCachedDensity < 0 ? 1.5f : mCachedDensity;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null)
            return metrics.heightPixels;
        else
            return 801;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null)
            return metrics.widthPixels;
        else
            return 481;
    }

    public static int getScreenResolution(Context context) {
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null)
            return metrics.widthPixels * metrics.heightPixels;
        else
            return 480 * 800;
    }

    public static Point getScreenDimension(Context context) {
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null)
            return new Point(metrics.widthPixels, metrics.heightPixels);
        return new Point(480, 800);
    }

    /**
     * x = screen width; y = screen height; in dp.
     */
    public static Point getScreenDimensionInDp(Context context) {
        DisplayMetrics metrics = getMetric(context);
        if (metrics != null) {
            float density = metrics.density == 0 ? 1 : metrics.density;
            return new Point((int) (metrics.widthPixels / density),
                    (int) (metrics.heightPixels / density));
        }
        return new Point(320, 534); // 480X800 with hdpi
    }

    public static DisplayMetrics getMetric(Context context) {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager winMgr = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            winMgr.getDefaultDisplay().getMetrics(metrics);
            return metrics;
        } catch (Exception e) {
        }
        return null;
    }

    public static int measureViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }

    public static int measureViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        return width;
    }
}

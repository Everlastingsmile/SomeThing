package com.example.weiche.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.weiche.myapplication.api.Api;
import com.example.weiche.myapplication.api.HostType;
import com.example.weiche.myapplication.base.BaseActivity;
import com.example.weiche.myapplication.bean.GirlData;
import com.example.weiche.myapplication.bean.PhotoGirl;
import com.example.weiche.myapplication.widget.ItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PictureListActivity extends BaseActivity {

    private static final int SIZE = 20;
    private int mCurrentPage = 1;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private PictureListAdapter mAdapter;
    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        initView();
        initData();
        refreshData(false,mCurrentPage);
    }

    private void refreshData(final boolean loadMore, int index){
        Observable<GirlData> observable = Api.getDefault(HostType.GANK_GIRL_PHOTO).getPhotoList(Api.getCacheControl(),SIZE,index);
        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Subscriber<GirlData>() {
                    @Override
                    public void onCompleted() {
                        Log.e("print","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("print","onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(GirlData girlData) {

                        Log.e("print","onNext");
                        if (loadMore){
                            mAdapter.appendData(girlData.getResults());
                            mRefreshLayout.finishLoadMore();
                        }else {
                            mAdapter.setmDatas(girlData.getResults());
                            mRefreshLayout.finishRefresh();
                        }
                    }
                });
    }

    private void initData() {



    }

    private void initView() {
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.addItemDecoration(new ItemDecoration(this));
        mAdapter = new PictureListAdapter(this);
        mAdapter.setmItemOnClickListener(new PictureListAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position, PhotoGirl photoGirl) {
                jumpToBigPhotoActivity(photoGirl);
            }

            @Override
            public void onItemLongClick(View view, int position, PhotoGirl photoGirl) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mCurrentPage = 0;
                refreshData(false,0);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshData(true,mCurrentPage);
            }
        });

    }

    private void jumpToBigPhotoActivity(PhotoGirl photoGirl) {
        Intent intent = new Intent(this,BigPhotoActivity.class);
        intent.putExtra(BigPhotoActivity.INTENT_EXTRA_PHOTOGIRL,photoGirl);
        startActivity(intent);
    }
}

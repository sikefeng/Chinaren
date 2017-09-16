/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;


import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.renygit.recycleview.RRecyclerView;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.utils.img.ImageUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;


public class BindingEvent {

    /**
     * 数据加载最小时间
     */
    private static final int LOADING_MIN_TIME = 1000;

    /**
     * 关闭时间
     */
    private static final int CLOSE_HEADER = 1500;

    /**
     * 头部框的顶部内边距
     */
    private static final int HEADER_PADDING_TOP = 15;
    /**
     * 头部狂的地步内边距
     */
    private static final int HEADER_PADDING_BOTTOM = 10;

    /**
     * 组件的外边距
     */
    private static final int LAYOUT_MARGIN = -2;

    /**
     * 设置adapter
     *
     * @param rv      RecyclerView
     * @param adapter RecyclerView.Adapter
     */
    @BindingAdapter("adapter")
    public static void setAdapter(final RecyclerView rv, RecyclerView.Adapter adapter) {
        if (null != adapter) {
            rv.setAdapter(adapter);
        }
    }

    /**
     * 设置列表样式，例如瀑布型，普通列表型等。
     *
     * @param rv            RecyclerView
     * @param layoutManager RecyclerView.LayoutManager
     */
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(final RecyclerView rv, RecyclerView.LayoutManager layoutManager) {
        if (null == layoutManager) {
            layoutManager = new LinearLayoutManager(rv.getContext());
        }
        rv.setLayoutManager(layoutManager);
    }

    /**
     * 加载更多数据
     *
     * @param rv        RRecyclerView
     * @param presenter 数据协调器，判断是全新加载还是下拉加载等。
     */
    @BindingAdapter("presenter")
    public static void setLoadMore(final RRecyclerView rv, final BasePresenter presenter) {
        rv.setOnLoadMoreListener(new RRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.requestData(false);
            }
        });

        rv.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadMoreLoading(rv, true);
                presenter.requestData(false);
            }
        });
    }

    /**
     * 设置正在加载更多状态
     *
     * @param rv      RRecyclerView
     * @param loading boolean，true为正在加载
     */
    @BindingAdapter("loading")
    public static void setLoadMoreLoading(final RRecyclerView rv, boolean loading) {
        if (loading) {
            rv.setLoading();
        } else {
            rv.loadComplete();
        }
    }

    /**
     * 设置没有更多提示
     *
     * @param rv     RRecyclerView
     * @param noMore boolean, false为没有更多
     */
    @BindingAdapter("noMore")
    public static void setNoMore(final RRecyclerView rv, boolean noMore) {
        rv.setNoMore(noMore);
    }

    /**
     * 设置出错信息
     *
     * @param rv      RRecyclerView
     * @param isError 是否错误
     */
    @BindingAdapter("isError")
    public static void setError(final RRecyclerView rv, boolean isError) {
        if (isError) {
            rv.setError();
        }
    }

    /**
     * 是否开启加载更多
     *
     * @param rv     RRecyclerView
     * @param enable boolean true为加载，false为不加载。
     */
    @BindingAdapter("loadMoreEnabled")
    public static void setLoadMoreEnabled(final RRecyclerView rv, boolean enable) {
        rv.setLoadMoreEnabled(enable);
    }


    /**
     * 设置数据加载状态，例如没有数据，加载错误，没有网络等。
     *
     * @param view          MultipleStatusView
     * @param stateViewType 状态类型
     * @param presenter     数据协调器
     */
    @BindingAdapter({"multiState", "presenter"})
    public static void setMultiState(final MultipleStatusView view, final int stateViewType, final BasePresenter presenter) {
        switch (stateViewType) {
            case MultipleStatusView.STATUS_LOADING:
                view.showLoading();
                break;
            case MultipleStatusView.STATUS_EMPTY:
                view.showEmpty();
                break;
            case MultipleStatusView.STATUS_ERROR:
                view.showError();
                break;
            case MultipleStatusView.STATUS_NO_NETWORK:
                view.showNoNetwork();
                break;
            case MultipleStatusView.STATUS_CONTENT:
                view.showContent();
                break;
            default: break;
        }
        view.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestData(true);
            }
        });
    }

    /**
     * 设置数据
     *
     * @param pfl       PtrFrameLayout
     * @param presenter BasePresenter
     */
    @BindingAdapter("presenter")
    public static void setRefreshPresenter(PtrFrameLayout pfl, final BasePresenter presenter) {
        // header
        MaterialHeader header = new MaterialHeader(pfl.getContext());
        int[] colors = ResUtils.getArrInt(R.array.refresh_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, LAYOUT_MARGIN));
        header.setPadding(0, CommonUtils.dp2px(HEADER_PADDING_TOP), 0, CommonUtils.dp2px(HEADER_PADDING_BOTTOM));
        header.setPtrFrameLayout(pfl);

        pfl.setLoadingMinTime(LOADING_MIN_TIME);
        pfl.setDurationToCloseHeader(CLOSE_HEADER);
        pfl.setHeaderView(header);
        pfl.addPtrUIHandler(header);

        pfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.requestData(true);
            }
        });
    }

    /**
     * 绑定加载状态
     *
     * @param pfl     PtrFrameLayout
     * @param loading true为正在加载
     */
    @BindingAdapter("loading")
    public static void setRefreshLoading(final PtrFrameLayout pfl, boolean loading) {
        if (!loading) {
            pfl.refreshComplete();
        }
    }

    /**
     * 设置图片URL
     *
     * @param view 当前View
     * @param url  图片URL地址
     */
    @BindingAdapter("url")
    public static void setImgUrl(final View view, String url) {
        ImageUtils.getInstance().disPlay(url, view);
    }

}

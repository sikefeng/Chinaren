/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityImagesBinding;
import com.sikefeng.chinaren.entity.other.ImgsInfo;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.mvpvmlib.base.RBaseViewModel;
import com.sikefeng.chinaren.ui.adapter.SimpleSheetAdapter;
import com.sikefeng.chinaren.utils.DateTimeUtils;
import com.sikefeng.chinaren.utils.FileUtils;
import com.sikefeng.chinaren.utils.PermissionConstans;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.img.ImageUtils;
import com.sikefeng.chinaren.utils.img.glide.DownCallBack;
import com.sikefeng.chinaren.widget.DividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import io.reactivex.observers.DisposableObserver;


public class ImagesActivity extends BaseActivity<ActivityImagesBinding> {

    /**
     * 界面边界
     */
    private static final int PAGE_MARGIN = 15;

    /**
     * 图片适配器
     */
    private DraweePagerAdapter draweePagerAdapter;
    /**
     * 图片列表集合
     */
    private List<String> imgsList;
    /**
     * 数据协调器
     */
    private RBasePresenter presenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().flRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (null != getIntent()) {
            ImgsInfo imgsInfo = (ImgsInfo) getIntent().getSerializableExtra(ImgsInfo.KEY);
            if (null == imgsInfo) {
                return;
            }

            getBinding().vpImg.setPageMargin((int) (getResources().getDisplayMetrics().density * PAGE_MARGIN));

            draweePagerAdapter = new DraweePagerAdapter();
            imgsList = imgsInfo.getImgsList();
            draweePagerAdapter.setImgsUrl(imgsList);

            getBinding().vpImg.setAdapter(draweePagerAdapter);
            getBinding().vpImg.setCurrentItem(imgsInfo.getCurPos());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_images;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new RBasePresenter(this, new RBaseViewModel()) {
                @Override
                public void onCreate() {
                }
            };
        }
        return presenter;
    }

    /**
     * 图片适配器
     */
    private class DraweePagerAdapter extends PagerAdapter {

        /**
         * 图片列表
         */
        private List<String> imgsUrl;
        /**
         * RecyclerView控件
         */
        private RecyclerView rv;
        /**
         * 简单适配器
         */
        private SimpleSheetAdapter adapter;
        /**
         * 底部对话框
         */
        private BottomSheetDialog sheetDialog;
        /**
         * 图片框名称
         */
        private List<String> sheetNames;

        private void setImgsUrl(List<String> imgsUrl) {
            this.imgsUrl = imgsUrl;
        }

        @Override
        public int getCount() {
            return imgsUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, final int position) {
            PhotoView photoView = new PhotoView(viewGroup.getContext());
            ImageUtils.getInstance().disPlay(imgsUrl.get(position), photoView);
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    finish();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //ToastUtils.showShort("pos:"+position);
                    longPressImg(v, imgsUrl.get(position));
                    return false;
                }
            });
            try {
                viewGroup.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return photoView;
        }

        /**
         * 长按图片事件
         *
         * @param v      视图
         * @param imgUrl 图片地址
         */
        private void longPressImg(View v, final String imgUrl) {
            Context context = v.getContext();
            if (null == rv) {
                rv = new RecyclerView(context);
                rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rv.setLayoutManager(new XXLinearLayoutManager(context));
                rv.addItemDecoration(DividerItemDecoration.get1pxDividerV(context));
            }
            if (null == sheetNames) {
                sheetNames = new ArrayList<>();
            } else {
                sheetNames.clear();
            }

            sheetNames.add("保存图片");
            sheetNames.add("取消");

            if (null == adapter) {
                adapter = new SimpleSheetAdapter(rv);
                rv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            adapter.setData(sheetNames);
            if (null == sheetDialog) {
                sheetDialog = new BottomSheetDialog(context);
                sheetDialog.setContentView(rv);
            }
            sheetDialog.show();

            adapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    if (sheetDialog.isShowing()) {
                        sheetDialog.dismiss();
                    }
                    if (position == 0) {
                        //简单处理权限问题
                        presenter.checkPermissions(new DisposableObserver<Boolean>() {
                            @Override
                            public void onNext(Boolean value) {
                                if (value) {
                                    String dir = FileUtils.getDownLoadImgPath();
                                    if (dir != null) {
                                        String imgName = "img_" + DateTimeUtils.getCurDateStr(DateTimeUtils.getFormatYMDHMS());
                                        ImageUtils.getInstance().downLoadPic(getActivity(), imgUrl, dir, imgName, new DownCallBack() {
                                            @Override
                                            public void onSuccess(final File file) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ToastUtils.showLong("图片已保存到：" + file.getAbsolutePath());
                                                        FileUtils.notifyFileSystemChanged(file.getAbsolutePath());
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailed(final String err) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ToastUtils.showShort(err);
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        ToastUtils.showShort(getResources().getString(R.string.no_storage));
                                    }
                                } else {
                                    ToastUtils.showLong(getResources().getString(R.string.no_permission_WRITE_EXTERNAL));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        }, PermissionConstans.STORAGE_PERMISSIONS);
                    }
                }
            });
        }
    }

    private static final class XXLinearLayoutManager extends LinearLayoutManager {
        /**
         * 构造函数，加载上下文环境
         *
         * @param context 上下文环境
         */
        private XXLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }
}

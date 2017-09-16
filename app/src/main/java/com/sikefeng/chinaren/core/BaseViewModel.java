
package com.sikefeng.chinaren.core;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import com.classic.common.MultipleStatusView;
import com.sikefeng.chinaren.utils.Network;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.mvpvmlib.base.RBaseViewModel;
import com.sikefeng.mvpvmlib.utils.LogUtils;


public class BaseViewModel extends RBaseViewModel {

    /**
     * 是否第一次加载数据
     */
    private boolean firstLoadData = true;

    /**
     * 后去是否第一次加载数据
     * @return boolean true为第一次返回
     */
    public boolean isFirstLoadData() {
        return firstLoadData;
    }

    /**
     * 是否正在下拉刷新或加载更多
     */
    private ObservableBoolean loading = new ObservableBoolean(false);

    /**
     * 获取加载更多
     * @return ObservableBoolean ObservableBoolean
     */
    public ObservableBoolean getLoading() {
        return loading;
    }

    /**
     * 界面出错状态类型，如加载失败
     */
    private ObservableInt stateViewType = new ObservableInt(MultipleStatusView.STATUS_LOADING);

    /**
     * 获取状态类型
     * @return ObservableInt ObservableInt
     */
    public ObservableInt getStateViewType() {
        return stateViewType;
    }

    /**
     * 加载更多时出错
     */
    private ObservableBoolean isError = new ObservableBoolean(false);

    /**
     * 获取加载更多时出错
     * @return ObservableBoolean
     */
    public ObservableBoolean getIsError() {
        return isError;
    }

    /**
     * 加载更多时没有更多数据
     */
    private ObservableBoolean noMore = new ObservableBoolean(false);

    /**
     * 后去加载更多时没有更多数据提示
     * @return ObservableBoolean
     */
    public ObservableBoolean getNoMore() {
        return noMore;
    }

    /**
     * 数据刷新完成
     * @param isEmpty 是否为空，如果为空，不加载数据
     * @param error 是否出错
     */
    private void refreshComplete(boolean isEmpty, boolean error){
        //重置两次是因为怕状态值和上次一样，就无法使界面产生变化
        loading.set(true);
        loading.set(false);

        noMore.set(!isEmpty);
        noMore.set(isEmpty);

        isError.set(!error);
        isError.set(error);
    }

    /**
     * 设置数据状态
     * @param isEmpty 是否为空，如果
     */
    protected void setDataState(boolean isEmpty) {
        if (firstLoadData) {
            if (isEmpty) {
                stateViewType.set(MultipleStatusView.STATUS_EMPTY);
            }
            else {
                stateViewType.set(MultipleStatusView.STATUS_CONTENT);
                firstLoadData = false;//第一次加载数据成功
            }
        }
        refreshComplete(isEmpty, false);
    }

    /**
     * 出错监听处理
     * @param e Throwable异常
     * @param isRefresh 是否在刷新
     */
    public void onError(Throwable e, boolean isRefresh){
        LogUtils.e("网络请求错误："+e.getMessage());
        if (firstLoadData) {
            if(Network.isConnected()) {
                stateViewType.set(MultipleStatusView.STATUS_ERROR);
            }else {
                stateViewType.set(MultipleStatusView.STATUS_NO_NETWORK);
            }
        }else{
            if(isRefresh){
                ToastUtils.showShort("刷新失败，请重试");
            }
            refreshComplete(false, true);
        }
    }

}

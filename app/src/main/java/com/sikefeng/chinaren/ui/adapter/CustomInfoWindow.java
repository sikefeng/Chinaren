package com.sikefeng.chinaren.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.NavigationUtils;

/**
 * Created by Richard on 1/10/17.
 */

public class CustomInfoWindow implements AMap.InfoWindowAdapter , View.OnClickListener {
    private View infoWindow = null;
    private Context mcontext;
    private Marker mMarker;
    public CustomInfoWindow(Context mcontext) {
        this.mcontext = mcontext;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(mcontext).inflate(R.layout.custom_infowindow, null);
        }
        render(marker, infoWindow);
        mMarker=marker;
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
           //如果想修改自定义Infow中内容，请通过view找到它并修改
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView waitNum = (TextView) view.findViewById(R.id.waitNum);
        waitNum.setText(marker.getTitle());

        LinearLayout navigation = (LinearLayout) view.findViewById(R.id.navigation_LL);
        LinearLayout call = (LinearLayout) view.findViewById(R.id.call_LL);
        navigation.setOnClickListener(this);
        call.setOnClickListener(this);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.navigation_LL:  //点击导航
                if (mMarker!=null){
                    NavigationUtils.Navigation(mMarker.getPosition());
                }
                break;

            case R.id.call_LL:  //点击打电话

                break;
        }
        if (mMarker!=null){
            if (mMarker.isInfoWindowShown()){
                mMarker.hideInfoWindow();
            }
        }
    }
}

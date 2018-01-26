package com.sikefeng.chinaren.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.entity.model.MarkerBean;
import com.sikefeng.chinaren.ui.adapter.CustomInfoWindow;
import com.sikefeng.chinaren.utils.DataUtils;
import com.sikefeng.chinaren.utils.NetImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 24/9/17.
 */

public class HomeFragment extends android.support.v4.app.Fragment implements AMap.OnMapClickListener, AMap.OnMarkerClickListener {

    public AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private MapView mapView;
    private AMap aMap;
    private View mapLayout;
    private CustomInfoWindow customWindow;
    private Marker currentMarker=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mapLayout == null) {
            Log.i("sys", "MF onCreateView() null");
            mapLayout = inflater.inflate(R.layout.fragment_home, null);
            mapView = (MapView) mapLayout.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            if (aMap == null) {
                aMap = mapView.getMap();
            }
            aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        }else {
            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
        }
        addMarkersToMap();
        initOperation();

        SwipeBackHelper.getCurrentPage(getActivity())//获取当前页面
                .setSwipeBackEnable(false);//设置是否可滑动
        SwipeBackHelper.getCurrentPage(getActivity()).setSwipeRelateEnable(false);
        return mapLayout;
    }


    private void initOperation() {
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        customWindow=new CustomInfoWindow(getActivity());
        aMap.setInfoWindowAdapter(customWindow);//AMap类中

    }



    private void addMarkersToMap(){
        ArrayList<MarkerOptions> markerOptionsList=new ArrayList<MarkerOptions>();
        List<MarkerBean> data= DataUtils.getMarkerData();
//        List<MarkerBean> data=new ArrayList<>();
        for (MarkerBean bean: data) {
            System.out.println(bean.toString());
            LatLng latLng = new LatLng(bean.getLat(),bean.getLng());
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(bean.getUserName()).snippet(latLng.toString());
            markerOption.draggable(true);//设置Marker可拖动
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_marker, null);
            SimpleDraweeView imageView=(SimpleDraweeView)view.findViewById(R.id.headview);
            imageView.setImageURI(Uri.parse(bean.getIcon()));
            markerOption.icon(BitmapDescriptorFactory.fromView(view));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            markerOptionsList.add(markerOption);
        }
        aMap.addMarkers(markerOptionsList,true);

//        LatLng latLng = new LatLng(39.906901,116.397972);
//        MarkerOptions markerOption = new MarkerOptions();
//        markerOption.position(latLng);
//        markerOption.title("北京1").snippet("北京：39.906901,116.397972");
//        markerOption.draggable(true);//设置Marker可拖动
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_marker, null);
//        CircleImageView imageView=(CircleImageView)view.findViewById(R.id.icon);
//        ImageUtils.displayImage(imageView,"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1534445597,1881150091&fm=27&gp=0.jpg");
//        markerOption.icon(BitmapDescriptorFactory.fromView(view));
//        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
//        markerOptionsList.add(markerOption);
////
//        LatLng latLng2 = new LatLng(39.90,116.38);
//        MarkerOptions markerOption2 = new MarkerOptions();
//        markerOption2.position(latLng2);
//        markerOption2.title("北京2").snippet("北京：39.906901,116.397972");
//        markerOption2.draggable(true);//设置Marker可拖动
//        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_marker, null);
//        ImageView imageView2=(ImageView)view2.findViewById(R.id.icon);
//        ImageUtils.displayImage(imageView2,"https://adimg.uve.weibo.com/public/files/image/660x165_img59d091f75487d.png");
//        markerOption2.icon(BitmapDescriptorFactory.fromView(view2));
//        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption2.setFlat(true);//设置marker平贴地图效果
//        markerOptionsList.add(markerOption2);
//
//
//        View view3 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_marker, null);
//        ImageView iv_user = (ImageView) view3.findViewById(R.id.icon);
//        ImageUtils.displayImage(iv_user,"https://adimg.uve.weibo.com/public/files/image/660x165_img59d091f75487d.png");
//////
//////        //创建Marker对象
//        Marker marker = aMap.addMarker(new MarkerOptions().position(new LatLng(39.90,116.397972))
//                .icon(BitmapDescriptorFactory.fromView(view3)).draggable(true));
//        Bitmap bmp= ImageLoader.getInstance().loadImageSync("");
//        aMap.addMarker(new MarkerOptions().position(new LatLng(39.90,116.397972))
//                .icon(BitmapDescriptorFactory.fromBitmap(bmp)).draggable(true));

        LatLng mLocalLatlng=new LatLng(23.147267,113.312213);
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocalLatlng,20f));
        //添加Mark
        aMap.addMarker(new MarkerOptions()
//                .anchor(0.5f, 0.5f)//设置锚点
                .position(mLocalLatlng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka)));



        //设置缩放级别（缩放级别为4-20级）
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(mLocalLatlng, 4, 0, 0)));
        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                aMap.clear();
                addMarkersToMap();
            }
        },500);
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
//        ToastUtils.showBottom("222222222222222222");
        //点击地图上没marker 的地方，隐藏inforwindow
//        if (oldMarker != null) {
//            oldMarker.hideInfoWindow();
//            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka));
//        }
        if (currentMarker!=null){
            currentMarker.hideInfoWindow();
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        currentMarker=marker;
//        if (!marker.getPosition().equals(myLatLng)){ //点击的marker不是自己位置的那个marker
//            if (oldMarker != null) {
//                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka));
//            }
//            oldMarker = marker;
//            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka));
//        }

        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }




}
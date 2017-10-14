package com.sikefeng.chinaren.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.adapter.InfoWinAdapter;

public class MainActivity extends android.support.v4.app.Fragment implements AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMapLocationListener, LocationSource {

    private MapView mapView;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption;
    private AMap aMap;
    private UiSettings uiSettings;
    private InfoWinAdapter adapter;
    private Marker oldMarker;
    private OnLocationChangedListener mListener;
    private LatLng myLatLng;
    private View mapLayout;
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
        }else {
            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
        }
        //在执行onCreateView时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        initOperation();
        return mapLayout;
    }



    private void initOperation() {
        initMap();
        initLocation();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
//        if (aMap == null) {
//            aMap = mapView.getMap();
//            uiSettings = aMap.getUiSettings();
//            aMap.setOnMapClickListener(this);
//        }
//        uiSettings.setZoomControlsEnabled(false); //隐藏缩放控件

        aMap.setOnMarkerClickListener(this);
        //自定义InfoWindow
        adapter = new InfoWinAdapter(getActivity());
        aMap.setInfoWindowAdapter(adapter);

        addMarkerToMap(new LatLng(30.666482, 104.036407),"成都","中国四川省成都市");
    }


    /**
     * 初始化定位
     */
    private void initLocation(){
        // 自定义系统定位小蓝点--我的位置
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.empty_photo));// 设置小蓝点的图标
        myLocationStyle.strokeColor(getResources().getColor(R.color.text_color));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 29, 161, 242));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
//        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
//        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位资源。如果不设置此定位资源则定位按钮不可点击。并且实现activate激活定位,停止定位的回调方法
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }



    //激活定位
    //记得注册定位
    //<service android:name="com.amap.api.location.APSService"/>
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); //管理地图的生命周期
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); //管理地图的生命周期
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); //管理地图的生命周期
    }

    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        if (oldMarker != null) {
            oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.empty_photo));
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {

        if (!marker.getPosition().equals(myLatLng)){ //点击的marker不是自己位置的那个marker
            if (oldMarker != null) {
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.empty_photo));
            }
            oldMarker = marker;
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.empty_photo));
        }

        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }

    //定位成功的监听
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {

            if(mListener != null){
//                aMap.clear();  清除之前的marker
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点-我的位置
            }

            if (aMapLocation.getErrorCode() == 0) {
                myLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 14));
                String city = aMapLocation.getCity();
                String address = aMapLocation.getAddress();
//                addMarkerToMap(latLng,city,address);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void addMarkerToMap(LatLng latLng, String title, String snippet) {
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.empty_photo))
        );
    }

}

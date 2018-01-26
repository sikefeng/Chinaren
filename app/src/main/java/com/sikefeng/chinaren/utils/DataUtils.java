package com.sikefeng.chinaren.utils;

import com.sikefeng.chinaren.entity.model.GradeBean;
import com.sikefeng.chinaren.entity.model.MarkerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 24/9/17.
 */

public class DataUtils {


    //班级数据
    public static List<GradeBean> getGradClass(){
        ArrayList<String> albums=new ArrayList<>();
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767922&di=8e00a06e1683e87cc06b1f034849eb83&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fa1%2F120%2Fd%2F23.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477704444&di=017bb5bc6b54b6e226161b2640b3697b&imgtype=0&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20141027%2Fsy_73473478236.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477704799&di=09341a8e85329434c41fa22c25412687&imgtype=0&src=http%3A%2F%2Fpic9.photophoto.cn%2F20081013%2F0034034469050270_b.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767930&di=da325c85d377e3bd249024e65af38e64&imgtype=0&src=http%3A%2F%2Fpic23.photophoto.cn%2F20120522%2F0034034416383757_b.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767930&di=7c26d601168faf55f4de4fa5e3b618a8&imgtype=0&src=http%3A%2F%2Fpic34.photophoto.cn%2F20150314%2F0034034878331717_b.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767929&di=a251d7511e65eecf349c0e046bddccae&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F71%2F49%2F28558PICCfv_1024.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767925&di=b1d486b9c77e7cff9add3cfd1e49971f&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140817%2F0034034463193076_b.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767924&di=e53a2392cc31e361809db71711015840&imgtype=0&src=http%3A%2F%2Fpic20.photophoto.cn%2F20110902%2F0034034471873095_b.jpg");
            albums.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507477767923&di=5e003fdcd7a4e4925669cf1649dee3fa&imgtype=0&src=http%3A%2F%2Fpic31.nipic.com%2F20130706%2F8821914_141208752148_2.jpg");
        List<GradeBean> list=new ArrayList<GradeBean>();
        for (int i = 0; i < 10; i++) {
            GradeBean bean=new GradeBean();
            bean.setId(String.valueOf(i));
            bean.setGradeName("初中("+(i+1)+")班");
            bean.setPeopleNumber(10+i);
            bean.setGradeAlbums(albums);
            bean.setBgPhoto(albums.get(1));
            list.add(bean);
        }
        return list;
    }


    public static List<MarkerBean> getMarkerData(){
        List<MarkerBean> data=new ArrayList<>();
        MarkerBean mMarker=new MarkerBean();
        mMarker.setIcon("http://mingxing.facang.com/uploads/allimg/150727/150A450H-0.jpg");
        mMarker.setLat(23.13);
        mMarker.setLng(113.27);
        mMarker.setUserName("霍建华");
        mMarker.setAge(21);
        data.add(mMarker);

        MarkerBean mMarker2=new MarkerBean();
        mMarker2.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506885833574&di=690061a7f2b841c0ec5f444bda73e4a6&imgtype=0&src=http%3A%2F%2Ff2.topitme.com%2F2%2F8f%2F49%2F1184405753dea498f2o.jpg");
        mMarker2.setLat(23.117055306224895);
        mMarker2.setLng(113.2759952545166);
        mMarker2.setUserName("李敏镐");
        mMarker2.setAge(22);
        data.add(mMarker2);

        mMarker=new MarkerBean();
        mMarker.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506887138081&di=caf9b651f38f3dd89368edbd06fdf39a&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fcrop%253D0%252C0%252C2048%252C1296%2Fsign%3Ddbe2bc0a8182b90129e299734ebd8547%2Fc83d70cf3bc79f3decc2731db1a1cd11728b29ff.jpg");
        mMarker.setLat(23.1066805);
        mMarker.setLng(113.3245904);
        mMarker.setUserName("广州塔");
        mMarker.setAge(23);
        data.add(mMarker);

        mMarker=new MarkerBean();
        mMarker.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507284072695&di=929896070e33b851b12d4b4735e7948d&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-15%2F224002855.jpg");
        mMarker.setLat(23.1546538);
        mMarker.setLng(113.2888063);
        mMarker.setUserName("白云山");
        mMarker.setAge(24);
        data.add(mMarker);

        mMarker=new MarkerBean();
        mMarker.setIcon("http://img3.imgtn.bdimg.com/it/u=3070668342,1393287799&fm=27&gp=0.jpg");
        mMarker.setLat(22.50);
        mMarker.setLng(113.00);
        mMarker.setUserName("汉溪长隆");
        mMarker.setAge(25);
        data.add(mMarker);

        mMarker=new MarkerBean();
        mMarker.setIcon("http://img0.imgtn.bdimg.com/it/u=2086792619,2415661541&fm=27&gp=0.jpg");
        mMarker.setLat(23.126457);
        mMarker.setLng(113.317329);
        mMarker.setUserName("张三");
        mMarker.setAge(21);
        data.add(mMarker);
        return data;
    }



}

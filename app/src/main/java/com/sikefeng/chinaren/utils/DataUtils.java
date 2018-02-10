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
            albums.add("http://static.xialv.com/cover/2013/08/20/9d/13769934725482jnlzg_490X285.jpg");
            albums.add("http://www.ljyz.org/General/UploadFiles_5719/201106/2011062016113790.jpg");
            albums.add("http://img0.imgtn.bdimg.com/it/u=2055345719,1372590361&fm=27&gp=0.jpg");
            albums.add("http://pic18.nipic.com/20111031/8708076_114014306000_2.jpg");
            albums.add("http://image.cnpp.cn/upload/images/20170711/15443416745_700x466.jpg");
            albums.add("http://img4.imgtn.bdimg.com/it/u=3448633739,1452275249&fm=214&gp=0.jpg");
            albums.add("http://img2.imgtn.bdimg.com/it/u=3606080340,1870876656&fm=27&gp=0.jpg");
            albums.add("http://pic29.nipic.com/20130508/11918471_133030369170_2.jpg");
            albums.add("http://img0.imgtn.bdimg.com/it/u=1974474198,3145686328&fm=27&gp=0.jpg");
            albums.add("http://imgsrc.baidu.com/forum/w=580/sign=c50eae6aa8ec8a13141a57e8c7029157/dd22249b033b5bb55bc8aa7b37d3d539b600bc2b.jpg");
            albums.add("http://img5.imgtn.bdimg.com/it/u=4278707509,1321244264&fm=27&gp=0.jpg");
            albums.add("http://img2.imgtn.bdimg.com/it/u=2273697327,2112964007&fm=214&gp=0.jpg");
            albums.add("http://img2.imgtn.bdimg.com/it/u=33959737,3708939958&fm=214&gp=0.jpg");
            albums.add("http://img5.imgtn.bdimg.com/it/u=712290449,510755658&fm=27&gp=0.jpg");
            albums.add("http://img3.imgtn.bdimg.com/it/u=1008506640,3776531443&fm=27&gp=0.jpg");
            List<GradeBean> list=new ArrayList<GradeBean>();
        for (int i = 0; i < 12; i++) {
            GradeBean bean=new GradeBean();
            bean.setId(String.valueOf(i));
            bean.setGradeName("初中("+(i+1)+")班");
            bean.setPeopleNumber(10+i);
            bean.setGradeAlbums(albums);
            bean.setBgPhoto(albums.get(i));
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
        mMarker2.setIcon("http://img.tupianzj.com/uploads/allimg/150911/9-150911194G0-50.jpg");
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

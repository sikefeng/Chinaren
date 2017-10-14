package com.sikefeng.chinaren.entity.model;

/**
 * Created by Richard on 1/10/17.
 */

public class MarkerBean {

    private double lat;
    private double lng;
    private String userName;
    private String icon;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MarkerBean{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", userName='" + userName + '\'' +
                ", icon='" + icon + '\'' +
                ", age=" + age +
                '}';
    }
}

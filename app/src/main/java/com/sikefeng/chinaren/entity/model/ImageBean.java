package com.sikefeng.chinaren.entity.model;

import java.io.Serializable;

/**
 * @author Sikefeng
 * @description TODO:
 * @date 2018\7\9 0009 9:10
 **/

public class ImageBean implements Serializable {

    private String title;
    private String firstImage;
    private String imageList;

    public ImageBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }
}

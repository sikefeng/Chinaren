package com.sikefeng.chinaren.entity.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sikefeng
 * @description TODO:
 * @date 2018\7\9 0009 9:10
 **/

public class ImageBean implements Serializable {

    private String title;
    private String firstImage;
    private List<String> imageList;

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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}

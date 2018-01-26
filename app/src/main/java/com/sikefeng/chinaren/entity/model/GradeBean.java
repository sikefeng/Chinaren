package com.sikefeng.chinaren.entity.model;

import java.util.ArrayList;

/**
 * 文件名：GradeData <br>
 * 创建时间： 24/9/17 下午PM2:01 <br>
 * 文件描述：<br>
 * 年级实体类
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class GradeBean {

    /**
     * 班级ID
     */
    private String id;
    /**
     * 班级名称
     */
    private String gradeName;
    /**
     * 班级相册
     */
    private ArrayList<String> gradeAlbums;
    /**
     * 班级人数
     */
    private int peopleNumber;
    /**
     * 背景图片
     */
    private String bgPhoto;

    public String getBgPhoto() {
        return bgPhoto;
    }

    public void setBgPhoto(String bgPhoto) {
        this.bgPhoto = bgPhoto;
    }

    public ArrayList<String> getGradeAlbums() {
        return gradeAlbums;
    }

    public void setGradeAlbums(ArrayList<String> gradeAlbums) {
        this.gradeAlbums = gradeAlbums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}

package com.sikefeng.chinaren.entity.model;



/**
 * @author Sikefeng
 * @description TODO:
 * @date 2018/6/27 22:06
 **/
public class NoteBean{

    private String id; //ID
    private String createDate; //创建日期



    private String userId; //用户ID
    private String title; //标题
    private String content; //内容
    private String background; //背景
    private int isPrivate; // (0.私有的 1.公开的)

    public NoteBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}



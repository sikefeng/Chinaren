
package com.sikefeng.chinaren.entity.other;

import java.io.Serializable;
import java.util.List;


public class ImgsInfo implements Serializable{

    public static final String KEY = ImgsInfo.class.getSimpleName();

    /**
     * 图片列表
     */
    private List<String> imgsList;
    /**
     * 当前图片位置
     */
    private int curPos;

    /**
     * 图片详情
     * @param imgsList 图片列表
     * @param curPos 当前位置
     */
    public ImgsInfo(List<String> imgsList, int curPos) {
        this.imgsList = imgsList;
        this.curPos = curPos;
    }

    public List<String> getImgsList() {
        return imgsList;
    }

    public void setImgsList(List<String> imgsList) {
        this.imgsList = imgsList;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }
}

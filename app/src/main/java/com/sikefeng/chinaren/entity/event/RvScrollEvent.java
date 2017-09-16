
package com.sikefeng.chinaren.entity.event;


public class RvScrollEvent {

    /**
     * Tab索引
     */
    private int tabIndex;
    /**
     * 指针指向的某条数据
     */
    private int pos;

    /**
     * 滚动事件
     * @param tabIndex TAB索引
     * @param pos 指定的数据
     */
    public RvScrollEvent(int tabIndex, int pos){
        this.tabIndex = tabIndex;
        this.pos = pos;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public int getPos() {
        return pos;
    }
}

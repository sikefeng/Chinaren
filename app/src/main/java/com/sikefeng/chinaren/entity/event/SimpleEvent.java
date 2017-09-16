
package com.sikefeng.chinaren.entity.event;


public class SimpleEvent {

    /**
     * 事件类型
     */
    private String type;

    /**
     * 类型
     * @param type 类型
     * @return SimpleEvent
     */
    public SimpleEvent withType(String type){
        this.type = type;
        return this;
    }

    public String getType(){
        return type;
    }

    //public static final String name = "";

}

package com.sikefeng.chinaren.entity.event;

/**
 * 文件名：VerCodeEvent <br>
 * 创建时间： 25/7/17 下午AM7:35 <br>
 * 文件描述：<br>
 * 验证码事件
 *
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class UpdatePwdEvent {

    /**
     * 事件类型
     */
    private String type;


    /**
     * 类型
     * @param type 类型
     * @return UpdatePwdEvent
     */
    public UpdatePwdEvent withType(String type){
        this.type = type;
        return this;
    }

    public String getType(){
        return type;
    }

}

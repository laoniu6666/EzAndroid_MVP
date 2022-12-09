package com.laoniu.ezandroid.bean;

import com.laoniu.ezandroid.utils.http.BaseResponse;

/**
 * 聊天列表
 */
public class ConversationListBean extends BaseResponse {

    private String name;
    private String icon;
    private String time;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

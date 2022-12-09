package com.laoniu.ezandroid.mvp.im.conversation;

import com.laoniu.ezandroid.bean.ConversationListBean;

import java.util.ArrayList;
import java.util.List;

public class TalkData {

    public static List<ConversationListBean> getData(){
        List<ConversationListBean> mList = new ArrayList<>();
        mList.add(getTestBean1());
        mList.add(getTestBean2());
        mList.add(getTestBean3());
        mList.add(getTestBean4());
        mList.add(getTestBean5());
        return mList;
    }

    private static ConversationListBean getTestBean1(){
        ConversationListBean bean = new ConversationListBean();
        bean.setName("张三");
        bean.setTime("1");
        bean.setMessage("我通过了你的朋友验证请求，现在我们可以开始聊天了");
        return bean;
    }
    private static ConversationListBean getTestBean2(){
        ConversationListBean bean = new ConversationListBean();
        bean.setName("张三");
        bean.setTime("2");
        bean.setMessage("你好，nice to meet you !");
        return bean;
    }
    private static ConversationListBean getTestBean3(){
        ConversationListBean bean = new ConversationListBean();
        bean.setName("");
        bean.setTime("3");
        bean.setMessage("你好，nice to meet you too!");
        return bean;
    }
    private static ConversationListBean getTestBean4(){
        ConversationListBean bean = new ConversationListBean();
        bean.setName("");
        bean.setTime("4");
        bean.setMessage("我是老牛，以后多交流 !");
        return bean;
    }
    private static ConversationListBean getTestBean5(){
        ConversationListBean bean = new ConversationListBean();
        bean.setName("张三");
        bean.setTime("5");
        bean.setMessage("嗯嗯，好的，我先转给你100W做创业启动资金 !");
        return bean;
    }
}

package com.laoniu.ezandroid.mvp.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.bean.MessageListBean;
import com.laoniu.ezandroid.databinding.ActImBinding;
import com.laoniu.ezandroid.databinding.ItemListTestBinding;
import com.laoniu.ezandroid.mvp.im.conversation.ConversationActivity;
import com.laoniu.ezandroid.utils.other.OnFastClickListener;
import com.laoniu.ezandroid.utils.other.T;
import com.laoniu.ezandroid.utils.view.OnLoadMoreListener;
import com.laoniu.ezandroid.utils.view.adapter.MyRecycleViewAdapter2;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 消息列表
 */
public class IMActivity extends BaseActivity<BasePresenter, ActImBinding> {

    Context context;
    OnLoadMoreListener mOnLoadMoreListener = () -> T.toast("我是有下限的～");

    @Override
    protected int getLayoutId() {
        return R.layout.act_im;
    }

    @Override
    protected Class<BasePresenter> getPresenter() {
        return null;
    }

    @Override
    protected IBaseView getBaseView() {
        return null;
    }

    @Override
    protected void initData() {
        setBackViewVisible(View.VISIBLE);
        List<MessageListBean> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new MessageListBean());
        }
        context = IMActivity.this;
        binding.rcv.setLayoutManager(new LinearLayoutManager(context));
        MyRecycleViewAdapter2 adapter = new MyRecycleViewAdapter2
                <MessageListBean, ItemListTestBinding>(mList, R.layout.item_list_test) {
            @Override
            public void convert(MyViewHolder holder, int pos) {
//                ImgUtils.setView(holder.binding.icon, mList.get(pos).getIcon());
//                holder.binding.tv.setText(mList.get(pos).getMessage());
                holder.itemView.setOnClickListener(new OnFastClickListener() {
                    @Override
                    public void onFastClick(View v) {
                        startActivity(new Intent(IMActivity.this, ConversationActivity.class));
                    }
                });
            }
        };
        binding.rcv.setAdapter(adapter);
        binding.titleLayout.title.setText("消息列表");
    }

}

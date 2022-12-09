package com.laoniu.ezandroid.mvp.im.conversation;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.bean.ConversationListBean;
import com.laoniu.ezandroid.databinding.ActImConversationListBinding;
import com.laoniu.ezandroid.databinding.ItemListConversationBinding;
import com.laoniu.ezandroid.utils.other.T;
import com.laoniu.ezandroid.utils.view.OnLoadMoreListener;
import com.laoniu.ezandroid.utils.view.adapter.MyRecycleViewAdapter2;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 消息列表
 */
public class ConversationActivity extends BaseActivity<BasePresenter, ActImConversationListBinding> {

    Context context;
    OnLoadMoreListener mOnLoadMoreListener = () -> T.toast("我是有下限的～");

    @Override
    protected int getLayoutId() {
        return R.layout.act_im_conversation_list;
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
        List<ConversationListBean> mList = TalkData.getData();
        context = ConversationActivity.this;
        binding.rcv.setLayoutManager(new LinearLayoutManager(context));
        MyRecycleViewAdapter2 adapter = new MyRecycleViewAdapter2
                <ConversationListBean, ItemListConversationBinding>(mList, R.layout.item_list_conversation) {
            @Override
            public void convert(MyViewHolder holder, int pos) {
                String name = mList.get(pos).getName();
                String msg = mList.get(pos).getMessage();
                boolean isMe = TextUtils.isEmpty(name);

                holder.binding.rlOther.setVisibility(!isMe ? View.VISIBLE : View.GONE);
                holder.binding.rlMe.setVisibility(isMe ? View.VISIBLE : View.GONE);
                if (!isMe) {
                    holder.binding.icon.setBackgroundColor(Color.RED);
                    holder.binding.tvMsg.setText(msg);
                } else {
                    holder.binding.iconMe.setBackgroundColor(Color.GREEN);
                    holder.binding.tvMsgMe.setText(msg);
                }
            }
        };
        binding.rcv.setAdapter(adapter);
        binding.titleLayout.title.setOnClickListener(v -> showCheckDialog());
        binding.titleLayout.title.setText("消息列表");
    }

    private void showCheckDialog() {
        if (T.isFastClick()) {
            return;
        }
    }
}

package com.laoniu.ezandroid.mvp.test;

import android.content.Context;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.databinding.ActivityListBinding;
import com.laoniu.ezandroid.databinding.ItemListTestBinding;
import com.laoniu.ezandroid.utils.other.T;
import com.laoniu.ezandroid.utils.view.OnLoadMoreListener;
import com.laoniu.ezandroid.utils.view.PagingScrollHelper;
import com.laoniu.ezandroid.utils.view.adapter.MyRecycleViewAdapter2;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestListActivity extends BaseActivity<BasePresenter, ActivityListBinding> {

    Context context;
    PagingScrollHelper helper;
    OnLoadMoreListener mOnLoadMoreListener = () -> T.toast("我是有下限的～");

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
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
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 110; i++) {
            mList.add("------第" + i + "格-----");
        }
        context = TestListActivity.this;
        binding.rcv.setLayoutManager(new GridLayoutManager(context, 2, RecyclerView.VERTICAL, false));
        MyRecycleViewAdapter2 adapter = new MyRecycleViewAdapter2
                <String, ItemListTestBinding>(mList, R.layout.item_list_test) {
            @Override
            public void convert(MyViewHolder holder, int pos) {
                holder.binding.tv.setText(mList.get(pos));
            }
        };
        binding.rcv.setAdapter(adapter);
        helper = new PagingScrollHelper();
        binding.titleLayout.title.setOnClickListener(v -> showCheckDialog());
    }

    private void showCheckDialog() {
        if (T.isFastClick()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否进入走行规制？");
        builder.setPositiveButton("进入", (dialog, which) -> {
            helper.setUpRecycleView(binding.rcv, mOnLoadMoreListener);
            dialog.dismiss();
        });
        builder.setNegativeButton("退出", (dialog, which) -> {
            helper.clear();
            dialog.dismiss();
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != helper) {
            helper.clear();
        }
    }
}

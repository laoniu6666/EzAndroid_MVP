package com.laoniu.ezandroid.mvp.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.LogUtils;
import com.laoniu.ezandroid.BaseFragment;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.FmtNewsBinding;
import com.laoniu.ezandroid.databinding.ItemNewsListBinding;
import com.laoniu.ezandroid.model.CommonData;
import com.laoniu.ezandroid.model.NewsListBean.NewsList;
import com.laoniu.ezandroid.mvp.webview.WebviewActivity;
import com.laoniu.ezandroid.utils.other.ImgUtils;
import com.laoniu.ezandroid.utils.other.OnFastClickListener;
import com.laoniu.ezandroid.utils.view.adapter.MyRecycleViewAdapter2;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment<NewsView,NewsPresenter, FmtNewsBinding> implements NewsView {
    public static final String POSITION = "position";

    List<NewsList> lists = new ArrayList<>();
    MyRecycleViewAdapter2<NewsList, ItemNewsListBinding> adapter;

    public static NewsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fmt_news;
    }

    @Override
    protected Class<NewsPresenter> getPresenter() {
        return NewsPresenter.class;
    }

    @Override
    protected NewsView getBaseView() {
        return this;
    }


    @Override
    protected void initData() {
        binding.rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        adapter = new MyRecycleViewAdapter2<NewsList, ItemNewsListBinding>(lists,R.layout.item_news_list) {
            @Override
            public void convert(MyViewHolder holder, final int pos) {
                ImgUtils.setView(holder.binding.iv1,lists.get(pos).getThumbnail_pic_s());
                ImgUtils.setView(holder.binding.iv2,lists.get(pos).getThumbnail_pic_s02());
                ImgUtils.setView(holder.binding.iv3,lists.get(pos).getThumbnail_pic_s03());
                holder.binding.tvTitle.setText(lists.get(pos).getTitle());
                holder.binding.tvAuthor.setText(lists.get(pos).getAuthor_name());
                holder.binding.tvTime.setText(lists.get(pos).getDate());
                holder.binding.getRoot().setOnClickListener(new OnFastClickListener() {
                    @Override
                    public void onFastClick(View v) {
                        String url = lists.get(pos).getUrl();
                        if(!TextUtils.isEmpty(url)){
                            Intent i = new Intent();
                            i.putExtra("title","新闻详情");
                            i.putExtra("data",url);
                            i.setClass(getActivity(), WebviewActivity.class);
                            getActivity().startActivity(i);
                        }
                    }
                });
            }
        };
        binding.rcv.setAdapter(adapter);

        int pos = getArguments().getInt(POSITION, 0);
        presenter.getData(CommonData.news_title_value[pos]);
    }

    @Override
    public void refreshData(List<NewsList> data) {
        LogUtils.e("newsfmt,refreshData,data.size="+data.size());
        lists.clear();
        lists.addAll(data);
        adapter.notifyDataSetChanged();
    }
}


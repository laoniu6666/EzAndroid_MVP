package com.laoniu.ezandroid.utils.view.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class MyRecycleViewAdapter2<T,B extends ViewDataBinding> extends RecyclerView.Adapter<MyRecycleViewAdapter2.MyViewHolder>{
    List<T> list;
    int ItemViewId=-1;
    B binding;

    public MyRecycleViewAdapter2(List<T> list, @IdRes int ItemViewId){
        this.list=list;
        this.ItemViewId=ItemViewId;
    }

    @NonNull
    @Override
    public MyRecycleViewAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), ItemViewId, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter2.MyViewHolder holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * viewholder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public B binding;
        public MyViewHolder(B binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    //****************************  out method  *********************************

    /**
     * bind data to view
     * @param holder
     * @param pos
     */
    public abstract void convert(MyViewHolder holder, int pos);

}
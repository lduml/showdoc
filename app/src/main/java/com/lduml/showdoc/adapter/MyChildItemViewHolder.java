package com.lduml.showdoc.adapter;


import androidx.recyclerview.widget.RecyclerView;;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lduml.showdoc.R;
import com.lduml.showdoc.inter.MyOnItemClickListener;

public class MyChildItemViewHolder extends RecyclerView.ViewHolder  {

    //implements View.OnClickListener,View.OnLongClickListener

    private MyOnItemClickListener mListener;// 声明自定义的接口
    TextView nameTv;

    // 构造函数中添加自定义的接口的参数
    public MyChildItemViewHolder(View itemView, MyOnItemClickListener listener) {
        super(itemView);
        mListener = listener;
        // 为ItemView添加点击事件
       /* itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);*/
        nameTv = (TextView) itemView.findViewById(R.id.child_item_name);

    }
    /*@Override
    public void onClick(View v) {
        // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
        mListener.onItemClick(v,getPosition());
    }
    @Override
    public boolean onLongClick(View v) {
        mListener.onItemLongClick(v,getPosition());
        //true时 长按事件响应，短按不响应。false时长按时，长按、短按事件同时响应
        return true;
    }*/
}

package com.lduml.showdoc.adapter;


import androidx.recyclerview.widget.RecyclerView;;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lduml.showdoc.R;
import com.lduml.showdoc.inter.MyOnItemClickListener;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
 //   TextView textView;
    private MyOnItemClickListener mListener;// 声明自定义的接口

    ImageView img_item_name;
    TextView tv_item_name;
    /*public HomeHolder(View itemView) {
        super(itemView,mListener);


    }*/


    // 构造函数中添加自定义的接口的参数
    public MyViewHolder(View itemView, MyOnItemClickListener listener) {
        super(itemView);
        mListener = listener;
        // 为ItemView添加点击事件
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        tv_item_name = itemView.findViewById(R.id.tv_item_name);;
        img_item_name = itemView.findViewById(R.id.img_item_name);;
       // textView = (TextView) itemView.findViewById(R.id.textView);

    }
    @Override
    public void onClick(View v) {
        // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
        mListener.onItemClick(v,getPosition());
    }
    @Override
    public boolean onLongClick(View v) {
        mListener.onItemLongClick(v,getPosition());
        //true时 长按事件响应，短按不响应。false时长按时，长按、短按事件同时响应
        return true;
    }
}

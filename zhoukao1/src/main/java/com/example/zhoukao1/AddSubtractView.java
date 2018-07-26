package com.example.zhoukao1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 撩个小媳妇 on 2018/5/15.
 */

public class AddSubtractView extends LinearLayout {

    private TextView tv_num;

    public AddSubtractView(Context context) {
        this(context, null);
    }

    public AddSubtractView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubtractView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.add_subtract, this);
        final TextView tv_add = view.findViewById(R.id.tv_add);
        tv_num = view.findViewById(R.id.tv_num);
        TextView tv_subtract = view.findViewById(R.id.tv_subtract);

        tv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickAdd(v);
                }
            }
        });

        tv_subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickSubtract(v);
                }
            }
        });
    }

    //设置接口
    public interface OnAddSubtractListener {
        void onClickAdd(View view);

        void onClickSubtract(View view);
    }

    //声明接口
    private OnAddSubtractListener listener;

    //设置实现接口的方法
    public void setOnAddSubtractListener(OnAddSubtractListener listener) {
        this.listener = listener;
    }
    //给tv_num赋值的方法

    public void setTv_num(int i) {
        tv_num.setText(i+"条数据");
    }

    //取值的方法
    public int getTv_num(){
        return Integer.parseInt(tv_num.getText().toString().split("条")[0]);
    }

}

package com.example.zhoukao_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 撩个小媳妇 on 2018/5/15.
 */

public class MyView extends RelativeLayout {

    private Button titleBarLeft;
    private TextView titleBarTitle;
    private Button titleBarRight;
    private int num = 0;
    private TypedArray attributes;
    private TextView show;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setupView(context,attrs);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.title,this,true);
        titleBarLeft = (Button) findViewById(R.id.title_bar_left);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarRight = (Button) findViewById(R.id.title_bar_right);
        //show = findViewById(R.id.mes);
    }

    public void setupView(Context context,AttributeSet attrs) {
        attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        if (attributes !=null){
            //设置体title背景色
            int titleBarBackground = attributes.getResourceId(R.styleable.CustomTitleBar_title_background_color, Color.GREEN);
            setBackgroundResource(titleBarBackground);
            //左边按钮
            //设置左边按钮的文字
            String leftButtonText = "-";
            if (!TextUtils.isEmpty(leftButtonText)) {
                titleBarLeft.setText(leftButtonText);
                //设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_left_button_text_color, Color.WHITE);
                titleBarLeft.setTextColor(leftButtonTextColor);
            }
            //右边按钮
            //设置右边按钮的文字
            String rightButtonText = "+";
            if (!TextUtils.isEmpty(rightButtonText)) {
                titleBarRight.setText(rightButtonText);
                //设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_right_button_text_color, Color.WHITE);
                titleBarRight.setTextColor(leftButtonTextColor);
            }
            //标题文字
            //设置标题文字
            String titleText  = num+"条数据";
            if (!TextUtils.isEmpty(titleText)) {
                titleBarTitle.setText(titleText);
                //设置title文字颜色
                int titleTextColor = attributes.getColor(R.styleable.CustomTitleBar_title_text_color, Color.WHITE);
                titleBarTitle.setTextColor(titleTextColor);
            }

            //show.setText(titleText);

            attributes.recycle();
        }
    }

    public void setTitleClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            titleBarLeft.setOnClickListener(onClickListener);
            titleBarRight.setOnClickListener(onClickListener);
        }
    }

    public Button getTitleBarLeftBtn() {
        return titleBarLeft;
    }

    public Button getTitleBarRightBtn() {
        return titleBarRight;
    }

    public TextView getTitleBarTitle() {
        return titleBarTitle;
    }



    public void setTitleText(int titleText) {
        num = titleText;
        Log.i("---",num+"");
        String titleTextNow  = num+"条数据";
        titleBarTitle.setText(titleTextNow);
    }
}

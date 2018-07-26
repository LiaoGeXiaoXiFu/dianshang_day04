package com.example.zhoukao1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 撩个小媳妇 on 2018/5/15.
 */

public class LadderView extends ViewGroup {

    public LadderView(Context context) {
        this(context, null);
    }

    public LadderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LadderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;//自己测量的宽度
        int height = 0;//自己测量的高度
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子view的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行时候
            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                width += lineWidth;
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {//不换行情况
                //叠加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //拿到子控件的个数
        int childCount = getChildCount();
        int width = getWidth();
        //定义一个临时变量 高度
        int lineWidth = 0;
        int lineHeight = 0;
        //循环遍历每一个View
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            //给每一个View设置自己的位置
            v.layout(lineHeight, lineWidth, lineHeight + v.getMeasuredWidth(), lineWidth + v.getMeasuredHeight());
            lineWidth += v.getMeasuredHeight();
            lineHeight += v.getMeasuredWidth();
            int m=300;
            if (lineWidth + v.getMeasuredWidth() > m) {
                lineWidth = m;
                lineHeight = lineWidth++;
                lineWidth = lineWidth--;
            }

        }
    }

    //添加条目的方法
    public TextView addItem(int num) {
        TextView textView = new TextView(getContext());
        textView.setText("第" + num + "条数据");
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.BLUE);

        return textView;
    }

    //删除条目的方法
    public TextView subtractItem(int num) {
        TextView childAt = (TextView) getChildAt(num);
        return childAt;
    }
}
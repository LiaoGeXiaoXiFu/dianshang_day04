package com.example.zhoukao_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by 撩个小媳妇 on 2018/5/15.
 */

public class ShowView extends ViewGroup {

    //记录是添加还是删除状态
    private boolean isAdd;
    private Paint paint1;
    //屏幕宽度
    private int maxWidth;
    //显示控件宽度
    private int showWidth;
    //当前已添加所有控件总宽度
    private int countWidth;
    private int num;

    private int firstX = 0;
    private int firstY = 0;
    private int lastX = 200;
    private int lastY = 100;

    private String showMes;
    private Paint paint2;
    //判断是否重置循环
    private boolean isReset;

    private int z;

    public ShowView(Context context) {
        this(context,null);
    }

    public ShowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint1 = new Paint();
        paint2 = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //屏幕宽度宽度
        maxWidth = 600;
        int maxHeight = heightMeasureSpec;
        //设置宽高
        setMeasuredDimension(maxWidth, maxHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color = getResources().getColor(R.color.aqua);
        paint1.setTextSize(40);
        paint1.setColor(Color.WHITE);
        paint2.setColor(color);

        for (int i = 1;i<=num;i++){

            if (lastX>maxWidth){
                firstX = lastX-400;
                firstY = lastY-100;
                lastX = lastX-200;
                for (int q = 1;q<z;q++){
                    if (firstX>=0){
                        //Log.i("------","二次循环--"+"左上X : "+firstX+"左上Y : "+firstY+"右下X : "+lastX+"右下Y : "+lastY);
                        showMes = "第" + i + "条数据";
                        canvas.drawRect(firstX,firstY,lastX,lastY,paint2);
                        canvas.drawText(showMes,firstX,firstY+50,paint1);
                        firstX = lastX-400;
                        firstY = lastY;
                        lastX-=200;
                        lastY+=100;
                        i++;

                    }
                }
                isReset = true;
            }

//                showMes = "第" + i + "条数据";
//                canvas.drawRect(firstX,firstY,lastX,lastY,paint2);
//                canvas.drawText(showMes,firstX,firstY+50,paint1);

            if (firstX<0&&firstY!=0&&isReset){
                firstX = 0;
                firstY = lastY-100;
                lastX = firstX+200;
                showMes = "第" + i + "条数据";
                Log.i("------","左上X : "+firstX+"左上Y : "+firstY+"右下X : "+lastX+"右下Y : "+lastY);
                canvas.drawRect(firstX,firstY,lastX,lastY,paint2);
                canvas.drawText(showMes,firstX,firstY+50,paint1);
                firstX = lastX;
                firstY = lastY;
                lastX+=200;
                lastY+=100;
                isReset = false;
            }else {
                showMes = "第" + i + "条数据";
                canvas.drawRect(firstX,firstY,lastX,lastY,paint2);
                canvas.drawText(showMes,firstX,firstY+50,paint1);

                firstX = lastX;
                firstY = lastY;
                lastX+=200;
                lastY+=100;
            }

            //Log.i("------","左上X : "+firstX+"左上Y : "+firstY+"右下X : "+lastX+"右下Y : "+lastY);
            z = num - i;
        }

        firstX = 0;
        firstY = 0;
        lastX = 200;
        lastY = 100;

    }

    public void changeShow(int num){
        this.num = num;
        postInvalidate();
    }

}
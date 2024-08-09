package com.example.wechel.heart.Rate_graph.graph_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class Graph_bacgroud extends View {
    PathEffect effect = new PathEffect();
    protected Paint tpaint;
    protected int tlinecolor = Color.parseColor("#FF76f112");//折线颜色；
    protected int tgezicolor = Color.parseColor("#FF1b4200");//网格颜色；
    //小网格颜色
    protected int mSGridColor = Color.parseColor("#FF092100");
    //背景颜色
    protected int mBackgroundColor = Color.BLACK;
    //自身的大小
    protected int mWidth,mHeight;
    //网格宽度
    protected int mGridWidth = 50;
    //小网格的宽度
    protected int mSGridWidth = 10;
    //心电图折线
    protected Path mPath ;
    public Graph_bacgroud(Context context) {
        this(context,null);
    }
    public Graph_bacgroud(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public Graph_bacgroud(Context context,AttributeSet attrs,int defStyleAtter){
        super(context,attrs,defStyleAtter);
        tpaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mBackgroundColor);
        //画小网格

        //竖线个数
        int vSNum = 10000;

        //横线个数
        int hSNum = mHeight/mSGridWidth;//无限延伸；
        tpaint.setColor(mSGridColor);
        tpaint.setStrokeWidth(2);
        //画竖线
        for(int i = 0;i<vSNum+1;i++){
            canvas.drawLine(i*mSGridWidth,0,i*mSGridWidth,mHeight,tpaint);
        }
        //画横线
        for(int i = 0;i<hSNum+1;i++){

            canvas.drawLine(0,i*mSGridWidth,10000,i*mSGridWidth,tpaint);
        }

        //竖线个数
        int vNum = 10000;//mWidth / mGridWidth;
        //横线个数
        int hNum = mHeight / mGridWidth;
        tpaint.setColor(tgezicolor);
        tpaint.setStrokeWidth(2);
        //画竖线
        for(int i = 0;i<vNum+1;i++){
            canvas.drawLine(i*mGridWidth,0,i*mGridWidth,mHeight,tpaint);
        }
        //画横线
        for(int i = 0;i<hNum+1;i++){
            canvas.drawLine(0,i*mGridWidth,10000,i*mGridWidth,tpaint);
        }
    }
//
//    private void initBackground(Canvas canvas) {
//
//        canvas.drawColor(mBackgroundColor);
//        //画小网格
//
//        //竖线个数
//        int vSNum = mWidth /mSGridWidth;
//
//        //横线个数
//        int hSNum = mHeight/mSGridWidth;
//        tpaint.setColor(mSGridColor);
//        tpaint.setStrokeWidth(2);
//        //画竖线
//        for(int i = 0;i<vSNum+1;i++){
//            canvas.drawLine(i*mSGridWidth,0,i*mSGridWidth,mHeight,tpaint);
//        }
//        //画横线
//        for(int i = 0;i<hSNum+1;i++){
//
//            canvas.drawLine(0,i*mSGridWidth,mWidth,i*mSGridWidth,tpaint);
//        }
//
//        //竖线个数
//        int vNum = mWidth / mGridWidth;
//        //横线个数
//        int hNum = mHeight / mGridWidth;
//        tpaint.setColor(tgezicolor);
//        tpaint.setStrokeWidth(2);
//        //画竖线
//        for(int i = 0;i<vNum+1;i++){
//            canvas.drawLine(i*mGridWidth,0,i*mGridWidth,mHeight,tpaint);
//        }
//        //画横线
//        for(int i = 0;i<hNum+1;i++){
//            canvas.drawLine(0,i*mGridWidth,mWidth,i*mGridWidth,tpaint);
//        }
//
//    }
}

package com.example.wechel.heart.Rate_graph.graph_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.Log;

public class Drawpath extends Graph_bacgroud {
    public Drawpath(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();  //重置path;
        effect = new CornerPathEffect(10);
        //用path模拟一个心电图样式
        mPath.moveTo(0, mHeight / 2);
//        try {
//            sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int tmp = 0;
        for (int i = 0,j=50; i < 70; i++) {
//            switch (i % 3) {
//                case 0:
                    mPath.lineTo(tmp + 20, 300);//(int)(Math.random()+1)*50
                    mPath.lineTo(tmp + 70, mHeight / 2 +(int)(Math.random()+1)*100);
                    mPath.lineTo(tmp + 80, mHeight / 2);
                    mPath.lineTo(tmp + 200, mHeight / 2);
                    tmp = tmp +200;//(int)(Math.random()+1)*100 ;//200;
//                    break;
//                case 1:
//                    mPath.lineTo(tmp + 20, 70);
//                    mPath.lineTo(tmp + 70, mHeight / 2 + 50);
//                    mPath.lineTo(tmp + 80, mHeight / 2);
//                    mPath.lineTo(tmp + 120, mHeight / 2);
//                    tmp = tmp + 200;
//                    break;
//                case 2:
//                    mPath.lineTo(tmp + 20, 80);
//                    mPath.lineTo(tmp + 70, mHeight / 2 + 50);
//                    mPath.lineTo(tmp + 80, mHeight / 2);
//                    mPath.lineTo(tmp + 100, mHeight / 2);
//                    tmp = tmp + 200;
//                    break;
//            }
//            mPath.lineTo(tmp + 70, mHeight / 2 + 50);
//            mPath.lineTo(tmp + 80, mHeight / 2);
//            mPath.lineTo(tmp + 200, mHeight / 2);
//            tmp = tmp + 200;
        }
        //设置画笔style
        tpaint.setPathEffect(effect);
        tpaint.setAntiAlias(true);
        tpaint.setStyle(Paint.Style.STROKE);
        tpaint.setColor(tlinecolor);
        tpaint.setStrokeWidth(5);
        canvas.drawPath(mPath, tpaint);
        scrollBy(6, 0);
        Log.e("","OnDraw执行了");
        postInvalidateDelayed(1500);
        Log.e("","refresh");
    }
}

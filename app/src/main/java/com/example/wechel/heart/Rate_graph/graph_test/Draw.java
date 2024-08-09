package com.example.wechel.heart.Rate_graph.graph_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class Draw extends View {
    private Paint paint;
    private Path path;
    float phase;
    public Draw(Context context) {
        super(context);
        paint = new Paint();//创建一个画笔；
        paint.setStyle(Paint.Style.STROKE);//单线；
        paint.setStrokeWidth(2);
        path = new Path();
        path.moveTo(10,10);
        for(int i=1;i<=15;i++){
            path.lineTo(i*20,(float) Math.random()*60);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.RED);
        canvas.drawPath(path,paint);
        canvas.translate(0,60);
        phase++;
        invalidate();
    }
}

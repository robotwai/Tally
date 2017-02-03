package com.example.lz.tally.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by liz on 17-1-7.
 */

public class TestView2 extends View {
    public TestView2(Context context) {
        super(context);
    }

    public TestView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = getPaint();
    }
    int dx;
    Paint mPaint;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);

        canvas.drawPath(path,mPaint);
        mPaint.setColor(Color.RED);

//使用DashPathEffect画线段
        mPaint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
        canvas.translate(0,100);
        canvas.drawPath(path,mPaint);

//画同一条线段,偏移值为15
        mPaint.setPathEffect(new DashPathEffect(new float[]{20,10,50,100},dx));
        mPaint.setColor(Color.YELLOW);
        canvas.translate(0,100);
        canvas.drawPath(path,mPaint);
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }
    public void startAnim(){
        final ValueAnimator animator = ValueAnimator.ofInt(0,180);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }

        });

        animator.start();
    }
}

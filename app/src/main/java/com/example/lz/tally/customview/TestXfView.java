package com.example.lz.tally.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.lz.tally.R;

/**
 * Created by liz on 17-2-13.
 */

public class TestXfView extends View {
    private Paint mPaint;
    private Bitmap mBmp;
    public TestXfView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.dog);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,0,width,height,mPaint);

        canvas.restoreToCount(layerID);
    }
}

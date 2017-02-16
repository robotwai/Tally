package com.example.lz.tally.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.lz.tally.R;

/**
 * Created by liz on 17-1-5.
 */

public class SlideActivity extends BaseActivity {
    View mRootView;
    private GestureDetector mDetector;
    private int mWindowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    void initView(){
        mRootView = getWindow().getDecorView();
        mRootView.setBackgroundColor(R.color.colorMain);
        mDetector = new GestureDetector(this, new GestureListener());
        mWindowWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    /**
     * 手势监听
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1 != null) {
                handlerCurrentActivityScroll(e2);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * 处理当前页面滑动
         */
        private void handlerCurrentActivityScroll(MotionEvent e2) {
            mRootView.setTranslationX(e2.getX());
            if (e2.getX() > mWindowWidth - 20) {
                finish();
            }
        }
    }
}

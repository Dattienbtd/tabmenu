package com.dattien.tabmenu.tabview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// >=== #123455
public class FlingRecycleView extends RecyclerView {
    public static boolean isShow = false;
    public static boolean isScroll = false;
    CircleLayoutManager circleLayoutManager;
    private boolean mIsFlingAble = true;
    private Context mContext;

    public FlingRecycleView(Context context) {

        super(context);
        mContext = context;
    }

    public FlingRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public FlingRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setFlingAble(boolean flingAble) {
        mIsFlingAble = flingAble;
    }

    public void setL(CircleLayoutManager circleLayoutManager) {
        this.circleLayoutManager = circleLayoutManager;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX *= 0.4f;
        return mIsFlingAble ? super.fling(velocityX, velocityY) : false;
    }

    @Override
    public void smoothScrollBy(int dx, int dy) {
        super.smoothScrollBy(dx, dy);
    }


}
// <=== #123455

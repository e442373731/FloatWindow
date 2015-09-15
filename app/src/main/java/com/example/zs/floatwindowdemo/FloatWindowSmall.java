package com.example.zs.floatwindowdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by zs on 2015/9/14.
 */
public class FloatWindowSmall extends LinearLayout {
    public static int viewWidth;
    public static int viewHeight;
    private WindowManager windowManager;
    private int statusHeight;

    /**
     * Text��layout�е�λ��
     */
    private float xInView;
    private float yInView;

    /**
     * Layout����Ļ�е�λ��
     */
    private float xInScreen;
    private float yInScreen;

    /**
     * ����λ��
     */
    private float xDown;
    private float yDowm;

    private WindowManager.LayoutParams mParams;

    public void setmParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    public FloatWindowSmall(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.small_float_window, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        TextView percentView = (TextView) findViewById(R.id.percent_text);
        percentView.setText(MyWindowManager.getUsedPercentValue(context));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStutasBarHeight();
                xDown = event.getRawX();
                yDowm = event.getRawY() - getStutasBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStutasBarHeight();
                updateLocation();
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(xDown - xInScreen) < 5 && Math.abs(yDowm - yInScreen) < 5){
                    openBigWindow(getContext());
                }
            default:
                break;
        }
        return true;
    }

    /**
     * �򿪴󴰿�
     */
    private void openBigWindow(Context context){
        MyWindowManager.removeSmallWindow(context);
        MyWindowManager.createBigWindow(context);
    }

    /**
     * ����windowλ��
     */
    private void updateLocation() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * ��ȡ״̬���ĸ߶�
     */
    public int getStutasBarHeight() {
        if (statusHeight == 0) {
            try {
                Class<?> c = Class.forName("cpm.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}

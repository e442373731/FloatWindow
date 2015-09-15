package com.example.zs.floatwindowdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by zs on 2015/9/14.
 */
public class MyWindowManager {
    private static FloatWindowSmall smallWindow;
    private static FloatWindowBig bigWindow;

    private static ActivityManager activityManager;
    private static WindowManager windowManager;

    private static WindowManager.LayoutParams smallWindowParams;
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 获取WindowManager
     */
    public static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * 创建小窗体
     */
    public static void createSmallFloatWindow(Context context) {
        windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmall(context);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmall.viewWidth;
                smallWindowParams.height = FloatWindowSmall.viewHeight;
                //设置小window初始位置
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setmParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    /**
     * 去除小窗体
     */
    public static void removeSmallWindow(Context context){
        if(smallWindow != null){
            windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 判断是否有窗体在运行
     */
    public static boolean isWindowShowing(){
        return smallWindow != null || bigWindow != null;
    }

    /**
     * 创建大窗体
     */
    public static void createBigWindow(Context context){

    }

    /**
     * 移除大窗体
     */
    public static void removeBigWindow(Context context){

    }

    /**
     * 获取用了多少内存
     */
}

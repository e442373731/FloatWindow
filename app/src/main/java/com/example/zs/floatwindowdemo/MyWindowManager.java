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
     * ��ȡWindowManager
     */
    public static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * ����С����
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
                //����Сwindow��ʼλ��
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setmParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    /**
     * ȥ��С����
     */
    public static void removeSmallWindow(Context context){
        if(smallWindow != null){
            windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * �ж��Ƿ��д���������
     */
    public static boolean isWindowShowing(){
        return smallWindow != null || bigWindow != null;
    }

    /**
     * ��������
     */
    public static void createBigWindow(Context context){

    }

    /**
     * �Ƴ�����
     */
    public static void removeBigWindow(Context context){

    }

    /**
     * ��ȡ���˶����ڴ�
     */
}

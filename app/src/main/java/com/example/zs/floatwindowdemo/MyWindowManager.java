package com.example.zs.floatwindowdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
     * 获取ActivityManager实例
     */
    public static ActivityManager getActivityManager(Context context){
        if(activityManager == null){
            activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return activityManager;
    }

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
        windowManager = getWindowManager(context);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if(bigWindow == null){
            bigWindow = new FloatWindowBig(context);
            if(bigWindowParams == null){
                bigWindowParams = new  WindowManager.LayoutParams();
                bigWindowParams.x = width / 2 - FloatWindowBig.viewWidth / 2;
                bigWindowParams.y = height / 2 - FloatWindowBig.viewHeight / 2;
                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBig.viewWidth;
                bigWindowParams.height = FloatWindowBig.viewHeight;
                bigWindowParams.format = PixelFormat.RGBA_8888;
            }
            windowManager.addView(bigWindow, bigWindowParams);
        }
    }

    /**
     * 移除大窗体
     */
    public static void removeBigWindow(Context context){
        if(bigWindow != null){
            windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    /**
     * 更新所用内存百分比显示
     */
    public static void updateUsedMemory(Context context){
        if(smallWindow != null){
            TextView percentView = (TextView)smallWindow.findViewById(R.id.percent_text);
            percentView.setText(getUsedPercentValue(context));
        }
    }

    /**
     * 获取用了内存百分比
     */
    public static String getUsedPercentValue(Context context){
        String dir = "/proc/meminfo";
        BufferedReader reader = null;
        try {
            FileReader fileReader = new FileReader(dir);
            reader = new BufferedReader(fileReader,2048);
            String memoryLine = reader.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int)((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "Windows";
    }


    /**
     * 获取当前可用内存
     */
    private static long getAvailableMemory(Context context){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }
}

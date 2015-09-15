package com.example.zs.floatwindowdemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zs on 2015/9/14.
 */
public class FloatWindowService extends Service {
    private Handler handler = new Handler();
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 定时任务
     */
    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            if(isHome() && !MyWindowManager.isWindowShowing()){
                //创建小窗体 - 通过handler
            }else if(!isHome() && MyWindowManager.isWindowShowing()){
                //移除窗体
            }else if(isHome() && MyWindowManager.isWindowShowing()){
                //更新percent text view
            }
        }
    }

    /**
     * 获取所有的桌面应用
     */
    private List<String> getHomes(){
        List<String> names = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo info : resolveInfo){
            names.add(info.activityInfo.packageName);
        }
        return names;
    }

    /**
     * 判断是是在桌面
     */
    private boolean isHome(){
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        return getHomes().contains(tasks.get(0).topActivity.getPackageName());
    }
}

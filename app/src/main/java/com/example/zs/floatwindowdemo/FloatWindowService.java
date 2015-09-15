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
     * ��ʱ����
     */
    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            if(isHome() && !MyWindowManager.isWindowShowing()){
                //����С���� - ͨ��handler
            }else if(!isHome() && MyWindowManager.isWindowShowing()){
                //�Ƴ�����
            }else if(isHome() && MyWindowManager.isWindowShowing()){
                //����percent text view
            }
        }
    }

    /**
     * ��ȡ���е�����Ӧ��
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
     * �ж�����������
     */
    private boolean isHome(){
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        return getHomes().contains(tasks.get(0).topActivity.getPackageName());
    }
}

package com.example.zs.floatwindowdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by zs on 2015/9/14.
 */
public class FloatWindowBig extends LinearLayout {
    public static int viewWidth;
    public static int viewHeight;

    public FloatWindowBig(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.big_float_window,this);
        View view = findViewById(R.id.small_window_layout);
        viewHeight = view.getLayoutParams().height;
        viewWidth = view.getLayoutParams().width;
        Button close = (Button)findViewById(R.id.close_button);
        Button back = (Button)findViewById(R.id.back_button);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //close big window

                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭大窗体 打开小的
            }
        });
    }
}

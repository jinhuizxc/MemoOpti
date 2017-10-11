package com.example.jh.memoopti;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 内存优化篇
 * Android系统内存分配与回收方式：
 * 1、一个app通常是一个进程，对应一个虚拟机
 * 2、GC只在Heap剩余空间不够时才会发垃圾回收
 * 3、GC触发时，所有的线程都是会被暂停
 * <p>
 * App内存限制机制：
 * 每个app分配的最大内存限制，随不同设备而不同
 * 吃内存大户：图片
 * <p>
 * 切换应用时后台App清理机制：
 * APP切换时的LRU Cache
 * onTrimMemory()回调方法
 * <p>
 * 监控内存的方法演示
 * as代码演示
 * <p>
 * <p>
 * 在cmd命令行 输入
 * adb shell：进入安卓底层linux系统命令
 * ps 查看系统里面进程的命令
 * 查看进程相关信息：dumpsys meminfo com.example.jh.memoopti
 */
public class MainActivity extends AppCompatActivity {

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        calculate();
    }

    private void calculate() {
        StringBuilder strBuilder = new StringBuilder();
        // ActivityManager 系统服务
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getMemoryClass();  // 以m为单位
        // 以m为单位,针对 清单文件下android:largeHeap="true"
        int largementClass = activityManager.getLargeMemoryClass();
        strBuilder.append("memClass =" + memClass + "\n");
        strBuilder.append("largementClass =" + largementClass + "\n");

        Float totalMemory = Runtime.getRuntime().totalMemory() * 1.0f / (1024 * 1024);
        Float freeMemory = Runtime.getRuntime().freeMemory() * 1.0f / (1024 * 1024);
        Float maxMemory = Runtime.getRuntime().maxMemory() * 1.0f / (1024 * 1024);
        strBuilder.append("maxMemory =" + maxMemory + "\n");
        strBuilder.append("freeMemory =" + freeMemory + "\n");
        strBuilder.append("totalMemory =" + totalMemory + "\n");

        info.setText(strBuilder.toString());
        Log.e("TAG", strBuilder.toString()); // 384 表明模拟器最多分配内存384m
    }

    private void init() {
        info = (TextView) findViewById(R.id.text);
    }
}

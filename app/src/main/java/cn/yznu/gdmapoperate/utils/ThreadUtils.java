package cn.yznu.gdmapoperate.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者：uiho_mac
 * 时间：2018/9/4
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ThreadUtils {
    public static void runOnUiThread(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.post(r);
        }
    }

    public static void runOnUiThread(Runnable r, long delay) {
        LazyHolder.sUiThreadHandler.postDelayed(r, delay);
    }

    public static void removeCallbacks(Runnable r) {
        LazyHolder.sUiThreadHandler.removeCallbacks(r);
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sUiThreadHandler = new Handler(Looper.getMainLooper());
    }

}

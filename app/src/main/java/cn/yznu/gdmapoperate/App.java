package cn.yznu.gdmapoperate;

import android.app.Application;
import android.content.Context;

/**
 * 作者：uiho_mac
 * 时间：2018/3/1
 * 描述：application
 * 版本：1.0
 * 修订历史：
 */

public class App extends Application {
    private static App instance;


    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

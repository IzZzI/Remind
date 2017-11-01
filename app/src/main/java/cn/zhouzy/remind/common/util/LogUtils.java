package cn.zhouzy.remind.common.util;

import android.util.Log;

/**
 * Created by zhouzy on 2017-11-1.
 * Log日志工具类
 */

public class LogUtils {

    private static final String LOG_TAG = "zhouzy";

    public static void E(String msg) {
        Log.e(LOG_TAG, msg);
    }
}

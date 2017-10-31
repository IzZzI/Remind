package cn.zhouzy.remind.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by zhouzy on 2017-10-31.
 * 文本转语音服务
 */

public class TextToSpeechService extends Service{

    private IBinder mBinder;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private class TTSBinder extends Binder{

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

package cn.zhouzy.remind.base;

import android.app.Application;

import com.baidu.tts.client.SpeechSynthesizer;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.zhouzy.remind.common.constant.Constant;
import cn.zhouzy.remind.mian.TTSSpeechSynthesizerListener;

/**
 * Created by zhouzy on 2017-10-31.
 * BaseApplication
 */

public class BaseApplication extends Application {

    private static SpeechSynthesizer mSpeechSynthesizer;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化百度TTS
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setAppId(Constant.BAIDU_TTS_APP_ID);
        mSpeechSynthesizer.setApiKey(Constant.BAIDU_TTS_APP_Key, Constant.BAIDU_TTS_APP_SECERT);
        mSpeechSynthesizer.setSpeechSynthesizerListener(new TTSSpeechSynthesizerListener());
        //初始化腾讯Bugly
        CrashReport.initCrashReport(this, Constant.BUGLY_APP_ID, false);
    }

    public static SpeechSynthesizer getSpeechSynthesizer() {
        return mSpeechSynthesizer;
    }


}

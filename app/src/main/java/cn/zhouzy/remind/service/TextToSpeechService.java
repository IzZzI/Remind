package cn.zhouzy.remind.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.zhouzy.remind.common.constant.Constant;
import cn.zhouzy.remind.common.util.DateUtil;
import cn.zhouzy.remind.entity.TTSMessage;
import cn.zhouzy.remind.mian.TTSSpeechSynthesizerListener;
import cn.zhouzy.remind.common.util.LogUtils;

/**
 * Created by zhouzy on 2017-10-31.
 * 文本转语音服务
 */

public class TextToSpeechService extends Service {

    /**
     * 百度TTS
     */
    private SpeechSynthesizer mSpeechSynthesizer;
    /**
     * 百度TTS是否初始化成功
     */
    private boolean isTTSInit = false;
    /**
     * 任务执行间隔周期
     */
    private long mTaskPeriod = 5;
    /**
     * 定时器
     */
    private ScheduledThreadPoolExecutor exec;

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("Service onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("Service onCreate");
        EventBus.getDefault().register(this);
        initTTS();


    }

    private void initTTS() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //验证权限
                checkAuth();
                //设置参数
                setParams();

            }
        }).start();

    }

    /**
     * 开始执行任务
     */
    public void startTask() {


        LogUtils.e("startTask");
        if (exec != null) {
            exec.shutdown();
        }
        exec = new ScheduledThreadPoolExecutor(2);
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String mCurrentTime = DateUtil.getCurrentDate("HH:mm:ss");
                LogUtils.e(mCurrentTime);
                mSpeechSynthesizer.speak("当前时间" + mCurrentTime);
            }
        }, 1, mTaskPeriod, TimeUnit.SECONDS);

    }

    /**
     * 停止执行任务
     */
    public void stopTask() {
        LogUtils.e("stopTask");
        if (exec != null) {
            exec.shutdown();
        }
    }

    /**
     * 设置改变后调用方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSettingChanged(TTSMessage event) {
        switch (event.getWhat()) {
            case 0:
                if (event.getPriod() != 0){
                    mTaskPeriod = event.getPriod();
                }
                startTask();
                break;
            case 1:
                stopTask();
                break;
            default:
                break;
        }

    }

    /**
     * 设置TTS参数
     */
    private void setParams() {
        mSpeechSynthesizer.setContext(TextToSpeechService.this);
        mSpeechSynthesizer.setAppId(Constant.BAIDU_TTS_APP_ID);
        mSpeechSynthesizer.setApiKey(Constant.BAIDU_TTS_APP_Key, Constant.BAIDU_TTS_APP_SECERT);
        mSpeechSynthesizer.setSpeechSynthesizerListener(new TTSSpeechSynthesizerListener());
        // 以下setParam 参数选填。不填写则默认值生效
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9"); // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
        //修改标识符状态
        isTTSInit = true;
    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     *
     * @return
     */
    private boolean checkAuth() {
        //初始化百度TTS
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        //TtsMode.ONLINE  纯在线
        //TtsMode.MIX) 离在线混合
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.ONLINE);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e("zhouzy", "【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            Log.e("zhouzy", "验证通过，离线正式授权文件存在。");
//            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME); // 文本模型文件路径 (离线引擎使用)
//            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME); // 声学模型文件路径 (离线引擎使用)
            return true;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("Service onDestroy");
        EventBus.getDefault().unregister(this);
        if (exec != null){
        	exec.shutdown();;
        	exec = null;
		}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


}

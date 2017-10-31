package cn.zhouzy.remind.mian;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhouzy.remind.R;
import cn.zhouzy.remind.base.BaseActivity;
import cn.zhouzy.remind.base.BaseApplication;
import cn.zhouzy.remind.service.TextToSpeechService;

public class MainActivity extends Activity {

    /**
     * 百度TTS
     */
    private SpeechSynthesizer mSpeechSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent in = new Intent(MainActivity.this, TextToSpeechService.class);
        startService(in);
//        initTTS();
    }

    private void initTTS() {
        mSpeechSynthesizer = BaseApplication.getSpeechSynthesizer();
        //检查授权
        checkAuth();

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

    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     * @return
     */
    private boolean checkAuth() {
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

    @OnClick({R.id.main_btn_start})
    void onClick(View v){
        switch (v.getId()){
            case R.id.main_btn_start:
                mSpeechSynthesizer.speak("2017-10-31 16:57");

                Log.e("zhouzy", "finished");
                break;
            default:
                break;
        }

    }
}

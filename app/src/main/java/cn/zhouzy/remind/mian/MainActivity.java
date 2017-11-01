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

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhouzy.remind.R;
import cn.zhouzy.remind.base.BaseActivity;
import cn.zhouzy.remind.base.BaseApplication;
import cn.zhouzy.remind.service.TextToSpeechService;

public class MainActivity extends BaseActivity {

    /**
     * 百度TTS
     */
    private SpeechSynthesizer mSpeechSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动TTS Service
        startTTSService();


    }

    /**
     * 启动TTS Service
     */
    private void startTTSService() {
        Intent in = new Intent(MainActivity.this, TextToSpeechService.class);
        startService(in);
    }


    @OnClick({R.id.main_btn_start})
    void onClick(View v){
        switch (v.getId()){
            case R.id.main_btn_start:
                EventBus.getDefault().post("test");
                break;
            default:
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止服务
        Intent in = new Intent(MainActivity.this, TextToSpeechService.class);
        stopService(in);
    }
}

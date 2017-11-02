package cn.zhouzy.remind.mian;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhouzy.remind.R;
import cn.zhouzy.remind.base.BaseActivity;
import cn.zhouzy.remind.base.BaseApplication;
import cn.zhouzy.remind.entity.TTSMessage;
import cn.zhouzy.remind.service.TextToSpeechService;

public class MainActivity extends BaseActivity {


    @Bind(R.id.main_et_time_period)
    EditText mTimePeriodEditText;

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


    @OnClick({R.id.main_btn_start, R.id.main_btn_stop})
    void onClick(View v){
        switch (v.getId()){
            case R.id.main_btn_start:
                TTSMessage ttsMessage = new TTSMessage();
                ttsMessage.setWhat(0);
                if(!"".equals(mTimePeriodEditText.getText().toString())){
                    ttsMessage.setPriod(Integer.parseInt(mTimePeriodEditText.getText().toString()));
                }
                EventBus.getDefault().post(ttsMessage);
                break;
            case R.id.main_btn_stop:
                ttsMessage = new TTSMessage();
                ttsMessage.setWhat(1);
                EventBus.getDefault().post(ttsMessage);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务
        Intent in = new Intent(MainActivity.this, TextToSpeechService.class);
        stopService(in);
    }
}

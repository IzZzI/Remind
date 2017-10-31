package cn.zhouzy.remind.mian;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

/**
 * Created by zhouzy on 2017-10-31.
 * TTS回调Listener
 */

public class TTSSpeechSynthesizerListener implements SpeechSynthesizerListener{

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }
}

package cn.zhouzy.remind.base;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import cn.zhouzy.remind.R;

/**
 * Created by zhouzy on 2017-10-31.
 * BaseActivity
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
    }
}

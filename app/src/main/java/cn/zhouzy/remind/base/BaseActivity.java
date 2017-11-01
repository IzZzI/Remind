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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

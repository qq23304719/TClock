package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class ShowAllActivity extends AppCompatActivity implements TClock.ITLockListener {

    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);


    }

    //监听范围:圆环中心为中心,圆环半径的1.1倍为半径范围的圆
    //只有在up事件触发时候才进行回调,防止事件的超频率不合规触发.
    @Override
    public void onTClockTouchUp(int clockNum) {
        //可以做一些网络请求操作(推荐使用retrofit+rxjava通过订阅方式,
        // 处理最新的TouchUP回调,取消订阅以前的触控回调)
        mTV.setText(clockNum + "点");
    }
}

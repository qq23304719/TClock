package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class NoArcActivity extends AppCompatActivity implements TClock.ITLockListener {
    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_arc);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
        //不显示圆弧,通过xml设置,各种属性意义见attrs.也可下面方法设置
//        mTCk.setShowArc(false);
    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        mTV.setText(clockNum+"点");
    }
}

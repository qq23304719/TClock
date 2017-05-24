package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class NoShaderActivity extends AppCompatActivity implements TClock.ITLockListener {

    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_shader);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
        //不显示渲染色,也可以通过xml设置
        mTCk.setShowBigCircleShader(false);
    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        mTV.setText(clockNum+"点");
    }
}

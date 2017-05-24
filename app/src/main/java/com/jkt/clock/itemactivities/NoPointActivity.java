package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class NoPointActivity extends AppCompatActivity implements TClock.ITLockListener {

    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_point);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
        //不显示时钟点,也可以通过xml设置
        mTCk.setShowPoint(false);
        //设置数字到圆心距离(通过半径相对值衡量,1为整个半径长)
        mTCk.setNumRatio((float) 0.85);
    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        mTV.setText(clockNum+"点");
    }
}

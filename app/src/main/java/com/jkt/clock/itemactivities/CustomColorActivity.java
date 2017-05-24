package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class CustomColorActivity extends AppCompatActivity implements TClock.ITLockListener {
    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_color);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
        //自定义各种颜色,也可以通过xml设置
        mTCk.setBigCircleColor(getResources().getColor(R.color.colorWhite));
        mTCk.setSmallCircleColor(getResources().getColor(R.color.colorDark));
        mTCk.setArcColor(getResources().getColor(R.color.colorAccent));
        mTCk.setPointColor(getResources().getColor(R.color.colorPink));
        mTCk.setNumColor(getResources().getColor(R.color.colorPink));
        mTCk.setPointChooseColor(getResources().getColor(R.color.colorPurple));
        mTCk.setNumChooseColor(getResources().getColor(R.color.colorPurple));
        mTCk.setShader1(getResources().getColor(R.color.colorGreen));
        mTCk.setShader2(getResources().getColor(R.color.colorOrangne));
    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        mTV.setText(clockNum+"点");
    }
}

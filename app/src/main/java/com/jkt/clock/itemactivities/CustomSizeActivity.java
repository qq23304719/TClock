package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.DensityUtil;
import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class CustomSizeActivity extends AppCompatActivity implements TClock.ITLockListener {
    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_size);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
        //自定义各种尺寸,也可以通过xml设置,各种属性意义见attrs
        mTCk.setRadius(DensityUtil.dp2px(this,100));
        mTCk.setPointRatio((float) 0.9);
        mTCk.setNumRatio((float) 1.3);
        mTCk.setNumSize(DensityUtil.sp2px(this,12));
        mTCk.setNumChooseSize(DensityUtil.sp2px(this,15));
        mTCk.setPointRadius(DensityUtil.dp2px(this,3));
        mTCk.setPointChooseRadius(DensityUtil.dp2px(this,4));
        mTCk.setMsgSize(DensityUtil.sp2px(this,8));
    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        mTV.setText(clockNum+"点");
    }
}

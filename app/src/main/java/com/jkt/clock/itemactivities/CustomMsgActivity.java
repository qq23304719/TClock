package com.jkt.clock.itemactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkt.clock.R;
import com.jkt.tclock.TClock;

public class CustomMsgActivity extends AppCompatActivity implements TClock.ITLockListener {

    private TClock mTCk;
    private TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_msg);
        mTCk = (TClock) findViewById(R.id.tck);
        mTV = (TextView) findViewById(R.id.tv);
        mTCk.setOnTLockListener(this);
//设置Msg样式
        mTCk.setCenterMsg("~~" + mTCk.getChooseUnm() + "~~");

    }

    @Override
    public void onTClockTouchUp(int clockNum) {
        //如果不设置那么默认为时钟数字,
        //如果不想让其显示,可以设置属性setShowMsg(false);
        mTCk.setCenterMsg("~~" + clockNum + "~~");
    }
}

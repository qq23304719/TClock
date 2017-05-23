package com.jkt.clock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jkt.tclock.TClock;

public class MainActivity extends AppCompatActivity implements TClock.ITLockListener {

    private TClock mTClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTClock = (TClock) findViewById(R.id.main_TClock);
        mTClock.setITLockListener(this);
    }

    @Override
    public void onTClockTouch(int clockNum) {
        Toast.makeText(this, clockNum+"aa", Toast.LENGTH_SHORT).show();
        mTClock.setCenterMsg(clockNum+"点");
    }
}

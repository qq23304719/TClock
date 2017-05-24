package com.jkt.clock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Adapter adapter = new Adapter(this);
        adapter.setList(getList());
        RecyclerView rv = (RecyclerView) findViewById(R.id.base_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }


    public abstract List<TypeBean> getList();

    private class Adapter extends BaseAdapter<TypeBean> {

        public Adapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_base;
        }

        @Override
        public void onBindItemHolder(BaseViewHolder holder, final int position) {
            Button button = holder.getView(R.id.item_base_bn);
            button.setText(mList.get(position).getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, mList.get(position).gettClass()));
                }
            });
        }
    }
}

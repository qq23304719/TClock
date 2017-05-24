package com.jkt.clock;

import com.jkt.clock.itemactivities.BaseDataActivity;
import com.jkt.clock.itemactivities.CustomColorActivity;
import com.jkt.clock.itemactivities.CustomMsgActivity;
import com.jkt.clock.itemactivities.CustomSizeActivity;
import com.jkt.clock.itemactivities.NoArcActivity;
import com.jkt.clock.itemactivities.NoMsgActivity;
import com.jkt.clock.itemactivities.NoNumActivity;
import com.jkt.clock.itemactivities.NoPointActivity;
import com.jkt.clock.itemactivities.NoShaderActivity;
import com.jkt.clock.itemactivities.NoSmallCircleActivity;
import com.jkt.clock.itemactivities.ShowAllActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //直接查看对应Activity即可,各种属性含义见attrs
    @Override
    public List<TypeBean> getList() {
        return getData();
    }

    private List<TypeBean> getData() {
        List<TypeBean> list = new ArrayList<>();
        list.add(new TypeBean("标准样式(ShowAllActivity.class)", ShowAllActivity.class));
        list.add(new TypeBean("没有时钟点(NoPointActivity.class)", NoPointActivity.class));
        list.add(new TypeBean("没有时钟数字(NoNumActivity.class)", NoNumActivity.class));
        list.add(new TypeBean("没有圆弧(NoArcActivity.class)", NoArcActivity.class));
        list.add(new TypeBean("没有小圆(NoSmallCircleActivity.class)", NoSmallCircleActivity.class));
        list.add(new TypeBean("没有Msg(NoMsgActivity.class)", NoMsgActivity.class));
        list.add(new TypeBean("没有渲染色(NoShaderActivity.class)", NoShaderActivity.class));
        list.add(new TypeBean("基础时间设定(BaseDataActivity.class)", BaseDataActivity.class));
        list.add(new TypeBean("自定义Msg(NoMsgActivity.class)", CustomMsgActivity.class));
        list.add(new TypeBean("自定义颜色(CustomColorActivity.class)", CustomColorActivity.class));
        list.add(new TypeBean("自定义尺寸(CustomSizeActivity.class)", CustomSizeActivity.class));
        return list;
    }

}

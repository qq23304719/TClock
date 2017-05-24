# TClock
###  Hello,这是一个时钟圆环自定义View
  不仅有好看的样式,而且支持触控事件,每次点击、滑动然后松手的时候,判断触控区间(12区间),
  根据区间显示选中时间以及产生包含对应参数的回调事件.支持开发者的自定义需求.<br>
  另外该控件支持多达25+种属性的设置,你可以设置项目需要的样式.</br>
###  预览图
  <img width="350"  src="https://github.com/HoldMyOwn/TClock/blob/master/preview/3986947346.gif"/><br>
  大概演示,详见各个Activity</br>
  
###  样式
 
 <div>
 <img width="500"  src="https://github.com/HoldMyOwn/TClock/blob/master/preview/a.png"/>
  <img width="500"  src="https://github.com/HoldMyOwn/TClock/blob/master/preview/b.png"/>
 </div>
 包括大圆、小圆、圆弧、时钟点、时钟数字、中心Msg的显示与否、颜色、字体大小、半径、厚度、选中时候样式都可以设定.</br>
 详见项目attrs文件</br>
 
###  事件监听
   
    //监听范围:圆环中心为中心,圆环半径的1.1倍为半径范围的圆
    //只有在up事件触发时候才进行回调,防止事件的超频率不合规触发.
    @Override
    public void onTClockTouchUp(int clockNum) {
       //参数为当前选择的时间点
       //可以做一些网络请求操作(推荐使用retrofit+rxjava通过订阅方式,
       // 处理最新的TouchUP回调,取消订阅以前的触控回调)
    }
    
###   具体细节用法,下载查看Demo</br>
###   模板依赖:&nbsp;&nbsp;项目里面的TClcok模板(可更加灵活扩展)</br>
###   gradle依赖:&nbsp;&nbsp;&nbsp;compile&nbsp;'com.jkt:tclock:1.0.0'</br>


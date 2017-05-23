package com.jkt.tclock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 天哥哥 at 2017/5/23 14:17
 * 一个12大刻度的时钟圆环,包括各种手势的滑动事件以及点击事件回调
 */
public class TClock extends View {
    private Context mContext;
    private String mCenterMsg;
    private int mChooseUnm;
    private Paint mPaint;
    private TypedArray mTypedArray;
    private int mAngle;
    private float oldX;
    private float oldY;
    private int mCx;
    private int mCy;
    private float mRadius;
    private ITLockListener mITLockListener;
    private float textSize;
    private int mViewHeight;
    private int mViewWidth;
    private int mMinWH;

    public TClock(Context context) {
        this(context, null);
    }

    public TClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TClock, defStyleAttr, 0);
        initPaint(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMinWH = Math.min(mViewHeight, mViewWidth);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setITLockListener(ITLockListener ITLockListener) {
        mITLockListener = ITLockListener;
    }

    public String getCenterMsg() {
        return mCenterMsg;
    }

    public void setCenterMsg(String centerMsg) {
        mCenterMsg = centerMsg;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }


    private void initTypeArray(Context context, AttributeSet attrs) {
        mTypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.TClock
        );
    }

    /**
     * 初始化画笔对象，为了减少画笔对象的创建，本例子采用单画笔，动态改变属性实现不同画图
     *
     * @param context
     */
    private void initPaint(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCx = mViewWidth / 2;
        mCy = mViewHeight / 2;
        mRadius = (float) (mMinWH / 2.4);
        //画圆
        drawCircle(canvas, mCx, mCy, mRadius);
        //画时钟时间数字和圆点
        drawNumberAndPoint(canvas, mCx, mCy, mRadius);
        //画圆弧
        drawArc(canvas, mCx, mCy, mRadius);
        //中间画数字和百分号
        drawPercent(canvas, mCx, mCy, mRadius);
        //画大圆边环上面的圆
        drawSmallCircle(canvas, mCx, mCy, mRadius);

    }

    private void drawNumberAndPoint(Canvas canvas, int cx, int cy, float radius) {
        float textSize = DensityUtil.dp2px(mContext, 13);
        mPaint.setTextSize(textSize);
        mPaint.setStyle(Paint.Style.FILL);
        int color = 0xff074591;
        int timeChooseColor = Color.rgb(0xAE, 0xEE, 0xEE);
        mPaint.setColor(color);
        for (int i = 1; i <= 12; i++) {
            String number = String.valueOf(i);
            double sin = Math.sin(Math.toRadians(60 - (i - 1) * 30));
            double cos = Math.cos(Math.toRadians(60 - (i - 1) * 30));
            float measureText = mPaint.measureText(number);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float height = fontMetrics.bottom - fontMetrics.top;
            if (i == mChooseUnm) {
                mPaint.setColor(timeChooseColor);
                mPaint.setTextSize((float) (textSize * 1.2));
                canvas.drawText(number, (float) (cx - measureText / 2 + radius * 5.2f / 7 * cos), (float) (cy + height / 3.5 - radius * 5.2f / 7 * sin), mPaint);
                canvas.drawCircle((float) (cx + radius * 6.15f / 7 * cos), (float) (cy - radius * 6.15f / 7 * sin), (float) 8.5, mPaint);
            } else {
                mPaint.setTextSize((float) (textSize));
                mPaint.setColor(color);
                canvas.drawText(number, (float) (cx - measureText / 2 + radius * 5.2f / 7 * cos), (float) (cy + height / 3.5 - radius * 5.2f / 7 * sin), mPaint);
                canvas.drawCircle((float) (cx + radius * 6.15f / 7 * cos), (float) (cy - radius * 6.15f / 7 * sin), 6, mPaint);

            }
        }
    }

    /**
     * 画大圆边上的环
     *
     * @param canvas 画布
     * @param cx     圆心x轴坐标
     * @param cy     圆心y轴坐标
     * @param radius 圆的半径
     */
    private void drawArc(Canvas canvas, int cx, int cy, float radius) {
        int color = mTypedArray.getColor(R.styleable.TClock_arc_color, Color.rgb(0xAE, 0xEE, 0xEE));
        int strokeWidth = mTypedArray.getInteger(R.styleable.TClock_arc_color, 10);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(DensityUtil.dp2px(mContext, strokeWidth));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(new RectF(cx - radius, cy - radius, cx + radius, cy + radius), mAngle - 11 - 90, 22, false, mPaint);
        canvas.save();
    }

    /**
     * 画圆边上的小圆
     *
     * @param canvas 画布
     * @param cx     圆心x轴坐标
     * @param cy     圆心y轴坐标
     * @param radius 圆的半径
     */
    private void drawSmallCircle(Canvas canvas, int cx, int cy, float radius) {
        int color = mTypedArray.getColor(R.styleable.TClock_small_circle_color, Color.WHITE);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.rotate(mAngle, cx, cy);
        canvas.drawCircle(cx, cy - radius, DensityUtil.dp2px(mContext, 8), mPaint);
        canvas.rotate(mAngle, cx, cy);
        canvas.save();
    }

    /**
     * 画大圆
     *
     * @param canvas 画布
     * @param cx     圆心x轴坐标
     * @param cy     圆心y轴坐标
     * @param radius 圆的半径
     */
    private void drawCircle(Canvas canvas, int cx, int cy, float radius) {
        int color = mTypedArray.getColor(R.styleable.TClock_big_circle_color, 0xff4169E1);
        int strokeWidth = mTypedArray.getInteger(R.styleable.TClock_big_circle_solid_width, 8);
        LinearGradient shader = new LinearGradient(cx, cy - radius, cx, cy + radius, new int[]{0xff4169E1, 0xff87CEFA}, null, Shader.TileMode.CLAMP);
        boolean aBoolean = mTypedArray.getBoolean(R.styleable.TClock_big_circle_shader, true);
        if (aBoolean) {
            mPaint.setShader(shader);
        }
        mPaint.setStrokeWidth(DensityUtil.dp2px(mContext, strokeWidth));
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.save();
        mPaint.setShader(null);
        shader = null;
    }

    /**
     * 画园中心百分比
     *
     * @param canvas 画布
     * @param cx     圆心x轴坐标
     * @param cy     圆心y轴坐标
     * @param radius 圆的半径
     */
    private void drawPercent(Canvas canvas, int cx, int cy, float radius) {
        mPaint.setTextSize(DensityUtil.dp2px(mContext, 13));
        mPaint.setColor(0xaa86a7e8);
        //获取xml设置的比例（times）
        float times = mTypedArray.getFloat(R.styleable.TClock_num_size, 1);
        textSize = (DensityUtil.dp2px(mContext, 20)) * times;
        textSize = (DensityUtil.dp2px(mContext, 35)) * times;
        int textColor = mTypedArray.getColor(R.styleable.TClock_center_msg_Color, Color.YELLOW);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (TextUtils.isEmpty(mCenterMsg)) {
            return;
        }
        float measureNumber = mPaint.measureText(mCenterMsg);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        mPaint.setTextSize((float) (textSize / 2));
        mPaint.setTextSize(textSize);
        canvas.drawText(mCenterMsg, cx - measureNumber / 2, (float) (cy + textHeight / 3.5), mPaint);
        mPaint.setTextSize((float) (textSize / 2));
        canvas.save();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        //乘1.1主要是做个缓冲,万一用户手在圆环外侧一点
        if ((eventX - mCx) * (eventX - mCx) + (eventY - mCy) * (eventY - mCy) <= mRadius * mRadius * 1.1) {
            getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_DOWN:
                    oldX = event.getX();
                    oldY = event.getY();
                    if (oldX - mCx > 0) {
                        double angle = Math.toDegrees(Math.atan((mCy - oldY) / (oldX - mCx)));
                        mChooseUnm = (int) (3 - Math.round(angle / 30));
                        if (mChooseUnm == 0) {
                            mChooseUnm += 12;
                        }
                        mAngle = 30 * mChooseUnm;
                    } else if (oldX - mCx < 0) {
                        double angle = Math.toDegrees(Math.atan((mCy - oldY) / (oldX - mCx)));
                        mChooseUnm = (int) (9 - Math.round(angle / 30));
                        mAngle = 30 * mChooseUnm;
                    } else if (oldX >= (mCx - mRadius / 2) && oldX <= (mCx + mRadius / 2) && oldY > mCy) {
                        mChooseUnm = 12;
                        mAngle = 30 * mChooseUnm;
                    } else if (oldX >= (mCx - mRadius / 2) && oldX <= (mCx + mRadius / 2) && oldY < mCy) {
                        mChooseUnm = 6;
                        mAngle = 30 * mChooseUnm;
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (mITLockListener != null) {
                        mITLockListener.onTClockTouch(mChooseUnm);
                    }
                    break;
            }
        }

        return true;

    }

    public interface ITLockListener {
        void onTClockTouch(int clockNum);
    }
}

package com.jkt.tclock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
    private ITLockListener mITLockListener;
    private String mCenterMsg;
    private int mChooseUnm=12;
    private int mAngle;
    private float oldX;
    private float oldY;
    private int mCx;
    private int mCy;
    private float mRadius;
    private int mShader1;
    private int mShader2;
    private boolean mShowBigCircleShader;
    private Paint mBigCirclePaint;
    private Paint mSmallCirclePaint;
    private boolean mShowSmallCircle;
    private Paint mPointPaint;
    private float mWidth;
    private boolean mShowSmallCircle1;
    private float mSmallCircleRadius;
    private int mPointChooseColor;
    private int mPointColor;
    private float mPointRadius;
    private float mPointchooseRadiusSize;
    private boolean mShowPoint;
    private Paint mPoint1CirclePaint;
    private int mSmallCircleColor;
    private float mBigCircleWidth;
    private int mBigCircleColor;
    private int mArcColor;
    private float mArcWidth;
    private boolean mShowArc;
    private float mPointRatio;
    private float mNumSize;
    private int mNumColor;
    private int mNumChooseColor;
    private float mNumRatio;
    private float mMsgSize;
    private int mMsgColor;
    private boolean mShowMsg;
    private Paint mArcPaint;
    private Paint mNumPaint;
    private Paint mMsgPaint;
    private float mNumChooseSize;
    private float mPointChooseRadius;

    public TClock(Context context) {
        this(context, null);
    }

    public TClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TClock, defStyleAttr, 0);
        initAttr(typedArray);
        initPaint();
    }

    private void initPaint() {
        mSmallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMsgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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


    private void initAttr(TypedArray typedArray) {
        mRadius = typedArray.getDimension(R.styleable.TClock_radius, 0);
        initBigCircleAttr(typedArray);
        initSmallCircleAttr(typedArray);
        initPointCircleAttr(typedArray);
        initArcAttr(typedArray);
        initNumAttr(typedArray);
        initMsgAttr(typedArray);

    }


    private void initBigCircleAttr(TypedArray typedArray) {
        mBigCircleColor = typedArray.getColor(R.styleable.TClock_big_circle_color, 0xff4169E1);
        mBigCircleWidth = typedArray.getDimension(R.styleable.TClock_big_circle_width, DensityUtil.dp2px(mContext, 8));
        mShader1 = typedArray.getColor(R.styleable.TClock_big_circle_shader1, 0xff4169E1);
        mShader2 = typedArray.getColor(R.styleable.TClock_big_circle_shader2, 0xff87CEFA);
        mShowBigCircleShader = typedArray.getBoolean(R.styleable.TClock_big_circle_shader_show, true);

    }

    private void initSmallCircleAttr(TypedArray typedArray) {
        mSmallCircleColor = typedArray.getColor(R.styleable.TClock_small_circle_color, 0xffFFFFFF);
        mSmallCircleRadius = typedArray.getDimension(R.styleable.TClock_small_circle_radius, DensityUtil.dp2px(mContext, 8));
        mShowSmallCircle = typedArray.getBoolean(R.styleable.TClock_small_circle_show, true);
    }

    private void initPointCircleAttr(TypedArray typedArray) {
        mPointRadius = typedArray.getDimension(R.styleable.TClock_point_radius, DensityUtil.dp2px(mContext, 2));
        mPointChooseRadius = typedArray.getDimension(R.styleable.TClock_point_radius_choose,  DensityUtil.dp2px(mContext, 3));
        mPointColor = typedArray.getColor(R.styleable.TClock_point_color, 0xff074591);
        mPointChooseColor = typedArray.getColor(R.styleable.TClock_point_color_choose, 0xffAEEEEE);
        mPointRatio = typedArray.getFloat(R.styleable.TClock_point_radius_ratio, (float) 0.88);
        mShowPoint = typedArray.getBoolean(R.styleable.TClock_point_show, true);
    }

    private void initArcAttr(TypedArray typedArray) {
        mArcColor = typedArray.getColor(R.styleable.TClock_arc_color, 0xffAEEEEE);
        mArcWidth = typedArray.getDimension(R.styleable.TClock_arc_width, DensityUtil.dp2px(mContext, 8));
        mShowArc = typedArray.getBoolean(R.styleable.TClock_arc_show, true);
    }

    private void initNumAttr(TypedArray typedArray) {
        mNumSize = typedArray.getDimension(R.styleable.TClock_num_radius, DensityUtil.sp2px(mContext, 13));
        mNumChooseSize = typedArray.getDimension(R.styleable.TClock_num_radius_choose, DensityUtil.sp2px(mContext, 16));
        mNumColor = typedArray.getColor(R.styleable.TClock_num_color, 0xff074591);
        mNumChooseColor = typedArray.getColor(R.styleable.TClock_num_color_choose, 0xffaeeeee);
        mNumRatio = typedArray.getFloat(R.styleable.TClock_num_radius_ratio, (float) 0.72);
    }

    private void initMsgAttr(TypedArray typedArray) {
        mMsgColor = typedArray.getColor(R.styleable.TClock_center_msg_Color, 0xff074591);
        mMsgSize = typedArray.getDimension(R.styleable.TClock_center_msg_Size, DensityUtil.sp2px(mContext, 19));
        mShowMsg = typedArray.getBoolean(R.styleable.TClock_center_msg_show, true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCx = getWidth() / 2;
        mCy = getHeight() / 2;
        mRadius = mRadius == 0 ? (float) (Math.min(getWidth(), getHeight()) / 2.4) : mRadius;
        //画大圆
        drawBigCircle(canvas, mRadius);
        //画时钟时间数字和圆点
        drawNumberAndPoint(canvas);
        //画圆弧
        drawArc(canvas, mRadius);
        //中间消息文本
        drawMsg(canvas, mRadius);
        //画大圆边环上面的小圆
        drawSmallCircle(canvas);

    }

    private void drawNumberAndPoint(Canvas canvas) {
        mNumPaint.setTextSize(mNumSize);
        mNumPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStyle(Paint.Style.FILL);
        for (int i = 1; i <= 12; i++) {
            String number = String.valueOf(i);
            double sin = Math.sin(Math.toRadians(60 - (i - 1) * 30));
            double cos = Math.cos(Math.toRadians(60 - (i - 1) * 30));
            float measureText = mNumPaint.measureText(number);
            Paint.FontMetrics fontMetrics = mNumPaint.getFontMetrics();
            float height = fontMetrics.bottom - fontMetrics.top;
            if (i == mChooseUnm) {
                mNumPaint.setColor(mNumChooseColor);
                mNumPaint.setTextSize(mNumChooseSize);
                mPointPaint.setColor(mPointChooseColor);
                canvas.drawText(number, (float) (mCx - measureText / 2 + mRadius * mNumRatio * cos), (float) (mCy + height / 3.5 - mRadius * mNumRatio * sin), mNumPaint);
                canvas.drawCircle((float) (mCx + mRadius * mPointRatio * cos), (float) (mCy - mRadius * mPointRatio * sin), mPointChooseRadius, mPointPaint);
            } else {
                mNumPaint.setColor(mNumColor);
                mNumPaint.setTextSize(mNumSize);
                mPointPaint.setColor(mPointColor);
                canvas.drawText(number, (float) (mCx - measureText / 2 + mRadius * mNumRatio * cos), (float) (mCy + height / 3.5 - mRadius * mNumRatio * sin), mNumPaint);
                canvas.drawCircle((float) (mCx + mRadius * mPointRatio * cos), (float) (mCy - mRadius * mPointRatio * sin), mPointRadius, mPointPaint);

            }
        }
    }

    private void drawArc(Canvas canvas, float radius) {
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStrokeWidth(mArcWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(new RectF(mCx - radius, mCy - radius, mCx + radius, mCy + radius), mAngle - 11 - 90, 22, false, mArcPaint);
        canvas.save();
    }

    private void drawSmallCircle(Canvas canvas) {
        mSmallCirclePaint.setColor(mSmallCircleColor);
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
        canvas.rotate(mAngle, mCx, mCy);
        canvas.drawCircle(mCx, mCy - mRadius, mSmallCircleRadius, mSmallCirclePaint);
        canvas.rotate(mAngle, mCx, mCy);
        canvas.save();

    }

    private void drawBigCircle(Canvas canvas, float radius) {
        if (mShowBigCircleShader) {
            LinearGradient shader = new LinearGradient(mCx, mCy - radius, mCx, mCy + radius, new int[]{mShader1, mShader2}, null, Shader.TileMode.CLAMP);
            mBigCirclePaint.setShader(shader);
        }
        mBigCirclePaint.setStrokeWidth(mBigCircleWidth);
        mBigCirclePaint.setColor(mBigCircleColor);
        mBigCirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCx, mCy, mRadius, mBigCirclePaint);
        canvas.save();
    }

    private void drawMsg(Canvas canvas, float radius) {
        mMsgPaint.setTextSize(mMsgSize);
        mMsgPaint.setColor(mMsgColor);
        mMsgPaint.setStyle(Paint.Style.FILL);
        if (TextUtils.isEmpty(mCenterMsg) || !mShowMsg) {
            return;
        }
        float measureNumber = mMsgPaint.measureText(mCenterMsg);
        Paint.FontMetricsInt fontMetricsInt = mMsgPaint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        canvas.drawText(mCenterMsg, mCx - measureNumber / 2, (float) (mCy + textHeight / 3.5), mMsgPaint);
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

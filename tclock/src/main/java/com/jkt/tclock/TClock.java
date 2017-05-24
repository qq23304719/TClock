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
    //不可设置更改
    private Context mContext;
    private int mCx;
    private int mCy;
    private float oldX;
    private float oldY;
    private int mAngle;
    private Paint mBigCirclePaint;
    private Paint mSmallCirclePaint;
    private Paint mPointPaint;
    private Paint mArcPaint;
    private Paint mNumPaint;
    private Paint mMsgPaint;
    //可设置(主选项)
    private String mCenterMsg;
    private int mChooseUnm = 12;
    private ITLockListener mITLockListener;
    //可设置(是否显示)
    private boolean mShowBigCircleShader;
    private boolean mShowSmallCircle;
    private boolean mShowNum;
    private boolean mShowMsg;
    private boolean mShowPoint;
    private boolean mShowArc;
    //可设置(样式)
    private float mRadius;
    //大圆相关
    private int mShader1;
    private int mShader2;
    private int mBigCircleColor;
    private float mBigCircleWidth;
    //小圆相关
    private int mSmallCircleColor;
    private float mSmallCircleRadius;
    //时钟点相关
    private int mPointColor;
    private int mPointChooseColor;
    private float mPointRadius;
    private float mPointChooseRadius;
    private float mPointRatio;
    //圆弧相关
    private int mArcColor;
    private float mArcWidth;
    //时钟数字相关
    private float mNumSize;
    private float mNumChooseSize;
    private int mNumColor;
    private int mNumChooseColor;
    private float mNumRatio;
    //中间文本信息相关
    private float mMsgSize;
    private int mMsgColor;

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
        typedArray.recycle();
    }

    private void initPaint() {
        mSmallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMsgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        mBigCircleColor = typedArray.getColor(R.styleable.TClock_big_circle_color, mContext.getResources().getColor(R.color.bigCircleColor));
        mBigCircleWidth = typedArray.getDimension(R.styleable.TClock_big_circle_width, DensityUtil.dp2px(mContext, 8));
        mShader1 = typedArray.getColor(R.styleable.TClock_big_circle_shader1, mContext.getResources().getColor(R.color.shader1));
        mShader2 = typedArray.getColor(R.styleable.TClock_big_circle_shader2, mContext.getResources().getColor(R.color.shader2));
        mShowBigCircleShader = typedArray.getBoolean(R.styleable.TClock_big_circle_shader_show, true);

    }

    private void initSmallCircleAttr(TypedArray typedArray) {
        mSmallCircleColor = typedArray.getColor(R.styleable.TClock_small_circle_color, mContext.getResources().getColor(R.color.smallCircleColor));
        mSmallCircleRadius = typedArray.getDimension(R.styleable.TClock_small_circle_radius, DensityUtil.dp2px(mContext, 8));
        mShowSmallCircle = typedArray.getBoolean(R.styleable.TClock_small_circle_show, true);
    }

    private void initPointCircleAttr(TypedArray typedArray) {
        mPointRadius = typedArray.getDimension(R.styleable.TClock_point_radius, DensityUtil.dp2px(mContext, 2));
        mPointChooseRadius = typedArray.getDimension(R.styleable.TClock_point_radius_choose, DensityUtil.dp2px(mContext, 3));
        mPointColor = typedArray.getColor(R.styleable.TClock_point_color, mContext.getResources().getColor(R.color.pointColor));
        mPointChooseColor = typedArray.getColor(R.styleable.TClock_point_color_choose, mContext.getResources().getColor(R.color.pointChooseColor));
        mPointRatio = typedArray.getFloat(R.styleable.TClock_point_radius_ratio, (float) 0.88);
        mShowPoint = typedArray.getBoolean(R.styleable.TClock_point_show, true);
    }

    private void initArcAttr(TypedArray typedArray) {
        mArcColor = typedArray.getColor(R.styleable.TClock_arc_color, mContext.getResources().getColor(R.color.pointChooseColor));
        mArcWidth = typedArray.getDimension(R.styleable.TClock_arc_width, DensityUtil.dp2px(mContext, 8));
        mShowArc = typedArray.getBoolean(R.styleable.TClock_arc_show, true);
    }

    private void initNumAttr(TypedArray typedArray) {
        mNumSize = typedArray.getDimension(R.styleable.TClock_num_radius, DensityUtil.sp2px(mContext, 13));
        mNumChooseSize = typedArray.getDimension(R.styleable.TClock_num_radius_choose, DensityUtil.sp2px(mContext, 16));
        mNumColor = typedArray.getColor(R.styleable.TClock_num_color, mContext.getResources().getColor(R.color.numColor));
        mNumChooseColor = typedArray.getColor(R.styleable.TClock_num_color_choose, mContext.getResources().getColor(R.color.numChooseColor));
        mNumRatio = typedArray.getFloat(R.styleable.TClock_num_radius_ratio, (float) 0.72);
        mShowNum = typedArray.getBoolean(R.styleable.TClock_num_show, true);
    }

    private void initMsgAttr(TypedArray typedArray) {
        mMsgColor = typedArray.getColor(R.styleable.TClock_center_msg_Color, mContext.getResources().getColor(R.color.msgColor));
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
                if (mShowNum) {
                    canvas.drawText(number, (float) (mCx - measureText / 2 + mRadius * mNumRatio * cos), (float) (mCy + height / 3.5 - mRadius * mNumRatio * sin), mNumPaint);
                }
                if (mShowPoint) {
                    canvas.drawCircle((float) (mCx + mRadius * mPointRatio * cos), (float) (mCy - mRadius * mPointRatio * sin), mPointChooseRadius, mPointPaint);
                }
            } else {
                mNumPaint.setColor(mNumColor);
                mNumPaint.setTextSize(mNumSize);
                mPointPaint.setColor(mPointColor);
                if (mShowNum) {
                    canvas.drawText(number, (float) (mCx - measureText / 2 + mRadius * mNumRatio * cos), (float) (mCy + height / 3.5 - mRadius * mNumRatio * sin), mNumPaint);
                }
                if (mShowPoint) {
                    canvas.drawCircle((float) (mCx + mRadius * mPointRatio * cos), (float) (mCy - mRadius * mPointRatio * sin), mPointRadius, mPointPaint);
                }

            }
        }
    }

    private void drawArc(Canvas canvas, float radius) {
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStrokeWidth(mArcWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        if (mShowArc) {
            canvas.drawArc(new RectF(mCx - radius, mCy - radius, mCx + radius, mCy + radius), mAngle - 11 - 90, 22, false, mArcPaint);
        }
        canvas.save();
    }

    private void drawSmallCircle(Canvas canvas) {
        mSmallCirclePaint.setColor(mSmallCircleColor);
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
        canvas.rotate(mAngle, mCx, mCy);
        if (mShowSmallCircle) {
            canvas.drawCircle(mCx, mCy - mRadius, mSmallCircleRadius, mSmallCirclePaint);
        }
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
        String msg = null;
        mMsgPaint.setTextSize(mMsgSize);
        mMsgPaint.setColor(mMsgColor);
        mMsgPaint.setStyle(Paint.Style.FILL);
        if (TextUtils.isEmpty(mCenterMsg)) {
            msg = mChooseUnm + "";
        } else {
            msg = mCenterMsg;
        }
        float measureNumber = mMsgPaint.measureText(msg);
        Paint.FontMetricsInt fontMetricsInt = mMsgPaint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        if (mShowMsg) {
            canvas.drawText(msg, mCx - measureNumber / 2, (float) (mCy + textHeight / 3.5), mMsgPaint);
        }
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
                        mITLockListener.onTClockTouchUp(mChooseUnm);
                    }
                    break;
            }
        }

        return true;

    }

    //------------------------松手时钟点监听-----------------------------------
    public interface ITLockListener {
        void onTClockTouchUp(int clockNum);
    }

    public void setOnTLockListener(ITLockListener ITLockListener) {
        mITLockListener = ITLockListener;
    }
    //-----------------------------默认选中值设置以及获取--------------------------------------

    public void setChooseUnm(int chooseUnm) {
        if (1 <= chooseUnm && chooseUnm <= 12) {
            mChooseUnm = chooseUnm;
            mAngle = 30 * mChooseUnm;
            if (Looper.getMainLooper() == Looper.myLooper()) {
                invalidate();
            } else {
                postInvalidate();
            }
        }
    }

    public int getChooseUnm() {
        return mChooseUnm;
    }

    //------------------------------中心内容Msg设置和获取-------------------------------------
    public void setCenterMsg(String centerMsg) {
        mCenterMsg = centerMsg;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public String getCenterMsg() {
        return mCenterMsg;
    }
    //-----------------------------显示设置----------------------------------------

    public void setShowBigCircleShader(boolean showBigCircleShader) {
        mShowBigCircleShader = showBigCircleShader;
    }

    public void setShowSmallCircle(boolean showSmallCircle) {
        mShowSmallCircle = showSmallCircle;
    }

    public void setShowNum(boolean showNum) {
        mShowNum = showNum;
    }

    public void setShowMsg(boolean showMsg) {
        mShowMsg = showMsg;
    }

    public void setShowPoint(boolean showPoint) {
        mShowPoint = showPoint;
    }

    public void setShowArc(boolean showArc) {
        mShowArc = showArc;
    }

    //-----------------------样式设置----------------------------------------------------


    public void setRadius(float radius) {
        mRadius = radius;
    }

    public void setShader1(int shader1) {
        mShader1 = shader1;
    }

    public void setShader2(int shader2) {
        mShader2 = shader2;
    }

    public void setBigCircleColor(int bigCircleColor) {
        mBigCircleColor = bigCircleColor;
    }

    public void setBigCircleWidth(float bigCircleWidth) {
        mBigCircleWidth = bigCircleWidth;
    }

    public void setSmallCircleColor(int smallCircleColor) {
        mSmallCircleColor = smallCircleColor;
    }

    public void setSmallCircleRadius(float smallCircleRadius) {
        mSmallCircleRadius = smallCircleRadius;
    }

    public void setPointColor(int pointColor) {
        mPointColor = pointColor;
    }

    public void setPointChooseColor(int pointChooseColor) {
        mPointChooseColor = pointChooseColor;
    }

    public void setPointRadius(float pointRadius) {
        mPointRadius = pointRadius;
    }

    public void setPointChooseRadius(float pointChooseRadius) {
        mPointChooseRadius = pointChooseRadius;
    }

    public void setPointRatio(float pointRatio) {
        mPointRatio = pointRatio;
    }

    public void setArcColor(int arcColor) {
        mArcColor = arcColor;
    }

    public void setArcWidth(float arcWidth) {
        mArcWidth = arcWidth;
    }

    public void setNumSize(float numSize) {
        mNumSize = numSize;
    }

    public void setNumChooseSize(float numChooseSize) {
        mNumChooseSize = numChooseSize;
    }

    public void setNumColor(int numColor) {
        mNumColor = numColor;
    }

    public void setNumChooseColor(int numChooseColor) {
        mNumChooseColor = numChooseColor;
    }

    public void setNumRatio(float numRatio) {
        mNumRatio = numRatio;
    }

    public void setMsgSize(float msgSize) {
        mMsgSize = msgSize;
    }
    public void setMsgColor( int msgColor) {
        mMsgColor = msgColor;
    }
}

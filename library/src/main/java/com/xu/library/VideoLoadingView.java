package com.xu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Xu on 2016/8/1.
 */
public class VideoLoadingView extends View {

    private static String TAG = "VideoLoadingView";

    /**
     * @hide
     */
    @IntDef({VideoLoadingViewSpeed.SPEED_SLOW, VideoLoadingViewSpeed.SPEED_MEDIUM, VideoLoadingViewSpeed.SPEED_FAST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SPEED_CHECK {
    }

    private static int DEFAULT_WIDTH = 50;
    private static int DEFAULT_HEIGHT = 50;
    private static int DEFAULT_RADIUS = 25;
    private static float DEFAULT_ROTATE_DEGREE = 120;
    private static int DEFAULT_MARGIN = 5;
    private static int DEFAULT_TRIANGLE_LENGTH = 14;

    private static int STATUS_START = 0;
    private static int STATUS_PAUSE = 1;
    private static int STATUS_STOP = 2;

    private int margin;
    private int triangle_length;

    private Paint mArcPaint;
    private Paint mTrianglePaint;

    private float progress;
    private float rotate_degree;
    private int radius;
    private int mArcColor;
    private int mTriangleColor;

    private boolean isSpinning;
    private boolean isRotate;
    private int status;

    private int speed;

    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    private double first_pointX;
    private double first_pointY;
    private double second_pointX;
    private double second_pointY;
    private double third_pointX;
    private double third_pointY;

    private Context mContext;
    private VideoViewListener mListener;

    public void registerVideoViewListener(VideoViewListener listener) {
        this.mListener = listener;
    }

    public VideoLoadingView(Context context) {
        super(context);
        init(context);
    }

    public VideoLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.VideoLoadingView, defStyleAttr, 0);
        initAttrs(attributes);
        init(context);
    }

    private void initAttrs(TypedArray attributes) {
        try {
            mArcColor = attributes.getColor(R.styleable.VideoLoadingView_ArcColor, Color.GREEN);
            mTriangleColor = attributes.getColor(R.styleable.VideoLoadingView_TriangleColor, Color.GREEN);
        } finally {
            attributes.recycle();
        }
    }

    private void init(Context context) {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);

        mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaint.setColor(mTriangleColor);
        mTrianglePaint.setStrokeWidth(2);
        mTrianglePaint.setStyle(Paint.Style.FILL);
        mContext = context;

        progress = 0;
        rotate_degree = 0;

        isSpinning = true;
        isRotate = false;

        paddingTop = 5;
        paddingBottom = 5;
        paddingLeft = 5;
        paddingRight = 5;

        speed = VideoLoadingViewSpeed.SPEED_MEDIUM;
        status = STATUS_STOP;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DisplayUtil.dp2px(mContext, DEFAULT_WIDTH), DisplayUtil.dp2px(mContext, DEFAULT_HEIGHT));
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DisplayUtil.dp2px(mContext, DEFAULT_WIDTH), heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, DisplayUtil.dp2px(mContext, DEFAULT_HEIGHT));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        radius = Math.min(width, height) / 2;
        margin = radius / (DEFAULT_RADIUS / DEFAULT_MARGIN);
        triangle_length = radius / (DEFAULT_RADIUS / DEFAULT_TRIANGLE_LENGTH);

        if (status != STATUS_STOP) {
            if (isRotate) {
                drawTriangle(canvas);
            }

            if (isSpinning) {
                drawTriangleOnly(canvas);
                drawArc(canvas);
            }
        }

    }

    private void redrawArc() {
        isRotate = false;
        isSpinning = true;
        progress += speed * 3;
        if (progress > 360) {
            progress = 0;
            isRotate = true;
            isSpinning = false;
        }
        if (status != STATUS_PAUSE) {
            postInvalidate();
        }
    }

    private void redrawTriangle() {
        isRotate = true;
        isSpinning = false;
        rotate_degree += speed;
        if (rotate_degree > DEFAULT_ROTATE_DEGREE) {
            rotate_degree = 0;
            isRotate = false;
            isSpinning = true;
        }
        if (status != STATUS_PAUSE) {
            postInvalidate();
        }
    }

    private void drawArc(Canvas canvas) {
//        int min = Math.min(paddingLeft, Math.min(paddingRight, Math.min(paddingTop, paddingBottom)));
//        RectF rectf = new RectF(paddingLeft + DEFAULT_MARGIN, paddingTop + DEFAULT_MARGIN, 2 * radius - paddingRight - DEFAULT_MARGIN, 2 * radius - paddingBottom - DEFAULT_MARGIN);
//        RectF rectf = new RectF(min + DEFAULT_MARGIN, min + DEFAULT_MARGIN, 2 * radius - min - DEFAULT_MARGIN, 2 * radius - min - DEFAULT_MARGIN);
        mArcPaint.setStrokeWidth(margin);
        RectF rectf = new RectF(margin, margin, 2 * radius - margin, 2 * radius - margin);
        canvas.drawArc(rectf, -90, progress, false, mArcPaint);
        redrawArc();
    }

    private void drawTriangle(Canvas canvas) {
        Path p = new Path();

        if (rotate_degree >= 0 && rotate_degree < 30) {
            first_pointX = radius - triangle_length * Math.abs(Math.cos(Math.PI / 3 + rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            first_pointY = radius - triangle_length * Math.abs(Math.sin(Math.PI / 3 + rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        } else if (rotate_degree >= 30 && rotate_degree <= 120) {
            first_pointX = radius + triangle_length * Math.abs(Math.cos(Math.PI / 3 + rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            first_pointY = radius - triangle_length * Math.abs(Math.sin(Math.PI / 3 + rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        }

        if (rotate_degree >= 0 && rotate_degree < 90) {
            second_pointX = radius + triangle_length * Math.abs(Math.cos(rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            second_pointY = radius + triangle_length * Math.abs(Math.sin(rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        } else if (rotate_degree > 90 && rotate_degree <= 120) {
            second_pointX = radius - triangle_length * Math.abs(Math.cos(rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            second_pointY = radius + triangle_length * Math.abs(Math.sin(rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        }

        if (rotate_degree >= 0 && rotate_degree < 60) {
            third_pointX = radius - triangle_length * Math.abs(Math.cos(Math.PI / 3 - rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            third_pointY = radius + triangle_length * Math.abs(Math.sin(Math.PI / 3 - rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        } else if (rotate_degree >= 60 && rotate_degree <= 120) {
            third_pointX = radius - triangle_length * Math.abs(Math.cos(Math.PI / 3 - rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
            third_pointY = radius - triangle_length * Math.abs(Math.sin(Math.PI / 3 - rotate_degree * Math.PI / 180) * Math.sqrt(3) / 3);
        }

        p.moveTo((float) first_pointX, (float) first_pointY);
        p.lineTo((float) second_pointX, (float) second_pointY);
        p.lineTo((float) third_pointX, (float) third_pointY);
        p.close();
        canvas.drawPath(p, mTrianglePaint);
        redrawTriangle();
    }

    private void drawTriangleOnly(Canvas canvas) {
        Path p = new Path();
        p.moveTo((float) (radius - triangle_length / 2 * Math.sqrt(3) / 3), radius - triangle_length / 2);
        p.lineTo((float) (radius + triangle_length * Math.sqrt(3) / 3), radius);
        p.lineTo((float) (radius - triangle_length / 2 * Math.sqrt(3) / 3), radius + triangle_length / 2);
        p.close();
        canvas.drawPath(p, mTrianglePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refreshLayout();
    }

    public void start() {
        status = STATUS_START;
        if (mListener != null) {
            mListener.onStart();
        }
        refreshLayout();
    }

    public void pause() {
        status = STATUS_PAUSE;
        if (mListener != null) {
            double result = 0;
            if (rotate_degree == 0 && progress != 0) {
                result = progress / 4.8;
                mListener.onPause(result);
            } else if (rotate_degree != 0 && progress == 0) {
                result = 75.0 + rotate_degree / 4.8;
                mListener.onPause(result);
            }
        }
    }

    public void stop() {
        status = STATUS_STOP;
        if (mListener != null) {
            mListener.onStop();
        }
        refreshLayout();
    }

    public int getArcColor() {
        return mArcColor;
    }

    public void setArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
        refreshLayout();
    }

    public int getTriangleColor() {
        return mTriangleColor;
    }

    public void setTriangleColor(int mTriangleColor) {
        this.mTriangleColor = mTriangleColor;
        refreshLayout();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(@SPEED_CHECK int speed) {
        if (speed <= 0 || speed >= 120) {
            throw new SpeedOutOfRangeException("The speed is out of range! please use the CustomLoadingLayoutSetting.SPEED_SLOW/SPEED_MEDIUM/SPEED_FAST! ");
        }
        this.speed = speed;
        refreshLayout();
    }

    public void refreshLayout() {
        invalidate();
        requestLayout();
    }

    public class VideoLoadingViewSpeed {
        public static final int SPEED_SLOW = 2;
        public static final int SPEED_MEDIUM = 3;
        public static final int SPEED_FAST = 5;
    }

    class SpeedOutOfRangeException extends RuntimeException {
        public SpeedOutOfRangeException(String detailMessage) {
            super(detailMessage);
        }
    }

    public interface VideoViewListener {
        void onStart();
        void onPause(double progress);
        void onStop();
    }

}

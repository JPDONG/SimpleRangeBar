package com.learn.myrangebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dongjiangpeng on 2016/7/22 0022.
 */
public class RangeBar extends View {
    private Bar mBar;
    private Thumb mThumb;
    private int mTickCount = 11;
    private int mThumbIndex = 0;

    public void setmThumbIndex(int mThumbIndex) {
        this.mThumbIndex = mThumbIndex;
    }

    private int mMarginLeft = 40;
    private RangeBar.OnRangeBarChangeListener mListener;
    private String TAG = "RangerBar";

    public RangeBar(Context context) {
        super(context);
    }

    public RangeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        rangeBarInit(context, attrs);
    }

    public RangeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rangeBarInit(context, attrs);
    }


    private void rangeBarInit(Context context, AttributeSet attrs) {
        Log.d(TAG, "rangeBarInit: true");
        if (mListener != null) {
            mListener.onIndexChangeListener(this, mThumbIndex);
        }

    }

    private void createThumbs() {
        Context ctx = getContext();
        float yPos = getYPos();
        mThumb = new Thumb(ctx, yPos, Color.RED, 10);
        float barLength = getBarLength();
        mThumb.setX(mMarginLeft + (mThumbIndex / (float) (mTickCount - 1)) * barLength);
        invalidate();
    }

    private void createBar() {
        mBar = new Bar(getContext(), mMarginLeft, getYPos(), getBarLength(), mTickCount);
        invalidate();
    }

    private float getBarLength() {
        return (getWidth() - 2 * mMarginLeft);
    }

    private float getYPos() {
        return (getHeight() / 2f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                Log.d(TAG, "onTouchEvent: X:" + event.getX() + ",Y:" + event.getY());
                onActionUp(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX());
                return true;
            default:
                return false;
        }
    }

    private void onActionMove(float x) {
        moveThumb(mThumb, x);
        int newThumbIndex = mBar.getNearestTickIndex(mThumb);
        mThumbIndex = newThumbIndex;
        if (mListener != null) {
            mListener.onIndexChangeListener(this, mThumbIndex);
        }
    }

    private void moveThumb(Thumb mThumb, float x) {
        if (x < mBar.getLeftX() || x > mBar.getRightX()) {
            // Do nothing.
        } else {
            mThumb.setX(x);
            invalidate();
        }
    }

    private void onActionUp(float x, float y) {
        mThumb.setX(x);
        int newThumbIndex = mBar.getNearestTickIndex(mThumb);
        if(newThumbIndex < 0 || newThumbIndex > 10){
            return;
        }
        mThumbIndex = newThumbIndex;
        if (mListener != null) {
            mListener.onIndexChangeListener(this, mThumbIndex);
        }
    }

    public int getTickIndexNow() {
        if (mThumb == null) {
            return 0;
        } else {
            return mBar.getNearestTickIndex(mThumb);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        createThumbs();
        createBar();
        mBar.draw(canvas);
        mThumb.draw(canvas);
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw: true");

    }

    public void setOnRangeBarChangeListener(RangeBar.OnRangeBarChangeListener listener) {
        mListener = listener;
    }

    public static interface OnRangeBarChangeListener {
        public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex);
    }

    public class Thumb {

        private final float mY;
        private float mX;

        private Paint mPaint;

        private float mThumbRadiusPx;
        private Bitmap bitmap;

        Thumb(Context ctx,
              float y,
              int thumbColorNormal,
              float thumbRadiusDP) {

            final Resources res = ctx.getResources();
            bitmap = BitmapFactory.decodeResource(res,R.drawable.ic_beautification_highlight);
            mY = y;
            mThumbRadiusPx = thumbRadiusDP;
            // Creates the paint and sets the Paint values
            mPaint = new Paint();
            mPaint.setColor(thumbColorNormal);
            mPaint.setAntiAlias(true);
        }

        void setX(float x) {
            mX = x;
        }

        float getX() {
            return mX;
        }

        void draw(Canvas canvas) {
            //canvas.drawCircle(mX, mY, mThumbRadiusPx, mPaint);
            canvas.drawBitmap(bitmap,null,new RectF((mX - 15),(mY - 40),(mX + 15),(mY + 40)),mPaint);
        }

    }

    public class Bar {


        private final Paint mPaint;

        private final float mLeftX;
        private final float mRightX;
        private final float mY;

        private int mNumSegments;
        private float mTickDistance;
        private final float mTickHeight = 10;
        private final float mTickStartY;
        private final float mTickEndY;
        private String TAG = "Bar";

        Bar(Context ctx, float x, float y, float length, int tickCount) {

            mLeftX = x;
            mRightX = x + length;
            mY = y;
            mNumSegments = tickCount - 1;
            mTickDistance = length / mNumSegments;
            mTickStartY = mY - mTickHeight / 2f;
            mTickEndY = mY + mTickHeight / 2f;

            // Initialize the paint.
            mPaint = new Paint();
            mPaint.setColor(Color.GRAY);
            mPaint.setStrokeWidth(5);
            mPaint.setTextSize(30);
            mPaint.setAntiAlias(true);
        }

        void draw(Canvas canvas) {

            //canvas.drawLine(mLeftX, mY, mRightX, mY, mPaint);

            drawTicks(canvas);
        }

        float getLeftX() {
            return mLeftX;
        }

        float getRightX() {
            return mRightX;
        }

        int getNearestTickIndex(Thumb thumb) {

            final int nearestTickIndex = (int) ((thumb.getX() - mLeftX + mTickDistance / 2f) / mTickDistance);

            return nearestTickIndex;
        }

        private void drawTicks(Canvas canvas) {


            for (int i = 0; i < mNumSegments; i++) {
                final float x = i * mTickDistance + mLeftX;
                //canvas.drawLine(x, mTickStartY, x, mTickEndY, mPaint);
                canvas.drawPoint(x, mTickStartY - 40, mPaint);
                canvas.drawText(i + "", x - 8, mTickEndY + 5, mPaint);
                Log.d(TAG, "drawTicks: " + x + "," + mTickEndY + "," + mTickEndY + "mNum:" + mNumSegments + "distance" + mTickDistance);
            }
            canvas.drawPoint(mRightX, mTickStartY - 40, mPaint);
            canvas.drawText("10", mRightX - 15, mTickEndY + 5, mPaint);
            //canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mPaint);
        }
    }
}

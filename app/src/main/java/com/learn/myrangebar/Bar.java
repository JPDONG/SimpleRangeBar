package com.learn.myrangebar;

/**
 * Created by dongjiangpeng on 2016/7/22 0022.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;

/*class Bar {


    private final Paint mPaint;

    private final float mLeftX;
    private final float mRightX;
    private final float mY;

    private int mNumSegments;
    private float mTickDistance;
    private final float mTickHeight;
    private final float mTickStartY;
    private final float mTickEndY;
    private String TAG = "Bar";

    Bar(Context ctx,
        float x,
        float y,
        float length,
        int tickCount,
        float tickHeightDP) {

        mLeftX = x;
        mRightX = x + length;
        mY = y;

        mNumSegments = tickCount - 1;
        mTickDistance = length / mNumSegments;
        mTickHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                tickHeightDP,
                ctx.getResources().getDisplayMetrics());
        mTickStartY = mY - mTickHeight / 2f;
        mTickEndY = mY + mTickHeight / 2f;

        // Initialize the paint.
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(40);
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
            canvas.drawPoint(x,mTickStartY - 20,mPaint);
            canvas.drawText(i+"", x - 10, mTickEndY, mPaint);
            Log.d(TAG, "drawTicks: "+x+","+mTickEndY+","+mTickEndY+"mNum:"+mNumSegments+"distance"+mTickDistance);
        }
        canvas.drawPoint(mRightX,mTickStartY - 20,mPaint);
        canvas.drawText("10", mRightX - 10, mTickEndY, mPaint);
        //canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mPaint);
    }
}*/


package com.example.sketcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description This class represents a View to draw onto, extending the View class. It do so by
 *              creating a Canvas with Bitmap, and using Path and Paint to draw onto the Canvas.
 */
public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    // A List of Path objects that associated with different colors and width values.
    List<Path> paths = new ArrayList<Path>();
    // A list of colors (int) for the Paint
    List<Integer> colors = new ArrayList<Integer>();
    // A list of width values (float) for the Paint
    List<Float> widthValues = new ArrayList<Float>();
    //drawing and canvas paint
    private Paint drawPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    /**
     * The constructor of the View. It sets up the background color and sets up the
     * Path and Paint to draw.
     * @param context   The context of the application
     * @param attrs     The AttributeSet.
     */
    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.setBackgroundColor(0xFFFFFFFF);
    }

    /**
     * This method sets up drawPath and drawPaint, along with the Lists of Paths,
     * colors and width values.
     */
    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15.0f);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setColor(getResources().getColor(R.color.black));

        paths.add(new Path());
        colors.add(getResources().getColor(R.color.black));
        widthValues.add(15.0f);
    }

    /**
     * This method assigns the size to the view.
     * @param w The new width
     * @param h The new height
     * @param oldw  The old width
     * @param oldh  The old height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    /**
     * This method is used to draw the view. It will be called after onTouchEvent().
     * @param canvas    The canvas to be drawn onto.
     */
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(canvasBitmap, 0, 0, drawPaint);
        for (int i = 0; i < paths.size(); i++ ){
            drawPath = paths.get(i);
            drawPaint.setColor(colors.get(i));
            drawPaint.setStrokeWidth(widthValues.get(i));

            canvas.drawPath(drawPath, drawPaint);
        }
    }

    /**
     * This method is used to handle touch event by the user.
     * @param event The event from the user.
     * @return  true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(x, y);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(x, y);
                break;
            default: break;
        }
        invalidate();
        return true;
    }

    /**
     * This method is used to change the color of the Paint.
     * @param color The int of the color changed to.
     */
    public void changeColor(int color) {
        paths.add(new Path());
        colors.add(color);
        widthValues.add(widthValues.get(widthValues.size() - 1));
        invalidate();
    }

    /**
     * This method is used to change the width of the Paint.
     * @param width The float to change the width to.
     */
    public void changeWidthValue(float width) {
        paths.add(new Path());
        colors.add(colors.get(colors.size() - 1));
        widthValues.add(width);
        invalidate();
    }

    /**
     * This method resets the canvas and other associated fields to start a new draw again.
     */
    public void startNew(){
        int color = colors.get(colors.size() - 1);
        float widthValue = widthValues.get(widthValues.size() - 1);
        paths = new ArrayList<Path>();
        colors = new ArrayList<Integer>();
        widthValues = new ArrayList<Float>();
        paths.add(new Path());
        colors.add(color);
        widthValues.add(widthValue);
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    /**
     * This method is used to get the underlying Bitmap (the drawn image).
     * @return  A copy of the underlying Bitmap.
     */
    public Bitmap getBitmap() {
        return canvasBitmap.copy(canvasBitmap.getConfig(), true);
    }

}
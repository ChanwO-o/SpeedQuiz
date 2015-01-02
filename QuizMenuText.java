package com.speedquiz.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class QuizMenuText extends View {

	Typeface font;
	Paint textPaint;
	
	public QuizMenuText(Context context) {
		super(context);
		textPaint = new Paint();
		font = Typeface.createFromAsset(context.getAssets(), "azoft-sans.ttf");
	}
	
	public QuizMenuText(Context context, AttributeSet attrs) {
		super(context, attrs);
		textPaint = new Paint();
		font = Typeface.createFromAsset(context.getAssets(), "azoft-sans.ttf");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		textPaint.setColor(Color.RED);
		textPaint.setTextAlign(Paint.Align.LEFT); //Draws the text starting from the left at coordinates below
		textPaint.setTextSize(30);
		textPaint.setTypeface(font);
		canvas.drawText("Ver. 2.00", 0, canvas.getHeight(), textPaint);
    }
}
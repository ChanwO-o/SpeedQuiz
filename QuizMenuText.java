package com.speedquiz.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class QuizMenuText extends View {

	Typeface font;
	Paint textPaint;
	
	public QuizMenuText(Context context, AttributeSet attrs) {
		super(context, attrs);
		textPaint = new Paint();
		font = Typeface.createFromAsset(context.getAssets(), "sadfilms.ttf");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		textPaint.setColor(Color.RED);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(100);
		textPaint.setTypeface(font);
		canvas.drawText("Classic", canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
    }
}
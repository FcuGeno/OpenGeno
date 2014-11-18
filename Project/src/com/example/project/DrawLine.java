package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class DrawLine extends Drawer {
	int index;

	public DrawLine(MainActivity activity, int H, int W, int index) {
		super(activity, H, W);
		this.index = index;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.TRANSPARENT);
		paint.setStrokeWidth((float) 4.0);
		paint.setStyle(Style.STROKE);
		
		if(index == 13)
			canvas.drawLine(H/2, 0, H/2, W, paint);
		else
			canvas.drawLine(0, W/2, H, W/2, paint);
			
	}
}

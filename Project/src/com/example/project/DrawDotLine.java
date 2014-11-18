package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Paint.Style;

public class DrawDotLine extends Drawer {
	int index;

	public DrawDotLine(MainActivity activity, int H, int W, int index) {
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
		PathEffect effects = new DashPathEffect(new float[] { 10, 5 }, 1);
		paint.setPathEffect(effects);
		paint.setAntiAlias(true);
		
		if(index == 14)
			canvas.drawLine(H/2, 0, H/2, W, paint);
		else
			canvas.drawLine(0, W/2, H, W/2, paint);
	}
}

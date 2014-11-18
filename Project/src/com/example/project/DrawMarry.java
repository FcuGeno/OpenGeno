package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class DrawMarry extends Drawer {

	public DrawMarry(MainActivity activity, int H, int W) {
		super(activity, H, W);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.TRANSPARENT);
		paint.setStrokeWidth((float) 8.0);
		paint.setStyle(Style.STROKE);

		canvas.drawLine(0, 0, 0, W, paint);
		canvas.drawLine(0, W, H, W, paint);
		canvas.drawLine(H, 0, H, W, paint);
	}
}
package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class DrawDivorce extends Drawer {

	public DrawDivorce(MainActivity activity, int H, int W) {
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

		canvas.drawLine(0, 0, 0, W - (W / 6), paint);
		canvas.drawLine(H, 0, H, W - (W / 6), paint);
		
		paint.setStrokeWidth((float) 4.0);
		canvas.drawLine(0, W - (W / 6), H, W - (W / 6), paint);
		canvas.drawLine(H / 3, W, H / 2, W - (W / 3), paint);
		canvas.drawLine(H / 2, W, H - (H / 3), W - (W / 3), paint);
	}

}

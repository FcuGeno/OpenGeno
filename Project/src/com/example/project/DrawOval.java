package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.widget.ImageView;

public class DrawOval extends Drawer {
	float T;

	public DrawOval(MainActivity activity, int H, int W, float T) {
		super(activity, H, W);
		this.T = T;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		canvas.drawColor(Color.TRANSPARENT);
		paint.setStrokeWidth(T);
		paint.setStyle(Style.STROKE);

		RectF oval = new RectF();
		oval.left = 4;
		oval.top = 4;
		oval.right = H - 4;
		oval.bottom = W - 4;
		canvas.drawOval(oval, paint);
	}
}

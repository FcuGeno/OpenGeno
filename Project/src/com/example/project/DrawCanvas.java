package com.example.project;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

public class DrawCanvas {
	private ImageView iv_canvas;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	private RelativeLayout C;
	MainActivity activity;
	LinkedList<Integer> pointX;
	LinkedList<Integer> pointY;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	int height;
	int width;

	public DrawCanvas(MainActivity activity) {
		this.activity = activity;
		C = (RelativeLayout) activity.findViewById(R.id.RelativeLayout1);
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) activity.findViewById(R.id.sd_right);
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		height = metrics.heightPixels;
		width = metrics.widthPixels;
		paint = new Paint();
		paint.setStrokeWidth(5);
	}

	public void loadDrawCanvas(int X, int Y, int H, int W,
			LinkedList<Integer> pointX, LinkedList<Integer> pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
		iv_canvas = new ImageView(activity);
		C.addView(iv_canvas);
		createView(X, Y, H, W);
	}

	public void groupDrawCanvas(int X, int Y, int H, int W,
			LinkedList<Integer> pointX, LinkedList<Integer> pointY,
			MyViewGroup MVG) {
		this.pointX = pointX;
		this.pointY = pointY;
		iv_canvas = new ImageView(activity);
		C.addView(iv_canvas);
		createView(X, Y, H, W);
		MVG.addView(iv_canvas, -1, pointX, pointY);
	}

	public void toDrawCanvas() {
		pointX = new LinkedList<Integer>();
		pointY = new LinkedList<Integer>();
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		iv_canvas = new ImageView(activity);
		iv_canvas.setImageBitmap(bm);
		paint.setColor(Color.RED);
		C.addView(iv_canvas);
		iv_canvas.setOnTouchListener(touch);
	}

	private OnTouchListener touch = new OnTouchListener() {
		int startX;
		int startY;
		int X;
		int Y;
		int L, R, U, D;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(),
						iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
				canvas = new Canvas(baseBitmap);
				canvas.drawColor(Color.TRANSPARENT);
				startX = (int) event.getX();
				startY = (int) event.getY();
				X = startX;
				Y = startY;
				L = Y;
				R = Y;
				U = X;
				D = X;
				pointX.add(X);
				pointY.add(Y);
				break;
			case MotionEvent.ACTION_MOVE:
				int stopX = (int) event.getX();
				int stopY = (int) event.getY();

				canvas.drawLine(X, Y, stopX, stopY, paint);

				X = (int) event.getX();
				Y = (int) event.getY();
				pointX.add(X);
				pointY.add(Y);
				if (X > D)
					D = X;
				if (X < U)
					U = X;
				if (Y > R)
					R = Y;
				if (Y < L)
					L = Y;
				iv_canvas.setImageBitmap(baseBitmap);
				break;
			case MotionEvent.ACTION_UP:
				D = D + 2;
				U = U - 2;
				R = R + 2;
				L = L - 2;
				int W = D - U;
				int H = R - L;
				X = (int) event.getX();
				Y = (int) event.getY();
				pointX.add(X);
				pointY.add(Y);

				for (int i = 0; i < pointX.size(); i++) {
					pointX.add(i, pointX.remove(i) - U);
					pointY.add(i, pointY.remove(i) - L);
				}
				createView(U, L, H, W);
				break;
			default:
				break;
			}
			return true;
		}
	};

	public void createView(int U, int L, int H, int W) {
		float startX = pointX.get(0);
		float startY = pointY.get(0);
		float X = startX;
		float Y = startY;

		paint.setColor(Color.BLACK);
		baseBitmap = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(baseBitmap);
		iv_canvas.setImageBitmap(baseBitmap);

		ViewList VL = activity.getViewList();
		iv_canvas.setId(VL.getIDNumber());
		PencilViewObject obj = new PencilViewObject(iv_canvas.getId(), U, L, H,
				W, iv_canvas);
		obj.setPoints(pointX, pointY);
		VL.add(obj);

		for (int i = 1; i < pointX.size(); i++)
			canvas.drawLine(X, Y, X = pointX.get(i), Y = pointY.get(i), paint);
		canvas.drawLine(startX, startY, X, Y, paint);

		iv_canvas.setTranslationX(U);
		iv_canvas.setTranslationY(L);

		new ImageViewHelper(iv_canvas, -1, -1, activity, pointX, pointY);
		History h = new History(iv_canvas, -1);
		h.pencil(U, L, H, W, VL.getLoc(iv_canvas.getId()), pointX, pointY);
		activity.getHistoryList().add(h);

		sd_top.bringToFront();
		sd_right.bringToFront();
		activity.findViewById(R.id.save).bringToFront();
	}
}

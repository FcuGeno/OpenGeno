package com.example.project;

import android.graphics.Canvas;
import android.widget.ImageView;

public class Drawer extends ImageView {
	int H;
	int W;

	public Drawer(MainActivity activity, int H, int W) {
		super(activity);
		this.H = H;
		this.W = W;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public void setHW(int H, int W) {
		this.H = H;
		this.W = W;
	}
}
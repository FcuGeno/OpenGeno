package com.example.project;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DimenRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class Rotation {
	MainActivity activity;
	RelativeLayout canvas;
	ViewList VL;
	SeekBar bar;

	public Rotation(RelativeLayout canvas, MainActivity activity) {
		DisplayMetrics metrics = new DisplayMetrics();  
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		this.activity = activity;
		this.canvas = canvas;
		this.VL = activity.getViewList();
		bar = new SeekBar(activity);
		bar.setMax(360);
		bar.setLayoutParams(new RelativeLayout.LayoutParams(width/4, 50));
		bar.setTranslationX(20);
		bar.setTranslationY(height - (height/9));
		bar.setVisibility(View.GONE);
		canvas.addView(bar);
	}

	public void setRotation(final int id, final ImageView imageView) {
		final int h;
		final int w;
		final int X;
		final int Y;
		if(imageView.getLayoutParams().height < imageView.getLayoutParams().width){
			h = imageView.getLayoutParams().height;
			w = imageView.getLayoutParams().width;
			X = (int) imageView.getTranslationX() + w/2;
			Y = (int) imageView.getTranslationY() + h/2;
		}else{
			h = imageView.getLayoutParams().width;
			w = imageView.getLayoutParams().height;
			X = (int) imageView.getTranslationX() + h/2;
			Y = (int) imageView.getTranslationY() + w/2;
		}

		bar.setVisibility(View.VISIBLE);
		bar.bringToFront();
		bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Bitmap bitmap = ((BitmapDrawable) activity.getResources()
						.getDrawable(id)).getBitmap();
				Matrix matrix = new Matrix();
				
				RelativeLayout.LayoutParams params = null;
				if ((progress >= 0 && progress <= 15)
						|| (progress >= 165 && progress <= 195)
						|| (progress >= 345 && progress <= 360)) {
					imageView.setTranslationX(X - w/2);
					imageView.setTranslationY(Y - 10);
					params = new RelativeLayout.LayoutParams(w, 20);
					if (progress >= 165 && progress <= 195)
						matrix.setRotate(180);
					else
						matrix.setRotate(0);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageBitmap(bitmap);
					
					VL.setViewXY(imageView.getId(), X - w/2, Y - 10);
					VL.setViewHW(imageView.getId(), w, 20);
					VL.setRotation(imageView.getId(), progress);
				} else if ((progress >= 75 && progress <= 105)
						|| (progress >= 255 && progress <= 285)) {
					imageView.setTranslationX(X - 10);
					imageView.setTranslationY(Y - w/2);
					params = new RelativeLayout.LayoutParams(20, w);
					if (progress >= 75 && progress <= 105)
						matrix.setRotate(90);
					else
						matrix.setRotate(270);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageBitmap(bitmap);
					
					VL.setViewXY(imageView.getId(), X - 10, Y - w/2);
					VL.setViewHW(imageView.getId(), 20, w);
					VL.setRotation(imageView.getId(), progress);
				} else {
					if(h > w){
						imageView.setTranslationX(X - h/2);
						imageView.setTranslationY(Y - h/2);
						params = new RelativeLayout.LayoutParams(h, h);
						
						VL.setViewXY(imageView.getId(), X - h/2, Y - h/2);
						VL.setViewHW(imageView.getId(), h, h);
						VL.setRotation(imageView.getId(), progress);
					}else{
						imageView.setTranslationX(X - w/2);
						imageView.setTranslationY(Y - w/2);
						params = new RelativeLayout.LayoutParams(w, w);
						
						VL.setViewXY(imageView.getId(), X - w/2, Y - w/2);
						VL.setViewHW(imageView.getId(), w, w);
						VL.setRotation(imageView.getId(), progress);
					}
					matrix.setRotate(progress);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageBitmap(bitmap);
				}
				imageView.setLayoutParams(params);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void setVisibility(int v) {
		bar.setVisibility(v);
	}
	
	public int getRotation() {
		return bar.getProgress();
	}
	
	public SeekBar getBar(){
		return bar;
	}
}

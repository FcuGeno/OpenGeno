package com.example.project;

import android.app.AlertDialog.Builder;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AboutUs {
	ImageButton button;
	MainActivity activity;
	String s = "版　　本：\t1 . 0\n" +
			"開發日期：\t2014 / 04 / 19\n" +
			"更新日期：\t2014 / 11 / 13\n" +
			"開發人員：\t妘 企 石";

	public AboutUs(ImageButton button, MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	private void setOnClickListener(ImageButton about) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Builder builder = new Builder(activity);
				// Builder builder = new Builder(activity);
				builder.setTitle("關於");
				builder.setMessage(s);
				builder.setPositiveButton("確定", null);
				builder.show();
			}
		});
	}
	
}

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
	String s = "���@�@���G\t1 . 0\n" +
			"�}�o����G\t2014 / 04 / 19\n" +
			"��s����G\t2014 / 11 / 13\n" +
			"�}�o�H���G\tʳ �� ��";

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
				builder.setTitle("����");
				builder.setMessage(s);
				builder.setPositiveButton("�T�w", null);
				builder.show();
			}
		});
	}
	
}

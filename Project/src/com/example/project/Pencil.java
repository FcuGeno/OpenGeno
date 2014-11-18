package com.example.project;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Pencil {
	ImageButton button;
	MainActivity activity;
	DrawCanvas DC;
	public Pencil(ImageButton button, RelativeLayout canvas,
			MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.DC = activity.getDrawCanvas();
		this.button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	private void setOnClickListener(ImageButton about) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DC.toDrawCanvas();
			}
		});
	}
}

package com.example.project;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CreateText{
	MainActivity activity;
	ImageButton button;
	AddView addView;

	public CreateText(ImageButton button, MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.addView = activity.getAddView();
		setOnClickListener(button);
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
	}

	public void setOnClickListener(ImageView button) {
		button.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				addView.createText();
			}
		});
	}
}

package com.example.project;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AddComment {
	MainActivity activity;
	ImageButton button;
	AddView addView;
	boolean createText = false;
	public AddComment(ImageButton button, RelativeLayout canvas,
			MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.addView = activity.getAddView();
		setOnClickListener(button);
		button.setBackgroundResource(R.drawable.selector);
	}

	private void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				addView.createComment();
			}
		});
	}
}

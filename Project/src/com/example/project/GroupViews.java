package com.example.project;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GroupViews {
	ImageButton button;
	MainActivity activity;
	AddView addView;

	public GroupViews(ImageButton button, MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.button.setBackgroundResource(R.drawable.selector);
		this.addView = activity.getAddView();
		setOnClickListener(button);
	}

	private void setOnClickListener(ImageButton about) {
		button.setOnTouchListener(new ImageView.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				addView.GroupReagy(true);
				break;
			case MotionEvent.ACTION_UP:
				addView.GroupReagy(false);
				break;
			}
			return true;
			}
		});
	}
}

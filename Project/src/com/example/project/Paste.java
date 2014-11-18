package com.example.project;

import java.util.LinkedList;

import android.view.View;
import android.widget.ImageButton;

public class Paste {
	boolean ready = false;
	ImageButton button;
	MainActivity activity;
	AddView addView;
	Copy copier;
	public Paste(ImageButton button, MainActivity activity){
		this.activity = activity;
		this.button = button;
		copier = activity.getCopier();
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}
	
	public void setOnClickListener(ImageButton button){
		button.setOnClickListener
		(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				ready = true;
				activity.getCopier().reset();
			}
		});
	}
	
	public MyViewGroup getMVG(){
		return copier.getMVG();
	}
	
	public int getIndex(){
		return copier.getIndex();
	}
	
//	public String getText(){
//		return copier.getText();
//	}
//	
//	public boolean getComment(){
//		return copier.getComment();
//	}
	
	public int getHeight(){
		return copier.getHeight();
	}
	
	public int getWidth(){
		return copier.getWidth();
	}

	public int getProgress() {
		return copier.getProgress();
	}
	
	public LinkedList<Integer> getPointX(){
		return copier.getPointX();
	}
	
	public LinkedList<Integer> getPointY(){
		return copier.getPointY();
	}
	
	public void reset(){
		ready = false;
	}
	
	public boolean isReady(){
		return ready;
	}
}

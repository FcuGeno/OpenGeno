package com.example.project;

import java.util.LinkedList;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Copy {
	boolean ready = false;
	ImageButton button;
	MainActivity activity;
	int index = -2;
	int height = 100;
	int width = 100;
	int progress = 0;
	String text = null;
	MyViewGroup MVG = null;
	boolean isComment = false;
	LinkedList<Integer> pointX = new LinkedList<Integer>();
	LinkedList<Integer> pointY = new LinkedList<Integer>();

	public Copy(ImageButton button, MainActivity activity) {
		this.activity = activity;
		this.button = button;
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				ready = true;
				activity.getPaster().reset();
			}
		});
	}

	public void setCopy(int index, int height, int width, int progress) {
		text = null;
		isComment = false;
		MVG = null;
		pointX.clear();
		pointY.clear();
		this.index = index;
		this.height = height;
		this.width = width;
		this.progress = progress;
	}

	public void setCopy(MyViewGroup MVG) {
		text = null;
		isComment = false;
		index = -2;
		pointX.clear();
		pointY.clear();
		this.MVG = MVG.clone();
	}

//	public void setCopy(String text) {
//		index = -2;
//		isComment = false;
//		MVG = null;
//		pointX.clear();
//		pointY.clear();
//		this.text = text;
//	}
//
//	public void setCopy(RelativeLayout comment, String text) {
//		isComment = true;
//		index = -2;
//		MVG = null;
//		pointX.clear();
//		pointY.clear();
//		this.text = text;
//	}

	public void setCopy(int height, int width, LinkedList<Integer> pointX,
			LinkedList<Integer> pointY) {
		text = null;
		index = -1;
		isComment = false;
		MVG = null;
		this.height = height;
		this.width = width;
		this.pointX = clonePoint(pointX);
		this.pointY = clonePoint(pointY);
	}

	public MyViewGroup getMVG() {
		return MVG;
	}

	public int getIndex() {
		return index;
	}

//	public String getText() {
//		return text;
//	}
//
//	public boolean getComment() {
//		return isComment;
//	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public LinkedList<Integer> getPointX(){
		return clonePoint(pointX);
	}

	public LinkedList<Integer> getPointY(){
		return clonePoint(pointY);
	}
	
	public void reset() {
		ready = false;
	}

	public boolean isReady() {
		return ready;
	}

	public int getProgress() {
		return progress;
	}

	public LinkedList<Integer> clonePoint(LinkedList<Integer> point){
		LinkedList<Integer> P = new LinkedList<Integer>();
		for(int i = 0; i < point.size(); i++)
			P.add(point.get(i));
		return P;
	}
}

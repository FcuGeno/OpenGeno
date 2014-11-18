package com.example.project;

import java.util.LinkedList;

import android.widget.ImageView;

public class PencilViewObject extends ViewObject {
	LinkedList<Integer> pointX = new LinkedList<Integer>();
	LinkedList<Integer> pointY = new LinkedList<Integer>();
	private ImageView img;
	private int height = 0;
	private int width = 0;

	public PencilViewObject(int id, int X, int Y, int height, int width,
			ImageView img) {
		viewKind = "VIEWKIND_PENCIL";
		this.id = id;
		this.img = img;
		this.X = X;
		this.Y = Y;
		this.height = height;
		this.width = width;
	}

	public void setPoints(LinkedList<Integer> pointX, LinkedList<Integer> pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}
	
	public ImageView getImg(){
		return img;
	}

	@Override
	public String toString() {
		String pX = "", pY = "";
		for (int i = 0; i < pointX.size(); i++) {
			pX = pX + pointX.get(i) + " ";
			pY = pY + pointY.get(i) + " ";
		}
		return viewKind + " " + X + " " + Y + " " + height + " " + width + " "
				+ pointX.size() + " " + pX + " " + pY;
	}
}

package com.example.project;

import java.util.LinkedList;

import android.widget.EditText;
import android.widget.ImageView;

public class History {
	private int action;
	int count;
	EditText tv = null;
	String text = null;
	ImageView img = null;
	int index = -1;
	int height;
	int width;
	int x;
	int y;
	int layers;
	MyViewGroup MVG;
	LinkedList<Integer> pointX = new LinkedList<Integer>();
	LinkedList<Integer> pointY = new LinkedList<Integer>();

	public History() {
	}

	public History(int count) {
		this.count = count;
		copyMVG();
	}

	public History(ImageView img, int index) {
		this.img = img;
		this.index = index;
	}

	public History(EditText tv) {
		this.tv = tv;
	}

	public History(ImageView img, MyViewGroup MVG) {
		this.img = img;
		this.MVG = MVG;
	}

	public History(MyViewGroup MVG) {
		this.MVG = MVG;
	}

	public void addImg(int x, int y, int height, int width, int layers) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.layers = layers;
		action = 1;
	}

	public void deleteImg(int x, int y, int height, int width, int layers) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.layers = layers;
		action = 2;
	}

	public void moveImg(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		action = 3;
	}

	public void addText(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		action = 4;
	}

	public void deleteText(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		action = 5;
	}

	public void moveText(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		action = 6;
	}

	public void changeLayers(int layers) {
		this.layers = layers;
		action = 7;
	}

	public void setLink() {
		action = 8;
	}

	public void cancelMVG(MyViewGroup MVG) {
		this.MVG = MVG;
		action = 9;
	}

	public void copyMVG() {
		action = 10;
	}

	public void group(MyViewGroup MVG) {
		this.MVG = MVG;
		action = 11;
	}

	public void pencil(int x, int y, int height, int width, int layers,
			LinkedList<Integer> pointX, LinkedList<Integer> pointY) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.layers = layers;
		this.pointX = pointX;
		this.pointY = pointY;
		action = 12;
	}

	public void setMVG(MyViewGroup MVG) {
		this.MVG = MVG;
	}

	public int getLayers() {
		return layers;
	}

	public ImageView getImg() {
		return img;
	}

	public EditText getTextView() {
		return tv;
	}

	public MyViewGroup getMVG() {
		return MVG;
	}

	public int getCount() {
		return count;
	}

	public int getAction() {
		return action;
	}
}

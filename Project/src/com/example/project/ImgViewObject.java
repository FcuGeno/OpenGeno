package com.example.project;

import android.widget.ImageView;

public class ImgViewObject extends ViewObject {
	private int height = 0;
	private int width = 0;
	private int imgIndex = -1;
	private ImageView img;
	private int progress = 0;

	public ImgViewObject(int id, int X, int Y, int height, int width,
			int imgIndex, ImageView img, int progress) {
		super();
		viewKind = "VIEWKIND_IMAGE";
		this.id = id;
		this.X = X;
		this.Y = Y;
		this.height = height;
		this.width = width;
		this.imgIndex = imgIndex;
		this.img = img;
		this.progress = progress;
	}

	public void setParams(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public void setProgress(int progress){
		this.progress = progress;
	}
	
	public void setimgIndex(int imgIndex) {
		this.imgIndex = imgIndex;
	}

	public void setImg(ImageView img) {
		this.img = img;
	}
	
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public int getIndex(){
		return imgIndex;
	}
	public ImageView getImg() {
		return img;
	}
	@Override
	public String toString() {
		return viewKind + " " + X + " " + Y + " " + height + " " + width + " "
				+ imgIndex + " " + progress;
	}
}

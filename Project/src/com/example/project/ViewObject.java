package com.example.project;

public class ViewObject {
	protected String viewKind = null;
	protected int id = -1;
	protected int X = 0;
	protected int Y = 0;
	public ViewObject(){};
	public int getId(){
		return id;
	}
	public void setXY(int X, int Y){
		this.X = X;
		this.Y = Y;
	}
	public String getViewKind(){
		return viewKind;
	}
	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}
	public String toString(){
		return viewKind +" "+ X +" "+ Y;
	}
}

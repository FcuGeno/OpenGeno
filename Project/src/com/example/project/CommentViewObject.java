package com.example.project;

import android.widget.RelativeLayout;

public class CommentViewObject extends ViewObject{
	private String text = null;
	RelativeLayout comment;
	
	public CommentViewObject(int id, int X, int Y, String text, RelativeLayout comment){
		super();
		viewKind = "VIEWKIND_COMMENT";
		this.id = id;
		this.X = X;
		this.Y = Y;
		this.text = text;
		this.comment = comment;
	}
	public void setText(String text){
		this.text = text;
	}
	public RelativeLayout getView(){
		return comment;
	}
	@Override
	public String toString(){
		if(text.equals("")) return "";
		return viewKind +" "+ X +" "+ Y +" "+ text;
	}
}

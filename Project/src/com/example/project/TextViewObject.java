package com.example.project;

import android.widget.EditText;

public class TextViewObject extends ViewObject{
	private String text = null;
	EditText tv;
	
	public TextViewObject(int id, int X, int Y, String text, EditText tv){
		super();
		viewKind = "VIEWKIND_TEXT";
		this.id = id;
		this.X = X;
		this.Y = Y;
		this.text = text;
		this.tv = tv;
	}	
	public void setText(String text){
		this.text = text;
	}
	public EditText getView(){
		return tv;
	}
	@Override
	public String toString(){
		if(text.equals("")) return "";
		return viewKind +" "+ X +" "+ Y +" "+ text;
	}
}

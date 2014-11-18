package com.example.project;

import java.lang.reflect.Field;

import android.view.View;
import android.widget.ImageView;

public class ImageSet {
	AddView addView;
	LinkedViews link;
	String[] imageID = {"man", "woman","this_man", "this_wman",
			"death_man", "death_wman", "imp_man", "imp_wman",
			"abortion", "antiabortion", "deadbaby_boy", 
			"adoption", "child", "cohabit", "nocohabit",
			"marry", "divorce", "twin", "identical",
			"close", "clash", "closeclash", "veryclose",
			"interrupt", "far", "ecology", "outresource",
			"deadbaby_girl", "pregnancy", "shortline", "child_two", 
			"line", "line_one", "line_two", "dotline", "dotline_one",
			"dotline_two", "press", "press_one", "press_two", "rectangle"};
	public ImageSet(MainActivity activity){
		addView = activity.getAddView();
		link = activity.getLink();
		for(int i = 0; i < imageID.length; i++){
			int tmp = getid(imageID[i]);
			if(tmp == 0) continue;
			ImageView image = (ImageView) activity.findViewById(tmp);
			image.setClickable(true);
			image.setBackgroundResource(R.drawable.selector);
			setOnClickListener(image, imageID[i]);
		}
		for(int i = 0; i < imageID.length; i++){
			int tmp = getid(imageID[i]+"2");
			if(tmp == 0) continue;
			ImageView image = (ImageView) activity.findViewById(tmp);
			image.setClickable(true);
			image.setBackgroundResource(R.drawable.selector);
			setOnClickListener(image, imageID[i]);
		}
	}
	
	public void setOnClickListener(ImageView image, final String s){
		image.setOnClickListener
		(new ImageView.OnClickListener(){
			@Override
		    public void onClick(View v) {
				if(link.isLineReady())
					link.setLine(s);
				else
					addView.imgClicked(getdrawable(s));
		    }
		});
	}
	
	public int getdrawable(String imageName){
        Class drawable  =  R.drawable.class;
		Field field = null;
		int r_id = 0;
		try {
		    field = drawable.getField(imageName);
		    r_id = field.getInt(field.getName());
		} catch (Exception e) {
		    System.out.println("Not Found");
		}
		return r_id;
	}
	
	public int getid(String imageName){
        Class id  =  R.id.class;
		Field field = null;
		int r_id = 0;
		try {
		    field = id.getField(imageName);
		    r_id = field.getInt(field.getName());
		} catch (Exception e) {
		    System.out.println("Not Found");
		}
		return r_id;
	}
}

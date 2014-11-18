package com.example.project;

import java.util.LinkedList;

import android.view.View;

public class ViewGroupList{
	LinkedList<MyViewGroup> VGL;
	public ViewGroupList(){
		VGL = new LinkedList<MyViewGroup>();
	}
	
	public void add(MyViewGroup object){
		VGL.addFirst(object);
	}
	
	public MyViewGroup getMVG(View actionView){
		for(int i = 0; i < VGL.size(); i++){
			if(VGL.get(i).check(actionView) == true){
				return VGL.get(i);
			}	
		}
		return null;
	}
	
	public MyViewGroup getMVG(MyViewGroup actionMVG){
		for(int i = 0; i < VGL.size(); i++){
			if(VGL.get(i).equals(actionMVG) == true){
				return VGL.get(i);
			}	
		}
		return null;
	}
	
	public void cancelGroup(View actionView){
		for(int i = 0; i < VGL.size(); i++){
			if(VGL.get(i).check(actionView) == true){
				VGL.remove(i);
			}	
		}
	}
	
	public void cancelGroup(MyViewGroup actionGroup){
		for(int i = 0; i < VGL.size(); i++){
			if(VGL.get(i).equals(actionGroup)){
				VGL.remove(i);
			}
		}
	}
}
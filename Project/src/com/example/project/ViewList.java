package com.example.project;

import java.util.LinkedList;

public class ViewList {
	MainActivity activity;
	LinkedList<ViewObject> VL;
	private int number;

	public ViewList(MainActivity activity) {
		this.activity = activity;
		VL = new LinkedList<ViewObject>();
		number = 0;
	}

	public void addFirst(ViewObject obj) {
		number = number + 1;
		VL.addFirst(obj);
	}

	public void add(ViewObject obj) {
		number = number + 1;
		VL.add(obj);
	}

	public void add(int loc, ViewObject obj) {
		number = number + 1;
		VL.add(loc, obj);
	}

	public ViewObject get(int loc) {
		return VL.get(loc);
	}

	public ViewObject remove(int viewId) {
		number = number - 1;
		int loc = getLoc(viewId);
		return VL.remove(loc);
	}

	public void setViewXY(int viewId, int X, int Y) {
		int loc = getLoc(viewId);
		VL.get(loc).setXY(X, Y);
	}

	public void setViewHW(int viewId, int H, int W) {
		int loc = getLoc(viewId);
		((ImgViewObject) VL.get(loc)).setParams(H, W);
	}

	public void setRotation(int viewId, int progress){
		int loc = getLoc(viewId);
		((ImgViewObject) VL.get(loc)).setProgress(progress);
	}
	
	public void setText(int viewId, String s) {
		int loc = getLoc(viewId);
		((TextViewObject) VL.get(loc)).setText(s);
	}
	
	public void setComment(int viewId, String s) {
		int loc = getLoc(viewId);
		((CommentViewObject) VL.get(loc)).setText(s);
	}

	public int getIDNumber() {
		return number;
	}

	public String toSave() {
		String data = "";
		for (int i = 0; i < VL.size(); i++) {
			ViewObject VO = VL.get(i);
			data = data + VO.toString() + " ";
		}
		return data;
	}

	public int size() {
		return VL.size();
	}

	public void clear() {
		number = 0;
		activity.getLink().reset();
		VL.clear();
	}

	public int getLoc(int viewId) {
		for(int i = 0; i < VL.size(); i++){
			if (VL.get(i).getId() == viewId)
				return i;
		}
		return -1;
	}
}

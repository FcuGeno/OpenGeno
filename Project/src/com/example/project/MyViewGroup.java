package com.example.project;

import java.util.LinkedList;
import android.view.View;

public class MyViewGroup {
	LinkedList<MyViewGroup> MVOL;
	LinkedList<View> VOL;
	LinkedList<Integer> IL;
	ViewList VL;
	LinkedList<LinkedList<Integer>> PX;
	LinkedList<LinkedList<Integer>> PY;

	public MyViewGroup(ViewList VL) {
		this.VL = VL;
		MVOL = new LinkedList<MyViewGroup>();
		VOL = new LinkedList<View>();
		IL = new LinkedList<Integer>();
		PX = new LinkedList<LinkedList<Integer>>();
		PY = new LinkedList<LinkedList<Integer>>();
	}

	public void addView(View otherView, int index, LinkedList<Integer> pointX,
			LinkedList<Integer> pointY) {
		VOL.add(otherView);
		IL.add(index);
		PX.add(pointX);
		PY.add(pointY);
	}
	
	public void addView(MyViewGroup otherGroup) {
		MVOL.add(otherGroup);
	}

	public void move(int vX, int vY) {
		for (int i = 0; i < VOL.size(); i++) {
			int X = (int) (VOL.get(i).getTranslationX() + vX);
			int Y = (int) (VOL.get(i).getTranslationY() + vY);
			VOL.get(i).setTranslationX(X);
			VOL.get(i).setTranslationY(Y);
			VL.setViewXY(VOL.get(i).getId(), X, Y);
		}
		for (int i = 0; i < MVOL.size(); i++) {
			MVOL.get(i).move(vX, vY);
		}
	}

	public boolean check(View otherView) {
		for (int i = 0; i < VOL.size(); i++)
			if (VOL.get(i).equals(otherView))
				return true;
		for (int i = 0; i < MVOL.size(); i++)
			if (MVOL.get(i).check(otherView))
				return true;
		return false;
	}

	public View removeView(View otherView) {
		for (int i = 0; i < MVOL.size(); i++) {
			MVOL.get(i).removeView(otherView);
		}
		for (int i = 0; i < VOL.size(); i++) {
			if (VOL.get(i).equals(otherView))
				return VOL.remove(i);
		}
		return null;
	}

	public MyViewGroup removeGrouop(MyViewGroup otherGroup) {
		for (int i = 0; i < VOL.size(); i++) {
			if (MVOL.get(i).equals(otherGroup))
				return MVOL.remove(i);
		}
		return null;
	}

	public LinkedList<MyViewGroup> getMVOL() {
		return MVOL;
	}

	public LinkedList<View> getVOL() {
		return VOL;
	}

	public LinkedList<Integer> getIL() {
		return IL;
	}

	public LinkedList<LinkedList<Integer>> getPX() {
		return PX;
	}

	public LinkedList<LinkedList<Integer>> getPY() {
		return PY;
	}

	public void cancelGroup() {
		VOL.clear();
		MVOL.clear();
		IL.clear();
		PX.clear();
		PY.clear();
	}

	public void setMVG(Object otherMVOL, Object otherVOL, Object otherIL,
			Object otherPX, Object otherPY) {
		VOL = (LinkedList<View>) otherVOL;
		MVOL = (LinkedList<MyViewGroup>) otherMVOL;
		IL = (LinkedList<Integer>) otherIL;
		PX = (LinkedList<LinkedList<Integer>>) otherPX;
		PY = (LinkedList<LinkedList<Integer>>) otherPY;
	}

	public MyViewGroup clone() {
		MyViewGroup clone = new MyViewGroup(VL);
		clone.setMVG(MVOL.clone(), VOL.clone(), IL.clone(), PX.clone(),
				PY.clone());
		return clone;
	}
}

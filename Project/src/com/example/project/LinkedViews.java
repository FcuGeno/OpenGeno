package com.example.project;

import java.lang.reflect.Field;
import java.util.LinkedList;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.Toast;

public class LinkedViews {
	MainActivity activity;
	RelativeLayout canvas;
	AddView addView;
	ViewList VL;
	HistoryList HL;
	ViewGroupList VGL;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	Spinner spnMaterial;
	boolean isLink = false;
	boolean lineReady = false;
	boolean isGroup = false;
	boolean groupReady = false;
	View vL1 = null;
	View vL2 = null;
	View vG1 = null;
	View vG2 = null;
	int indexL1 = -1;
	int indexL2 = -1;
	int indexG1 = -1;
	int indexG2 = -1;
	LinkedList<Integer> pointX1 = new LinkedList<Integer>();
	LinkedList<Integer> pointY1 = new LinkedList<Integer>();
	LinkedList<Integer> pointX2 = new LinkedList<Integer>();
	LinkedList<Integer> pointY2 = new LinkedList<Integer>();

	public LinkedViews(RelativeLayout canvas, MainActivity activity) {
		this.activity = activity;
		this.canvas = canvas;
		addView = activity.getAddView();
		VL = activity.getViewList();
		HL = activity.getHistoryList();
		VGL = activity.getVGL();
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) activity.findViewById(R.id.sd_right);
		spnMaterial = activity.getSpnMaterial();
	}

	public void setLine(String imageName) {
		int index = getdrawable(imageName);
		switch (imageName) {
		case "adoption":
		case "child":
			setLine(index, 1);
			break;
		case "cohabit":
		case "divorce":
		case "marry":
		case "nocohabit":
			setLine(index, 2);
			break;
		case "identical":
		case "line":
			setLine(index, 3);
			break;
		case "twin":
			setLine(index, 4);
			break;
		case "child_two":
			setLine(index, 5);
			break;
		default:
			break;
		}
	}

	public void setLink(View v, int index) {
		if (v.equals(vL1))
			return;
		if (vL1 != null && vL1.getVisibility() == View.GONE) {
			reset();
		}
		if (isLink == true) {
			vL2 = v;
			indexL2 = index;
			lineReady = true;
			if (VGL.getMVG(vL1) != null && VGL.getMVG(vL2) != null) {
				if (VGL.getMVG(vL1).equals(VGL.getMVG(vL2))) {
					Toast.makeText(activity, "已群組", Toast.LENGTH_SHORT).show();
					reset();
					return;
				}
			}
			Toast.makeText(activity, "請選擇關係", Toast.LENGTH_SHORT).show();
			if (!sd_right.isOpened())
				sd_right.open();
			spnMaterial.setSelection(2);
		} else if (vL1 == null) {
			vL1 = v;
			indexL1 = index;
			isLink = true;
		}
	}

	public void setGroup(View v, int index, LinkedList<Integer> pointX,
			LinkedList<Integer> pointY) {
		if (v.equals(vG1))
			return;
		if (vG1 != null && vG1.getVisibility() == View.GONE) {
			reset();
		}
		if (isGroup == true) {
			vG2 = v;
			indexG2 = index;
			pointX2 = pointX;
			pointY2 = pointY;
			if (VGL.getMVG(vG1) != null && VGL.getMVG(vG2) != null) {
				if (VGL.getMVG(vG1).equals(VGL.getMVG(vG2))) {
					Toast.makeText(activity, "已群組", Toast.LENGTH_SHORT).show();
					reset();
					return;
				}
			}
			setGroup();
			Toast.makeText(activity, "已群組", Toast.LENGTH_SHORT).show();
		} else if (vG1 == null) {
			vG1 = v;
			indexG1 = index;
			pointX1 = pointX;
			pointY1 = pointY;
			isGroup = true;
		}
	}

	public void setLine(int index, int number) {
		if (vL1.getVisibility() == View.GONE
				|| vL2.getVisibility() == View.GONE) {
			reset();
			Toast.makeText(activity, "圖片被刪除", Toast.LENGTH_SHORT).show();
			return;
		}

		MyViewGroup MVG = new MyViewGroup(VL);
		MyViewGroup tmp1 = VGL.getMVG(vL1);
		MyViewGroup tmp2 = VGL.getMVG(vL2);

		int organizeX = (int) vL2.getTranslationX();
		int organizeY = (int) vL2.getTranslationY();
		int x = 0, y = 0;

		History h2;
		if (tmp2 != null) {
			h2 = new History((ImageView) vL2, tmp2);
			h2.moveImg(organizeX, organizeY, vL2.getHeight(), vL2.getWidth());
		} else {
			h2 = new History((ImageView) vL2, ((ImgViewObject) VL.get(VL
					.getLoc(vL2.getId()))).getIndex());
			h2.moveImg(organizeX, organizeY, vL2.getHeight(), vL2.getWidth());
		}
		switch (number) {
		case 1:
			x = (int) vL1.getTranslationX();
			y = (int) vL1.getTranslationY() + vL1.getWidth();
			addView.add(index, x + vL1.getHeight() / 3, y);
			if (tmp2 != null) {
				tmp2.move(x - organizeX, y + vL1.getWidth() - organizeY);
			} else {
				vL2.setTranslationX(x);
				vL2.setTranslationY(y + vL1.getWidth());
				VL.setViewXY(vL2.getId(), x, y + vL1.getWidth());
			}
			break;
		case 2:
			x = (int) vL1.getTranslationX() + vL1.getHeight() / 2
					+ vL1.getHeight() / 8;
			y = (int) vL1.getTranslationY() + vL1.getWidth();
			addView.add(index, x - 15, y);
			if (tmp2 != null) {
				tmp2.move(x + vL1.getHeight() / 2 - organizeX,
						y - vL1.getWidth() - organizeY);
			} else {
				vL2.setTranslationX(x + vL1.getHeight() / 2);
				vL2.setTranslationY(y - vL1.getWidth());
				VL.setViewXY(vL2.getId(), x + vL1.getHeight() / 2,
						y - vL1.getWidth());
			}
			break;
		case 3:
			x = (int) vL1.getTranslationX() + vL1.getHeight();
			y = (int) vL1.getTranslationY();
			addView.add(index, x, y + vL1.getWidth() / 3);
			if (tmp2 != null) {
				tmp2.move(x + vL1.getHeight() - organizeX, y - organizeY);
			} else {
				vL2.setTranslationX(x + vL1.getHeight());
				vL2.setTranslationY(y);
				VL.setViewXY(vL2.getId(), x + vL1.getHeight(), y);
			}
			break;
		case 4:
			x = (int) vL1.getTranslationX() + vL1.getHeight() / 2
					+ vL1.getHeight() / 8;
			y = (int) vL1.getTranslationY() - vL1.getWidth();
			addView.add(index, x - 15, y + vL1.getWidth() / 20);
			if (tmp2 != null) {
				tmp2.move(x + vL1.getHeight() / 2 - organizeX,
						y + vL1.getWidth() - organizeY);
			} else {
				vL2.setTranslationX(x + vL1.getHeight() / 2);
				vL2.setTranslationY(y + vL1.getWidth());
				VL.setViewXY(vL2.getId(), x + vL1.getHeight() / 2,
						y + vL1.getWidth());
			}
			break;
		case 5:
			x = (int) vL1.getTranslationX() + vL1.getHeight() / 2
					+ vL1.getHeight() / 8;
			y = (int) vL1.getTranslationY();
			addView.add(index, x - vL1.getHeight() / 7, y - vL1.getWidth() / 2);
			if (tmp2 != null) {
				tmp2.move(x + vL1.getHeight() / 2 - organizeX, y - organizeY);
			} else {
				vL2.setTranslationX(x + vL1.getHeight() / 2);
				vL2.setTranslationY(y);
				VL.setViewXY(vL2.getId(), x + vL1.getHeight() / 2, y);
			}
			break;
		default:
			break;
		}
		vL2.setLayoutParams(new RelativeLayout.LayoutParams(vL1.getHeight(),
				vL1.getWidth()));
		HL.add(h2);

		activity.findViewById(R.id.sd_top).bringToFront();
		activity.findViewById(R.id.sd_right).bringToFront();
		activity.findViewById(R.id.save).bringToFront();

		if (tmp1 != null) {
			MVG.addView(tmp1);
		} else {
			MVG.addView(vL1, indexL1, null, null);
		}

		ImgViewObject IVO = (ImgViewObject) VL.get(VL.getIDNumber() - 1);
		MVG.addView(IVO.getImg(), IVO.getIndex(), null, null);

		if (tmp2 != null) {
			MVG.addView(tmp2);
		} else {
			MVG.addView(vL2, indexL2, null, null);
		}
		VGL.add(MVG);

		History h = new History(MVG);
		h.setLink();
		HL.add(h);

		reset();
	}

	public void setGroup() {
		if (vG1.getVisibility() == View.GONE
				|| vG2.getVisibility() == View.GONE) {
			reset();
			Toast.makeText(activity, "圖片被刪除", Toast.LENGTH_SHORT).show();
			return;
		}
		History h = new History();
		MyViewGroup MVG = new MyViewGroup(VL);
		MyViewGroup tmp1 = VGL.getMVG(vG1);
		MyViewGroup tmp2 = VGL.getMVG(vG2);

		if (tmp1 != null) {
			MVG.addView(tmp1);
		} else {
			MVG.addView(vG1, indexG1, pointX1, pointY1);
		}
		if (tmp2 != null) {
			MVG.addView(tmp2);
		} else {
			MVG.addView(vG2, indexG2, pointX2, pointY2);
		}
		VGL.add(MVG);
		h.group(MVG);
		HL.add(h);
		Toast.makeText(activity, "已群組", Toast.LENGTH_SHORT).show();
		reset();
	}

	public boolean isLineReady() {
		return lineReady;
	}

	public boolean isGroupReady() {
		return groupReady;
	}

	public void reset() {
		vL1 = null;
		vL2 = null;
		vG1 = null;
		vG2 = null;
		indexL1 = -1;
		indexL2 = -1;
		indexG1 = -1;
		indexG2 = -1;
		isLink = false;
		lineReady = false;
		isGroup = false;
		groupReady = false;
	}

	public boolean isLink() {
		return isLink;
	}

	public int getdrawable(String imageName) {
		Class drawable = R.drawable.class;
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
}

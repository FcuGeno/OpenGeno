package com.example.project;

import java.util.LinkedList;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UndoRedo {
	MainActivity activity;
	ViewList VL;
	HistoryList HL;
	ViewGroupList VGL;
	LinkedList<History> LLR;
	LinkedList<History> LLU;
	ImageButton undo;
	ImageButton redo;
	int[] imgID;
	RelativeLayout canvas;

	public UndoRedo(ImageButton undo, ImageButton redo, MainActivity activity) {
		this.activity = activity;
		this.undo = undo;
		this.redo = redo;
		HL = activity.getHistoryList();
		VL = activity.getViewList();
		VGL = activity.getVGL();
		LLR = HL.getLLR();
		LLU = HL.getLLU();
		imgID = activity.getImgID();
		canvas = (RelativeLayout) activity.findViewById(R.id.RelativeLayout1);
		setOnTouchListener();
		setOnClickListener();
	}

	public void setOnTouchListener() {
		undo.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				checkBackground();
				return false;
			}
		});

		redo.setOnTouchListener(new ImageButton.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				checkBackground();
				return false;
			}
		});
	}

	public void checkBackground() {
		if (!LLU.isEmpty())
			undo.setBackgroundResource(R.drawable.selector);
		else
			undo.setBackgroundResource(0);
		if (!LLR.isEmpty())
			redo.setBackgroundResource(R.drawable.selector);
		else
			redo.setBackgroundResource(0);
	}

	public void setOnClickListener() {
		undo.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				undo();
			}
		});

		redo.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				redo();
			}
		});
	}

	public void undo() {
		ImageView img;
		EditText tv;
		int x, y;
		if (!LLU.isEmpty()) {
			History h = LLU.pop();
			switch (h.getAction()) {
			case 1:
				deleteImg(h);
				break;
			case 2:
				addImg(h);
				break;
			case 3:
				int height = 0;
				int width = 0;
				img = h.getImg();
				x = (int) img.getTranslationX();
				y = (int) img.getTranslationY();
				height = img.getHeight();
				width = img.getWidth();
				moveImg(h);
				h.moveImg(x, y, width, height);
				break;
			case 4:
				deleteText(h);
				break;
			case 5:
				addText(h);
				break;
			case 6:
				tv = h.getTextView();
				x = (int) tv.getTranslationX();
				y = (int) tv.getTranslationY();
				String text = tv.getText().toString();
				moveText(h);
				h.moveText(x, y, text);
				break;
			case 7:
				int layers;
				img = h.getImg();
				if (img == null) {
					tv = h.getTextView();
					layers = VL.getLoc(tv.getId());
				} else {
					layers = VL.getLoc(img.getId());
				}
				changeLayers(h);
				h.changeLayers(layers);
				break;
			case 8:
				cancelLink(h);
				break;
			case 9:
				setGroup(h);
				break;
			case 10:
				for (int i = 0; i < h.getCount(); i++) {
					History tmp = LLU.pop();
					deleteImg(tmp);
					LLR.push(tmp);
				}
				break;
			case 11:
				cancelGroup(h);
				break;
			case 12:
				deleteImg(h);
				break;
			default:
				break;
			}
			LLR.push(h);
		}
	}

	public void redo() {
		ImageView img;
		EditText tv;
		int x, y;
		if (!LLR.isEmpty()) {
			History h = LLR.pop();
			switch (h.getAction()) {
			case 1:
				addImg(h);
				break;
			case 2:
				deleteImg(h);
				break;
			case 3:
				int height = 0;
				int width = 0;
				img = h.getImg();
				x = (int) img.getTranslationX();
				y = (int) img.getTranslationY();
				height = img.getHeight();
				width = img.getWidth();
				moveImg(h);
				h.moveImg(x, y, width, height);
				break;
			case 4:
				addText(h);
				break;
			case 5:
				deleteText(h);
				break;
			case 6:
				tv = h.getTextView();
				x = (int) tv.getTranslationX();
				y = (int) tv.getTranslationY();
				String text = tv.getText().toString();
				moveText(h);
				h.moveText(x, y, text);
				break;
			case 7:
				int layers;
				img = h.getImg();
				if (img == null) {
					tv = h.getTextView();
					layers = VL.getLoc(tv.getId());
				} else {
					layers = VL.getLoc(img.getId());
				}
				changeLayers(h);
				h.changeLayers(layers);
				break;
			case 8:
				linkViews(h);
				break;
			case 9:
				cancelGroup(h);
				break;
			case 10:
				for (int i = 0; i < h.getCount(); i++) {
					History tmp = LLR.pop();
					addImg(tmp);
					LLU.push(tmp);
				}
				break;
			case 11:
				setGroup(h);
				break;
			case 12:
				addPencil(h);
				break;
			default:
				break;
			}
			LLU.push(h);
		}
	}

	public void addPencil(History h) {
		MyViewGroup MVG = h.getMVG();
		ImageView img = h.getImg();
		if (MVG != null) {
			VGL.getMVG(MVG).addView(img, h.index, null, null);
		}
		PencilViewObject PVO = new PencilViewObject(img.getId(), h.x, h.y,
				h.height, h.width, img);
		PVO.setPoints(h.pointX, h.pointY);
		VL.add(h.getLayers(), PVO);
		img.setVisibility(View.VISIBLE);
	}

	public void addImg(History h) {
		MyViewGroup MVG = h.getMVG();
		ImageView img = h.getImg();
		if (MVG != null) {
			VGL.getMVG(MVG).addView(img, h.index, null, null);
		}
		ImgViewObject IVO = new ImgViewObject(img.getId(), h.x, h.y, h.height,
				h.width, h.index, img, 0);
		VL.add(h.getLayers(), IVO);
		img.setVisibility(View.VISIBLE);
	}

	public void deleteImg(History h) {
		MyViewGroup MVG = h.getMVG();
		ImageView img = h.getImg();
		if (MVG != null) {
			MVG.removeView(img);
		}
		VL.remove(img.getId());
		img.setVisibility(View.GONE);
	}

	public void moveImg(History h) {
		MyViewGroup MVG = h.getMVG();
		if (MVG != null) {
			MVG.move(h.x - (int) h.getImg().getTranslationX(), h.y
					- (int) h.getImg().getTranslationY());
		} else {
			ImageView img = h.getImg();
			img.setLayoutParams(new RelativeLayout.LayoutParams(h.height,
					h.width));
			img.setTranslationX(h.x);
			img.setTranslationY(h.y);
			for (int i = 31; i < 40; i++)
				if (h.index == imgID[i]) {
					((Drawer) h.getImg()).setHW(h.height, h.width);
					h.getImg().invalidate();
				}
			if(h.index != -1)
				VL.setViewHW(img.getId(), h.height, h.width);
			VL.setViewXY(img.getId(), h.x, h.y);
		}
	}

	public void addText(History h) {
		MyViewGroup MVG = h.getMVG();
		EditText tv = h.getTextView();
		if (MVG != null) {
			VGL.getMVG(MVG).addView(tv, h.index, null, null);
		}
		TextViewObject TVO = new TextViewObject(tv.getId(), h.x, h.y, h.text,
				tv);
		VL.add(h.getLayers(), TVO);
		tv.setText(h.text);
		tv.setAnimation(tv.getAnimation());
		tv.setVisibility(View.VISIBLE);
	}

	public void deleteText(History h) {
		MyViewGroup MVG = h.getMVG();
		EditText tv = h.getTextView();
		if (MVG != null) {
			MVG.removeView(tv);
		}
		VL.remove(tv.getId());
		tv.clearAnimation();
		tv.setVisibility(View.GONE);
	}

	public void moveText(History h) {
		EditText tv = h.getTextView();
		tv.setText(h.text);
		tv.setTranslationX(h.x);
		tv.setTranslationY(h.y);
	}

	public void changeLayers(History h) {
		if (h.getImg() == null)
			VL.add(h.getLayers(), VL.remove(h.getTextView().getId()));
		else
			VL.add(h.getLayers(), VL.remove(h.getImg().getId()));
		
		for (int i = h.getLayers(); i < VL.size(); i++) {
			ViewObject obj = VL.get(i);
			if (obj.getViewKind().equals("VIEWKIND_IMAGE")) {
				ImgViewObject img = (ImgViewObject) obj;
				img.getImg().bringToFront();
				img.getImg().setTranslationX(img.getImg().getTranslationX() + 1);
			} else if (obj.getViewKind().equals("VIEWKIND_TEXT")) {
				TextViewObject text = (TextViewObject) obj;
				text.getView().bringToFront();
				text.getView().setTranslationX(text.getView().getTranslationX() + 1);
			} else if (obj.getViewKind().equals("VIEWKIND_COMMENT")) {
				CommentViewObject comment = (CommentViewObject) obj;
				comment.getView().bringToFront();
				comment.getView().setTranslationX(comment.getView().getTranslationX() + 1);
			} else if(obj.getViewKind().equals("VIEWKIND_PENCIL")){
				PencilViewObject img = (PencilViewObject) obj;
				img.getImg().bringToFront();
				img.getImg().setTranslationX(img.getImg().getTranslationX() + 1);
			}
		}
	}

	public void linkViews(History h) {
		VGL.add(h.getMVG());
		History h1 = LLR.pop();
		addImg(h1);
		LLU.push(h1);
		History h2 = LLR.pop();
		ImageView img = h2.getImg();
		int x = (int) img.getTranslationX();
		int y = (int) img.getTranslationY();
		int height = img.getHeight();
		int width = img.getWidth();
		moveImg(h2);
		h2.moveImg(x, y, height, width);
		LLU.push(h2);
	}

	public void cancelLink(History h) {
		VGL.cancelGroup(h.getMVG());
		History h1 = LLU.pop();
		ImageView img = h1.getImg();
		int x = (int) img.getTranslationX();
		int y = (int) img.getTranslationY();
		int height = img.getHeight();
		int width = img.getWidth();
		moveImg(h1);
		h1.moveImg(x, y, height, width);
		LLR.push(h1);
		History h2 = LLU.pop();
		deleteImg(h2);
		LLR.push(h2);
	}

	public void setGroup(History h) {
		VGL.add(h.getMVG());
	}

	public void cancelGroup(History h) {
		VGL.cancelGroup(h.getMVG());
	}

}

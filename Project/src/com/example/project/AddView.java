package com.example.project;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SlidingDrawer;

public class AddView {
	MainActivity activity;
	RelativeLayout canvas;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	int index = -1;
	boolean createText = false;
	boolean createComment = false;
	ViewList VL;
	HistoryList HL;
	ViewGroupList VGL;
	DrawCanvas DC;
	Copy copier;
	Paste paster;
	Rotation rotation;
	int defaultHeight = 100;
	int defaultWidth = 100;
	int[] imgID;
	boolean groupReady = false;
	
	public AddView(RelativeLayout canvas, MainActivity activity) {
		this.activity = activity;
		this.canvas = canvas;
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) activity.findViewById(R.id.sd_right);
		VL = activity.getViewList();
		HL = activity.getHistoryList();
		VGL = activity.getVGL();
		DC = activity.getDrawCanvas();
		copier = activity.getCopier();
		paster = activity.getPaster();
		rotation = activity.getRotation();
		imgID = activity.getImgID();
		canvasSet();
	}

	public void canvasSet() {
		canvas.setOnTouchListener(new ImageView.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					addView(event);
					break;
				}
				return true;
			}
		});
	}

	public void addView(MotionEvent event) {
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		if (index != -1) {
			add(index, x - defaultHeight / 2, y - defaultWidth / 2);
			index = -1;
			copier.reset();
		} else if (paster.isReady()) {
			if (paster.getMVG() != null) {
				int count = 0;
				MyViewGroup MVG = paster.getMVG();
				count = viewNumber(MVG);
				History h = new History(count);
				MyViewGroup newMVG = new MyViewGroup(VL);
				if (MVG.getVOL().size() != 0) {
					View v = MVG.getVOL().get(0);
					x = (int) v.getTranslationX() - x;
					y = (int) v.getTranslationY() - y;
				}else{
					View v = MVG.getMVOL().get(0).getVOL().get(0);
					x = (int) v.getTranslationX() - x;
					y = (int) v.getTranslationY() - y;
				}
				addMVG(MVG, x, y, newMVG);
				VGL.add(newMVG);
				HL.add(h);
			}else if (paster.getIndex() == -2){
			}else if (paster.getIndex() != -1)
				add(paster.getIndex(), x - paster.getHeight() / 2,
						y - paster.getWidth() / 2, paster.getHeight(),
						paster.getWidth(), findKind(paster.getIndex()),
						paster.getProgress(), null);
//			else if (paster.getComment())
//				addCommentView(paster.getText(), x, y);
//			else if (paster.getText() != null)
//				addTextView(paster.getText(), x, y);
			else if (paster.getIndex() == -1)
				DC.loadDrawCanvas(x - paster.getWidth() / 2,
						y - paster.getHeight() / 2, paster.getHeight(),
						paster.getWidth(), paster.getPointX(),
						paster.getPointY());
			paster.reset();
		} else if (createText) {
			addTextView(null, x, y);
			createText = false;
		} else if (createComment) {
			addCommentView(null, x, y);
			createComment = false;
		} else {
			if (sd_top.isOpened())
				sd_top.animateClose();
			if (sd_right.isOpened())
				sd_right.animateClose();
			rotation.setVisibility(View.GONE);
		}
	}

	public int viewNumber(MyViewGroup MVG) {
		int count = 0;
		for (int i = 0; i < MVG.getMVOL().size(); i++) {
			count = count + viewNumber(MVG.getMVOL().get(i));
		}
		count = count + MVG.getVOL().size();
		return count;
	}

	public void add(int index, int x, int y, int height, int width, int kind,
			int progress, MyViewGroup MVG) {
		ImageView img = null;
		Bitmap bm;
		switch (kind) {
		case 0:
			img = new ImageView(activity);
			bm = BitmapFactory.decodeResource(activity.getResources(), index);
			img.setImageBitmap(bm);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			break;
		case 1:
			if(index == imgID[13])
				img = new DrawLine(activity, height, width, 13);
			else
				img = new DrawDotLine(activity, height, width, 14);
			break;
		case 2:
			if(index == imgID[15])
				img = new DrawLine(activity, height, width, 15);
			else if(index == imgID[16])
				img = new DrawLine(activity, height, width, 16);
			else
				img = new DrawDotLine(activity, height, width, 17);
			break;
		case 3:
			img = new ImageView(activity);
			bm = BitmapFactory.decodeResource(activity.getResources(), index);
			img.setImageBitmap(bm);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			if (progress != 0) {
				Bitmap bitmap = ((BitmapDrawable) activity.getResources()
						.getDrawable(index)).getBitmap();
				Matrix matrix = new Matrix();
				if ((progress >= 0 && progress <= 15)
						|| (progress >= 345 && progress <= 360))
					matrix.setRotate(0);
				else if (progress >= 75 && progress <= 105)
					matrix.setRotate(90);
				else if (progress >= 165 && progress <= 195)
					matrix.setRotate(180);
				else if (progress >= 255 && progress <= 285)
					matrix.setRotate(270);
				else
					matrix.setRotate(progress);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
				img.setImageBitmap(bitmap);
			}
			break;
		case 4:
			if (index == imgID[31])
				img = new DrawMarry(activity, height, width);
			else if (index == imgID[32])
				img = new DrawDivorce(activity, height, width);
			else if (index == imgID[33])
				img = new DrawCohabit(activity, height, width);
			else if (index == imgID[34])
				img = new DrawNocohabit(activity, height, width);
			else if (index == imgID[35])
				img = new DrawChild(activity, height, width);
			break;
		case 5:
			if (index == imgID[36])
				img = new DrawOval(activity, height, width, 3);
			else if (index == imgID[37])
				img = new DrawOval(activity, height, width, 9);
			break;
		case 6:
			if (index == imgID[38])
				img = new DrawTwins(activity, height, width);
			else if (index == imgID[39])
				img = new DrawRectangle(activity, height, width);
			break;
		default:
			break;
		}
		if (MVG != null) {
			MVG.addView(img, index, null, null);
		}
		LayoutParams param = new LayoutParams(height, width);
		canvas.addView(img, param);
		img.setId(VL.getIDNumber());
		img.setTranslationX(x);
		img.setTranslationY(y);
		new ImageViewHelper(img, index, kind, activity, null, null);
		ImgViewObject obj = new ImgViewObject(img.getId(), x, y, height, width,
				index, img, progress);
		VL.add(obj);
		History h = new History(img, index);
		h.addImg(x, y, height, width, VL.getLoc(img.getId()));
		HL.add(h);
		sd_top.bringToFront();
		sd_right.bringToFront();
		activity.findViewById(R.id.save).bringToFront();
	}

	public int findKind(int index) {
		int tmp = -1;
		for (int i = 0; i < 13; i++) {
			if (index == imgID[i])
				tmp = 0;
		}
		if (tmp == -1)
			for (int i = 13; i < 15; i++) {
				if (index == imgID[i])
					tmp = 1;
			}
		if (tmp == -1)
			for (int i = 15; i < 18; i++) {
				if (index == imgID[i])
					tmp = 2;
			}
		if (tmp == -1)
			for (int i = 18; i < 31; i++) {
				if (index == imgID[i])
					tmp = 3;
			}
		if (tmp == -1)
			for (int i = 31; i < 36; i++) {
				if (index == imgID[i])
					tmp = 4;
			}
		if (tmp == -1)
			for (int i = 36; i < 38; i++) {
				if (index == imgID[i])
					tmp = 5;
			}
		if (tmp == -1)
			for (int i = 38; i < 40; i++) {
				if (index == imgID[i])
					tmp = 6;
			}
		switch (tmp) {
		case 0:
			defaultHeight = 100;
			defaultWidth = 100;
			break;
		case 1:
			defaultHeight = 30;
			defaultWidth = 100;
			break;
		case 2:
			defaultHeight = 100;
			defaultWidth = 30;
			break;
		case 3:
			defaultHeight = 100;
			defaultWidth = 20;
			break;
		case 4:
			defaultHeight = 115;
			defaultWidth = 50;
			break;
		case 5:
			defaultHeight = 200;
			defaultWidth = 100;
			break;
		case 6:
			defaultHeight = 115;
			defaultWidth = 100;
			break;
		default:
			defaultHeight = 20;
			defaultWidth = 20;
			break;
		}
		return tmp;
	}

	public void add(int i, int x, int y) {
		int kind = findKind(i);
		add(i, x, y, defaultHeight, defaultWidth, kind, 0, null);
	}

	public void addTextView(String text, int x, int y) {
		EditText tv = new EditText(activity);
		canvas.addView(tv);
		tv.setId(VL.getIDNumber());
		if (text == null) {
			new TextViewHelper(tv, activity);
			tv.setText("text");
		} else {
			tv.setText(text);
			new TextViewHelper(tv, activity);
		}
		tv.setBackgroundColor(color.transparent);
		tv.setTranslationX(x);
		tv.setTranslationY(y);
		TextViewObject obj = new TextViewObject(tv.getId(), x, y, tv.getText()
				.toString(), tv);
		History h = new History(tv);
		h.addText(x, y, text);
		HL.add(h);
		VL.add(obj);
		sd_top.bringToFront();
		sd_right.bringToFront();
	}

	public void addCommentView(String text, int x, int y) {
		RelativeLayout comment = new RelativeLayout(activity);
		Button btn = new Button(activity);
		btn.setBackgroundResource((R.drawable.min_postit));
		EditText tv = new EditText(activity);
		tv.setText(text);
		tv.setBackgroundResource(R.drawable.postit);
		tv.setVisibility(View.GONE);
		RelativeLayout.LayoutParams editloc = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		comment.addView(tv, editloc);
		RelativeLayout.LayoutParams btnloc = new RelativeLayout.LayoutParams(
				55, 40);
		btnloc.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		btnloc.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		comment.addView(btn, btnloc);
		comment.setTranslationX(x);
		comment.setTranslationY(y);
		canvas.addView(comment);
		CommentViewObject obj = new CommentViewObject(tv.getId(), x, y, tv
				.getText().toString(), comment);
		new CommentViewHelper(comment, btn, tv, activity);
		HL.setChange();
		HL.setSave();
		VL.add(obj);
		sd_top.bringToFront();
		sd_right.bringToFront();
	}

	public void addMVG(MyViewGroup MVG, int x, int y, MyViewGroup newMVG) {
		for (int i = 0; i < MVG.getMVOL().size(); i++) {
			addMVG(MVG.getMVOL().get(i), x, y, newMVG);
		}
		for (int i = 0; i < MVG.getVOL().size(); i++) {
			View v = MVG.getVOL().get(i);
			int index = MVG.getIL().get(i);
			if (index == -1) {
				DC.groupDrawCanvas((int) v.getTranslationX() - x,
						(int) v.getTranslationY() - y, v.getHeight(),
						v.getWidth(), MVG.getPX().get(i), MVG.getPY().get(i), newMVG);
			} else if (index != -1) {
				add(index, (int) v.getTranslationX() - x,
						(int) v.getTranslationY() - y, v.getWidth(),
						v.getHeight(), findKind(index), 0, newMVG);
			}
		}
	}

	public void imgClicked(int tmp) {
		index = tmp;
		createText = false;
		createComment = false;
	}

	public void createText() {
		createText = true;
		index = -1;
		createComment = false;
	}

	public void createComment() {
		createComment = true;
		index = -1;
		createText = false;
	}

	public void reset() {
		index = -1;
		createText = false;
		createComment = false;
	}

	public boolean isAddView() {
		if (createComment == false && index == -1 && createText == false)
			return false;
		else
			return true;
	}
	
	public void GroupReagy(boolean TF){
		groupReady = TF;
	}
	
	public boolean isGroupReady(){
		return groupReady;
	}
}

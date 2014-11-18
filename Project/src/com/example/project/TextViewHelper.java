package com.example.project;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class TextViewHelper {
	boolean isMoving = false;
	boolean isZooming = false;
	boolean isRelease = true;
	boolean isLongClick = false;
	MainActivity activity;
	AddView addView;
	ViewList VL;
	HistoryList HL;
	ViewGroupList VGL;
	MyViewGroup MVG;
	Copy copier;
	LinkedViews link;
	EditText tv;
	SlidingDrawer sd_top;
	String text = "";

	public TextViewHelper(EditText tv, MainActivity activity) {
		this.activity = activity;
		addView = activity.getAddView();
		VL = activity.getViewList();
		HL = activity.getHistoryList();
		VGL = activity.getVGL();
		copier = activity.getCopier();
		link = activity.getLink();
		this.copier = activity.getCopier();
		this.tv = tv;
		text = tv.getText().toString();
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		tv.setOnTouchListener(textOnTouchListener);
		tv.setOnLongClickListener(textOnLongClickListener);
	}

	private OnTouchListener textOnTouchListener = new OnTouchListener() {
		private int x, y;
		private int touchX, touchY;
		private int organizeX, organizeY;
		private int vX, vY;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			MVG = VGL.getMVG(v);
			int pointerNum = event.getPointerCount();
			organizeX = (int) v.getTranslationX();
			organizeY = (int) v.getTranslationY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(addView.isGroupReady()){
					link.setGroup(tv, -1, null, null);
					return true;
				}
				isRelease = false;
				isZooming = false;
				touchX = (int) (event.getRawX() - v.getTranslationX());
				touchY = (int) (event.getRawY() - v.getTranslationY());
				return false;

			case MotionEvent.ACTION_UP:
				if(addView.isGroupReady())
					return true;
				if (!isMoving && !isLongClick) {
					dialog();
				} else {
					VL.setViewXY(v.getId(), x, y);
				}
				isMoving = false;
				isRelease = true;
				isLongClick = false;
				return true;

			case MotionEvent.ACTION_MOVE:
				if (!isMoving) {
					vX = x - organizeX;
					vY = y - organizeY;
					History h = new History((EditText) v);
					h.moveText((int) tv.getTranslationX(),
							(int) tv.getTranslationY(), tv.getText().toString());
					HL.add(h);
				}
				isMoving = true;
				if (pointerNum == 1) {
					x = (int) (event.getRawX() - touchX);
					y = (int) (event.getRawY() - touchY);
					vX = x - organizeX;
					vY = y - organizeY;
					if (MVG != null && !isZooming) {
						MVG.move(vX, vY);
					} else {
						v.setTranslationX(x);
						v.setTranslationY(y);
					}
				} else if (pointerNum == 2) {
					isZooming = true;
					x = (int) (event.getRawX() - touchX);
					y = (int) (event.getRawY() - touchY);
					v.setTranslationX(x);
					v.setTranslationY(y);
				}
				return true;
			}
			return false;
		}
	};

	public void dialog() {
		final EditText editText = new EditText(activity);
		editText.setText(text);
		Builder builder = new Builder(activity);
		builder.setView(editText);
		builder.setTitle("輸入文字");
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				History h = new History(tv);
				h.moveText((int) tv.getTranslationX(),
						(int) tv.getTranslationY(), text);
				HL.add(h);
				text = editText.getText().toString();
				tv.setText(text);
				VL.setText(tv.getId(), text);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	private OnLongClickListener textOnLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (!isMoving && !isRelease) {
				isLongClick = true;
				LongClickDialog(v);
			}
			return true;
		}
	};

	public void LongClickDialog(View v) {
		String[] s;
		if (MVG != null) {
			s = new String[] { "最上層", "最下層", "刪除", "群組", "解除群組" };
		} else {
			s = new String[] { "最上層", "最下層", "刪除", "群組" };
		}
		Builder builder = new Builder(activity);
		builder.setTitle("選擇");
		ChoiceOnClickListener onClickListener = new ChoiceOnClickListener();
		builder.setSingleChoiceItems(s, 0, onClickListener);
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	public class ChoiceOnClickListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			actions(which);
			dialog.dismiss();
		}

		public void actions(int action) {
			History h;
			switch (action) {
			case 0:
				h = new History(tv);
				h.changeLayers(VL.getLoc(tv.getId()));
				HL.add(h);
				VL.add(VL.remove(tv.getId()));
				tv.bringToFront();
				activity.findViewById(R.id.sd_top).bringToFront();
				activity.findViewById(R.id.sd_right).bringToFront();
				activity.findViewById(R.id.save).bringToFront();
				tv.setTranslationX(tv.getTranslationX()+1);
				break;
			case 1:
				h = new History(tv);
				h.changeLayers(VL.getLoc(tv.getId()));
				HL.add(h);
				VL.addFirst(VL.remove(tv.getId()));
				for (int i = 0; i < VL.size(); i++) {
					ViewObject obj = VL.get(i);
					if (obj.getViewKind().equals("VIEWKIND_IMAGE")) {
						ImgViewObject img = (ImgViewObject) obj;
						img.getImg().bringToFront();
					} else if (obj.getViewKind().equals("VIEWKIND_TEXT")) {
						TextViewObject text = (TextViewObject) obj;
						text.getView().bringToFront();
					} else if (obj.getViewKind().equals("VIEWKIND_COMMENT")) {
						CommentViewObject comment = (CommentViewObject) obj;
						comment.getView().bringToFront();
					}
				}
				activity.findViewById(R.id.sd_top).bringToFront();
				activity.findViewById(R.id.sd_right).bringToFront();
				activity.findViewById(R.id.save).bringToFront();
				tv.setTranslationX(tv.getTranslationX()+1);
				break;
			case 2:
				h = new History(tv);
				if (MVG != null) {
					MVG.removeView(tv);
					h.setMVG(MVG);
				}
				h.deleteText((int) tv.getTranslationX(),
						(int) tv.getTranslationY(), tv.getText().toString());
				HL.add(h);
				VL.remove(tv.getId());
				tv.clearAnimation();
				tv.setVisibility(View.GONE);
				break;
			case 3:
				link.setGroup(tv, -1, null, null);
				break;
			case 4:
				VGL.cancelGroup(MVG);
				h = new History(MVG);
				h.cancelMVG(MVG);
				HL.add(h);
			default:
				break;
			}
		}
	}
}

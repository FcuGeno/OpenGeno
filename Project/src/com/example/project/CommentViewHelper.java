package com.example.project;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class CommentViewHelper {
	boolean isMoving = false;
	boolean isRelease = true;
	boolean isLongClick = false;
	MainActivity activity;
	ViewList VL;
	Copy copier;
	RelativeLayout comment;
	Button btn;
	EditText tv;
	SlidingDrawer sd_top;
	String text = "";

	public CommentViewHelper(RelativeLayout comment, Button btn,
		final EditText tv, MainActivity activity) {
		this.activity = activity;
		VL = activity.getViewList();
		copier = activity.getCopier();
		this.copier = activity.getCopier();
		this.comment = comment;
		this.btn = btn;
		this.tv = tv;
		text = tv.getText().toString();
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		btn.setOnTouchListener(onTouchListener);
		btn.setOnLongClickListener(onLongClickListener);
		tv.setOnTouchListener(onTouchListener);
		tv.setOnLongClickListener(onLongClickListener);
	}

	private OnTouchListener onTouchListener = new OnTouchListener() {
		private int x, y;
		private int touchX, touchY;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isRelease = false;
				touchX = (int) (event.getRawX() - comment.getTranslationX());
				touchY = (int) (event.getRawY() - comment.getTranslationY());
				return false;

			case MotionEvent.ACTION_UP:
				if (!isMoving && !isLongClick) {
					if (copier.isReady()) {
//						copier.setCopy(comment, tv.getText().toString());
						copier.reset();
					} else {
						if (v.getClass().equals(tv.getClass())) {
							dialog();
						} else {
							if (tv.getVisibility() == View.GONE) {
								tv.setVisibility(View.VISIBLE);
								btn.setBackgroundColor(Color.RED);
								comment.setTranslationX(comment
										.getTranslationX()
										- (tv.getHeight() / 2)
										+ (btn.getHeight() / 2));
							} else {
								tv.setVisibility(View.GONE);
								btn.setBackgroundResource(R.drawable.min_postit);
								comment.setTranslationX(comment
										.getTranslationX()
										+ (tv.getHeight() / 2)
										- (btn.getHeight() / 2));
							}
						}
					}
				} else {
					VL.setViewXY(v.getId(), x, y);
				}
				isMoving = false;
				isRelease = true;
				isLongClick = false;
				return true;

			case MotionEvent.ACTION_MOVE:
				isMoving = true;
				x = (int) (event.getRawX() - touchX);
				y = (int) (event.getRawY() - touchY);
				comment.setTranslationX(x);
				comment.setTranslationY(y);
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
				text = editText.getText().toString();
				tv.setText(text);
				VL.setComment(tv.getId(), text);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	private OnLongClickListener onLongClickListener = new OnLongClickListener() {
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
		String[] s = new String[] { "最上層", "最下層", "刪除" };
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
			switch (action) {
			case 0:
				VL.add(VL.remove(comment.getId()));
				comment.bringToFront();
				activity.findViewById(R.id.sd_top).bringToFront();
				activity.findViewById(R.id.sd_right).bringToFront();
				activity.findViewById(R.id.save).bringToFront();
				comment.setTranslationX(comment.getTranslationX()+1);
				break;
			case 1:
				VL.addFirst(VL.remove(comment.getId()));
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
				comment.setTranslationX(comment.getTranslationX()+1);
				break;
			case 2:
				VL.remove(tv.getId());
				comment.clearAnimation();
				comment.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	}
}

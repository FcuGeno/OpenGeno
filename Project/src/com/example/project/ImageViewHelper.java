package com.example.project;

import java.lang.reflect.Field;
import java.util.LinkedList;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.Toast;

public class ImageViewHelper {
	boolean isMoving = false;
	boolean isRelease = true;
	boolean isZooming = false;
	boolean isLongclick = false;
	int index;
	int kind;
	MainActivity activity;
	ViewList VL;
	HistoryList HL;
	ViewGroupList VGL;
	MyViewGroup MVG;
	Copy copier;
	AddView addView;
	Paste paster;
	ImageView imageView;
	LinkedViews link;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	Spinner spnMaterial;
	int defaultHeight = 100;
	int defaultWidth = 100;
	int[] imgID;
	Rotation rotation;
	LinkedList<Integer> pointX;
	LinkedList<Integer> pointY;

	public ImageViewHelper(ImageView imageView, int index, int kind,
			MainActivity activity, LinkedList<Integer> pointX,
			LinkedList<Integer> pointY) {
		this.activity = activity;
		VL = activity.getViewList();
		HL = activity.getHistoryList();
		VGL = activity.getVGL();
		copier = activity.getCopier();
		paster = activity.getPaster();
		addView = activity.getAddView();
		link = activity.getLink();
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) activity.findViewById(R.id.sd_right);
		spnMaterial = activity.getSpnMaterial();
		rotation = activity.getRotation();
		this.imageView = imageView;
		this.index = index;
		this.kind = kind;
		this.pointX = pointX;
		this.pointY = pointY;
		imgID = activity.getImgID();
		imageView.setOnTouchListener(imgOnTouchListener);
		imageView.setOnLongClickListener(imgOnLongClickListener);
		if (kind == 3) {
			rotation.setRotation(index, imageView);
		}
	}

	private OnTouchListener imgOnTouchListener = new OnTouchListener() {
		private int touchX, touchY;
		private int organizeX, organizeY;
		private int vX, vY;
		private int x, y;
		private int x1, y1;
		private int x2, y2;
		private int height, width;
		private int organizeHeight, organizeWidth;
		private int nextHeight, nextWidth;
		private int zoomHeight, zoomWidth;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			MVG = VGL.getMVG(v);
			int pointerNum = event.getPointerCount();
			organizeX = (int) v.getTranslationX();
			organizeY = (int) v.getTranslationY();
			height = v.getHeight();
			width = v.getWidth();
			if (kind == 3) {
				rotation.setRotation(index, imageView);
			} else {
				rotation.setVisibility(View.GONE);
			}
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(addView.isGroupReady()){
					link.setGroup(imageView, index, pointX, pointY);
					return true;
				}
				touchX = (int) (event.getRawX() - v.getTranslationX());
				touchY = (int) (event.getRawY() - v.getTranslationY());
				isRelease = false;
				return false;
			case MotionEvent.ACTION_POINTER_2_DOWN:
				organizeHeight = 0;
				organizeWidth = 0;
				return true;
			case MotionEvent.ACTION_UP:
				if(addView.isGroupReady())
					return true;
				if (isLongclick) {
					isMoving = false;
					isRelease = true;
					isZooming = false;
					isLongclick = false;
					return true;
				}
				if (!isMoving && !isZooming) {
					if (paster.isReady()) {
						addView.addView(event);
					}
					if (copier.isReady()) {
						if (MVG != null)
							copier.setCopy(MVG);
						else if (kind == -1)
							copier.setCopy(v.getHeight(), v.getWidth(), pointX,
									pointY);
						else
							copier.setCopy(index, v.getWidth(), v.getHeight(),
									rotation.getRotation());
						copier.reset();
						Toast.makeText(activity, "已複製", Toast.LENGTH_SHORT)
								.show();
					} else if (addView.isAddView()) {
						addView.addView(event);
					} else {
						if (kind == 0) {
							link.setLink(v, index);
						}
					}
				} else {
					if (isMoving) {
						VL.setViewXY(v.getId(), x, y);
					}
					if (isZooming && kind != -1) {
						VL.setViewHW(v.getId(), width, height);
					}
				}
				isMoving = false;
				isRelease = true;
				isZooming = false;
				return true;

			case MotionEvent.ACTION_MOVE:
				if (!isMoving) {
					x = (int) (event.getRawX() - touchX);
					y = (int) (event.getRawY() - touchY);
					vX = x - organizeX;
					vY = y - organizeY;
					if ((vX > -5 && vX < 5) && (vY > -5 && vY < 5))
						return false;

					History h;
					if (MVG != null) {
						h = new History((ImageView) v, MVG);
					} else {
						h = new History((ImageView) v, index);
					}
					h.moveImg(organizeX, organizeY, width, height);
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
					if (kind != -1) {
						if (!isZooming) {
							History h;
							if (MVG != null) {
								h = new History((ImageView) v, index);
								h.moveImg(organizeX, organizeY, width, height);
								HL.add(h);
							}
						}
						if (kind != 0) {
							x1 = (int) event.getY(0);
							y1 = (int) event.getX(0);
							x2 = (int) event.getY(1);
							y2 = (int) event.getX(1);

							if (x1 > x2)
								nextHeight = x1 - x2;
							else
								nextHeight = x2 - x1;
							if (y1 > y2)
								nextWidth = y1 - y2;
							else
								nextWidth = y2 - y1;

							if (organizeHeight == 0 && organizeWidth == 0) {
								organizeHeight = nextHeight;
								organizeWidth = nextWidth;
							}
							zoomHeight = nextHeight - organizeHeight;
							zoomWidth = nextWidth - organizeWidth;
							organizeHeight = nextHeight;
							organizeWidth = nextWidth;

							height = height + zoomHeight;
							width = width + zoomWidth;

							HWSet();
							v.setLayoutParams(new RelativeLayout.LayoutParams(
									width, height));
							if (kind == 1 || kind == 2 || kind >= 4) {
								((Drawer) v).setHW(width, height);
								v.invalidate();
							}
						}
					}
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

		public void HWSet() {
			int h = imageView.getHeight();
			int w = imageView.getWidth();
			switch (kind) {
			case 1:
				width = 20;
				if (height <= 20)
					height = 20;
				else if (height >= 250)
					height = 250;
				break;
			case 2:
				if (width <= 20)
					width = 20;
				else if (width >= 250) {
					width = 250;
				}
				height = 20;
				break;
			case 3:
				int progress = (int) rotation.getRotation();
				if ((progress >= 0 && progress <= 15)
						|| (progress >= 165 && progress <= 195)
						|| (progress >= 345 && progress <= 360)) {
					if (width <= 20)
						width = 20;
					else if (width >= 250) {
						width = 250;
					}
					height = h;
				} else if ((progress >= 75 && progress <= 105)
						|| (progress >= 255 && progress <= 285)) {
					if (height <= 20)
						height = 20;
					else if (height >= 250) {
						height = 250;
					}
					width = w;
				} else {
					if (width <= 20)
						width = 20;
					else if (width >= 400) {
						width = 400;
					}
					if (height <= 20)
						height = 20;
					else if (height >= 400) {
						height = 400;
					}
				}
				break;
			case 4:
				if (width <= 20)
					width = 20;
				else if (width >= 600) {
					width = 600;
				}
				if (height <= 20)
					height = 20;
				else if (height >= 100) {
					height = 100;
				}
				break;
			case 5:
				if (width <= 20)
					width = 20;
				if (height <= 20)
					height = 20;
				break;
			case 6:
				if (width <= 20)
					width = 20;
				else if (width >= 400) {
					width = 400;
				}
				if (height <= 20)
					height = 20;
				else if (height >= 250) {
					height = 250;
				}
				break;
			default:
				height = 20;
				width = 20;
				break;
			}

		}
	};

	private OnLongClickListener imgOnLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (!isMoving && !isRelease) {
				isLongclick = true;
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
				h = new History(imageView, index);
				h.changeLayers(VL.getLoc(imageView.getId()));
				HL.add(h);
				VL.add(VL.remove(imageView.getId()));
				imageView.bringToFront();
				activity.findViewById(R.id.sd_top).bringToFront();
				activity.findViewById(R.id.sd_right).bringToFront();
				activity.findViewById(R.id.save).bringToFront();
				imageView.setTranslationX(imageView.getTranslationX() + 1);
				break;
			case 1:
				h = new History(imageView, index);
				h.changeLayers(VL.getLoc(imageView.getId()));
				HL.add(h);
				VL.addFirst(VL.remove(imageView.getId()));
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
				imageView.setTranslationX(imageView.getTranslationX() + 1);
				break;
			case 2:
				h = new History(imageView, index);
				if (MVG != null) {
					MVG.removeView(imageView);
					h.setMVG(MVG);
				}
				h.deleteImg((int) imageView.getTranslationX(),
						(int) imageView.getTranslationY(),
						imageView.getHeight(), imageView.getWidth(),
						VL.getLoc(imageView.getId()));
				HL.add(h);
				VL.remove(imageView.getId());
				imageView.setVisibility(View.GONE);
				activity.getRotation().getBar().setVisibility(View.GONE);
				break;
			case 3:
				link.setGroup(imageView, index, pointX, pointY);
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

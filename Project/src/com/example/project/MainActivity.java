package com.example.project;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;

public class MainActivity extends Activity {

	private ViewList VL;
	private HistoryList HL;
	private ViewGroupList VGL;
	private Copy copier;
	private Paste paster;
	private Save S;
	private DrawCanvas DC; 
	private AddView addView;
	private LinkedViews link;
	private Spinner spnMaterial;
	private Rotation rotation;
	private SlidingDrawer sd_top;
	private SlidingDrawer sd_right;
	private RelativeLayout canvas;
	private ImageButton newpic;
	private ImageButton save;
	private ImageButton folder;
	private ImageButton undo;
	private ImageButton redo;
	private ImageButton copy;
	private ImageButton paste;
	private ImageButton pencil;
	private ImageButton create_text;
	private ImageButton comment;
	private ImageButton groupViews;
	private ImageButton export;
	private ImageButton share;
	private ImageButton about;
	private String[] imageID = { "man", "woman", "this_man", "this_wman",
			"death_man", "death_wman", "imp_man", "imp_wman", "deadbaby_boy",
			"deadbaby_girl", "abortion", "antiabortion", "pregnancy",

			"child", "adoption",

			"identical", "line", "dotline",

			"line_one", "line_two", "dotline_one", "dotline_two", "press",
			"press_one", "press_two", "close", "clash", "closeclash",
			"veryclose", "interrupt", "far",

			"marry", "divorce", "cohabit", "nocohabit", "child_two",

			"ecology", "outresource",

			"twin", "rectangle" };
	private int[] imgID = new int[imageID.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < imageID.length; i++) {
			imgID[i] = getdrawable(imageID[i]);
		}
		setContentView(R.layout.activity_main);
		findId();
		newObjects();

		/* 抽屜開關 */
		new slidingOC(sd_top, sd_right, this);

		/* 下拉式清單內容 */
		new spnMaterial(spnMaterial, this);

		/* 右抽屜圖片設定 */
		new ImageSet(this);

		/* 開新檔案 */
		new NewPage(newpic, canvas, this);

		new Load(folder, this);

		new UndoRedo(undo, redo, this);

		new Pencil(pencil, canvas, this);
		
		new CreateText(create_text, this);

		new AddComment(comment, canvas, this);

		new GroupViews(groupViews ,this);
		
		new ExportImage(export, canvas, this);

		new ShareImage(share, canvas, this);
		
		new AboutUs(about, this);
	}

	private void findId() {
		sd_top = (SlidingDrawer) findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) findViewById(R.id.sd_right);

		spnMaterial = (Spinner) findViewById(R.id.spnMaterial);

		canvas = (RelativeLayout) findViewById(R.id.RelativeLayout1);

		newpic = (ImageButton) findViewById(R.id.newpic);

		save = (ImageButton) findViewById(R.id.save);
		folder = (ImageButton) findViewById(R.id.folder);

		undo = (ImageButton) findViewById(R.id.undo);
		redo = (ImageButton) findViewById(R.id.redo);

		copy = (ImageButton) findViewById(R.id.copy);
		paste = (ImageButton) findViewById(R.id.paste);
		pencil =  (ImageButton) findViewById(R.id.pencil);
		create_text = (ImageButton) findViewById(R.id.create_text);
		comment = (ImageButton) findViewById(R.id.comment);
		groupViews = (ImageButton) findViewById(R.id.group);
		export = (ImageButton) findViewById(R.id.export);
		share = (ImageButton) findViewById(R.id.share);
		about = (ImageButton) findViewById(R.id.about);
	}

	private void newObjects() {
		VL = new ViewList(this);
		HL = new HistoryList();
		VGL = new ViewGroupList();
		copier = new Copy(copy, this);
		paster = new Paste(paste, this);
		S = new Save(save, this);
		rotation = new Rotation(canvas, this);
		DC = new DrawCanvas(this);
		addView = new AddView(canvas, this);
		link = new LinkedViews(canvas, this);
	}

	public Spinner getSpnMaterial() {
		return spnMaterial;
	}

	public ViewList getViewList() {
		return VL;
	}

	public HistoryList getHistoryList() {
		return HL;
	}

	public ViewGroupList getVGL() {
		return VGL;
	}

	public Copy getCopier() {
		return copier;
	}

	public Paste getPaster() {
		return paster;
	}

	public Save getSave() {
		return S;
	}

	public AddView getAddView() {
		return addView;
	}

	public DrawCanvas getDrawCanvas(){
		return DC;
	}
	
	public LinkedViews getLink() {
		return link;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public int[] getImgID() {
		return imgID;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			dialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void dialog() {
		Builder builder = new Builder(this);
		builder.setTitle("離開");
		builder.setMessage("確定要離開?");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
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

package com.example.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class NewPage {
	MainActivity activity;
	RelativeLayout canvas;
	ImageButton button;
	ImageButton save;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	SeekBar bar;
	ImageButton undo;
	ImageButton redo;
	LinkedViews link;
	HistoryList HL;
	ViewList VL;
	String fileName = "default.txt";
	EditText editText;
	Save S;

	public NewPage(ImageButton button, RelativeLayout canvas,
			MainActivity activity) {
		this.activity = activity;
		this.button = button;
		this.canvas = canvas;
		sd_top = (SlidingDrawer) activity.findViewById(R.id.sd_top);
		sd_right = (SlidingDrawer) activity.findViewById(R.id.sd_right);
		bar = activity.getRotation().getBar();
		save = (ImageButton) activity.findViewById(R.id.save);
		undo = (ImageButton) activity.findViewById(R.id.undo);
		redo = (ImageButton) activity.findViewById(R.id.redo);
		link = activity.getLink();
		HL = activity.getHistoryList();
		VL = activity.getViewList();
		S = activity.getSave();
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (HL.isChange() && HL.needSave()) {
					dialog();
				} else {
					reset();
				}
			}
		});
	}

	public void reset() {
		canvas.removeAllViews();
		undo.setBackgroundResource(0);
		redo.setBackgroundResource(0);
		link.reset();
		HL.clear();
		VL.clear();
		S.setFileName("default.txt");
		canvas.addView(sd_top);
		canvas.addView(sd_right);
		canvas.addView(save);
		canvas.addView(bar);
		bar.setVisibility(View.GONE);
	}

	public void dialog() {
		Builder builder = new Builder(activity);
		builder.setTitle("開新檔案");
		builder.setMessage("要儲存嗎?");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setNeutralButton("不要儲存", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				reset();
				Toast.makeText(activity, "開新檔案", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("儲存", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				saveDialog();
			}
		});
		builder.show();
	}

	public void saveDialog() {
		final EditText editText = new EditText(activity);
		editText.setText(fileName);
		Builder builder = new Builder(activity);
		builder.setView(editText);
		builder.setTitle("儲存");
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String data = VL.toSave();
				String output = "";
				char[] tmp = data.toCharArray();
				for(int i = 0; i < tmp.length; i++){
					output = output + (int)tmp[i] +" ";
				}
				fileName = editText.getText().toString();
				File path = new File("/sdcard/Genogram/save");
				File file = new File(path.getPath() + "/" + fileName);
				if (!file.exists()) {
					try {
						
						file.createNewFile();
						FileOutputStream outputStream = new FileOutputStream(
								file);
						outputStream.write(output.getBytes());
						outputStream.close();
						reset();
						Toast.makeText(activity, "已儲存", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					fileNameExistDialog(output, file);
				}
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	public void fileNameExistDialog(final String data, final File file) {
		Builder builder = new Builder(activity);
		builder.setTitle("儲存");
		builder.setMessage("檔案已存在，是否覆蓋?");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				try {
					FileOutputStream outputStream = new FileOutputStream(file);
					outputStream.write(data.getBytes());
					outputStream.close();
					reset();
					Toast.makeText(activity, "已儲存", Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		builder.setNegativeButton("否", null);
		builder.show();
	}
}

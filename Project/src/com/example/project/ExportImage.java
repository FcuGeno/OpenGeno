package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ExportImage {
	MainActivity activity;
	RelativeLayout canvas;
	ImageButton button;
	ViewList VL;
	String fileName = "default.png";

	public ExportImage(ImageButton button, RelativeLayout canvas,
			MainActivity activity) {
		this.activity = activity;
		this.canvas = canvas;
		this.button = button;
		VL = activity.getViewList();
		setOnClickListener(button);
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog();
			}
		});
	}

	public void dialog() {
		final EditText editText = new EditText(activity);
		editText.setText(fileName);
		Builder builder = new Builder(activity);
		builder.setView(editText);
		builder.setTitle("匯出");
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				fileName = editText.getText().toString();
				export();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	public void export() {
		activity.findViewById(R.id.save).setVisibility(View.GONE);
		activity.findViewById(R.id.sd_top).setVisibility(View.GONE);
		activity.findViewById(R.id.sd_right).setVisibility(View.GONE);
		activity.getRotation().setVisibility(View.GONE);
		for (int i = 0; i < VL.size(); i++) {
			if (VL.get(i).getViewKind().equals("VIEWKIND_COMMENT"))
				((CommentViewObject) VL.get(i)).getView().setVisibility(
						View.GONE);
		}
		View content = canvas;
		content.setDrawingCacheEnabled(true);
		Bitmap bitmap = content.getDrawingCache();
		File path = new File("/sdcard/Genogram/image");
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(path.getPath() + "/" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream ostream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.PNG, 10, ostream);
				ostream.close();
				content.invalidate();
				Toast.makeText(activity, "已匯出", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				content.setDrawingCacheEnabled(false);
			}
		} else {
			fileNameExistDialog(file, bitmap, content);
		}

		for (int i = 0; i < VL.size(); i++) {
			if (VL.get(i).getViewKind().equals("VIEWKIND_COMMENT"))
				((CommentViewObject) VL.get(i)).getView().setVisibility(
						View.VISIBLE);
		}
		activity.findViewById(R.id.save).setVisibility(View.VISIBLE);
		activity.findViewById(R.id.sd_top).setVisibility(View.VISIBLE);
		activity.findViewById(R.id.sd_right).setVisibility(View.VISIBLE);
	}

	public void fileNameExistDialog(final File file, final Bitmap bitmap,
			final View content) {
		Builder builder = new Builder(activity);
		builder.setTitle("匯出");
		builder.setMessage("檔案已存在，是否覆蓋?");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				try {
					FileOutputStream ostream = new FileOutputStream(file);
					bitmap.compress(CompressFormat.PNG, 10, ostream);
					ostream.close();
					content.invalidate();
					Toast.makeText(activity, "已匯出", Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					content.setDrawingCacheEnabled(false);
				}
			}
		});
		builder.setNegativeButton("否", null);
		builder.show();
	}
}

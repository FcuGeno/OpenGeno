package com.example.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class ShareImage extends Activity {
	RelativeLayout canvas;
	MainActivity activity;
	ViewList VL;
	String fileName = "TempImage.png";
	File file;

	public ShareImage(ImageButton button, RelativeLayout canvas,
			MainActivity activity) {
		this.activity = activity;
		this.canvas = canvas;
		VL = activity.getViewList();
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				export();
				Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
				myIntent.setAction(android.content.Intent.ACTION_SEND);
				myIntent.setType("image/*");
				myIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
				activity.startActivity(myIntent);
			}
		});
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
		File path = new File("/sdcard/Genogram/temp");
		if (!path.exists()) {
			path.mkdirs();
		}
		file = new File(path.getPath() + "/" + fileName);
		try {
			if (!file.exists())
				file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 10, ostream);
			ostream.close();
			content.invalidate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			content.setDrawingCacheEnabled(false);
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
}

package com.example.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Save {
	ImageButton button;
	MainActivity activity;
	ViewList VL;
	HistoryList HL;
	String fileName = "default.txt";

	public Save(ImageButton button, MainActivity activity) {
		this.button = button;
		this.activity = activity;
		VL = activity.getViewList();
		HL = activity.getHistoryList();
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog();
			}
		});
	}

	public void writeToFile() {
		String data = VL.toSave();
		String output = "";
		char[] tmp = data.toCharArray();
		for(int i = 0; i < tmp.length; i++){
			output = output + (int)tmp[i] +" ";
		}
		File path = new File("/sdcard/Genogram/save");
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(path.getPath() + "/" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(output.getBytes());
				outputStream.close();
				HL.resetChange();
				HL.resetSave();
				Toast.makeText(activity, "�w�x�s", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			fileNameExistDialog(output, file);
		}
	}

	public void dialog() {
		final EditText editText = new EditText(activity);
		editText.setText(fileName);
		Builder builder = new Builder(activity);
		builder.setView(editText);
		builder.setTitle("�x�s");
		builder.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				fileName = editText.getText().toString();
				writeToFile();
			}
		});
		builder.setNegativeButton("����", null);
		builder.show();
	}


	public class ChoiceOnClickListener implements
			DialogInterface.OnClickListener {
		int which;

		@Override
		public void onClick(DialogInterface dialog, int which) {
			this.which = which;
		}

		public int getwitch() {
			return which;
		}
	}

	public void fileNameExistDialog(final String data, final File file) {
		Builder builder = new Builder(activity);
		builder.setTitle("�x�s");
		builder.setMessage("�ɮפw�s�b�A�O�_�л\?");
		builder.setPositiveButton("�O", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				try {
					FileOutputStream outputStream = new FileOutputStream(file);
					outputStream.write(data.getBytes());
					outputStream.close();
					HL.resetChange();
					HL.resetSave();
					Toast.makeText(activity, "�w�x�s", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		builder.setNegativeButton("�_", null);
		builder.show();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}

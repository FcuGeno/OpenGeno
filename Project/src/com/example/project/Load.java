package com.example.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class Load {
	ImageButton button;
	MainActivity activity;
	AddView addView;
	DrawCanvas DC;
	HistoryList HL;
	ViewList VL;
	Save save;
	String input;
	String[] fileList;
	String fileName = "default.txt";
	static String VIEWKIND_IMAGE = "VIEWKIND_IMAGE";
	static String VIEWKIND_TEXT = "VIEWKIND_TEXT";
	static String VIEWKIND_COMMENT = "VIEWKIND_COMMENT";
	static String VIEWKIND_PENCIL = "VIEWKIND_PENCIL";
	public Load(ImageButton button, MainActivity activity) {
		this.button = button;
		this.activity = activity;
		addView = activity.getAddView();
		DC = activity.getDrawCanvas();
		HL = activity.getHistoryList();
		VL = activity.getViewList();
		save = activity.getSave();
		button.setClickable(true);
		button.setBackgroundResource(R.drawable.selector);
		setOnClickListener(button);
	}

	public void setOnClickListener(ImageButton button) {
		button.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				fileList = fileFilter();
				dialog();
			}
		});
	}

	public void dataAnalyse() {
		String data = "";
		StringTokenizer st = new StringTokenizer(input, " ");
		while (st.hasMoreTokens()) {
			int tmp = Integer.parseInt(st.nextToken());
			data = data + (char) tmp;
		}
		st = new StringTokenizer(data, " ");
		String viewKind = null;
		if (st.hasMoreTokens())
			viewKind = st.nextToken();
		while (st.hasMoreTokens()) {
			int X = 0;
			int Y = 0;
			int height;
			int width;
			int imgIndex = -1;
			int progress;
			LinkedList<Integer> pointX;
			LinkedList<Integer> pointY;
			String text = "";
			if (viewKind.equals(VIEWKIND_IMAGE)) {
				X = Integer.valueOf(st.nextToken());
				Y = Integer.valueOf(st.nextToken());
				height = Integer.valueOf(st.nextToken());
				width = Integer.valueOf(st.nextToken());
				imgIndex = Integer.valueOf(st.nextToken());
				progress = Integer.valueOf(st.nextToken());
				addView.add(imgIndex, X, Y, height, width,
						addView.findKind(imgIndex), progress, null);
				if (st.hasMoreTokens())
					viewKind = st.nextToken();
			} else if (viewKind.equals(VIEWKIND_TEXT)) {
				X = Integer.valueOf(st.nextToken());
				Y = Integer.valueOf(st.nextToken());
				while (st.hasMoreTokens()) {
					viewKind = st.nextToken();
					if (viewKind.equals(VIEWKIND_IMAGE)
							|| viewKind.equals(VIEWKIND_TEXT)
							|| viewKind.equals(VIEWKIND_COMMENT)
							|| viewKind.equals(VIEWKIND_PENCIL))
						break;
					text = text + viewKind + " ";
				}
				addView.addTextView(text, X, Y);
			} else if (viewKind.equals(VIEWKIND_COMMENT)) {
				X = Integer.valueOf(st.nextToken());
				Y = Integer.valueOf(st.nextToken());
				while (st.hasMoreTokens()) {
					viewKind = st.nextToken();
					if (viewKind.equals(VIEWKIND_IMAGE)
							|| viewKind.equals(VIEWKIND_TEXT)
							|| viewKind.equals(VIEWKIND_COMMENT)
							|| viewKind.equals(VIEWKIND_PENCIL))
						break;
					text = text + viewKind + " ";
				}
				addView.addCommentView(text, X, Y);
			} else if (viewKind.equals(VIEWKIND_PENCIL)) {
				pointX = new LinkedList<Integer>();
				pointY = new LinkedList<Integer>();
				X = Integer.valueOf(st.nextToken());
				Y = Integer.valueOf(st.nextToken());
				height = Integer.valueOf(st.nextToken());
				width = Integer.valueOf(st.nextToken());
				int size = Integer.valueOf(st.nextToken());
				for(int i = 0; i < size ; i++)
					pointX.add(Integer.valueOf(st.nextToken()));
				for(int i = 0; i < size ; i++)
					pointY.add(Integer.valueOf(st.nextToken()));
				DC.loadDrawCanvas(X, Y, height, width, pointX, pointY);
				if (st.hasMoreTokens())
					viewKind = st.nextToken();
			}
		}
	}

	public void load(String fileName) {
		input = "";
		File path = new File("/sdcard/Genogram/save");
		File file = new File(path.getPath() + "/" + fileName);
		try {
			InputStream inputStream = new FileInputStream(file);
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();
				save.setFileName(fileName);
				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}
				inputStream.close();
				input = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}
	}

	public String[] fileFilter() {
		FilenameFilter filter = new FilenameFilter() {
			private String s = ".txt";

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.indexOf(s) != -1)
					return true;
				return false;
			}
		};
		File path = new File("/sdcard/Genogram/save");
		if (!path.exists())
			path.mkdirs();
		File[] files = path.listFiles(filter);
		String[] fileList = new String[files.length];
		for (int i = 0; i < files.length; i++)
			fileList[i] = files[i].getName();
		Arrays.sort(fileList);
		return fileList;
	}

	public void reset() {
		RelativeLayout canvas = (RelativeLayout) activity
				.findViewById(R.id.RelativeLayout1);
		SlidingDrawer sd_top = (SlidingDrawer) activity
				.findViewById(R.id.sd_top);
		SlidingDrawer sd_right = (SlidingDrawer) activity
				.findViewById(R.id.sd_right);
		ImageButton save = (ImageButton) activity.findViewById(R.id.save);

		canvas.removeAllViews();
		VL.clear();
		canvas.addView(sd_top);
		canvas.addView(sd_right);
		canvas.addView(save);
		canvas.addView(activity.getRotation().getBar());
	}

	public void dialog() {
		Builder builder = new Builder(activity);
		builder.setTitle("開啟舊檔");
		final ChoiceOnClickListener onClickListener = new ChoiceOnClickListener();
		builder.setSingleChoiceItems(fileList, -1, onClickListener);
		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(onClickListener.getwitch() == -1){
					return;
				}
				if(HL.isChange() && HL.needSave())
					askSave(fileList[onClickListener.getwitch()]);
				else{
					reset();
					load(fileList[onClickListener.getwitch()]);
					dataAnalyse();
					HL.clear();
					activity.getRotation().getBar().setVisibility(View.GONE);
				}
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	public class ChoiceOnClickListener implements
			DialogInterface.OnClickListener {
		int which = -1;

		@Override
		public void onClick(DialogInterface dialog, int which) {
			this.which = which;
		}

		public int getwitch() {
			return which;
		}
	}

	public void askSave(final String fileName) {
		Builder builder = new Builder(activity);
		builder.setTitle("開啟舊案");
		builder.setMessage("要儲存嗎?");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setNeutralButton("不要儲存", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				reset();
				load(fileName);
				dataAnalyse();
				HL.clear();
			}
		});
		builder.setNegativeButton("儲存", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				saveDialog(fileName);
			}
		});
		builder.show();
	}

	public void saveDialog(final String theFileName) {
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
						load(theFileName);
						dataAnalyse();
						HL.clear();
						Toast.makeText(activity, "已儲存", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					fileNameExistDialog(output, theFileName, file);
				}
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	public void fileNameExistDialog(final String data, final String fileName, final File file) {
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
					load(fileName);
					dataAnalyse();
					HL.clear();
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

package com.example.project;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class spnMaterial {
	String[] Material = { "全部", "人物", "關係", "生態圈" };
	
	private RelativeLayout changeLayout;
	private RelativeLayout eco;
	private RelativeLayout fig;
	private RelativeLayout rel;
	private RelativeLayout all;
	
	public spnMaterial(Spinner spnMaterial, MainActivity activity){
		changeLayout = (RelativeLayout) activity.findViewById(R.id.changeLayout);
		all	= (RelativeLayout) activity.findViewById(R.id.all);
		fig	= (RelativeLayout) activity.findViewById(R.id.fig);
		rel	= (RelativeLayout) activity.findViewById(R.id.rel);
		eco = (RelativeLayout) activity.findViewById(R.id.eco);
		// 建立ArrayAdapter
		ArrayAdapter<String> adapterMaterial = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, Material);
		// 設定spinner顯示格式
		adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 設定spinner資料來源
		spnMaterial.setAdapter(adapterMaterial);
		// 定義onItemSelected方法
		spnMaterial.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView parent, View v, int position, long id) {
				// parent = 事件發生的母體 spinner_items
				// position = 被選擇的項目index =
				// parent.getSelectedItemPosition()
				// id = row id，通常給資料庫使用
				int index = parent.getSelectedItemPosition();
				switch (index) {
				case 0:
					changeLayout.removeAllViews();// 要加入內容版面前前移除掉所有已存在的版面
					changeLayout.addView(all);// 加入第1個視窗中的內容版面
					break;
				case 1:
					changeLayout.removeAllViews();// 要加入內容版面前前移除掉所有已存在的版面
					changeLayout.addView(fig);// 加入第2個視窗中的內容版面
					break;
				case 2:
					changeLayout.removeAllViews();// 要加入內容版面前前移除掉所有已存在的版面
					changeLayout.addView(rel);// 加入第3個視窗中的內容版面
					break;
				case 3:
					changeLayout.removeAllViews();// 要加入內容版面前前移除掉所有已存在的版面
					changeLayout.addView(eco);// 加入第4個視窗中的內容版面
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView parent) {
				//do nothing
			}
		});
	}
}

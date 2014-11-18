package com.example.project;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class spnMaterial {
	String[] Material = { "����", "�H��", "���Y", "�ͺA��" };
	
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
		// �إ�ArrayAdapter
		ArrayAdapter<String> adapterMaterial = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, Material);
		// �]�wspinner��ܮ榡
		adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// �]�wspinner��ƨӷ�
		spnMaterial.setAdapter(adapterMaterial);
		// �w�qonItemSelected��k
		spnMaterial.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView parent, View v, int position, long id) {
				// parent = �ƥ�o�ͪ����� spinner_items
				// position = �Q��ܪ�����index =
				// parent.getSelectedItemPosition()
				// id = row id�A�q�`����Ʈw�ϥ�
				int index = parent.getSelectedItemPosition();
				switch (index) {
				case 0:
					changeLayout.removeAllViews();// �n�[�J���e�����e�e�������Ҧ��w�s�b������
					changeLayout.addView(all);// �[�J��1�ӵ����������e����
					break;
				case 1:
					changeLayout.removeAllViews();// �n�[�J���e�����e�e�������Ҧ��w�s�b������
					changeLayout.addView(fig);// �[�J��2�ӵ����������e����
					break;
				case 2:
					changeLayout.removeAllViews();// �n�[�J���e�����e�e�������Ҧ��w�s�b������
					changeLayout.addView(rel);// �[�J��3�ӵ����������e����
					break;
				case 3:
					changeLayout.removeAllViews();// �n�[�J���e�����e�e�������Ҧ��w�s�b������
					changeLayout.addView(eco);// �[�J��4�ӵ����������e����
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

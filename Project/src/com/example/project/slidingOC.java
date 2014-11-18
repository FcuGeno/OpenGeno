package com.example.project;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class slidingOC {
	MainActivity activity;
	SlidingDrawer sd_top;
	SlidingDrawer sd_right;
	LinkedViews link;
	Copy copier;
	Paste paster;
	AddView addView;

	public slidingOC(SlidingDrawer sd_top, SlidingDrawer sd_right,
			MainActivity activity) {
		this.activity = activity;
		this.sd_top = sd_top;
		this.sd_right = sd_right;
		link = activity.getLink();
		copier = activity.getCopier();
		paster = activity.getPaster();
		addView = activity.getAddView();
		setClose();
	}

	public void setClose() {
		/* 設定SlidingDrawer_Top打開後關閉SlidingDrawer_Right */
		sd_top.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				if (sd_right.isOpened())
					sd_right.animateClose();
			}
		});

		/* 設定SlidingDrawer_Right打開後關閉SlidingDrawer_Top */
		sd_right.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				if (sd_top.isOpened())
					sd_top.animateClose();
				activity.findViewById(R.id.changeLayout).setVisibility(
						View.VISIBLE);
			}
		});

		sd_top.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				if (addView.isAddView())
					addView.reset();
				if (copier.isReady())
					copier.reset();
				if (paster.isReady())
					paster.reset();
			}
		});

		sd_right.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				if (link.isLineReady()) {
					link.reset();
					Toast.makeText(activity, "取消選擇", Toast.LENGTH_SHORT).show();
				}
				if (addView.isAddView())
					addView.reset();
				activity.findViewById(R.id.changeLayout).setVisibility(
						View.GONE);
			}
		});
	}
}

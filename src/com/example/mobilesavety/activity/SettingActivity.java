package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUpdate();

	}

	/**
	 * 版本更新开关
	 */
	private void initUpdate() {
		// TODO Auto-generated method stub
		final SettingItemView settingItemView = (SettingItemView) findViewById(R.id.siv_update);
		// 获取已有的开关状态,用作显示
		boolean open_update = SpUtils.getBoolean(this,
				ConstantValue.OPEN_UPDATE, false);
		// 是否选中,根据上一次存储的结果去做决定
		settingItemView.setCheck(open_update);

		settingItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 如果之前是选中的,点击过后,变成未选中
				// 如果之前是未选中的,点击过后,变成选中

				// 获取之前的选中状态
				boolean isCheck = settingItemView.isCheck();

				// 将原有状态取反,等同上诉的两部操作
				settingItemView.setCheck(!isCheck);
				// 将取反后的状态存储到相应sp中s
				SpUtils.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_UPDATE, !isCheck);

			}
		});
	}

}

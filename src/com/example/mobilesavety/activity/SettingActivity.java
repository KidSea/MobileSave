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
	 * �汾���¿���
	 */
	private void initUpdate() {
		// TODO Auto-generated method stub
		final SettingItemView settingItemView = (SettingItemView) findViewById(R.id.siv_update);
		// ��ȡ���еĿ���״̬,������ʾ
		boolean open_update = SpUtils.getBoolean(this,
				ConstantValue.OPEN_UPDATE, false);
		// �Ƿ�ѡ��,������һ�δ洢�Ľ��ȥ������
		settingItemView.setCheck(open_update);

		settingItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ���֮ǰ��ѡ�е�,�������,���δѡ��
				// ���֮ǰ��δѡ�е�,�������,���ѡ��

				// ��ȡ֮ǰ��ѡ��״̬
				boolean isCheck = settingItemView.isCheck();

				// ��ԭ��״̬ȡ��,��ͬ���ߵ���������
				settingItemView.setCheck(!isCheck);
				// ��ȡ�����״̬�洢����Ӧsp��s
				SpUtils.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_UPDATE, !isCheck);

			}
		});
	}

}

package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SetupFourActivity extends BaseSetupActivity {

	private CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		checkBox = (CheckBox) findViewById(R.id.cb_box);
		// 1.�Ƿ�ѡ�л���
		boolean open_security = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		// 2,����״̬,�޸�checkbox������������ʾ
		checkBox.setChecked(open_security);
		if (open_security) {
			checkBox.setText("��ȫ�����ѿ���");
		} else {
			checkBox.setText("��ȫ�����ѹر�");
		}
		//checkBox.setChecked(!checkBox.isChecked());
		// 3,���������,����ѡ��״̬�����ı����,
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 4,isChecked������״̬,�洢�����״̬
				SpUtils.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_SECURITY, isChecked);
				// 5,���ݿ����ر�״̬,ȥ�޸���ʾ������
				if (isChecked) {
					checkBox.setText("��ȫ�����ѿ���");
				} else {
					checkBox.setText("��ȫ�����ѹر�");
				}
			}
		});
	}


	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		boolean open_security = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			// ��ת����һ��ҳ��
			Intent intent = new Intent(getApplicationContext(),
					SetupOverActivity.class);
			startActivity(intent);
			finish();
			SpUtils.putBoolean(this, ConstantValue.SETUP_OVER, true);
			
			// ����ƽ�ƶ���
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
			
			
		} else {
			ToastUtil.show(getApplicationContext(), "�뿪��������");
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// ��ת����һ��ҳ��
		Intent intent = new Intent(getApplicationContext(),
				SetupThreeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

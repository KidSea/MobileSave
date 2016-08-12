package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetupThreeActivity extends BaseSetupActivity {
	private EditText et_phone_number;
	private Button bt_select_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);

		initView();
	}

	// ��ʼ���ؼ�
	private void initView() {
		// TODO Auto-generated method stub
		// ��ʾ�绰����������
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		// ��ȡ��ϵ�˵绰������Թ���
		String phone = SpUtils.getString(this, ConstantValue.CONTACT_PHONE, "");
		et_phone_number.setText(phone);
		// ���ѡ����ϵ�˵ĶԻ���
		bt_select_number = (Button) findViewById(R.id.bt_select_number);
		// ���õ���¼�
		bt_select_number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
				startActivityForResult(intent, 0);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			// 1,���ص���ǰ�����ʱ��,���ܽ���ķ���
			String phone = data.getStringExtra("phone");
			// 2,�������ַ�����(�л���ת���ɿ��ַ���)
			phone = phone.replace("-", "").replace(" ", "").trim();
			et_phone_number.setText(phone);

			// 3,�洢��ϵ����sp��
			SpUtils.putString(getApplicationContext(),
					ConstantValue.CONTACT_PHONE, phone);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		// �����ť�Ժ�,��Ҫ��ȡ������е���ϵ��,������һҳ����
		String phone = et_phone_number.getText().toString();
		if (!TextUtils.isEmpty(phone)) {
			// ��ת����һ��ҳ��
			Intent intent = new Intent(getApplicationContext(),
					SetupFourActivity.class);
			startActivity(intent);
			finish();
			// �������������绰����,����Ҫȥ����
			SpUtils.putString(getApplicationContext(),
					ConstantValue.CONTACT_PHONE, phone);

			// ����ƽ�ƶ���
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// ��ת����һ��ҳ��
		Intent intent = new Intent(getApplicationContext(),
				SetupTwoActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

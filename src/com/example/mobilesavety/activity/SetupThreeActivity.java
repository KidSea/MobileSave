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

	// 初始化控件
	private void initView() {
		// TODO Auto-generated method stub
		// 显示电话号码的输入框
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		// 获取联系人电话号码回显过程
		String phone = SpUtils.getString(this, ConstantValue.CONTACT_PHONE, "");
		et_phone_number.setText(phone);
		// 点击选择联系人的对话框
		bt_select_number = (Button) findViewById(R.id.bt_select_number);
		// 设置点击事件
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
			// 1,返回到当前界面的时候,接受结果的方法
			String phone = data.getStringExtra("phone");
			// 2,将特殊字符过滤(中划线转换成空字符串)
			phone = phone.replace("-", "").replace(" ", "").trim();
			et_phone_number.setText(phone);

			// 3,存储联系人至sp中
			SpUtils.putString(getApplicationContext(),
					ConstantValue.CONTACT_PHONE, phone);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		// 点击按钮以后,需要获取输入框中的联系人,再做下一页操作
		String phone = et_phone_number.getText().toString();
		if (!TextUtils.isEmpty(phone)) {
			// 跳转到下一个页面
			Intent intent = new Intent(getApplicationContext(),
					SetupFourActivity.class);
			startActivity(intent);
			finish();
			// 如果现在是输入电话号码,则需要去保存
			SpUtils.putString(getApplicationContext(),
					ConstantValue.CONTACT_PHONE, phone);

			// 开启平移动画
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// 跳转到上一个页面
		Intent intent = new Intent(getApplicationContext(),
				SetupTwoActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

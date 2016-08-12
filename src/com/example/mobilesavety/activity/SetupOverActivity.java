package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SetupOverActivity extends Activity {
	private TextView tv_phone;
	private TextView tv_reset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		boolean step_over = SpUtils.getBoolean(this, ConstantValue.SETUP_OVER, false);
		if(step_over){
			//如果之前设置过就进入最终页面，密码输入成功,并且四个导航界面设置完成
			setContentView(R.layout.activity_setup_over);
			initView();
		}else{
			//如果之前没设置过就调到设置向导，//密码输入成功,四个导航界面没有设置完成----->跳转到导航界面第1个
			Intent intent = new Intent(this,SetupOneActivity.class);
			startActivity(intent);
			//开启了一个新的界面以后,关闭功能列表界面
			finish();
		}
	}

	private void initView() {
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		//设置联系人号码
		String num = SpUtils.getString(this, ConstantValue.CONTACT_PHONE,"");
		tv_phone.setText(num);
		tv_reset = (TextView) findViewById(R.id.tv_reset_setup);
		tv_reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), SetupOneActivity.class);
				startActivity(intent);
				
				finish();
			}
		});
	}
}

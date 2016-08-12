package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupOneActivity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	//加上通过手势判断来滑动页面
	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		//跳转到下一个页面
		Intent intent = new Intent(getApplicationContext(),SetupTwoActivity.class);
		startActivity(intent);
		finish();
		
		//开启平移动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		//空
	}
}

package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupThreeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}
	public void nextPage(View view){
		//跳转到下一个页面
		Intent intent = new Intent(getApplicationContext(),SetupFourActivity.class);
		startActivity(intent);
		finish();
		
		//开启平移动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
	public void prePage(View view){
		//空
		//跳转到上一个页面
		Intent intent = new Intent(getApplicationContext(),SetupTwoActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

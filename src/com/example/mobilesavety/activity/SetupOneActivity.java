package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupOneActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	public void nextPage(View view){
		//��ת����һ��ҳ��
		Intent intent = new Intent(getApplicationContext(),SetupTwoActivity.class);
		startActivity(intent);
		finish();
		
		//����ƽ�ƶ���
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
	public void prePage(View view){
		//��
	}
}

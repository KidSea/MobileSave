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
			//���֮ǰ���ù��ͽ�������ҳ�棬��������ɹ�,�����ĸ����������������
			setContentView(R.layout.activity_setup_over);
			initView();
		}else{
			//���֮ǰû���ù��͵��������򵼣�//��������ɹ�,�ĸ���������û���������----->��ת�����������1��
			Intent intent = new Intent(this,SetupOneActivity.class);
			startActivity(intent);
			//������һ���µĽ����Ժ�,�رչ����б����
			finish();
		}
	}

	private void initView() {
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		//������ϵ�˺���
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

package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SetupOverActivity extends Activity {
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
		// TODO Auto-generated method stub
		
	}
}

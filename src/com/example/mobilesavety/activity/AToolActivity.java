package com.example.mobilesavety.activity;
import com.example.mobilesavety.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
/**��
 * ϵͳ�߼����߽���
 * @author yuxuehai
 *
 */
public class AToolActivity extends Activity {
	private TextView tv_query_phone_address, tv_sms_backup;
	private ProgressBar pb_bar;
	private TextView tv_commonnumber_query;
	private TextView tv_app_lock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atool);

		// �绰�����ز�ѯ����
		initPhoneAddress();
		// ���ű��ݷ���
		initSmsBackUp();
		// ���ú����ѯ
		initCommonNumberQuery();
		initAppLock();
	}

	private void initAppLock() {
		// TODO Auto-generated method stub
		tv_app_lock = (TextView) findViewById(R.id.tv_app_lock);
	}

	private void initCommonNumberQuery() {
		// TODO Auto-generated method stub
		tv_commonnumber_query = (TextView) findViewById(R.id.tv_commonnumber_query);
	}

	private void initSmsBackUp() {
		// TODO Auto-generated method stub
		tv_sms_backup = (TextView) findViewById(R.id.tv_sms_backup);
	}

	private void initPhoneAddress() {
		// TODO Auto-generated method stub
		tv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
		tv_query_phone_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),QueryAddressActivity.class));
			}
		});
	}
}

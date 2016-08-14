package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.utils.ToastUtil;
import com.example.mobilesavety.view.SettingItemView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * �����򵼵ڶ���
 * @author yuxuehai
 *
 */
public class SetupTwoActivity extends BaseSetupActivity{

	private SettingItemView siv_sim_bound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
		// 1,����(��ȡ���еİ�״̬,������ʾ,sp���Ƿ�洢��sim�������к�)
		String sim_number = SpUtils.getString(this, ConstantValue.SIM_NUMBER,
				"");
		// 2,�ж��Ƿ����п���Ϊ""
		if (TextUtils.isEmpty(sim_number)) {
			siv_sim_bound.setCheck(false);
		} else {
			siv_sim_bound.setCheck(true);
		}

		siv_sim_bound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 3,��ȡԭ�е�״̬
				boolean isCheck = siv_sim_bound.isCheck();
				// 4,��ԭ��״̬ȡ��
				siv_sim_bound.setCheck(!isCheck);
				// 5,״̬���ø���ǰ��Ŀ
				if (!isCheck) {
					// 6,�洢(���п���)
					// 6.1��ȡsim�����к�TelephoneManager
					TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					// 6.2��ȡsim�������п���
					String simSerialNumber = telephonyManager
							.getSimSerialNumber();
					System.out.println("Sim: "+simSerialNumber);
					// 6.3�洢
					SpUtils.putString(getApplicationContext(),
							ConstantValue.SIM_NUMBER, simSerialNumber);
				} else {
					// 7,���洢���п��ŵĽڵ�,��sp��ɾ����
					SpUtils.remove(getApplicationContext(),
							ConstantValue.SIM_NUMBER);
				}
			}
		});

	}

	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		String serialNumber = SpUtils.getString(this, ConstantValue.SIM_NUMBER,
				"");
		if (!TextUtils.isEmpty(serialNumber)) {
			// ��ת����һ��ҳ��
			Intent intent = new Intent(getApplicationContext(),
					SetupThreeActivity.class);
			startActivity(intent);
			finish();

			// ����ƽ�ƶ���
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			ToastUtil.show(this, "�����Sim��");
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// ��ת����һ��ҳ��
		Intent intent = new Intent(getApplicationContext(),
				SetupOneActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

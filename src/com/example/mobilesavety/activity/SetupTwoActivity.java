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
 * 设置向导第二部
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
		// 1,回显(读取已有的绑定状态,用作显示,sp中是否存储了sim卡的序列号)
		String sim_number = SpUtils.getString(this, ConstantValue.SIM_NUMBER,
				"");
		// 2,判断是否序列卡号为""
		if (TextUtils.isEmpty(sim_number)) {
			siv_sim_bound.setCheck(false);
		} else {
			siv_sim_bound.setCheck(true);
		}

		siv_sim_bound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 3,获取原有的状态
				boolean isCheck = siv_sim_bound.isCheck();
				// 4,将原有状态取反
				siv_sim_bound.setCheck(!isCheck);
				// 5,状态设置给当前条目
				if (!isCheck) {
					// 6,存储(序列卡号)
					// 6.1获取sim卡序列号TelephoneManager
					TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					// 6.2获取sim卡的序列卡号
					String simSerialNumber = telephonyManager
							.getSimSerialNumber();
					System.out.println("Sim: "+simSerialNumber);
					// 6.3存储
					SpUtils.putString(getApplicationContext(),
							ConstantValue.SIM_NUMBER, simSerialNumber);
				} else {
					// 7,将存储序列卡号的节点,从sp中删除掉
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
			// 跳转到下一个页面
			Intent intent = new Intent(getApplicationContext(),
					SetupThreeActivity.class);
			startActivity(intent);
			finish();

			// 开启平移动画
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			ToastUtil.show(this, "必须绑定Sim卡");
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// 跳转到上一个页面
		Intent intent = new Intent(getApplicationContext(),
				SetupOneActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

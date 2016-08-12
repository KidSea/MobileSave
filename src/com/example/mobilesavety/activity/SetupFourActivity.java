package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SetupFourActivity extends BaseSetupActivity {

	private CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		checkBox = (CheckBox) findViewById(R.id.cb_box);
		// 1.是否选中回显
		boolean open_security = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		// 2,根据状态,修改checkbox后续的文字显示
		checkBox.setChecked(open_security);
		if (open_security) {
			checkBox.setText("安全设置已开启");
		} else {
			checkBox.setText("安全设置已关闭");
		}
		//checkBox.setChecked(!checkBox.isChecked());
		// 3,点击过程中,监听选中状态发生改变过程,
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 4,isChecked点击后的状态,存储点击后状态
				SpUtils.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_SECURITY, isChecked);
				// 5,根据开启关闭状态,去修改显示的文字
				if (isChecked) {
					checkBox.setText("安全设置已开启");
				} else {
					checkBox.setText("安全设置已关闭");
				}
			}
		});
	}


	@Override
	protected void showNextPage() {
		// TODO Auto-generated method stub
		boolean open_security = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			// 跳转到下一个页面
			Intent intent = new Intent(getApplicationContext(),
					SetupOverActivity.class);
			startActivity(intent);
			finish();
			SpUtils.putBoolean(this, ConstantValue.SETUP_OVER, true);
			
			// 开启平移动画
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
			
			
		} else {
			ToastUtil.show(getApplicationContext(), "请开防盗保护");
		}

	}

	@Override
	protected void showPrePage() {
		// TODO Auto-generated method stub
		// 跳转到上一个页面
		Intent intent = new Intent(getApplicationContext(),
				SetupThreeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}

package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.PackageUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Splash����
 * 
 * @author yuxuehai
 * 
 */
public class SplashActivity extends Activity {

	private TextView mTvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initDate();
		initView();
	}

	private void initView() {

		mTvVersion = (TextView) findViewById(R.id.textView);
		//��ȡ���Ĺ���z
		mTvVersion.setText("�汾�ţ�" + PackageUtils.getVersionCode(this));

	}

	private void initDate() {

	}

}

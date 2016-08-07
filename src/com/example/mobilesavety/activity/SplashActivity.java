package com.example.mobilesavety.activity;


import com.example.mobilesavety.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
/**
 * SplashΩÁ√Ê
 * @author yuxuehai
 *
 */
public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
	}
}

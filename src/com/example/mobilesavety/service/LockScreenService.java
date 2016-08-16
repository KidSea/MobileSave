package com.example.mobilesavety.service;



import com.example.mobilesavety.engine.ProcessInfoProvider;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
/**
 * 锁屏清除进程服务
 * @author yuxuehai
 *
 */
public class LockScreenService extends Service {
	private IntentFilter intentFilter;
	private InnerReceiver innerReceiver;
	@Override
	public void onCreate() {
		
		//锁屏action
		intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		innerReceiver = new InnerReceiver();
		registerReceiver(innerReceiver, intentFilter);
		
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
		if(innerReceiver!=null){
			unregisterReceiver(innerReceiver);
		}
		super.onDestroy();
	}
	
	class InnerReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//清除手机正在运行的进程
			ProcessInfoProvider.killAll(context);
		}
	}
}

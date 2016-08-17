package com.example.mobilesavety.receiver;



import com.example.mobilesavety.engine.ProcessInfoProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 杀死进程广播
 * @author yuxuehai
 *
 */
public class KillProcessReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		//杀死进程
		ProcessInfoProvider.killAll(context);
	}
}

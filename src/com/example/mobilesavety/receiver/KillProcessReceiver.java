package com.example.mobilesavety.receiver;



import com.example.mobilesavety.engine.ProcessInfoProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * ɱ�����̹㲥
 * @author yuxuehai
 *
 */
public class KillProcessReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		//ɱ������
		ProcessInfoProvider.killAll(context);
	}
}

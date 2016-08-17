package com.example.mobilesavety.service;

import java.util.Timer;
import java.util.TimerTask;

import com.example.mobilesavety.R;
import com.example.mobilesavety.engine.ProcessInfoProvider;
import com.example.mobilesavety.receiver.MyAppWidgetProvider;
import com.example.mobilesavety.service.UpdateWidgetService.InnerReceiver;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;
/**
 * ����С��������
 * @author yuxuehai
 *
 */
public class UpdateWidgetService extends Service {
	protected static final String tag = "UpdateWidgetService";
	private InnerReceiver mInnerReceiver;
	private Timer mTimer;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// �������������Ϳ����ڴ�������(��ʱ��)
		startTimer();

		// ע�Ὺ��,�����㲥������
		IntentFilter intentFilter = new IntentFilter();
		// ����action
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		// ����action
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

		mInnerReceiver = new InnerReceiver();
		registerReceiver(mInnerReceiver, intentFilter);
	}

	class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				// ������ʱ��������
				startTimer();
			} else {
				// �رն�ʱ��������
				cancelTimerTask();
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelTimerTask() {
		// TODO Auto-generated method stub
		// mTimer��cancel����ȡ����ʱ���񷽷�
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	public void startTimer() {
		// TODO Auto-generated method stub
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// ui��ʱˢ��
				updateAppWidget();
				//Log.i(tag, "5��һ�εĶ�ʱ����������������..........");
			}
		}, 0, 5000);
	}

	protected void updateAppWidget() {
		// TODO Auto-generated method stub
		// 1.��ȡAppWidget����
		AppWidgetManager aWM = AppWidgetManager.getInstance(this);
		// 2.��ȡ����С��������ת���ɵ�view����(��λӦ�õİ���,��ǰӦ���е��ǿ鲼���ļ�)
		RemoteViews remoteViews = new RemoteViews(getPackageName(),
				R.layout.process_widget);
		// 3.������С������view����,�ڲ��ؼ���ֵ
		remoteViews.setTextViewText(R.id.tv_process_count, "��������:"
				+ ProcessInfoProvider.getProcessCount(this));
		// 4.��ʾ�����ڴ��С
		String strAvailSpace = Formatter.formatFileSize(this,
				ProcessInfoProvider.getAvailSpace(this));
		remoteViews.setTextViewText(R.id.tv_process_memory, "�����ڴ�:"
				+ strAvailSpace);

		// �������С����,����Ӧ��
		// 1:���Ǹ��ؼ�����Ӧ����¼�2:���ڵ���ͼ
		Intent intent = new Intent("android.intent.action.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.ll_root, pendingIntent);

		// ͨ��������ͼ���͹㲥,�ڹ㲥��������ɱ������,ƥ�����action
		Intent broadCastintent = new Intent(
				"android.intent.action.KILL_BACKGROUND_PROCESS");
		PendingIntent broadcast = PendingIntent.getBroadcast(this, 0,
				broadCastintent, PendingIntent.FLAG_CANCEL_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.btn_clear, broadcast);

		// �����Ļ���,����С������Ӧ�㲥�����ߵ��ֽ����ļ�
		ComponentName componentName = new ComponentName(this,
				MyAppWidgetProvider.class);
		// ���´���С����
		aWM.updateAppWidget(componentName, remoteViews);
	}

	@Override
	public void onDestroy() {
		if (mInnerReceiver != null) {
			unregisterReceiver(mInnerReceiver);
		}
		// ����onDestroy���رշ���,�رշ���ķ������Ƴ����һ������С������ʱ����,��ʱ����Ҳû��Ҫά��
		cancelTimerTask();
		super.onDestroy();
	}
}
package com.example.mobilesavety.activity;

import com.example.mobilesavety.R;
import com.example.mobilesavety.service.AddressService;
import com.example.mobilesavety.service.BlackNumberService;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.ServiceUtils;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.view.SettingClickView;
import com.example.mobilesavety.view.SettingItemView;







import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * ���ý���,ʵ�����ý��湦��
 * @author yuxuehai
 *
 */
public class SettingActivity extends Activity {

	private SettingClickView scv_toast_style;
	private String[] mToastStyleDes;
	private int mToastStyle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUpdate();
		initAddress();
		initToastStyle();
		initLocation();
		initBlacknumber();
	}
	/**
	 * ����������
	 */
	private void initBlacknumber() {
		// TODO Auto-generated method stub
		final SettingItemView siv_blacknumber = (SettingItemView) findViewById(R.id.siv_blacknumber);
		boolean isRunning = ServiceUtils.isRunning(this, "com.example.mobilesavety.service.BlackNumberService");
		siv_blacknumber.setCheck(isRunning);
		
		siv_blacknumber.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isCheck = siv_blacknumber.isCheck();
				siv_blacknumber.setCheck(!isCheck);
				if(!isCheck){
					//��������
					startService(new Intent(getApplicationContext(), BlackNumberService.class));
				}else{
					//�رշ���
					stopService(new Intent(getApplicationContext(), BlackNumberService.class));
				}
			}
		});
	}
	/**
	 * ���ù��������ѱ�־��λ��
	 */
	private void initLocation() {
		// TODO Auto-generated method stub
		SettingClickView scv_location = (SettingClickView) findViewById(R.id.scv_location);
		scv_location.setTitle("��������ʾ���λ��");
		scv_location.setDes("���ù�������ʾ���λ��");
		scv_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ToastLocationActivity.class));
			}
		});
	}

	/**
	 * ������˾��ʽ
	 */
	private void initToastStyle() {
		 scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
		 //����(��Ʒ)
		 scv_toast_style.setTitle("���ù�������ʾ���");
		 mToastStyleDes = new String[]{"͸��","��ɫ","��ɫ","��ɫ","��ɫ"};
		 
		 mToastStyle = SpUtils.getInt(this, ConstantValue.TOAST_STYLE, 0);
		 
		 //3,ͨ������,��ȡ�ַ��������е�����,��ʾ���������ݿؼ�
		 scv_toast_style.setDes(mToastStyleDes[mToastStyle]);
		 //4,��������¼�,�����Ի���
		 scv_toast_style.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//5,��ʾ��˾��ʽ�ĶԻ���
				showToastStyleDialog();
			}
		});
	}
	protected void showToastStyleDialog() {
		// TODO Auto-generated method stub
		Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("��ѡ���������ʽ");
		//ѡ�񵥸���Ŀ�¼�����
		/*
		 * 1:string���͵�����������ɫ��������
		 * 2:�����Ի����ʱ���ѡ����Ŀ����ֵ
		 * 3:���ĳһ����Ŀ�󴥷��ĵ���¼�
		 * */
		builder.setSingleChoiceItems(mToastStyleDes, mToastStyle, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {//whichѡ�е�����ֵ
				//(1,��¼ѡ�е�����ֵ,2,�رնԻ���,3,��ʾѡ��ɫֵ����)
				SpUtils.putInt(getApplicationContext(), ConstantValue.TOAST_STYLE, which);
				dialog.dismiss();
				scv_toast_style.setDes(mToastStyleDes[which]);
			}
		});
		//������ť
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	/**
	 * �Ƿ���ʾ�绰��������صķ���
	 */
	private void initAddress() {
		// TODO Auto-generated method stub
		final SettingItemView siv_address = (SettingItemView) findViewById(R.id.siv_address);
		//�Է����Ƿ񿪵�״̬����ʾ
		boolean isRunning = ServiceUtils.isRunning(this, "com.example.mobilesavety.service.AddressService");
		siv_address.setCheck(isRunning);
		
		//���������,״̬(�Ƿ����绰���������)���л�����
		siv_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//���ص��ǰ��ѡ��״̬
				boolean isCheck = siv_address.isCheck();
				siv_address.setCheck(!isCheck);
				if(!isCheck){
					//��������,������˾
					startService(new Intent(getApplicationContext(),AddressService.class));
				}else{
					//�رշ���,����Ҫ��ʾ��˾
					stopService(new Intent(getApplicationContext(),AddressService.class));
				}
			}
		});
	}

	/**
	 * �汾���¿���
	 */
	private void initUpdate() {
		// TODO Auto-generated method stub
		final SettingItemView settingItemView = (SettingItemView) findViewById(R.id.siv_update);
		// ��ȡ���еĿ���״̬,������ʾ
		boolean open_update = SpUtils.getBoolean(this,
				ConstantValue.OPEN_UPDATE, false);
		// �Ƿ�ѡ��,������һ�δ洢�Ľ��ȥ������
		settingItemView.setCheck(open_update);

		settingItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ���֮ǰ��ѡ�е�,�������,���δѡ��
				// ���֮ǰ��δѡ�е�,�������,���ѡ��

				// ��ȡ֮ǰ��ѡ��״̬
				boolean isCheck = settingItemView.isCheck();

				// ��ԭ��״̬ȡ��,��ͬ���ߵ���������
				settingItemView.setCheck(!isCheck);
				// ��ȡ�����״̬�洢����Ӧsp��s
				SpUtils.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_UPDATE, !isCheck);

			}
		});
	}

}

package com.example.mobilesavety.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.PackageUtils;
import com.example.mobilesavety.utils.SpUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Splash����
 * 
 * @author yuxuehai
 * 
 */
public class SplashActivity extends Activity {

	
	/**
	 * �����°汾��״̬��
	 */
	protected static final int UPDATE_VERSION = 100;
	/**
	 * ����Ӧ�ó���������״̬��
	 */
	protected static final int ENTER_HOME = 101;
	
	private TextView mTvVersion;
	private int mLocalVersionCode;
	private String mDownLoadUrl;
	private String mDesc;
	private Handler mHandler = new Handler(){
		@Override
		//alt+ctrl+���¼�ͷ,���¿�����ͬ����
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_VERSION:
				//�����Ի���,��ʾ�û�����
				showDialog();
				break;
			case ENTER_HOME:
				//����Ӧ�ó���������,activity��ת����
				loadMainUI();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initDate();
		initView();
		
        //��ʼ�����ݿ�
        initDB();
	}

	private void initDB() {
		// TODO Auto-generated method stub
		//1,���������ݿ�������
		initAddressDB("address.db");
		//2,���ú������ݿ⿽������
		initAddressDB("commonnum.db");
		//3,�����������ݿ�
		initAddressDB("antivirus.db");
	}

	private void initAddressDB(String dbName) {
		// TODO Auto-generated method stub
		//1,��files�ļ����´���ͬ��dbName���ݿ��ļ�����
		File files = getFilesDir();
		File file = new File(files, dbName);
		if(file.exists()){
			return;
		}
		InputStream stream = null;
		FileOutputStream fos = null;
		//2,��������ȡ�������ʲ�Ŀ¼�µ��ļ�
		try {
			stream = getAssets().open(dbName);
			//3,����ȡ������д�뵽ָ���ļ��е��ļ���ȥ
			fos = new FileOutputStream(file);
			//4,ÿ�εĶ�ȡ���ݴ�С
			byte[] bs = new byte[1024];
			int temp = -1;
			while( (temp = stream.read(bs))!=-1){
				fos.write(bs, 0, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(stream!=null && fos!=null){
				try {
					stream.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initView() {

		mTvVersion = (TextView) findViewById(R.id.textView);
		// ��ȡ���Ĺ���z
		mTvVersion.setText("�汾�ţ�" + PackageUtils.getVersionName(this));
		mLocalVersionCode = PackageUtils.getVersionCode(this);

	}

	private void initDate() {
		// �Ȼ�ȡ����
		boolean isCheck = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_UPDATE, false);
		if (isCheck) {
			// ���汾��
			checkVerSion();
		} else {
			//ֱ�ӽ���Ӧ�ó���������
//			enterHome();
			//��Ϣ����
//			mHandler.sendMessageDelayed(msg, 4000);
			//�ڷ�����Ϣ4���ȥ����,ENTER_HOME״̬��ָ�����Ϣ
			mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
		}
	}

	/**
	 * ���汾
	 */
	private void checkVerSion() {
		// TODO Auto-generated method stub
		// ���������URL��ַ������Ϊtomcat�ĵ�ַ
		String url = "http://172.18.107.146:8080/Info.json";
		// ���������ӳ�ʱʱ��
		HttpUtils httpUtils = new HttpUtils(3000);
		/**
		 * ��һ������������ʽget��post �ڶ�������������url��ַ �������������ɹ���ʧ�ܵĻص�
		 */
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			// �ɹ��ص�
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println(responseInfo.result);
				String result = responseInfo.result;
				// �������ݣ�����Json
				processData(result);
			}

			// ʧ�ܻص�
			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				error.printStackTrace();
				// ����ʧ�ܽ���ԭ���汾����ҳ��
				loadMainUI();
			}
		});

	}

	/**
	 * ����Json
	 * 
	 * @param result
	 */
	protected void processData(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(result);

			mDownLoadUrl = obj.getString("downloadurl");
			// ��÷������汾��
			int netVersion = obj.getInt("Version");
			mDesc = obj.getString("desc");

			// �ж�����汾�źͱ��ذ汾�ŶԱȣ��Ƿ�Ϊһ�µİ汾
			// mLocalVersionCodeΪ���ذ汾�ţ�ͨ��PackageUtils.getVersionCode(this)��ȡ
			if (mLocalVersionCode == netVersion) {
				// ֱ�ӽ�����ҳ�棬˵�������°汾
				System.out.println("���°汾����Ҫ����");
				// ������ҳ��
				loadMainUI();
			} else {
				// ��Ҫ����
				System.out.println("�汾��ͬ����Ҫ����");
				// չʾ�����Ի���
				showDialog();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			loadMainUI();
		}

	}

	/**
	 * չʾ�����Ի���
	 */
	private void showDialog() {
		// TODO Auto-generated method stub
		// this��ʾ��ǰ���� �Ի�����activity��һ���֣������activity��
		// �Ի���ֻ������activity���ڣ�û��activity�Ͳ����жԻ������
		// getApplicationContext()��ʾȫ��

		// ��ʼ���Ի���
		AlertDialog.Builder builder = new Builder(this);
		// ���öԻ������ʾ
		builder.setTitle("������ʾ");
		// ���öԻ����������Ϣ
		builder.setMessage(mDesc);
		// �Ի���ȡ������
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				System.out.println("�����£�ֱ�ӽ�����ҳ��");
				loadMainUI();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println("�����£�ֱ�ӽ�����ҳ��");
				loadMainUI();
			}
		});
		builder.setPositiveButton("����", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println("���������");
				// Toast.makeText(getApplicationContext(), "������", 0).show();
				// ����APK��SD����
				downLoadApk(mDownLoadUrl);
				// ���ٶԻ���
				dialog.dismiss();
			}
		});
		builder.show();
	}

	protected void downLoadApk(String url) {
		// TODO Auto-generated method stub
		// ʹ��HttpЭ������
		HttpUtils httpUtils = new HttpUtils();
		/**
		 * ��һ��������ʾURL �ڶ���������ʾ��APK����λ��
		 * 
		 */
		httpUtils.download(url, "/mnt/sdcard/temp.apk",
				new RequestCallBack<File>() {

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						// TODO Auto-generated method stub
						// ��װ�ӷ��������ػ�����APK
						installApk("/mnt/sdcard/temp.apk");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						error.printStackTrace();
					}
				});
	}

	protected void installApk(String string) {
		// TODO Auto-generated method stub
		// ʹ����ͼ����װAPK
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		// �������ݺ�����
		// ��һ������:URL���ڶ�������������
		intent.setDataAndType(Uri.fromFile(new File(string)),
				"application/vnd.android.package-archive");
		// ֱ��������װʱ�����ȡ�����Ῠ��
		// ʹ�����淽ʽ����,������ʧ���ص�onActivityResult()����
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("onActivityResult()������������");
		// ����װ���汻ȡ���󣬷��ص�ǰ���棬������������
		loadMainUI();
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void loadMainUI() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();

	}

}

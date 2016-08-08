package com.example.mobilesavety.activity;

import java.io.File;

import org.json.JSONObject;

import com.example.mobilesavety.R;
import com.example.mobilesavety.utils.PackageUtils;
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

	private TextView mTvVersion;
	private int mLocalVersionCode;
	private String mDownLoadUrl;
	private String mDesc;

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
		// ��ȡ���Ĺ���z
		mTvVersion.setText("�汾�ţ�" + PackageUtils.getVersionName(this));
		mLocalVersionCode = PackageUtils.getVersionCode(this);

	}

	private void initDate() {
		// ���汾��
		checkVerSion();
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
		//ֱ��������װʱ�����ȡ�����Ῠ��
		//ʹ�����淽ʽ����,������ʧ���ص�onActivityResult()����
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("onActivityResult()������������");
		//����װ���汻ȡ���󣬷��ص�ǰ���棬������������
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

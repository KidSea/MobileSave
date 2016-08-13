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
 * Splash界面
 * 
 * @author yuxuehai
 * 
 */
public class SplashActivity extends Activity {

	
	/**
	 * 更新新版本的状态码
	 */
	protected static final int UPDATE_VERSION = 100;
	/**
	 * 进入应用程序主界面状态码
	 */
	protected static final int ENTER_HOME = 101;
	
	private TextView mTvVersion;
	private int mLocalVersionCode;
	private String mDownLoadUrl;
	private String mDesc;
	private Handler mHandler = new Handler(){
		@Override
		//alt+ctrl+向下箭头,向下拷贝相同代码
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_VERSION:
				//弹出对话框,提示用户更新
				showDialog();
				break;
			case ENTER_HOME:
				//进入应用程序主界面,activity跳转过程
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
		
        //初始化数据库
        initDB();
	}

	private void initDB() {
		// TODO Auto-generated method stub
		//1,归属地数据拷贝过程
		initAddressDB("address.db");
		//2,常用号码数据库拷贝过程
		initAddressDB("commonnum.db");
		//3,拷贝病毒数据库
		initAddressDB("antivirus.db");
	}

	private void initAddressDB(String dbName) {
		// TODO Auto-generated method stub
		//1,在files文件夹下创建同名dbName数据库文件过程
		File files = getFilesDir();
		File file = new File(files, dbName);
		if(file.exists()){
			return;
		}
		InputStream stream = null;
		FileOutputStream fos = null;
		//2,输入流读取第三方资产目录下的文件
		try {
			stream = getAssets().open(dbName);
			//3,将读取的内容写入到指定文件夹的文件中去
			fos = new FileOutputStream(file);
			//4,每次的读取内容大小
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
		// 获取包的管理z
		mTvVersion.setText("版本号：" + PackageUtils.getVersionName(this));
		mLocalVersionCode = PackageUtils.getVersionCode(this);

	}

	private void initDate() {
		// 先获取缓存
		boolean isCheck = SpUtils.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_UPDATE, false);
		if (isCheck) {
			// 检查版本号
			checkVerSion();
		} else {
			//直接进入应用程序主界面
//			enterHome();
			//消息机制
//			mHandler.sendMessageDelayed(msg, 4000);
			//在发送消息4秒后去处理,ENTER_HOME状态码指向的消息
			mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
		}
	}

	/**
	 * 检查版本
	 */
	private void checkVerSion() {
		// TODO Auto-generated method stub
		// 请求网络的URL地址，这里为tomcat的地址
		String url = "http://172.18.107.146:8080/Info.json";
		// 参数：链接超时时间
		HttpUtils httpUtils = new HttpUtils(3000);
		/**
		 * 第一个参数：请求方式get或post 第二个参数：请求url地址 第三个参数：成功或失败的回调
		 */
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			// 成功回调
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println(responseInfo.result);
				String result = responseInfo.result;
				// 处理数据，解析Json
				processData(result);
			}

			// 失败回调
			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				error.printStackTrace();
				// 请求失败进入原来版本的主页面
				loadMainUI();
			}
		});

	}

	/**
	 * 解析Json
	 * 
	 * @param result
	 */
	protected void processData(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(result);

			mDownLoadUrl = obj.getString("downloadurl");
			// 获得服务器版本号
			int netVersion = obj.getInt("Version");
			mDesc = obj.getString("desc");

			// 判断网络版本号和本地版本号对比，是否为一致的版本
			// mLocalVersionCode为本地版本号，通过PackageUtils.getVersionCode(this)获取
			if (mLocalVersionCode == netVersion) {
				// 直接进入主页面，说明是最新版本
				System.out.println("最新版本不需要升级");
				// 进入主页面
				loadMainUI();
			} else {
				// 需要升级
				System.out.println("版本不同，需要升级");
				// 展示升级对话框
				showDialog();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			loadMainUI();
		}

	}

	/**
	 * 展示升级对话框
	 */
	private void showDialog() {
		// TODO Auto-generated method stub
		// this表示当前对象 对话框是activity的一部分（特殊的activity）
		// 对话框只能依附activity存在，没有activity就不会有对话框存在
		// getApplicationContext()表示全局

		// 初始化对话框
		AlertDialog.Builder builder = new Builder(this);
		// 设置对话框的提示
		builder.setTitle("升级提示");
		// 设置对话框的描述信息
		builder.setMessage(mDesc);
		// 对话框取消监听
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				System.out.println("不更新，直接进入主页面");
				loadMainUI();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println("不更新，直接进入主页面");
				loadMainUI();
			}
		});
		builder.setPositiveButton("升级", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println("点击更新啦");
				// Toast.makeText(getApplicationContext(), "更新啦", 0).show();
				// 下载APK到SD卡中
				downLoadApk(mDownLoadUrl);
				// 销毁对话框
				dialog.dismiss();
			}
		});
		builder.show();
	}

	protected void downLoadApk(String url) {
		// TODO Auto-generated method stub
		// 使用Http协议下载
		HttpUtils httpUtils = new HttpUtils();
		/**
		 * 第一个参数表示URL 第二个参数表示：APK下载位置
		 * 
		 */
		httpUtils.download(url, "/mnt/sdcard/temp.apk",
				new RequestCallBack<File>() {

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						// TODO Auto-generated method stub
						// 安装从服务器下载回来的APK
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
		// 使用意图来安装APK
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		// 设置数据和类型
		// 第一个参数:URL，第二个参数：类型
		intent.setDataAndType(Uri.fromFile(new File(string)),
				"application/vnd.android.package-archive");
		// 直接启动安装时，点击取消，会卡死
		// 使用下面方式启动,界面消失后会回调onActivityResult()方法
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("onActivityResult()方法被调用了");
		// 当安装界面被取消后，返回当前界面，并进入主界面
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

package com.example.mobilesavety.activity;

import java.util.ArrayList;

import com.example.mobilesavety.R;
import com.example.mobilesavety.domain.AppInfo;
import com.example.mobilesavety.engine.AppInfoProvider;
import com.example.mobilesavety.utils.ToastUtil;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * �������
 * 
 * @author yuxuehai
 * 
 */
public class AppManagerActivity extends Activity implements OnClickListener {

	private TextView tv_memory;
	private TextView tv_sd_memory;
	private ListView lv_app_list;
	private TextView tv_des;

	private ArrayList<AppInfo> mAppInfo;
	private ArrayList<AppInfo> mSystemApp;
	private ArrayList<AppInfo> mCustomerApp;
	private AppInfo mAppInfom;

	private MyListViewAdapter mAdapter;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mAdapter = new MyListViewAdapter();
			lv_app_list.setAdapter(mAdapter);

			if (tv_des != null && mCustomerApp != null) {
				tv_des.setText("�û�Ӧ��(" + mCustomerApp.size() + ")");
			}
		};
	};
	private PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);

		initView();
		initData();
	}

	@Override
	protected void onResume() {
		// ���»�ȡ����
		getData();
		super.onResume();
	}

	private void initData() {
		// TODO Auto-generated method stub
		// 1,��ȡ����(�ڴ�,�������ֻ������ڴ�)���ô�С,����·��
		String path = Environment.getDataDirectory().getAbsolutePath();
		// 2,��ȡsd�����ô�С,sd��·��
		String sdPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		// 3,��ȡ��������·�����ļ��еĿ��ô�С
		String memoryAvailSpace = Formatter.formatFileSize(this,
				getAvailSpace(path));
		String sdMemoryAvailSpace = Formatter.formatFileSize(this,
				getAvailSpace(sdPath));

		tv_memory.setText("���̿��ã�" + memoryAvailSpace);
		tv_sd_memory.setText("SD�����ã�" + sdMemoryAvailSpace);
	}

	// int������ٸ�G
	/**
	 * ����ֵ�����λΪbyte = 8bit,�����Ϊ2147483647 bytes
	 * 
	 * @param path
	 * @return ����ָ��·�����������byte����ֵ
	 */
	private long getAvailSpace(String path) {
		// TODO Auto-generated method stub
		// ��ȡ���ô��̴�С��
		StatFs statFs = new StatFs(path);
		// ��ȡ��������ĸ���
		long count = statFs.getAvailableBlocks();
		// ��ȡ����Ĵ�С
		long size = statFs.getBlockSize();
		// �����С*����������� == ���ÿռ��С
		return count * size;
		// Integer.MAX_VALUE ����int�������ݵ�����С
		// 0x7FFFFFFF
		//
		// 2147483647bytes/1024 = 2096128 KB
		// 2096128KB/1024 = 2047 MB
		// 2047MB = 2G
	}

	private void getData() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				mAppInfo = (ArrayList<AppInfo>) AppInfoProvider
						.getAppInfoList(getApplicationContext());
				mSystemApp = new ArrayList<AppInfo>();
				mCustomerApp = new ArrayList<AppInfo>();
				for (AppInfo appInfo : mAppInfo) {
					if (appInfo.isSystem) {
						// ϵͳӦ��
						mSystemApp.add(appInfo);
					} else {
						// �û�Ӧ��
						mCustomerApp.add(appInfo);
					}
				}
				mHandler.sendEmptyMessage(0);
			};
		}.start();

	}

	private void initView() {
		tv_memory = (TextView) findViewById(R.id.tv_memory);
		tv_sd_memory = (TextView) findViewById(R.id.tv_sd_memory);

		tv_des = (TextView) findViewById(R.id.tv_des);
		lv_app_list = (ListView) findViewById(R.id.lv_app_list);

		lv_app_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 0 || position == mCustomerApp.size() + 1) {
					return;
				} else {
					if (position < mCustomerApp.size() + 1) {
						mAppInfom = mCustomerApp.get(position - 1);
					} else {
						// ����ϵͳӦ�ö�Ӧ��Ŀ�Ķ���
						mAppInfom = mSystemApp.get(position
								- mCustomerApp.size() - 2);
					}
					showPopupWindows(view);
				}

			}
		});

		lv_app_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				// ���������е��÷���
				// AbsListView��view����listView����
				// firstVisibleItem��һ���ɼ���Ŀ����ֵ
				// visibleItemCount��ǰһ����Ļ�Ŀɼ���Ŀ��
				// �ܹ���Ŀ����
				if (mCustomerApp != null && mSystemApp != null) {
					if (firstVisibleItem >= mCustomerApp.size() + 1) {
						// ��������ϵͳ��Ŀ
						tv_des.setText("ϵͳӦ��(" + mSystemApp.size() + ")");
					} else {
						// ���������û�Ӧ����Ŀ
						tv_des.setText("�û�Ӧ��(" + mCustomerApp.size() + ")");
					}
				}

			}

		});
	}

	protected void showPopupWindows(View view) {
		// TODO Auto-generated method stub
		View popupView = View.inflate(this, R.layout.popupwindow_layout, null);
		
		TextView tv_uninstall = (TextView) popupView.findViewById(R.id.tv_uninstall);
		TextView tv_start = (TextView) popupView.findViewById(R.id.tv_start);
		TextView tv_share = (TextView) popupView.findViewById(R.id.tv_share);
		
		tv_uninstall.setOnClickListener(this);
		tv_start.setOnClickListener(this);
		tv_share.setOnClickListener(this);
		
		//͸������(͸��--->��͸��)
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setFillAfter(true);
		
		//���Ŷ���
		ScaleAnimation scaleAnimation = new ScaleAnimation(
				0, 1, 
				0, 1, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);
		alphaAnimation.setFillAfter(true);
		//��������Set
		AnimationSet animationSet = new AnimationSet(true);
		//�����������
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		
		//1,�����������,ָ�����
		
		mPopupWindow = new PopupWindow(popupView, 
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		//2,����һ��͸������(new ColorDrawable())
		mPopupWindow.setBackgroundDrawable(new ColorDrawable());
		//3,ָ������λ��
		mPopupWindow.showAsDropDown(view, 50, -view.getHeight());
		//4,popupViewִ�ж���
		popupView.startAnimation(animationSet);
	}

	class MyListViewAdapter extends BaseAdapter {

		// ��ȡListviewչʾģʽ
		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			if (position == 0 || position == mCustomerApp.size() + 1) {
				// ����0,�����ı���Ŀ��״̬��
				return 0;
			} else {
				// ����1,����ͼƬ+�ı���Ŀ״̬��
				return 1;
			}
		}

		// ��ȡlistviewչʾģʽ����,ԭ��������+1
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount() + 1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mSystemApp.size() + mCustomerApp.size() + 2;// ϵͳ���+���û����+2����2��ʾ2������
		}

		@Override
		public AppInfo getItem(int position) {
			// TODO Auto-generated method stub
			if (position == 0 || position == mCustomerApp.size() + 1) {
				return null;
			} else {
				if (position < mCustomerApp.size() + 1) {
					// �����û�Ӧ����Ŀ
					return mCustomerApp.get(position - 1);
				} else {
					// ����ϵͳӦ����Ŀ
					return mSystemApp.get(position - mCustomerApp.size() - 2);
				}
			}
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			int type = getItemViewType(position);

			if (type == 0) {
				// ��ʾ�������
				ViewTitleHolder titleHolder = null;
				if (convertView == null) {
					convertView = View.inflate(getApplicationContext(),
							R.layout.listview_app_item_title, null);
					titleHolder = new ViewTitleHolder();

					titleHolder.tv_title = (TextView) convertView
							.findViewById(R.id.tv_title);

					convertView.setTag(titleHolder);
				} else {
					titleHolder = (ViewTitleHolder) convertView.getTag();
				}

				if (position == 0) {
					titleHolder.tv_title.setText("�û�Ӧ��(" + mCustomerApp.size()
							+ ")");
				} else {
					titleHolder.tv_title.setText("ϵͳӦ��(" + mSystemApp.size()
							+ ")");
				}

			} else {
				// ��ʾӦ����Ϣ
				// չʾͼƬ+������Ŀ
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(getApplicationContext(),
							R.layout.listview_app_item, null);
					holder = new ViewHolder();
					holder.im_icon = (ImageView) convertView
							.findViewById(R.id.iv_icon);
					holder.tv_app_name = (TextView) convertView
							.findViewById(R.id.tv_name);
					holder.tv_app_mode = (TextView) convertView
							.findViewById(R.id.tv_path);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.im_icon.setBackgroundDrawable(getItem(position).icon);
				holder.tv_app_name.setText(getItem(position).name);
				if (getItem(position).isSdCard) {
					holder.tv_app_mode.setText("sd��Ӧ��");
				} else {
					holder.tv_app_mode.setText("�ֻ�Ӧ��");
				}

			}
			return convertView;
		}

	}

	static class ViewHolder {
		TextView tv_app_name;
		TextView tv_app_mode;
		ImageView im_icon;
	}

	static class ViewTitleHolder {
		TextView tv_title;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_uninstall:
			if(mAppInfom.isSystem){
				ToastUtil.show(getApplicationContext(), "��Ӧ�ò���ж��");
			}else{
				Intent intent = new Intent("android.intent.action.DELETE");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("package:"+mAppInfom.getPackageName()));
				startActivity(intent);
			}
			break;
		case R.id.tv_start:
			//ͨ������ȥ����ָ������Ӧ��
			PackageManager pm = getPackageManager();
			//ͨ��Launch�����ƶ���������ͼ,ȥ����Ӧ��
			Intent launchIntentForPackage = pm.getLaunchIntentForPackage(mAppInfom.getPackageName());
			if(launchIntentForPackage!=null){
				startActivity(launchIntentForPackage);
			}else{
				ToastUtil.show(getApplicationContext(), "��Ӧ�ò��ܱ�����");
			}
			break;
			//����(������(΢��,����,��Ѷ)ƽ̨),�ǻ۱���
			//����-->����:��ͼƬ�ϴ���΢�ŷ�����,΢���ṩ�ӿ�api,�ƹ�
			//�鿴����Ȧ��ʱ��:�ӷ������ϻ�ȡ����(���ϴ���ͼƬ)
		case R.id.tv_share:
			//ͨ������Ӧ��,���ⷢ�Ͷ���
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT,"����һ��Ӧ��,Ӧ������Ϊ"+mAppInfom.getName());
			intent.setType("text/plain");
			startActivity(intent);
			break;
		}
		
		//����˴������ʧ����
		if(mPopupWindow!=null){
			mPopupWindow.dismiss();
		}
		
	}
}

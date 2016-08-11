package com.example.mobilesavety.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.mobilesavety.R;
import com.example.mobilesavety.R.layout;
import com.example.mobilesavety.domain.MainInfo;
import com.example.mobilesavety.utils.ConstantValue;
import com.example.mobilesavety.utils.Md5Util;
import com.example.mobilesavety.utils.SpUtils;
import com.example.mobilesavety.utils.ToastUtil;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnItemClickListener {

	private GridView mGridView;
	// 存放信息集合
	private ArrayList<MainInfo> infos;

	private String[] names = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };
	private int[] icons = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app_selector, R.drawable.taskmanager,
			R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
			R.drawable.atools, R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		initData();
	}

	// 初始化布局
	private void initView() {
		// 获取九宫格
		mGridView = (GridView) findViewById(R.id.gv_home);

		mGridView.setOnItemClickListener(this);

	}

	// 初始化数据，将九个条目的信息都放在集合里
	private void initData() {
		infos = new ArrayList<MainInfo>();

		for (int i = 0; i < icons.length; i++) {
			MainInfo mainInfo = new MainInfo();
			mainInfo.icon = icons[i];
			mainInfo.title = names[i];
			infos.add(mainInfo);
		}

		// 给宫格设置适配器
		mGridView.setAdapter(new MyAdapter());
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public MainInfo getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(HomeActivity.this,
						R.layout.item_main, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.iv_homeitem_icon);
				holder.textView = (TextView) convertView
						.findViewById(R.id.tv_homeitem_name);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 获取指定位置的Item信息
			MainInfo info = infos.get(position);

			// 设置图标
			holder.imageView.setImageResource(info.icon);
			// 设置标题
			holder.textView.setText(info.title);

			return convertView;
		}

	}

	static public class ViewHolder {

		private ImageView imageView;
		private TextView textView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			// 展示对话框
			showDialog();
			break;
		case 8:
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);

			break;
		default:
			break;
		}

	}

	private void showDialog() {
		// TODO Auto-generated method stub
		// 判断本地是否有存储密码(sp 字符串)
		String psd = SpUtils.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if (TextUtils.isEmpty(psd)) {
			// 1,初始设置密码对话框
			showSetPsdDialog();
		} else {
			// 2,确认密码对话框
			showConfirmPsdDialog();
		}
	}

	/**
	 * 确认密码对话框
	 */
	private void showConfirmPsdDialog() {
		// TODO Auto-generated method stub
		//因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
		//view是由自己编写的xml转换成的view对象xml----->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		
		final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
		//让对话框显示一个自己定义的对话框界面效果
//		dialog.setView(view);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
				
				String confirmPsd = et_confirm_psd.getText().toString();
				
				if(!TextUtils.isEmpty(confirmPsd)){
					//将存储在sp中32位的密码,获取出来,然后将输入的密码同样进行md5,然后与sp中存储密码比对
					String psd = SpUtils.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
					
					if(psd.equals(Md5Util.encoder(confirmPsd))){
						//进入应用手机防盗模块,开启一个新的activity
						//ToastUtil.show(getApplicationContext(),"密码验证成功");
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						//跳转到新的界面以后需要去隐藏对话框
						dialog.dismiss();
					}else{
						ToastUtil.show(getApplicationContext(),"确认密码错误");
					}
				}else{
					//提示用户密码输入有为空的情况
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	
	}

	/**
	 * 设置密码对话框
	 */
	private void showSetPsdDialog() {
		// TODO Auto-generated method stub
		// 因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
		// view是由自己编写的xml转换成的view对象xml----->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		final View view = View.inflate(this, R.layout.dialog_set_psd, null);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

		// 设置确认监听事件
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText et_set_psd = (EditText) view
						.findViewById(R.id.et_set_psd);
				EditText et_confirm_psd = (EditText) view
						.findViewById(R.id.et_confirm_psd);

				String psd = et_set_psd.getText().toString();
				String confirmPsd = et_confirm_psd.getText().toString();

				if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)) {
					if (psd.equals(confirmPsd)) {
						//密码一致，进入防盗引导页
						//ToastUtil.show(getApplicationContext(), "进入防盗引导页");
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						//跳转到新的界面以后需要去隐藏对话框
						dialog.dismiss();
						SpUtils.putString(getApplicationContext(), 
								ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(confirmPsd));
					}else{
						//输入的密码不一致，提示重输
						ToastUtil.show(getApplicationContext(), "密码不一致，请重输");
					}
				} else {
					// 提示用户密码输入有为空的情况
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}
			}
		});
		// 设置取消监听事件
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}
}

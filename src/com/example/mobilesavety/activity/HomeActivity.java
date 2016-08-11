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
	// �����Ϣ����
	private ArrayList<MainInfo> infos;

	private String[] names = { "�ֻ�����", "ͨѶ��ʿ", "����ܼ�", "���̹���", "����ͳ��", "�ֻ�ɱ��",
			"��������", "�߼�����", "��������" };
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

	// ��ʼ������
	private void initView() {
		// ��ȡ�Ź���
		mGridView = (GridView) findViewById(R.id.gv_home);

		mGridView.setOnItemClickListener(this);

	}

	// ��ʼ�����ݣ����Ÿ���Ŀ����Ϣ�����ڼ�����
	private void initData() {
		infos = new ArrayList<MainInfo>();

		for (int i = 0; i < icons.length; i++) {
			MainInfo mainInfo = new MainInfo();
			mainInfo.icon = icons[i];
			mainInfo.title = names[i];
			infos.add(mainInfo);
		}

		// ����������������
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

			// ��ȡָ��λ�õ�Item��Ϣ
			MainInfo info = infos.get(position);

			// ����ͼ��
			holder.imageView.setImageResource(info.icon);
			// ���ñ���
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
			// չʾ�Ի���
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
		// �жϱ����Ƿ��д洢����(sp �ַ���)
		String psd = SpUtils.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if (TextUtils.isEmpty(psd)) {
			// 1,��ʼ��������Ի���
			showSetPsdDialog();
		} else {
			// 2,ȷ������Ի���
			showConfirmPsdDialog();
		}
	}

	/**
	 * ȷ������Ի���
	 */
	private void showConfirmPsdDialog() {
		// TODO Auto-generated method stub
		//��Ϊ��Ҫȥ�Լ�����Ի����չʾ��ʽ,������Ҫ����dialog.setView(view);
		//view�����Լ���д��xmlת���ɵ�view����xml----->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		
		final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
		//�öԻ�����ʾһ���Լ�����ĶԻ������Ч��
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
					//���洢��sp��32λ������,��ȡ����,Ȼ�����������ͬ������md5,Ȼ����sp�д洢����ȶ�
					String psd = SpUtils.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
					
					if(psd.equals(Md5Util.encoder(confirmPsd))){
						//����Ӧ���ֻ�����ģ��,����һ���µ�activity
						//ToastUtil.show(getApplicationContext(),"������֤�ɹ�");
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						//��ת���µĽ����Ժ���Ҫȥ���ضԻ���
						dialog.dismiss();
					}else{
						ToastUtil.show(getApplicationContext(),"ȷ���������");
					}
				}else{
					//��ʾ�û�����������Ϊ�յ����
					ToastUtil.show(getApplicationContext(), "����������");
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
	 * ��������Ի���
	 */
	private void showSetPsdDialog() {
		// TODO Auto-generated method stub
		// ��Ϊ��Ҫȥ�Լ�����Ի����չʾ��ʽ,������Ҫ����dialog.setView(view);
		// view�����Լ���д��xmlת���ɵ�view����xml----->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		final View view = View.inflate(this, R.layout.dialog_set_psd, null);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

		// ����ȷ�ϼ����¼�
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
						//����һ�£������������ҳ
						//ToastUtil.show(getApplicationContext(), "�����������ҳ");
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						//��ת���µĽ����Ժ���Ҫȥ���ضԻ���
						dialog.dismiss();
						SpUtils.putString(getApplicationContext(), 
								ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(confirmPsd));
					}else{
						//��������벻һ�£���ʾ����
						ToastUtil.show(getApplicationContext(), "���벻һ�£�������");
					}
				} else {
					// ��ʾ�û�����������Ϊ�յ����
					ToastUtil.show(getApplicationContext(), "����������");
				}
			}
		});
		// ����ȡ�������¼�
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}
}

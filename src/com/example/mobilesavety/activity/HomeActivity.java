package com.example.mobilesavety.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.mobilesavety.R;
import com.example.mobilesavety.R.layout;
import com.example.mobilesavety.domain.MainInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("���");
			builder.setMessage("XXXX");
			builder.setNegativeButton("ȡ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.setPositiveButton("�_��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			builder.show();
			break;

		default:
			break;
		}

	}
}

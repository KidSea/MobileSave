package com.example.mobilesavety.activity;

import java.util.List;

import com.example.mobilesavety.R;
import com.example.mobilesavety.activity.ContactListActivity.MyListViewAdapter;
import com.example.mobilesavety.dao.BlackNumberDao;
import com.example.mobilesavety.domain.BlackNumberInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class BlackNumberActivity extends Activity {
	private Button bt_add;
	private ListView lv_blacknumber;
	private BlackNumberDao mDao;
	private int mCount;
	private boolean mIsLoad;
	private int mode;
	private List<BlackNumberInfo> mBlackNumberList;
	private MyListViewAdapter mAdapter;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 4,告知listView可以去设置数据适配器
			if (mAdapter == null) {
				mAdapter = new MyListViewAdapter();
				lv_blacknumber.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacknumber);

		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		// 获取数据库中所有电话号码
		new Thread() {

			public void run() {
				// 1,获取操作黑名单数据库的对象
				mDao = BlackNumberDao.getInstance(getApplicationContext());
				// 2,查询部分数据
				mBlackNumberList = mDao.find(0);
				mCount = mDao.getCount();

				// 3,通过消息机制告知主线程可以去使用包含数据的集合
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	private void initView() {
		bt_add = (Button) findViewById(R.id.bt_add);
		lv_blacknumber = (ListView) findViewById(R.id.lv_blacknumber);

		bt_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});

		// 监听其滚动状态
		lv_blacknumber.setOnScrollListener(new OnScrollListener() {

			// 滚动过程中,状态发生改变调用方法()
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// OnScrollListener.SCROLL_STATE_FLING 飞速滚动
				// OnScrollListener.SCROLL_STATE_IDLE 空闲状态
				// OnScrollListener.SCROLL_STATE_TOUCH_SCROLL 拿手触摸着去滚动状态

				if (mBlackNumberList != null) {
					// 条件一:滚动到停止状态
					// 条件二:最后一个条目可见(最后一个条目的索引值>=数据适配器中集合的总条目个数-1)
					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
							&& lv_blacknumber.getLastVisiblePosition() >= mBlackNumberList
									.size() - 1 && !mIsLoad) {
						/*
						 * mIsLoad防止重复加载的变量
						 * 如果当前正在加载mIsLoad就会为true,本次加载完毕后,再将mIsLoad改为false
						 * 如果下一次加载需要去做执行的时候
						 * ,会判断上诉mIsLoad变量,是否为false,如果为true,就需要等待上一次加载完成,将其值
						 * 改为false后再去加载
						 */

						// 如果条目总数大于集合大小的时,才可以去继续加载更多
						if (mCount > mBlackNumberList.size()) {
							// 加载下一页数据
							new Thread() {
								public void run() {
									// 1,获取操作黑名单数据库的对象
									mDao = BlackNumberDao
											.getInstance(getApplicationContext());
									// 2,查询部分数据
									List<BlackNumberInfo> moreData = mDao
											.find(mBlackNumberList.size());
									// 3,添加下一页数据的过程
									mBlackNumberList.addAll(moreData);
									// 4,通知数据适配器刷新
									mHandler.sendEmptyMessage(0);
								}
							}.start();
						}
					}
				}

			}

			// 滚动过程中调用方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	class MyListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mBlackNumberList.size();
		}

		@Override
		public BlackNumberInfo getItem(int position) {
			// TODO Auto-generated method stub
			return mBlackNumberList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.listview_blacknumber_item, null);

				holder = new ViewHolder();

				holder.tv_phone = (TextView) convertView
						.findViewById(R.id.tv_phone);
				holder.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_mode);
				holder.im_delete = (ImageView) convertView
						.findViewById(R.id.iv_delete);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.im_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 1,数据库删除
					mDao.delete(mBlackNumberList.get(position).phone);
					// 2,集合中的删除
					mBlackNumberList.remove(position);
					// 3,通知数据适配器刷新
					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();
					}
				}
			});

			holder.tv_phone.setText(mBlackNumberList.get(position).phone);
			int mode = Integer.parseInt(mBlackNumberList.get(position).mode);
			switch (mode) {
			case 1:
				holder.tv_mode.setText("拦截短信");
				break;
			case 2:
				holder.tv_mode.setText("拦截电话");
				break;
			case 3:
				holder.tv_mode.setText("拦截所有");
				break;
			}

			return convertView;
		}

	}

	// 使用viewHolder容器
	static class ViewHolder {
		private TextView tv_phone;
		private TextView tv_mode;
		private ImageView im_delete;
	}

	protected void showDialog() {
		// TODO Auto-generated method stub
		Builder builder = new AlertDialog.Builder(this);

		final AlertDialog dialog = builder.create();
		View view = View.inflate(getApplicationContext(),
				R.layout.dialog_add_blacknumber, null);
		dialog.setView(view, 0, 0, 0, 0);

		final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
		RadioGroup rg_group = (RadioGroup) view.findViewById(R.id.rg_group);

		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

		// 监听其选中条目的切换过程
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_sms:
					// 拦截短信
					mode = 1;
					break;
				case R.id.rb_phone:
					// 拦截电话
					mode = 2;
					break;
				case R.id.rb_all:
					// 拦截所有
					mode = 3;
					break;
				}
			}
		});

		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 1,获取输入框中的电话号码
				String phone = et_phone.getText().toString();
				if (!TextUtils.isEmpty(phone)) {
					// 2,数据库插入当前输入的拦截电话号码
					mDao.insert(phone, mode + "");
					// 3,让数据库和集合保持同步(1.数据库中数据重新读一遍,2.手动向集合中添加一个对象(插入数据构建的对象))
					BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
					blackNumberInfo.phone = phone;
					blackNumberInfo.mode = mode + "";
					// 4,将对象插入到集合的最顶部
					mBlackNumberList.add(0, blackNumberInfo);
					// 5,通知数据适配器刷新(数据适配器中的数据有改变了)
					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();
					}
					// 6,隐藏对话框
					dialog.dismiss();
				}
			}
		});

		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}
}

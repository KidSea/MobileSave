package com.example.mobilesavety.activity;

import java.util.List;

import com.example.mobilesavety.R;
import com.example.mobilesavety.activity.CommonNumberQueryActivity.MyAdapter;
import com.example.mobilesavety.engine.CommonnumDao;
import com.example.mobilesavety.engine.CommonnumDao.Child;
import com.example.mobilesavety.engine.CommonnumDao.Group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
/**
 * 常用号码界面
 * @author yuxuehai
 *
 */
public class CommonNumberQueryActivity extends Activity {

	private MyAdapter mAdapter;
	private List<Group> mGroup;
	private ExpandableListView elv_common_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_number);
		initView();
		initData();
	}

	/**
	 * 给可扩展ListView准备数据,并且填充
	 */
	private void initData() {
		// TODO Auto-generated method stub
		CommonnumDao commonnumDao = new CommonnumDao();
		mGroup = commonnumDao.getGroup();

		mAdapter = new MyAdapter();
		elv_common_number.setAdapter(mAdapter);
		// 给可扩展listview注册点击事件
		elv_common_number.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				startCall(mAdapter.getChild(groupPosition, childPosition).number);
				return false;
			}
		});
	}

	private void initView() {
		elv_common_number = (ExpandableListView) findViewById(R.id.elv_common_number);

	}

	protected void startCall(String number) {
		// 开启系统的打电话界面
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + number));
		startActivity(intent);
	}

	class MyAdapter extends BaseExpandableListAdapter {
		//父类数量
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return mGroup.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return mGroup.get(groupPosition).childList.size();
		}

		@Override
		public Group getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return  mGroup.get(groupPosition);
		}

		@Override
		public Child getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return mGroup.get(groupPosition).childList.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}
		//获取父类控件
		//dip = dp
		//dpi == ppi	像素密度(每一个英寸上分布的像素点的个数)
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(getApplicationContext());
			textView.setText("			"+getGroup(groupPosition).name);
			textView.setTextColor(Color.RED);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			return textView;
		}
		//获取子类的空间
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(getApplicationContext(), R.layout.elv_child_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
			
			tv_name.setText(getChild(groupPosition, childPosition).name);
			tv_number.setText(getChild(groupPosition, childPosition).number);
			
			return view;
		}
		//孩子节点是否响应事件
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}
}

package com.example.mobilesavety.view;

import com.example.mobilesavety.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * ���õ����Ŀ�Զ��岼��
 * @author yuxuehai
 *
 */
public class SettingClickView extends RelativeLayout {
	private TextView tv_des;
	private TextView tv_title;

	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		//xml--->view	�����ý����һ����Ŀת����view����,ֱ����ӵ��˵�ǰSettingItemView��Ӧ��view��
		View.inflate(context, R.layout.setting_click_view, this);
		
		//�Զ�����Ͽؼ��еı�������
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public SettingClickView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param title	���ñ�������
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	/**
	 * @param des	������������
	 */
	public void setDes(String des){
		tv_des.setText(des);
	}
}

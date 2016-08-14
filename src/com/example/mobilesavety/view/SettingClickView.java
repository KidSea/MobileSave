package com.example.mobilesavety.view;

import com.example.mobilesavety.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * 设置点击条目自定义布局
 * @author yuxuehai
 *
 */
public class SettingClickView extends RelativeLayout {
	private TextView tv_des;
	private TextView tv_title;

	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		//xml--->view	将设置界面的一个条目转换成view对象,直接添加到了当前SettingItemView对应的view中
		View.inflate(context, R.layout.setting_click_view, this);
		
		//自定义组合控件中的标题描述
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
	 * @param title	设置标题内容
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	/**
	 * @param des	设置描述内容
	 */
	public void setDes(String des){
		tv_des.setText(des);
	}
}

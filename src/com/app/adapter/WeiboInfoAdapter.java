package com.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.model.WeiboInfo;
import com.app.weibo.R;

public class WeiboInfoAdapter extends ArrayAdapter<WeiboInfo> {

	/**
	 * 为提高效率，缓存数据准备的一个自定义类 对应一条微博数据
	 */
	class ContentHolder {
		public ImageView content_image; // 对应微博显示的图片
		public ImageView content_icon; // 对应发微博人的头像
		public TextView content_user; // 对应发微博人的名称
		public TextView content_time; // 对应发微博的时间
		public TextView content_text; // 对应发微博的内容
	}

	private int resourceId;

	public WeiboInfoAdapter(Context context, int id,
			ArrayList<WeiboInfo> weiboInfoList) {
		super(context, id, weiboInfoList);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		WeiboInfo weiboInfo = getItem(position);
		// 记载微博的每条需要显示在什么布局上的布局对象
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		// 创建一个层次对应组件的类
		// 将R.layout.home_item 对应的组件和ContentHolder对象进行关联，提高效率
		ImageView content_icon = (ImageView) view
				.findViewById(R.id.content_head);
		TextView content_time = (TextView) view.findViewById(R.id.content_time);
		TextView content_user = (TextView) view.findViewById(R.id.content_user);
		ImageView content_image = (ImageView) view
				.findViewById(R.id.content_image);
		TextView content_text = (TextView) view.findViewById(R.id.content_text);

		content_icon.setImageResource(weiboInfo.getIconId());
		content_time.setText(weiboInfo.getTime());
		content_user.setText(weiboInfo.getUser());
		content_image.setImageResource(weiboInfo.getImageId());
		content_text.setText(weiboInfo.getText());

		return view;
	}

}

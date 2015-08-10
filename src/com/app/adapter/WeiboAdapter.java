package com.app.adapter;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import libcore.io.DiskLruCache;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.model.Weibo;
import com.app.weibo.R;

/**
 * GridView的适配器，负责异步从网络上下载图片展示在照片墙上。
 * 
 * @author guolin
 */
public class WeiboAdapter extends ArrayAdapter<Weibo> {

//	/**
//	 * 记录每个子项的高度。
//	 */
//	private int mItemHeight = 0;

	public WeiboAdapter(Context context, int textViewResourceId,List<Weibo> weiboList, ListView listView) {
		
		super(context, textViewResourceId,weiboList);
		
		AdapterUtils.listView = listView;
		
		AdapterUtils.taskCollection = new HashSet<BitmapWorkerTask>();
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// 设置图片缓存大小为程序最大可用内存的1/8
		AdapterUtils.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
		try {
			// 获取图片缓存路径
			File cacheDir = AdapterUtils.getDiskCacheDir(context, "thumb");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			// 创建DiskLruCache实例，初始化缓存数据
			AdapterUtils.mDiskLruCache = DiskLruCache.open(cacheDir,
					AdapterUtils.getAppVersion(context), 1, 10 * 1024 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Weibo weibo  = getItem(position);
		String url = weibo.getHead();
		String text = weibo.getText();
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.activity_wbstatus, null);
		} else {
			view = convertView;
		}
		TextView textView = (TextView)view.findViewById(R.id.content_text);
		textView.setText(text);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.content_head);
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
		imageView.setTag(url);
		imageView.setImageResource(R.drawable.ic_launcher);
		AdapterUtils.loadBitmaps(imageView, url);
		return view;
	}

//	/**
//	 * 设置item子项的高度。
//	 */
//	public void setItemHeight(int height) {
//		if (height == mItemHeight) {
//			return;
//		}
//		mItemHeight = height;
//		notifyDataSetChanged();
//	}
	
	/**
	 * 将缓存记录同步到journal文件中。
	 */
	public void fluchCache() {
		if (AdapterUtils.mDiskLruCache != null) {
			try {
				AdapterUtils.mDiskLruCache.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 取消所有正在下载或等待下载的任务。
	 */
	public void cancelAllTasks() {
		if (AdapterUtils.taskCollection != null) {
			for (BitmapWorkerTask task : AdapterUtils.taskCollection) {
				task.cancel(false);
			}
		}
	}
}
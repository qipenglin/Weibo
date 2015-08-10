package com.app.adapter;
//package com.app.adapter;
//
//import java.io.File;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.Volley;
//import com.app.model.Weibo;
//import com.app.weibo.R;
//import com.app.weibo.R.drawable;
//
//public class WeiboAdapter extends BaseAdapter {
//
//	protected static final int SUCCESS_GET_IMAGE = 0;
//	private Context context;
//	private List<Weibo> weiboList;
//	private File cache;
//	private LayoutInflater mInflater;
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return weiboList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return weiboList.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	public WeiboAdapter(Context context, List<Weibo> weiboList) {
//		this.context = context;
//		this.weiboList = weiboList;
//
//		mInflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		View view = null;
//		if (convertView != null) {
//			view = convertView;
//		} else {
//			view = mInflater.inflate(R.layout.weibo_item, null);
//		}
//
//		ImageView content_head = (ImageView) view
//				.findViewById(R.id.content_head);
//		TextView content_text = (TextView) view.findViewById(R.id.content_text);
//
//		Weibo weibo = weiboList.get(position);
//
//		// content_head.setImageBitmap(weibo.getHead());
//		content_text.setText(weibo.getText());
//		asyncloadImage(content_head, weibo.getHead());
//
//		return view;
//	}
//
//	private void asyncloadImage(ImageView content_head, String path) {
//		AsyncImageTask task = new AsyncImageTask(content_head);
//		task.execute(path);
//	}
//
//	private final class AsyncImageTask extends AsyncTask<String, Integer, Bitmap> {
//
//		private ImageView content_head;
//
//		public AsyncImageTask(ImageView content_head) {
//			this.content_head = content_head;
//		}
//
//		// 后台运行的子线程子线程
//		@Override
//		protected Bitmap doInBackground(String... params) {
//			Bitmap result;
//			RequestQueue mQueue = Volley.newRequestQueue(context);
//			ImageRequest imageRequest = new ImageRequest(params[0],
//					new Response.Listener<Bitmap>() {
//						@Override
//						public void onResponse(Bitmap response) {
//							result = response;
//							
//						}
//					}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError error) {
//						}
//					});
//			mQueue.add(imageRequest);
//
//			return result;
//		}
//
//		// 这个放在在ui线程中执行
//		@Override
//		protected void onPostExecute( Bitmap result) {
//			super.onPostExecute(result);
//			content_head.setImageBitmap(result);
//		}
//	}
//}

package com.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.weibo.R;

public class SendFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View sendLayout = inflater.inflate(R.layout.send_layout, container,
				false);
		return sendLayout;
	}
}

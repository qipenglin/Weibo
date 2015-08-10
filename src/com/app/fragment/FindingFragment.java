package com.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.weibo.R;

public class FindingFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View findingLayout = inflater.inflate(R.layout.finding_layout,
				container, false);
		return findingLayout;
	}

}

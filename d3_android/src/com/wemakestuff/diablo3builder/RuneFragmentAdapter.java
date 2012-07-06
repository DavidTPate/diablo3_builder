package com.wemakestuff.diablo3builder;

import java.util.UUID;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View.OnClickListener;

class RuneFragmentAdapter extends FragmentPagerAdapter {
	private String[] runeTypes;
	private Context context;
	private int mCount = 0;
	private String skillName;
	private String selectedClass;
	private int maxLevel;
	private OnClickListener listener;
	private UUID skillUUID;
	int GET_SKILL = -1;

	public RuneFragmentAdapter(FragmentManager fm, Context context,
			String skillName, UUID skillUUID, String selectedClass,
			int maxLevel) {
		super(fm);
		this.context = context;
		this.skillName = skillName;
		this.selectedClass = selectedClass;
		this.maxLevel = maxLevel;
		this.runeTypes = new String[] { this.skillName };
		this.skillUUID = skillUUID;
		mCount = runeTypes.length;
	}

	@Override
	public Fragment getItem(int position) {
		String s = runeTypes[position % runeTypes.length];
		RuneListFragment runeList = RuneListFragment.newInstance(s, context, s,
				skillUUID, selectedClass, maxLevel);
		runeList.setOnListItemClickListener(listener);
		return runeList;
	}

	@Override
	public int getItemPosition(Object runeType) {
		for (int x = 0; x <= runeTypes.length - 1; x++) {
			if (runeTypes[x].equals((String) runeType)) {
				return x;
			}
		}
		return -1;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

	@Override
	public String getPageTitle(int position) {
		return runeTypes[position % runeTypes.length];
	}

	public void setOnListItemClickListener(OnClickListener listener) {
		this.listener = listener;
	}
}
package com.pate.diablo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;

class SkillFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	protected static final String[] CONTENT = new String[] { "Primary", "Secondary", "Defensive", "Might", "Tactics", "Rage" };
	
	private int mCount = CONTENT.length;

	public SkillFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return SkillListFragment.newInstance(CONTENT[position % CONTENT.length]);
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
    public String getTitle(int position) {
        return SkillFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}
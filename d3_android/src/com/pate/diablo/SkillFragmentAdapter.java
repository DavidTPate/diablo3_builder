package com.pate.diablo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;

class SkillFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	protected static final String[] CONTENT = new String[] { "Primary", "Secondary", "Defensive", "Might", "Tactics", "Rage" };
	Context context;
	private int mCount = CONTENT.length;

	public SkillFragmentAdapter(FragmentManager fm, Context context) {
	    super(fm);
	    this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		return SkillListFragment.newInstance(CONTENT[position % CONTENT.length], context);
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
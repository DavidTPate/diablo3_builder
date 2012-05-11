package com.pate.diablo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pate.diablo.sectionlist.EmptyItem.SkillType;
import com.viewpagerindicator.TitleProvider;

class SkillFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	protected static final String[] CONTENT = new String[] { "Primary", "Secondary", "Defensive", "Might", "Tactics", "Rage" };
	private Context context;
	private int mCount = CONTENT.length;
	private SkillType skillType;
	private String selectedClass;
	private int requiredLevel;

	public SkillFragmentAdapter(FragmentManager fm, Context context, SkillType skillType, String selectedClass, int requiredLevel) {
	    super(fm);
	    this.context = context;
	    this.skillType = skillType;
	    this.selectedClass = selectedClass;
	    this.requiredLevel = requiredLevel;
	}

	@Override
	public Fragment getItem(int position) {
	    
		return SkillListFragment.newInstance(CONTENT[position % CONTENT.length], context, SkillType.valueOf(CONTENT[position % CONTENT.length]), selectedClass, requiredLevel);
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
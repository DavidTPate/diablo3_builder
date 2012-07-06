package com.wemakestuff.diablo3builder;

import java.util.List;

import android.content.Context;
import android.os.ParcelUuid;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wemakestuff.diablo3builder.model.D3Application;

class SkillFragmentAdapter extends FragmentPagerAdapter {
	private String[] skillTypes;
	private Context context;
	private int mCount = 0;
	private String skillType;
	private String selectedClass;
	private int maxLevel = 60;
	private List<ParcelUuid> excludeSkills;
	int GET_SKILL = -1;

	public SkillFragmentAdapter(FragmentManager fm, Context context, String skillType, String selectedClass, int maxLevel, List<ParcelUuid> excludeSkills) {
	    super(fm);
	    this.context = context;
	    this.skillType = skillType;
	    this.selectedClass = selectedClass;
	    this.maxLevel = maxLevel;
	    this.skillTypes = (skillType.equals("Passive") ? new String[] {"Passive"} : D3Application.getInstance().getClassAttributesByName(selectedClass).getSkillTypes());
	    this.excludeSkills = excludeSkills;
	    this.mCount = skillTypes.length;
	}

	@Override
	public Fragment getItem(int position) {
	    String s = skillTypes[position % skillTypes.length];
	    SkillListFragment skillList = SkillListFragment.newInstance(s, context, s, selectedClass, maxLevel, excludeSkills);
		return skillList;
	}
	
	@Override
	public int getItemPosition(Object skillType)
	{
		for (int x = 0; x <= skillTypes.length - 1; x++)
		{
			if (skillTypes[x].equals((String)skillType))
			{
				return x;
			}
		}
		return -1;
	}

	@Override
	public int getCount() {
		return mCount;
	}
	
    @Override
    public String getPageTitle(int position) {
        return skillTypes[position % skillTypes.length];
    }
    
}
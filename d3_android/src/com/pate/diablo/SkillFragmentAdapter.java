package com.pate.diablo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.pate.diablo.model.D3Application;
import com.viewpagerindicator.TitleProvider;

class SkillFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	private String[] skillTypes;
	private Context context;
	private int mCount = 0;
	private String skillType;
	private String selectedClass;
	private int requiredLevel;
	private OnClickListener listener;
	int GET_SKILL = -1;

	public SkillFragmentAdapter(FragmentManager fm, Context context, String skillType, String selectedClass, int requiredLevel) {
	    super(fm);
	    this.context = context;
	    this.skillType = skillType;
	    this.selectedClass = selectedClass;
	    this.requiredLevel = requiredLevel;
	    skillTypes = (skillType.equals("Passive") ? new String[] {"Passive"} : D3Application.dataModel.getClassAttributesByName(selectedClass).getSkillTypes());
	    mCount = skillTypes.length;
	}

	@Override
	public Fragment getItem(int position) {
	    String s = skillTypes[position % skillTypes.length];
	    SkillListFragment skillList = SkillListFragment.newInstance(s, context, s, selectedClass, requiredLevel);
	    skillList.setOnListItemClickListener(listener);
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
	
	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

    @Override
    public String getTitle(int position) {
        return skillTypes[position % skillTypes.length];
    }
    
    public void setOnListItemClickListener(OnClickListener listener)
    {
    	this.listener = listener;
    }
}
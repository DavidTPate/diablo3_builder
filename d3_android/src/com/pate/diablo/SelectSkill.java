package com.pate.diablo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pate.diablo.sectionlist.EmptyItem.SkillType;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class SelectSkill extends SherlockFragmentActivity
{
    SkillFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_skill);
        
        Bundle b = getIntent().getExtras();
        
        SkillType skillType = null;
        String selectedClass = null;
        int requiredLevel = 0;
        if (b.containsKey("SkillType"))
        {
            skillType = SkillType.valueOf(b.getString("SkillType"));
        }
        
        if (b.containsKey("SelectedClass"))
        {
            selectedClass = b.getString("SelectedClass");
        }
        
        if (b.containsKey("RequiredLevel"))
        {
            requiredLevel = b.getInt("RequiredLevel");
        }

        mAdapter = new SkillFragmentAdapter(getSupportFragmentManager(), SelectSkill.this, skillType, selectedClass, requiredLevel);
        
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.select_skill_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        mIndicator = indicator;
    }

}

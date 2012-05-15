package com.wemakestuff.d3builder;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class SelectSkill extends SherlockFragmentActivity
{
    SkillFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    int index;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_skill);
        
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest newAd = new AdRequest();
        newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
        newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
        adView.loadAd(newAd);
        
        Bundle b = getIntent().getExtras();
        
        String skillType = null;
        String selectedClass = null;
        List<ParcelUuid> skills = null;
        
        int requiredLevel = 0;
        if (b.containsKey("SkillType"))
        {
            skillType = b.getString("SkillType");
        }
        
        if (b.containsKey("SelectedClass"))
        {
            selectedClass = b.getString("SelectedClass");
        }
        
        if (b.containsKey("RequiredLevel"))
        {
            requiredLevel = b.getInt("RequiredLevel");
        }
        
        if (b.containsKey("Index"))
        {
        	index = b.getInt("Index");
        }
        
        if (b.containsKey("UUIDs"))
        {
            skills = b.getParcelableArrayList("UUIDs");
        }
        
        OnClickListener itemClickListener = new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				
				Intent intent = getIntent();
				intent.putExtra("Skill_UUID", v.getTag().toString());
				intent.putExtra("Index", index);
				setResult(RESULT_OK, intent);
				finish();
			}
        	
        };

        mAdapter = new SkillFragmentAdapter(getSupportFragmentManager(), SelectSkill.this, skillType, selectedClass, requiredLevel, skills);
        mAdapter.setOnListItemClickListener(itemClickListener);
        
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.select_skill_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        mIndicator = indicator;

        mPager.setCurrentItem(mAdapter.getItemPosition(skillType));
    }

}

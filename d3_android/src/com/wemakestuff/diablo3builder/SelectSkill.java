package com.wemakestuff.diablo3builder;


import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.diablo3builder.SkillListFragment.OnSkillSelectedListener;
import com.wemakestuff.diablo3builder.string.Replacer;
import com.wemakestuff.diablo3builder.string.Replacer.D3Color;

public class SelectSkill extends SherlockFragmentActivity implements OnSkillSelectedListener
{
    SkillFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    private int index;
    private int requiredLevel = 1;
    private int maxLevel = 60;
    private UUID selectedSkill;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_skill);
        
        Bundle b = getIntent().getExtras();
        
        String skillType = null;
        String selectedClass = null;
        List<ParcelUuid> skills = null;
        
        
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

        TextView requiredLevelText = (TextView) findViewById(R.id.required_level);
        requiredLevelText.setText(Replacer.replace("Required Level: " + requiredLevel, "\\d+", D3Color.DIABLO_GREEN));
        
        mAdapter = new SkillFragmentAdapter(getSupportFragmentManager(), SelectSkill.this, skillType, selectedClass, maxLevel, skills);
        
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.select_skill_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        mIndicator = indicator;

        mPager.setCurrentItem(mAdapter.getItemPosition(skillType));
    }

    @Override
    public void OnSkillSelected(UUID skill)
    {
        this.selectedSkill = skill;
        Intent intent = getIntent();
        intent.putExtra("Skill_UUID", selectedSkill.toString());
        intent.putExtra("Index", index);
        setResult(RESULT_OK, intent);
        finish();
        
    }

}

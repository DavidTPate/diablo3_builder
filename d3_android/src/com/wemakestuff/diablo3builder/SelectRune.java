package com.wemakestuff.diablo3builder;

import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.diablo3builder.RuneListFragment.OnRuneSelectedListener;
import com.wemakestuff.diablo3builder.string.Replacer;
import com.wemakestuff.diablo3builder.string.Replacer.D3Color;

public class SelectRune extends SherlockFragmentActivity implements OnRuneSelectedListener {
	RuneFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;
	int index;
	int maxLevel = 60;
	UUID skillUUID;
	UUID runeUUID;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_skill);
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest newAd = new AdRequest();
        newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
        newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
        newAd.addTestDevice("E9BD79A28E313B2BDFA0CB0AED6C9697");
        newAd.addTestDevice(AdRequest.TEST_EMULATOR);
        adView.loadAd(newAd);

		Bundle b = getIntent().getExtras();

		String skillName = null;
		String selectedClass = null;

		int requiredLevel = 0;

		if (b.containsKey("SkillName")) {
			skillName = b.getString("SkillName");
		}

		if (b.containsKey("SelectedClass")) {
			selectedClass = b.getString("SelectedClass");
		}

		if (b.containsKey("RequiredLevel")) {
			requiredLevel = b.getInt("RequiredLevel");
		}

		if (b.containsKey("SkillUUID")) {
			skillUUID = (UUID) b.getSerializable("SkillUUID");
		}

		if (b.containsKey("Index")) {
			index = b.getInt("Index");
		}

		OnClickListener itemClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = getIntent();
				intent.putExtra("Rune_UUID", v.getTag().toString());
				
				intent.putExtra("Index", index);
				setResult(RESULT_OK, intent);
				finish();
			}

		};
		
		TextView requiredLevelText = (TextView) findViewById(R.id.required_level);
        requiredLevelText.setText(Replacer.replace("Required Level: " + requiredLevel, "\\d+", D3Color.DIABLO_GREEN));

		mAdapter = new RuneFragmentAdapter(getSupportFragmentManager(),
				SelectRune.this, skillName, skillUUID, selectedClass,
				maxLevel);
		mAdapter.setOnListItemClickListener(itemClickListener);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.select_skill_indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;

		mPager.setCurrentItem(mAdapter.getItemPosition(skillName));
	}

    @Override
    public void OnRuneSelected(UUID rune)
    {
        this.runeUUID = rune;
        Intent intent = getIntent();
        intent.putExtra("Rune_UUID", rune.toString());
        intent.putExtra("Skill_UUID", skillUUID.toString());
        intent.putExtra("Index", index);
        setResult(RESULT_OK, intent);
        finish();        
    }

}

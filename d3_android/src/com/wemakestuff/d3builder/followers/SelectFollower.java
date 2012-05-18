package com.wemakestuff.d3builder.followers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnRequiredLevelUpdate;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class SelectFollower extends SherlockFragmentActivity implements OnRequiredLevelUpdate
{
    private FollowerFragmentAdapter mAdapter;
    private ViewPager               mPager;
    private PageIndicator           mIndicator;
    private String                  loadFromUrl;
    private boolean                 loadedFromUrl     = false;
    private TextView                requiredLevel;
    private LinearLayout            requiredLevelWrapper;
    private List<ParcelUuid>        templarSkills     = new ArrayList<ParcelUuid>();
    private List<ParcelUuid>        scoundrelSkills   = new ArrayList<ParcelUuid>();
    private List<ParcelUuid>        enchantressSkills = new ArrayList<ParcelUuid>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        if (data != null) {
            loadFromUrl = data.toString();
        }

        setContentView(R.layout.select_follower);

        requiredLevelWrapper = (LinearLayout) findViewById(R.id.follower_required_level_wrapper);
        requiredLevel = (TextView) findViewById(R.id.follower_required_level);
        setRequiredLevel(1);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest newAd = new AdRequest();
        newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
        newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
        newAd.addTestDevice("E9BD79A28E313B2BDFA0CB0AED6C9697");
        adView.loadAd(newAd);

        OnLoadFragmentsCompleteListener listener = new OnLoadFragmentsCompleteListener() {

            public void OnLoadFragmentsComplete(String className) {
                //
                // if (!loadedFromUrl && loadFromUrl != null)
                // {
                //
                // Pattern p =
                // Pattern.compile("^http://.*/calculator/(.*)#.*$");
                // Matcher m = p.matcher(loadFromUrl);
                //
                // while (m.find())
                // {
                // if (m.groupCount() >= 1)
                // {
                // int position =
                // mAdapter.getItemPosition(m.group(1).replace("-", " "));
                // if (position > 0 &&
                // className.equalsIgnoreCase(m.group(1).replace("-", " ")))
                // {
                // loadedFromUrl = true;
                // loadClassFromUrl(loadFromUrl);
                // }
                // else if (position == 0 &&
                // !className.equalsIgnoreCase(m.group(1).replace("-", " ")))
                // {
                // loadedFromUrl = true;
                // loadClassFromUrl(loadFromUrl);
                // }
                // }
                // }
                // }
                // else
                // {
                // }
            }
        };

        mAdapter = new FollowerFragmentAdapter(getSupportFragmentManager(), SelectFollower.this, listener);

        mPager = (ViewPager) findViewById(R.id.follower_pager);
        mPager.setOffscreenPageLimit(5);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.select_follower_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);

        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
//                FollowerListFragment frag = (FollowerListFragment) mAdapter.getItem(position);
//                setRequiredLevel(frag.getMaxLevel());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        mIndicator = indicator;

    }

    @Override
    public void onBackPressed() {

        FollowerListFragment frag = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("Templar", (ArrayList<ParcelUuid>) templarSkills);
        resultIntent.putParcelableArrayListExtra("Scoundrel", (ArrayList<ParcelUuid>) scoundrelSkills);
        resultIntent.putParcelableArrayListExtra("Enchantress", (ArrayList<ParcelUuid>) enchantressSkills);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
        super.onBackPressed();

    }

    public void updateData() {
        mIndicator.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    public void setRequiredLevel(int level) {
        Log.i("RequiredLevel", ":Setting to " + level);
        requiredLevel.setText(Replacer.replace("Required Level: " + level, "\\d+", Vars.DIABLO_GREEN));
    }

    public List<ParcelUuid> getTemplarSkills() {
        return templarSkills;
    }

    public void setTemplarSkills(List<ParcelUuid> templarSkills) {
        this.templarSkills = templarSkills;
    }

    public List<ParcelUuid> getScoundrelSkills() {
        return scoundrelSkills;
    }

    public void setScoundrelSkills(List<ParcelUuid> scoundrelSkills) {
        this.scoundrelSkills = scoundrelSkills;
    }

    public List<ParcelUuid> getEnchantressSkills() {
        return enchantressSkills;
    }

    public void setEnchantressSkills(List<ParcelUuid> enchantressSkills) {
        this.enchantressSkills = enchantressSkills;
    }

    @Override
    public void OnRequiredLevelUpdate(int level) {
        setRequiredLevel(level);

    }
}

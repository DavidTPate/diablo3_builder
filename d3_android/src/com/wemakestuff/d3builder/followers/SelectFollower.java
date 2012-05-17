package com.wemakestuff.d3builder.followers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.d3builder.ClassFragmentAdapter;
import com.wemakestuff.d3builder.ClassListFragment;
import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.SelectClass;

public class SelectFollower extends SherlockFragmentActivity
{
    private FollowerFragmentAdapter mAdapter;
    private ViewPager            mPager;
    private PageIndicator        mIndicator;
    private String               loadFromUrl;
    private boolean              loadedFromUrl = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        if (data != null)
        {
            loadFromUrl = data.toString();
        }
        
        setContentView(R.layout.select_follower);
        
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest newAd = new AdRequest();
        newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
        newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
        newAd.addTestDevice("E9BD79A28E313B2BDFA0CB0AED6C9697");
        adView.loadAd(newAd);
        
        OnLoadFragmentsCompleteListener listener = new OnLoadFragmentsCompleteListener()
        {

            public void OnLoadFragmentsComplete(String className)
            {
//
//                if (!loadedFromUrl && loadFromUrl != null)
//                {
//
//                    Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
//                    Matcher m = p.matcher(loadFromUrl);
//
//                    while (m.find())
//                    {
//                        if (m.groupCount() >= 1)
//                        {
//                            int position = mAdapter.getItemPosition(m.group(1).replace("-", " "));
//                            if (position > 0 && className.equalsIgnoreCase(m.group(1).replace("-", " ")))
//                            {
//                                loadedFromUrl = true;
//                                loadClassFromUrl(loadFromUrl);
//                            }
//                            else if (position == 0 && !className.equalsIgnoreCase(m.group(1).replace("-", " ")))
//                            {
//                                loadedFromUrl = true;
//                                loadClassFromUrl(loadFromUrl);
//                            }
//                        }
//                    }
//                }
//                else
//                {
//                }
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
                ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
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
}

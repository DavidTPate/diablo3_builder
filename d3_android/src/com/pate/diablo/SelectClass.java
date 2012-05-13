
package com.pate.diablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pate.diablo.model.D3Application;
import com.pate.diablo.model.DataModel;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class SelectClass extends SherlockFragmentActivity
{

    private ClassFragmentAdapter mAdapter;
    private ViewPager            mPager;
    private PageIndicator        mIndicator;
    private SpinnerAdapter       mSpinnerAdapter;
    private static int           maxLevel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.Share)
        {
            ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
            Log.i("linkTest", frag.linkifyClassBuild());
        }
        else if (item.getItemId() == R.id.Load)
        {
            Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
            Matcher m = p.matcher("http://us.battle.net/d3/en/calculator/barbarian#aZYdfT!aZb!aaaaaa");
            
            ClassListFragment frag = null;

            while (m.find())
            {
                if (m.groupCount() >= 1)
                {
                    int position =  mAdapter.getItemPosition(m.group(1).replace("-", " "));
                    frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + position);
                    
                    Log.i("delinkTest", "http://us.battle.net/d3/en/calculator/barbarian#aZYdfT!aZb!aaaaaa");
                    frag.delinkifyClassBuild("http://us.battle.net/d3/en/calculator/barbarian#aZYdfT!aZb!aaaaaa");
                    
                    mPager.setCurrentItem(position);
                }
            }
/*
            Log.i("delinkTest", "http://us.battle.net/d3/en/calculator/demon-hunter#ac!a");
            frag.delinkifyClassBuild("http://us.battle.net/d3/en/calculator/demon-hunter#ac!a");

            Log.i("delinkTest", "http://us.battle.net/d3/en/calculator/witch-doctor#!a");
            frag.delinkifyClassBuild("http://us.battle.net/d3/en/calculator/witch-doctor#!a");
            */

        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        if (D3Application.dataModel == null)
        {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.classes)));

            String fakeJson = "";
            String line;
            try
            {
                line = reader.readLine();
                while (line != null)
                {
                    fakeJson = fakeJson + line;
                    line = reader.readLine();
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            D3Application.setDataModel(gson.fromJson(fakeJson, DataModel.class));
        }

        setContentView(R.layout.select_skill);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey("MaxLevel"))
            {
                maxLevel = savedInstanceState.getInt("MaxLevel");
            }
        }
        else
        {
            maxLevel = 60;
        }

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest newAd = new AdRequest();
        newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
        newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
        adView.loadAd(newAd);

        mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_dropdown_item_1line);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ActionBar.OnNavigationListener mNavigationCallback = new ActionBar.OnNavigationListener()
        {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId)
            {

                maxLevel = 60 - itemPosition;
                Log.i("MaxLevel", "" + maxLevel);
                return true;
            }
        };

        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mNavigationCallback);
        actionBar.setSelectedNavigationItem(60 - maxLevel);

        mAdapter = new ClassFragmentAdapter(getSupportFragmentManager(), SelectClass.this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.select_skill_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        mIndicator = indicator;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        outState.putInt("MaxLevel", maxLevel);
        super.onSaveInstanceState(outState);
    }
}

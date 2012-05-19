
package com.wemakestuff.d3builder.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdView;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.d3builder.ClassListFragment;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.classes.listener.OnClassFragmentLoadListener;
import com.wemakestuff.d3builder.classes.listener.OnLoadBuildListener;
import com.wemakestuff.d3builder.global.Funcs;
import com.wemakestuff.d3builder.model.ClassBuild;

public class SelectClass extends SherlockFragmentActivity
{

    private ViewPager mPager;
    private Dialog               dialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        String loadFromUrl = getIntent().getData() != null ? getIntent().getData().toString() : "";

        if (loadFromUrl != null && !loadFromUrl.equals(""))
        {
            Log.i("Class Load SavedInstanceState", loadFromUrl);

        }

        setContentView(R.layout.select_skill);

        TextView requiredLevel = (TextView) findViewById(R.id.required_level);

        OnClassFragmentLoadListener classFragListener = new OnClassFragmentLoadListener()
        {

            @Override
            public void onClassFragmentLoaded(ClassListFragment frag)
            {

                // TODO: Handle fragment loading.
            }

        };

        ClassFragmentAdapter mAdapter = new ClassFragmentAdapter(getSupportFragmentManager(), SelectClass.this, classFragListener);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.select_skill_indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);

        OnPageChangeListener pageListener = new OnPageChangeListener()
        {

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

                return;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

                return;
            }

            @Override
            public void onPageSelected(int position)
            {

                ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
                Log.i("Frag Selected", "Pos: " + position + " " + frag.getSelectedClass());
            }

        };
        indicator.setOnPageChangeListener(pageListener);

        Funcs.getNewAd((AdView) this.findViewById(R.id.adView));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.share)
        {
            ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
            String subject = getString(R.string.Check_Out_My) + " " + frag.getSelectedClass() + " " + getString(R.string.Build);
            Funcs.shareBuild(this, subject, subject + ": " + frag.linkifyClassBuild());

        }
        else if (item.getItemId() == R.id.load)
        {
            ClassBuildAdapter aAdapter = new ClassBuildAdapter(Funcs.getClassAvailableBuilds(this), this);
            
            OnLoadBuildListener loadListener = new OnLoadBuildListener()
            {

                @Override
                public void onLoadBuild(ClassBuild build)
                {

                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onShareBuild(ClassBuild build)
                {

                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onDeleteBuild(ClassBuild build)
                {

                    // TODO Auto-generated method stub
                    
                }
                
            };
            aAdapter.setOnLoadBuildListener(loadListener);
            
            ListView buildList = new ListView(this);
            buildList.setAdapter(aAdapter);
            
            dialog = new AlertDialog.Builder(this).setTitle(getString(R.string.Load_Build)).setView(buildList).create();
            dialog.show();
            
        }
        else if (item.getItemId() == R.id.save)
        {

        }
        else if (item.getItemId() == R.id.clear)
        {

        }
        return false;
    }
}

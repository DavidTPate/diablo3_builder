
package com.wemakestuff.d3builder.classes;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdView;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.classes.interfaces.OnClassFragmentLoadedInterface;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnRequiredLevelUpdateListener;
import com.wemakestuff.d3builder.global.Funcs;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;



public class SelectClass extends SherlockFragmentActivity implements OnRequiredLevelUpdateListener, OnClassFragmentLoadedInterface
{
    private TextView requiredLevel;
    private ClassFragmentAdapter mAdapter;
    private ViewPager mPager;
    private TitlePageIndicator mIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void OnRequiredLevelUpdate(String name, int level)
    {

        // TODO Auto-generated method stub

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_skill);

        requiredLevel = (TextView) findViewById(R.id.required_level);
        setRequiredLevel(1);
        
        mAdapter = new ClassFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(5);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator) findViewById(R.id.select_skill_indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        
        Funcs.getNewAd((AdView) this.findViewById(R.id.adView));
    }
    
    public void setRequiredLevel(int level)
    {
        if (requiredLevel != null)
        requiredLevel.setText(Replacer.replace("Required Level: " + level, "\\d+", Vars.DIABLO_GREEN));
    }

    public final class ActionModeEditListItem implements ActionMode.Callback
    {

        int    position;
        String title;

        public ActionModeEditListItem(int position, String title)
        {

            this.position = position;
            this.title = title;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {

            mode.setTitle(title);
            //@formatter:off
            menu.add("Delete")
                .setIcon(R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            //@formatter:on
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {

            ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());

            if (frag != null)
            {
                //TODO:
                //frag.clearListItem(position);
                mode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {

        }
    }

    @Override
    public void onClassFragmentLoaded(ClassListFragment fragment)
    {

        // TODO Auto-generated method stub
        
    }

}

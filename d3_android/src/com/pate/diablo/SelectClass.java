
package com.pate.diablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
    private String loadFromUrl;
    private boolean loadedFromUrl = false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SelectClass", "OnResume");
        Uri data = getIntent().getData();
        
        if (data!=null)
        {
            loadFromUrl = data.toString();
            Log.i("loadFromUrl", loadFromUrl == null ? "Null" : loadFromUrl);
        }
    }
    
    public void loadClassFromUrl(String url)
    {
        Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
        Matcher m = p.matcher(url);
        
        ClassListFragment frag = null;

        while (m.find())
        {
            if (m.groupCount() >= 1)
            {
                int position =  mAdapter.getItemPosition(m.group(1).replace("-", " "));
                Log.i("LoadClassFromUrl Position", "" + position);
                frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + position);
                
                Log.i("loadClassFromUrl", url);
                if (frag != null)
                {
                    mAdapter.setOnLoadFragmentsCompleteListener(null);
                    frag.delinkifyClassBuild(url);
                    mPager.setCurrentItem(position);
                }
            }
        }
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
            loadClassFromUrl("http://us.battle.net/d3/en/calculator/monk#aZYdfT!aZb!aaaaaa");

        }
        else if (item.getItemId() == R.id.Clear)
        {
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.alert_dialog_title);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        if (data!=null)
        {
            loadFromUrl = data.toString();
            Log.i("OnCreate loadFromUrl", loadFromUrl == null ? "Null" : loadFromUrl);
        }
        
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
        newAd.addTestDevice("E9BD79A28E313B2BDFA0CB0AED6C9697");
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
        
        OnLoadFragmentsCompleteListener listener = new OnLoadFragmentsCompleteListener()
        {
            public void OnLoadFragmentsComplete(String className)
            {
                if (!loadedFromUrl && loadFromUrl != null)
                {
                    Log.i("SelectClass OnCreate", "Loading from url: " + loadFromUrl);
                    
                    Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
                    Matcher m = p.matcher(loadFromUrl);
                    
                    while (m.find())
                    {
                        if (m.groupCount() >= 1)
                        {
                            int position =  mAdapter.getItemPosition(m.group(1).replace("-", " "));
                            if (position > 0 && className.equalsIgnoreCase(m.group(1).replace("-", " ")))
                            {
                                loadedFromUrl = true;
                                loadClassFromUrl(loadFromUrl);
                            }
                            else if (position == 0 && !className.equalsIgnoreCase(m.group(1).replace("-", " ")))
                            {
                                loadedFromUrl = true;
                                loadClassFromUrl(loadFromUrl);
                            }
                        }
                    }
                }
                else
                {
                    Log.i("SelectClass OnCreate", "Load from URL was null");
                }
            }
        };

        mAdapter = new ClassFragmentAdapter(getSupportFragmentManager(), SelectClass.this, listener);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(5);
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
    
    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
        frag.clear();
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
    
    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((SelectClass)getActivity()).doPositiveClick();
                            }
                        }
                    )
                    .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((SelectClass)getActivity()).doNegativeClick();
                            }
                        }
                    )
                    .create();
        }
    }
    
}

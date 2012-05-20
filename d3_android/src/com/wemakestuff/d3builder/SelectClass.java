
package com.wemakestuff.d3builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnRequiredLevelUpdateListener;
import com.wemakestuff.d3builder.followers.SelectFollower;
import com.wemakestuff.d3builder.model.ClassBuild;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class SelectClass extends SherlockFragmentActivity implements OnRequiredLevelUpdateListener
{

    private ClassFragmentAdapter  mAdapter;
    private ViewPager             mPager;
    private PageIndicator         mIndicator;
    // private SpinnerAdapter mSpinnerAdapter;
    private static int           maxLevel      = 60;
    private String               loadFromUrl;
    private boolean              loadedFromUrl = false;
    private String               value;
    private Dialog               dialog;
    private ClassBuildAdapter    aAdapter;
    private TextView             requiredLevel;
    private LinearLayout         requiredLevelWrapper;
    private ArrayList<ClassBuild>     builds;
    private Map<String, Integer>    requiredLevelMap = new HashMap<String, Integer>();
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        Uri data = getIntent().getData();

        if (data != null)
        {
            loadFromUrl = data.toString();
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
                int position = mAdapter.getItemPosition(m.group(1).replace("-", " "));
                frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + position);

                if (frag != null)
                {
                    mAdapter.setOnLoadFragmentsCompleteListener(null);
                    frag.delinkifyClassBuild(url);
                    mPager.setCurrentItem(position);
                    setRequiredLevel(frag.getMaxLevel());
                }
            }
        }
    }

    public void loadClassFromUrl(ClassBuild build)
    {

        Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
        Matcher m = p.matcher(build.getUrl());

        ClassListFragment frag = null;

        while (m.find())
        {
            if (m.groupCount() >= 1)
            {
                int position = mAdapter.getItemPosition(m.group(1).replace("-", " "));
                frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + position);

                if (frag != null)
                {
                    mAdapter.setOnLoadFragmentsCompleteListener(null);
                    frag.delinkifyClassBuild(build.getUrl());
                    mPager.setCurrentItem(position);
                    setRequiredLevel(frag.getMaxLevel());

                    if (build.getFollowersUrl() != null)
                        frag.setFollowerSkills(build.getFollowersUrl());

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.share)
        {
            final ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
            String subject = getString(R.string.Check_Out_My) + " " + frag.getSelectedClass() + " " + getString(R.string.Build);
            String classes = frag.linkifyClassBuild(getString(R.string.EN_Build_URL));
            String followers = frag.getFollowerUrl();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, subject + " " + classes + (frag.getFollowerSkillsCount() > 0 ? " and my followers: " + followers : ""));
            startActivity(Intent.createChooser(intent, "Share using"));
        }
        else if (item.getItemId() == R.id.load)
        {
            builds = new ArrayList<ClassBuild>();

            SharedPreferences valVals = getSharedPreferences("saved_build_value", MODE_PRIVATE);
            SharedPreferences clssVals = getSharedPreferences("saved_build_class", MODE_PRIVATE);
            SharedPreferences followerVals = getSharedPreferences("saved_build_follower", MODE_PRIVATE);

            Map<String, String> urls = (Map<String, String>) valVals.getAll();
            Map<String, String> classes = (Map<String, String>) clssVals.getAll();
            Map<String, String> followers = (Map<String, String>) followerVals.getAll();

            for (Map.Entry<String, String> pairs : urls.entrySet())
            {
                if (classes.containsKey(pairs.getKey()))
                {
                    ClassBuild c = new ClassBuild(pairs.getKey(), classes.get(pairs.getKey()), pairs.getValue(), followers.get(pairs.getKey()));
                    builds.add(c);
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Load Saved Build");

            final ListView buildList = new ListView(this);

            OnLoadBuildClickListener loadBuildListener = new OnLoadBuildClickListener()
            {

                @Override
                public void onLoadBuildClick(ClassBuild build)
                {

                    loadClassFromUrl(build);
                }

                @Override
                public void onLoadBuildDismiss()
                {

                    dialog.dismiss();
                }

                @Override
                public void onDeleteBuild(ClassBuild build)
                {

                    builds.remove(build);
                    aAdapter.notifyDataSetChanged();
                }
            };

            aAdapter = new ClassBuildAdapter(builds, this, loadBuildListener);
            buildList.setAdapter(aAdapter);
            builder.setView(buildList);

            dialog = builder.create();

            dialog.show();

        }
        else if (item.getItemId() == R.id.save)
        {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Save Current Build As...");
            alert.setMessage("Please Enter a Name to use to Save the Current Build.");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    value = input.getText().toString();

                    SharedPreferences valVals = getSharedPreferences("saved_build_value", MODE_PRIVATE);
                    SharedPreferences.Editor valEdit = valVals.edit();
                    SharedPreferences clssVals = getSharedPreferences("saved_build_class", MODE_PRIVATE);
                    SharedPreferences.Editor clssEdit = clssVals.edit();
                    SharedPreferences followers = getSharedPreferences("saved_build_follower", MODE_PRIVATE);
                    SharedPreferences.Editor followersEdit = followers.edit();

                    while (valVals.contains(value) || clssVals.contains(value))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "A Build is Already Saved as: " + value + ". Enter a New Name.", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    if (value != null && !(value.length() == 0))
                    {
                        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
                        valEdit.putString(value, frag.linkifyClassBuild(getString(R.string.EN_Build_URL)));
                        clssEdit.putString(value, frag.getSelectedClass());
                        Log.i("Follower Url", frag.getFollowerUrl());
                        followersEdit.putString(value, frag.getFollowerUrl());

                        valEdit.commit();
                        clssEdit.commit();
                        followersEdit.commit();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    value = "";
                }
            });

            alert.show();

        }
        else if (item.getItemId() == R.id.clear)
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
        if (data != null)
        {
            loadFromUrl = data.toString();
            Log.i("LoadFromUrl", data.toString());

            if (loadFromUrl.contains("follower"))
            {
                Intent intent = new Intent(this, SelectFollower.class);
                intent.putExtra("url", loadFromUrl);
                startActivity(intent);
            }
        }

        setContentView(R.layout.select_skill);

        requiredLevelWrapper = (LinearLayout) findViewById(R.id.required_level_wrapper);
        requiredLevel = (TextView) findViewById(R.id.required_level);
        setRequiredLevel(1);

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

                if (!loadedFromUrl && loadFromUrl != null)
                {

                    Pattern p = Pattern.compile("^http://.*/calculator/(.*)#.*$");
                    Matcher m = p.matcher(loadFromUrl);

                    while (m.find())
                    {
                        if (m.groupCount() >= 1)
                        {
                            int position = mAdapter.getItemPosition(m.group(1).replace("-", " "));
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

                    if (!loadedFromUrl)
                    {

                    }
                }
                else
                {
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

        indicator.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {

                ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
                if (frag != null)
                    setRequiredLevel(frag.getMaxLevel());
                else
                    Log.e("Fragment was null!", "Could not set max level");
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

        mIndicator = indicator;

    }

    public void setRequiredLevel(int level)
    {
        requiredLevel.setText(Replacer.replace("Required Level: " + level, "\\d+", Vars.DIABLO_GREEN));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        // outState.putInt("MaxLevel", maxLevel);
        super.onSaveInstanceState(outState);
    }

    public void doPositiveClick()
    {

        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
        setRequiredLevel(1);
        frag.clear();
    }

    public void doNegativeClick()
    {

    }

    public static class MyAlertDialogFragment extends DialogFragment
    {

        public static MyAlertDialogFragment newInstance(int title)
        {

            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity()).setTitle(title).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    ((SelectClass) getActivity()).doPositiveClick();
                }
            }).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    ((SelectClass) getActivity()).doNegativeClick();
                }
            }).create();
        }
    }

    @Override
    public void OnRequiredLevelUpdate(String name, int level) {
        requiredLevelMap.put(name, level);
        setRequiredLevel(level);
    }

    
}

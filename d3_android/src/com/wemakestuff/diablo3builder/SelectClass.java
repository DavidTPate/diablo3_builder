package com.wemakestuff.diablo3builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.wemakestuff.diablo3builder.followers.FollowerListFragment.OnRequiredLevelUpdateListener;
import com.wemakestuff.diablo3builder.followers.SelectFollower;
import com.wemakestuff.diablo3builder.model.ClassBuild;
import com.wemakestuff.diablo3builder.string.Replacer;
import com.wemakestuff.diablo3builder.string.Replacer.D3Color;

public class SelectClass extends SherlockFragmentActivity implements OnRequiredLevelUpdateListener
{

    private ClassFragmentAdapter  mAdapter;
    private ViewPager             mPager;
    private PageIndicator         mIndicator;
    // private SpinnerAdapter mSpinnerAdapter;
    private static int            maxLevel         = 60;
    private String                loadFromUrl;
    private boolean               loadedFromUrl    = false;
    private String                value;
    private Dialog                dialog;
    private ClassBuildAdapter     aAdapter;
    private TextView              requiredLevel;
    private LinearLayout          requiredLevelWrapper;
    private ArrayList<ClassBuild> builds;
    private Map<String, Integer>  requiredLevelMap = new HashMap<String, Integer>();
    private ActionMode            mMode;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onListItemLongClick(int position, String title)
    {
        mMode = startActionMode(new ActionModeEditListItem(position, title));
    }
    
    public void clearActionMode()
    {
        if (mMode != null)
            mMode.finish();
    }

    
    public final class ActionModeEditListItem implements ActionMode.Callback
    {
        int position;
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
                frag.clearListItem(position);
                mode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
        }
    }

    public void updateData() {
        mIndicator.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
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

                    if (build.getFollowersUrl() != null)
                        frag.setFollowerSkills(build.getFollowersUrl());

                    setRequiredLevel(frag.getMaxLevel());
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.share)
        {
            final ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
            String subject = getString(R.string.Check_Out_My) + " " + frag.getSelectedClass() + " " + getString(R.string.Build);
            String classes = frag.linkifyClassBuild(getString(R.string.EN_Build_URL));
            String followers = frag.getFollowerUrl();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, subject + " " + classes
                    + (frag.getFollowerSkillsCount() > 0 ? " and my followers: " + followers : ""));
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

            OnLoadBuildClickListener loadBuildListener = new OnLoadBuildClickListener() {

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

            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    value = input.getText().toString();

                    SharedPreferences valVals = getSharedPreferences("saved_build_value", MODE_PRIVATE);
                    SharedPreferences.Editor valEdit = valVals.edit();
                    SharedPreferences clssVals = getSharedPreferences("saved_build_class", MODE_PRIVATE);
                    SharedPreferences.Editor clssEdit = clssVals.edit();
                    SharedPreferences followers = getSharedPreferences("saved_build_follower", MODE_PRIVATE);
                    SharedPreferences.Editor followersEdit = followers.edit();

                    if (valVals.contains(value) || clssVals.contains(value) || followers.contains(value))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "A Build is Already Saved as: " + value + ". Enter a New Name.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if (value != null && !(value.length() == 0))
                    {
                        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag(
                                "android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
                        valEdit.putString(value, frag.linkifyClassBuild(getString(R.string.EN_Build_URL)));
                        clssEdit.putString(value, frag.getSelectedClass());
                        followersEdit.putString(value, frag.getFollowerUrl());

                        valEdit.commit();
                        clssEdit.commit();
                        followersEdit.commit();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    value = "";
                }
            });

            alert.show();

        }
        else if (item.getItemId() == R.id.clear)
        {
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.alert_dialog_title, false);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
        else if (item.getItemId() == R.id.clear_all)
        {
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.alert_dialog_title_build_clear_all, true);
            newFragment.show(getSupportFragmentManager(), "dialog_clear_all");
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

        OnLoadFragmentsCompleteListener listener = new OnLoadFragmentsCompleteListener() {

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

        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position)
            {

                if (mMode != null)
                {
                    mMode.finish();
                }
                
                ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag(
                        "android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
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
        requiredLevel.setText(Replacer.replace("Required Level: " + level, "\\d+", D3Color.DIABLO_GREEN));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        // outState.putInt("MaxLevel", maxLevel);
        super.onSaveInstanceState(outState);
    }

    public void doPositiveClick(boolean clearAll)
    {

        if (clearAll)
        {
            ClassListFragment frag1 = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "0");
            ClassListFragment frag2 = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "1");
            ClassListFragment frag3 = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "2");
            ClassListFragment frag4 = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "3");
            ClassListFragment frag5 = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "4");

            if (frag1 != null)
                frag1.clear();
            if (frag2 != null)
                frag2.clear();
            if (frag3 != null)
                frag3.clear();
            if (frag4 != null)
                frag4.clear();
            if (frag5 != null)
                frag5.clear();
        }
        else
        {
            ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
            frag.clear();
        }
        setRequiredLevel(1);
    }

    public void doNegativeClick(boolean clearAll)
    {

    }

    public static class MyAlertDialogFragment extends DialogFragment
    {

        public static MyAlertDialogFragment newInstance(int title, boolean clearAll)
        {

            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            args.putBoolean("clearAll", clearAll);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            int title = getArguments().getInt("title");
            final boolean clearAll = getArguments().getBoolean("clearAll");

            return new AlertDialog.Builder(getActivity()).setTitle(title).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    ((SelectClass) getActivity()).doPositiveClick(clearAll);
                }
            }).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    ((SelectClass) getActivity()).doNegativeClick(clearAll);
                }
            }).create();
        }
    }

    @Override
    public void OnRequiredLevelUpdate(String name, int level)
    {
        requiredLevelMap.put(name, level);
        setRequiredLevel(level);
    }

}

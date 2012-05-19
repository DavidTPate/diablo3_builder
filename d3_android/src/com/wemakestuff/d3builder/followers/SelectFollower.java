package com.wemakestuff.d3builder.followers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.LinearLayout;
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
import com.wemakestuff.d3builder.ClassListFragment;
import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnLoadFragmentCompleteListener;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnRequiredLevelUpdateListener;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class SelectFollower extends SherlockFragmentActivity implements OnRequiredLevelUpdateListener, OnLoadFragmentCompleteListener
{
    private FollowerFragmentAdapter mAdapter;
    private ViewPager               mPager;
    private PageIndicator           mIndicator;
    private String                  loadFromUrl;
    private boolean                 loadedFromUrl     = false;
    private String selectedFollower;
    private TextView                requiredLevel;
    private LinearLayout            requiredLevelWrapper;
    private List<ParcelUuid>        templarSkills     = new ArrayList<ParcelUuid>();
    private List<ParcelUuid>        scoundrelSkills   = new ArrayList<ParcelUuid>();
    private List<ParcelUuid>        enchantressSkills = new ArrayList<ParcelUuid>();
    private int loadedFragmentsCount = 0;
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(Vars.TEMPLAR))
            templarSkills = getIntent().getParcelableArrayListExtra(Vars.TEMPLAR);
        
        if (getIntent().hasExtra(Vars.SCOUNDREL))
            scoundrelSkills = getIntent().getParcelableArrayListExtra(Vars.SCOUNDREL);

        if (getIntent().hasExtra(Vars.ENCHANTRESS))
            enchantressSkills = getIntent().getParcelableArrayListExtra(Vars.ENCHANTRESS);
        
        if (getIntent().hasExtra("Follower"))
            selectedFollower = getIntent().getStringExtra("Follower");
        
        Uri data = getIntent().getData();
        if (data != null) {
            loadFromUrl = data.toString();
            Log.i("LoadFromUrl", loadFromUrl);
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

        mAdapter = new FollowerFragmentAdapter(getSupportFragmentManager(), SelectFollower.this);

        mPager = (ViewPager) findViewById(R.id.follower_pager);
        mPager.setOffscreenPageLimit(2);
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

    public List<ParcelUuid> getSkillsByClass(String className)
    {
        if (className.equalsIgnoreCase(Vars.TEMPLAR))
        {
            return templarSkills;
        }
        else if (className.equalsIgnoreCase(Vars.SCOUNDREL))
        {
            return scoundrelSkills;
        }
        else if (className.equalsIgnoreCase(Vars.ENCHANTRESS))
        {
            return enchantressSkills;
        }
        
        return null;
    }
    
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

        if (item.getItemId() == R.id.share)
        {
            Toast.makeText(getApplicationContext(), getFollowersLink(), Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out my " + frag.getSelectedClass());
//            intent.putExtra(android.content.Intent.EXTRA_TEXT, frag.linkifyClassBuild());
//            startActivity(Intent.createChooser(intent, "Share using"));
        }
        else if (item.getItemId() == R.id.load)
        {
            mIndicator.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
            // loadClassFromUrl("http://us.battle.net/d3/en/calculator/monk#aZYdfT!aZb!aaaaaa");
            // SharedPreferences keyVals =
            // this.getApplicationContext().getSharedPreferences("saved_build_list",
            // MODE_PRIVATE);
            // loadClassFromUrl(keyVals.getString("test",
            // "http://us.battle.net/d3/en/calculator/barbarian#......!...!......"));

//            builds = new ArrayList<ClassBuild>();
//
//            SharedPreferences valVals = getSharedPreferences("saved_build_value", MODE_PRIVATE);
//            SharedPreferences clssVals = getSharedPreferences("saved_build_class", MODE_PRIVATE);
//
//            Map<String, String> urls = (Map<String, String>) valVals.getAll();
//            Map<String, String> classes = (Map<String, String>) clssVals.getAll();
//
//            for (Map.Entry<String, String> pairs : urls.entrySet())
//            {
//                if (classes.containsKey(pairs.getKey()))
//                {
//                    builds.add(new ClassBuild(pairs.getKey(), classes.get(pairs.getKey()), pairs.getValue()));
//                }
//            }
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Load Saved Build");
//
//            final ListView buildList = new ListView(this);
//
//            OnLoadBuildClickListener loadBuildListener = new OnLoadBuildClickListener()
//            {
//
//                @Override
//                public void onLoadBuildClick(ClassBuild build)
//                {
//
//                    loadClassFromUrl(build.getUrl());
//                }
//                
//                @Override
//                public void onLoadBuildDismiss()
//                {
//                    dialog.dismiss();
//                }
//                
//                @Override
//                public void onDeleteBuild(ClassBuild build)
//                {
//                    builds.remove(build);
//                    aAdapter.notifyDataSetChanged();
//                }
//            };
//
//            aAdapter = new ClassBuildAdapter(builds, this, loadBuildListener);
//            buildList.setAdapter(aAdapter);
//            builder.setView(buildList);
//
//            dialog = builder.create();
//
//            dialog.show();

        }
        else if (item.getItemId() == R.id.save)
        {
            delinkifyClassBuild("http://us.battle.net/d3/en/calculator/follower#0100!..10!.01");
//            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//            alert.setTitle("Save Current Build As...");
//            alert.setMessage("Please Enter a Name to use to Save the Current Build.");
//
//            // Set an EditText view to get user input
//            final EditText input = new EditText(this);
//            alert.setView(input);
//
//            alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
//            {
//
//                public void onClick(DialogInterface dialog, int whichButton)
//                {
//
//                    value = input.getText().toString();
//                    
//                    SharedPreferences valVals = getSharedPreferences("saved_build_value", MODE_PRIVATE);
//                    SharedPreferences.Editor valEdit = valVals.edit();
//                    SharedPreferences clssVals = getSharedPreferences("saved_build_class", MODE_PRIVATE);
//                    SharedPreferences.Editor clssEdit = clssVals.edit();
//
//                    while (valVals.contains(value) || clssVals.contains(value))
//                    {
//                        Toast toast = Toast.makeText(getApplicationContext(), "A Build is Already Saved as: " + value + ". Enter a New Name.", Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//
//                    if (value != null && !(value.length() == 0))
//                    {
//                        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
//                        valEdit.putString(value, frag.linkifyClassBuild());
//                        clssEdit.putString(value, frag.getSelectedClass());
//
//                        valEdit.commit();
//                        clssEdit.commit();
//                    }
//                }
//            });
//
//            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
//            {
//
//                public void onClick(DialogInterface dialog, int whichButton)
//                {
//
//                    value = "";
//                }
//            });
//
//            alert.show();

        }
        else if (item.getItemId() == R.id.clear)
        {
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.alert_dialog_title);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra(Vars.TEMPLAR, (ArrayList<ParcelUuid>) templarSkills);
        resultIntent.putParcelableArrayListExtra(Vars.SCOUNDREL, (ArrayList<ParcelUuid>) scoundrelSkills);
        resultIntent.putParcelableArrayListExtra(Vars.ENCHANTRESS, (ArrayList<ParcelUuid>) enchantressSkills);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
    

    public void delinkifyClassBuild(String url) {

        FollowerListFragment templar = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "0");
        FollowerListFragment scoundrel = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "1");
        FollowerListFragment enchantress = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "2");
        
        String build = url.split("#")[1];
        String templarLink;
        String scoundrelLink;
        String enchantressLink;
        
        Log.i("Split build", build);
        
        String[] followers = build.split("!");
        
        for (String s : followers)
        {
            Log.i("Followers@!#$" , s);
        }
        Log.i("Followers After split!", followers.toString());
        switch (followers.length) {
            case 1:
                templarLink = followers[0];
                templar.setSelectedSkills(templarLink);
                break;
    
            case 2:
                templarLink = followers[0];
                scoundrelLink = followers[1];
                
                templar.setSelectedSkills(templarLink);
                scoundrel.setSelectedSkills(scoundrelLink);
                break;
    
            case 3:
                templarLink = followers[0];
                scoundrelLink = followers[1];
                enchantressLink = followers[2];
                
                templar.setSelectedSkills(templarLink);
                scoundrel.setSelectedSkills(scoundrelLink);
                enchantress.setSelectedSkills(enchantressLink);
                break;
    
            default:
                break;
        }
        
        updateData();
    }
    
    public String getFollowersLink()
    {
        StringBuffer followerLink = new StringBuffer("http://us.battle.net/d3/en/calculator/follower#");
        
        Log.i("CurrentItem", mPager.getCurrentItem() + "");
        FollowerListFragment frag = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "0");
        followerLink.append(frag.linkifyClassBuild());
        followerLink.append(D3Application.getInstance().getSkillAttributes().getFollowerSeparator());

        frag = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "1");
        followerLink.append(frag.linkifyClassBuild());
        followerLink.append(D3Application.getInstance().getSkillAttributes().getFollowerSeparator());
        
        frag = (FollowerListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.follower_pager + ":" + "2");
        followerLink.append(frag.linkifyClassBuild());
        
        return followerLink.toString();
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
    
    public void doPositiveClick()
    {

        ClassListFragment frag = (ClassListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
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

                    ((SelectFollower) getActivity()).doPositiveClick();
                }
            }).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int whichButton)
                {

                    ((SelectFollower) getActivity()).doNegativeClick();
                }
            }).create();
        }
    }

    @Override
    public void OnLoadFragmentComplete(String follower) {
        loadedFragmentsCount++;
        
        if (loadedFragmentsCount == 3 && loadFromUrl != null)
        {
            int item = 0;
            
            if (selectedFollower == null || selectedFollower.equals(""))
                selectedFollower = Vars.TEMPLAR;
            
            if (selectedFollower.equals(Vars.TEMPLAR))
                item = 0;
            else if (selectedFollower.equals(Vars.SCOUNDREL))
                item = 1;
            else if (selectedFollower.equals(Vars.ENCHANTRESS))
                item = 2;
            
            ((TitlePageIndicator) mIndicator).setCurrentItem(item);
            
            delinkifyClassBuild(loadFromUrl);
        }
        
    }
}

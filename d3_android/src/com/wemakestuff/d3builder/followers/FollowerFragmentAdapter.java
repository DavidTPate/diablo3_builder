package com.wemakestuff.d3builder.followers;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;
import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Follower;

public class FollowerFragmentAdapter extends FragmentPagerAdapter implements TitleProvider
{
    private List<Follower> followers;
    private Context context;
    public OnLoadFragmentsCompleteListener listener;

    public FollowerFragmentAdapter(FragmentManager fm, Context context, OnLoadFragmentsCompleteListener listener) {
        super(fm);
        this.context = context;
        this.followers = D3Application.getInstance().getFollowers();
        this.listener = listener;
    }

    @Override
    public String getTitle(int position) {
        return followers.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        FollowerListFragment classList = FollowerListFragment.newInstance(followers.get(position).getName(), context, listener);
        return classList;
        
    }

    @Override
    public int getCount() {
        return followers.size();
    }
    
    @Override
    public int getItemPosition(Object className)
    {
        for (Follower follower : followers)
        {
            if (follower.getName().equalsIgnoreCase((String)className))
            {
                return followers.indexOf(follower);
            }
        }
        return -1;
    }
    
    public void setOnLoadFragmentsCompleteListener(OnLoadFragmentsCompleteListener listener)
    {
        this.listener = listener;
    }

}

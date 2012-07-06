package com.wemakestuff.diablo3builder.followers;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wemakestuff.diablo3builder.model.D3Application;
import com.wemakestuff.diablo3builder.model.Follower;

public class FollowerFragmentAdapter extends FragmentPagerAdapter
{
    private List<Follower> followers;
    private Context context;

    public FollowerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.followers = D3Application.getInstance().getFollowers();
    }

    @Override
    public String getPageTitle(int position) {
        return followers.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        FollowerListFragment classList = FollowerListFragment.newInstance(followers.get(position).getName(), context);
        return classList;
        
    }
    
    @Override
    public int getCount() {
        return followers.size();
    }
    
    @Override
    public int getItemPosition(Object className)
    {
        String followerName = null;
        
        if (className instanceof FollowerListFragment)
        {
            followerName = ((FollowerListFragment) className).getSelectedFollower();
        }
        else if (className instanceof String)
        {
            followerName = (String) className;
        }
        
        for (Follower follower : followers)
        {
            if (follower.getName().equalsIgnoreCase(followerName))
            {
                return followers.indexOf(follower);
            }
        }
        return -1;
    }
    
}

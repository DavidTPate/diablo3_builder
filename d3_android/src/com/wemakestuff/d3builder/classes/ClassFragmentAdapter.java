package com.wemakestuff.d3builder.classes;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;
import com.wemakestuff.d3builder.ClassListFragment;
import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.classes.listener.OnClassFragmentLoadListener;
import com.wemakestuff.d3builder.model.Class;
import com.wemakestuff.d3builder.model.D3Application;

public class ClassFragmentAdapter extends FragmentPagerAdapter implements TitleProvider
{
    private List<Class> classes;
    private Context context;
    public OnClassFragmentLoadListener listener;

    public ClassFragmentAdapter(FragmentManager fm, Context context, OnClassFragmentLoadListener listener) {
        super(fm);
        this.context = context;
        this.classes = D3Application.getInstance().getClasses();
        this.listener = listener;
    }

    @Override
    public String getTitle(int position) {
        return classes.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        ClassListFragment classList = ClassListFragment.newInstance(classes.get(position).getName(), context, null);
        return classList;
        
    }

    @Override
    public int getCount() {
        return classes.size();
    }
    
    @Override
    public int getItemPosition(Object className)
    {
        for (Class clss : classes)
        {
            if (clss.getName().equalsIgnoreCase((String)className))
            {
                return classes.indexOf(clss);
            }
        }
        return -1;
    }
    
    public void setOnLoadFragmentsCompleteListener(OnClassFragmentLoadListener listener)
    {
        this.listener = listener;
    }

}
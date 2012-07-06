package com.wemakestuff.diablo3builder;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wemakestuff.diablo3builder.model.Class;
import com.wemakestuff.diablo3builder.model.D3Application;

public class ClassFragmentAdapter extends FragmentPagerAdapter
{
    private List<Class> classes;
    private Context context;
    public OnLoadFragmentsCompleteListener listener;

    public ClassFragmentAdapter(FragmentManager fm, Context context, OnLoadFragmentsCompleteListener listener) {
        super(fm);
        this.context = context;
        this.classes = D3Application.getInstance().getClasses();
        this.listener = listener;
    }

    @Override
    public String getPageTitle(int position) {
        return classes.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        ClassListFragment classList = ClassListFragment.newInstance(classes.get(position).getName(), context, listener);
        return classList;
        
    }

    @Override
    public int getCount() {
        return classes.size();
    }
    
    @Override
    public int getItemPosition(Object className)
    {
        String tempClassName = null;
        
        if (className instanceof ClassListFragment)
        {
            tempClassName = ((ClassListFragment) className).getSelectedClass();
        }
        else if (className instanceof String)
        {
            tempClassName = (String) className;
        }
        
        for (Class clss : classes)
        {
            if (clss.getName().equalsIgnoreCase(tempClassName))
            {
                return classes.indexOf(clss);
            }
        }
        return -1;
    }
    
    public void setOnLoadFragmentsCompleteListener(OnLoadFragmentsCompleteListener listener)
    {
        this.listener = listener;
    }

}

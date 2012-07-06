
package com.wemakestuff.diablo3builder.classes;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wemakestuff.diablo3builder.model.Class;
import com.wemakestuff.diablo3builder.model.D3Application;

public class ClassFragmentAdapter extends FragmentPagerAdapter
{
    private List<Class> classes;

    public ClassFragmentAdapter(FragmentManager fm)
    {
        super(fm);
        this.classes = D3Application.getInstance().getClasses();
    }

    @Override
    public Fragment getItem(int position)
    {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCount()
    {
        return classes.size();
    }
    
    @Override
    public String getPageTitle(int position) {
        return classes.get(position).getName();
    }

    @Override
    public int getItemPosition(Object className)
    {
        /*
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
        }*/
        return -1;
    }

}

package com.pate.diablo;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.pate.diablo.model.Class;
import com.pate.diablo.model.D3Application;
import com.viewpagerindicator.TitleProvider;

public class ClassFragmentAdapter extends FragmentPagerAdapter implements TitleProvider
{
    private List<Class> classes;
    private Context context;

    public ClassFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.classes = D3Application.dataModel.getClasses();
    }

    @Override
    public String getTitle(int position) {
        return classes.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        ClassListFragment classList = ClassListFragment.newInstance(classes.get(position).getName(), context);
        return classList;
        
    }

    @Override
    public int getCount() {
        return classes.size();
    }

}

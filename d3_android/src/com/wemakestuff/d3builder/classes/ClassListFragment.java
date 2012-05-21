
package com.wemakestuff.d3builder.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListAdapter;

import com.wemakestuff.d3builder.classes.interfaces.OnClassFragmentLoadedInterface;
import com.wemakestuff.d3builder.followers.FollowerListFragment.OnRequiredLevelUpdateListener;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Follower;
import com.wemakestuff.d3builder.sectionlist.EmptyFollower;
import com.wemakestuff.d3builder.sectionlist.EmptyRune;
import com.wemakestuff.d3builder.sectionlist.EmptySkill;
import com.wemakestuff.d3builder.sectionlist.Item;
import com.wemakestuff.d3builder.sectionlist.SectionItem;
import com.wemakestuff.d3builder.widgets.ItemAdapter;

public class ClassListFragment extends ListFragment
{

    private String                         mSelectedClass;
    private OnClassFragmentLoadedInterface mLoadListener;
    private OnRequiredLevelUpdateListener  mRequiredLevelListener;

    public static ClassListFragment newInstance(String mSelectedClass)
    {

        ClassListFragment fragment = new ClassListFragment();

        fragment.mSelectedClass = mSelectedClass;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            mSelectedClass = savedInstanceState.getString("selectedClass");
        }

        setRetainInstance(true);
        setListAdapter(getSkillListAdapter());

    }

    private ItemAdapter getSkillListAdapter()
    {

        ListAdapter aAdapter = this.getListAdapter();
        if (aAdapter == null)
        {
            return getBlankSkillListAdapter(false);
        }
        else
        {
            return (ItemAdapter) this.getListAdapter();
        }
    }

    private ItemAdapter getBlankSkillListAdapter(boolean includeRunes)
    {

        ArrayList<Item> items = new ArrayList<Item>();
        String[] skillTypes = D3Application.getInstance().getClassAttributesByName(mSelectedClass).getSkillTypes();

        items.add(new SectionItem("Left Click - Primary"));
        items.add(new SectionItem(l, "Left Click - Primary"));
        items.add(new EmptySkill(l, "Choose Skill", 1, skillTypes[0]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));

        items.add(new SectionItem(l, "Right Click - Secondary"));
        items.add(new EmptySkill(l, "Choose Skill", 2, skillTypes[1]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));

        items.add(new SectionItem(l, "Action Bar Skills"));
        items.add(new EmptySkill(l, "Choose Skill", 4, skillTypes[2]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill(l, "Choose Skill", 9, skillTypes[3]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill(l, "Choose Skill", 14, skillTypes[4]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill(l, "Choose Skill", 19, skillTypes[5]));
        if (includeRunes)
            items.add(new EmptyRune(l, "Choose Rune", 1, "Rune", null));

        items.add(new SectionItem("Passive Skills"));
        items.add(new EmptySkill("Choose Skill", 10, "Passive"));
        items.add(new EmptySkill("Choose Skill", 20, "Passive"));
        items.add(new EmptySkill("Choose Skill", 30, "Passive"));
        items.add(new SectionItem(l, "Passive Skills"));
        items.add(new EmptySkill(l, "Choose Skill", 10, "Passive"));
        items.add(new EmptySkill(l, "Choose Skill", 20, "Passive"));
        items.add(new EmptySkill(l, "Choose Skill", 30, "Passive"));

        items.add(new SectionItem("Followers"));
        items.add(new SectionItem(l, "Followers"));
        for (Follower f : D3Application.getInstance().getFollowers())
        {
            items.add(new EmptyFollower(f.getName(), f.getShortDescription(), f.getIcon(), f.getUuid(), ""));
            items.add(new EmptyFollower(l, f.getName(), f.getShortDescription(), f.getIcon(), f.getUuid(), ""));
        }

        return new ItemAdapter(getActivity(), items);
    }

    public String getClassName()
    {

        return mSelectedClass;
    }

    @Override
    public void onAttach(Activity activity)
    {

        super.onAttach(activity);

        try
        {
            mRequiredLevelListener = (OnRequiredLevelUpdateListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnRequiredLevelUpdate(int level)");
        }

        try
        {
            mLoadListener = (OnClassFragmentLoadedInterface) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement onClassFragmentLoaded(ClassListFragment fragment)");
        }
    }
}

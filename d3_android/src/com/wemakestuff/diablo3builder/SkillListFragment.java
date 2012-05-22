package com.wemakestuff.diablo3builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.wemakestuff.diablo3builder.model.D3Application;
import com.wemakestuff.diablo3builder.model.Skill;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkill;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter;
import com.wemakestuff.diablo3builder.sectionlist.Item;

public class SkillListFragment extends ListFragment
{

    public interface OnSkillSelectedListener
    {
        public void OnSkillSelected(UUID skill);
    }

    private Context                 context;
    private String                  skillType;
    private String                  selectedClass;
    private int                     maxLevel    = 60;
    private static OnClickListener  listener;
    private List<ParcelUuid>        excludeSkills;
    private OnSkillSelectedListener onSkillSelectedListener;

    private ArrayList<Item>         items       = new ArrayList<Item>();
    private static final String     KEY_CONTENT = "TestFragment:Content";

    public static SkillListFragment newInstance(String content, Context c, String skillType, String selectedClass, int maxLevel, List<ParcelUuid> excludeSkills)
    {

        SkillListFragment fragment = new SkillListFragment();

        fragment.context = c;
        fragment.skillType = skillType;
        fragment.selectedClass = selectedClass;
        fragment.maxLevel = maxLevel;
        fragment.excludeSkills = excludeSkills;

        return fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            onSkillSelectedListener = (OnSkillSelectedListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnSkillSelectedListener(UUID skill)");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        LayoutInflater l = LayoutInflater.from(getActivity());

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey("skillType"))
            {
                skillType = savedInstanceState.getString("skillType");
            }

            if (savedInstanceState.containsKey("selectedClass"))
            {
                selectedClass = savedInstanceState.getString("selectedClass");
            }

            if (savedInstanceState.containsKey("maxLevel"))
            {
                maxLevel = savedInstanceState.getInt("maxLevel");
            }

        }

        setRetainInstance(true);
        for (Skill s : D3Application.getInstance().getClassByName(selectedClass).getActiveSkillsByTypeAndRequiredLevel(skillType, maxLevel))
        {
            // Exclude already selected skills
            if (excludeSkills == null || !excludeSkills.contains(new ParcelUuid(s.getUuid())))
                items.add(new EntrySkill(l, s));
        }
        EntrySkillAdapter adapter = new EntrySkillAdapter(getActivity(), items);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putString("skillType", skillType);
        outState.putString("selectedClass", selectedClass);
        outState.putInt("maxLevel", maxLevel);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Item item = (Item) getListAdapter().getItem(position);
        if (item instanceof EntrySkill)
        {
            EntrySkill e = (EntrySkill) item;
            onSkillSelectedListener.OnSkillSelected(e.getSkill().getUuid());
        }
    }

}

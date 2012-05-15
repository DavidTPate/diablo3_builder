package com.pate.diablo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.pate.diablo.model.D3Application;
import com.pate.diablo.model.Skill;
import com.pate.diablo.sectionlist.EntrySkill;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;

public class SkillListFragment extends ListFragment
{
   Context context;
    String skillType;
    String selectedClass;
    int requiredLevel;
    OnClickListener listener;
    List<ParcelUuid> excludeSkills;
    
    ArrayList<Item> items = new ArrayList<Item>();
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static SkillListFragment newInstance(String content, Context c, String skillType, String selectedClass, int requiredLevel, List<ParcelUuid> excludeSkills) {
        SkillListFragment fragment = new SkillListFragment();

        fragment.context = c;
        fragment.skillType = skillType;
        fragment.selectedClass = selectedClass;
        fragment.requiredLevel = requiredLevel;
        fragment.excludeSkills = excludeSkills;
        
        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        for (Skill s : D3Application.dataModel.getClassByName(selectedClass).getActiveSkillsByTypeAndRequiredLevel(skillType, requiredLevel))
        {
            // Exclude already selected skills
            if (excludeSkills == null || !excludeSkills.contains(new ParcelUuid(s.getUuid())))
                items.add(new EntrySkill(s));
        }
        EntrySkillAdapter adapter = new EntrySkillAdapter(context, items);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
    public void setOnListItemClickListener(OnClickListener listener)
    {
    	this.listener = listener;
    }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		listener.onClick(v);
		super.onListItemClick(l, v, position, id);
	}
    
}

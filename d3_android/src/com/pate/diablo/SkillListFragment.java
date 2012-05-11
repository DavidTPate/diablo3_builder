package com.pate.diablo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.pate.diablo.model.D3Application;
import com.pate.diablo.model.Skill;
import com.pate.diablo.sectionlist.EntrySkill;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;

public class SkillListFragment extends ListFragment
{
    static Context context;
    
    ArrayList<Item> items = new ArrayList<Item>();
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static SkillListFragment newInstance(String content, Context c) {
        context = c;
        SkillListFragment fragment = new SkillListFragment();
        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        for (Skill s : D3Application.dataModel.getClassByName("Barbarian").getActiveSkills())
        {
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
}

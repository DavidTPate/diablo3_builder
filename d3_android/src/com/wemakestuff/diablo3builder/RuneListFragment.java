package com.wemakestuff.diablo3builder;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.wemakestuff.diablo3builder.model.D3Application;
import com.wemakestuff.diablo3builder.model.Rune;
import com.wemakestuff.diablo3builder.sectionlist.EntryRune;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter;
import com.wemakestuff.diablo3builder.sectionlist.Item;

public class RuneListFragment extends ListFragment
{

    public interface OnRuneSelectedListener
    {
        public void OnRuneSelected(UUID rune);
    }

    Context                        context;
    String                         skillName;
    String                         selectedClass;
    int                            maxLevel;
    static OnClickListener         listener;
    UUID                           skillUUID;
    private OnRuneSelectedListener onRuneSelectedListener;

    ArrayList<Item>                items       = new ArrayList<Item>();
    private static final String    KEY_CONTENT = "TestFragment:Content";

    public static RuneListFragment newInstance(String content, Context c, String skillName, UUID skillUUID, String selectedClass, int maxLevel)
    {

        RuneListFragment fragment = new RuneListFragment();

        fragment.context = c;
        fragment.skillName = skillName;
        fragment.selectedClass = selectedClass;
        fragment.maxLevel = maxLevel;
        fragment.skillUUID = skillUUID;

        return fragment;
    }
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            onRuneSelectedListener = (OnRuneSelectedListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnRuneSelectedListener(UUID skill)");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        LayoutInflater l = LayoutInflater.from(getActivity());

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey("skillName"))
            {
                skillName = savedInstanceState.getString("skillName");
            }

            if (savedInstanceState.containsKey("selectedClass"))
            {
                selectedClass = savedInstanceState.getString("selectedClass");
            }

            if (savedInstanceState.containsKey("maxLevel"))
            {
                maxLevel = savedInstanceState.getInt("maxLevel");
            }

            if (savedInstanceState.containsKey("skillUUID"))
            {
                skillUUID = (UUID) savedInstanceState.getSerializable("skillUUID");
            }

        }

        for (Rune s : D3Application.getInstance().getClassByName(selectedClass).getActiveSkillByUUID(skillUUID).getRunes())
        {
            items.add(new EntryRune(l, s, skillName, skillUUID));
        }
        EntrySkillAdapter adapter = new EntrySkillAdapter(getActivity(), items);

        setListAdapter(adapter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putString("skillName", skillName);
        outState.putString("selectedClass", selectedClass);
        outState.putInt("maxLevel", maxLevel);
        outState.putSerializable("skillUUID", skillUUID);
    }

    public void setOnListItemClickListener(OnClickListener listener)
    {

        this.listener = listener;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {

        super.onListItemClick(l, v, position, id);
        Item item = (Item) getListAdapter().getItem(position);
        if (item instanceof EntryRune)
        {
            EntryRune e = (EntryRune) item;
            onRuneSelectedListener.OnRuneSelected(e.getRune().getUuid());
        }
    }

}

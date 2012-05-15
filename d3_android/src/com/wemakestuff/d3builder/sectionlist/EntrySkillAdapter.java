package com.wemakestuff.d3builder.sectionlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EntrySkillAdapter extends ArrayAdapter<Item>
{

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater  vi;

    public EntrySkillAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item i = items.get(position);
        return i.inflate(context, i);
    }
    
    public List<ParcelUuid> getCurrentSkills()
    {
        List<ParcelUuid> currentSkills = new ArrayList<ParcelUuid>();
        
        for (Item i : items)
        {
            if (i instanceof EntrySkill)
                currentSkills.add(new ParcelUuid(((EntrySkill) i).getSkill().getUuid()));
        }
        
        return currentSkills;
    }
    
    public void setList(ArrayList<Item> items)
    {
    	this.items = items;
    	notifyDataSetChanged();
    }
    
    public ArrayList<Item> getItems()
    {
    	return this.items;
    }

    public int getMaxLevel() 
    {
        int requiredLevel = 1;
        
        for (Item i : items)
        {
            if (i instanceof EntrySkill)
            {
                int tempLevel = ((EntrySkill) i).getSkill().getRequiredLevel();
                
                if (tempLevel > requiredLevel)
                    requiredLevel = tempLevel;
                
            }
            else if (i instanceof EntryRune)
            {
                int tempLevel = ((EntryRune) i).getRune().getRequiredLevel();
                
                if (tempLevel > requiredLevel)
                    requiredLevel = tempLevel;
            }
        }
        
        return requiredLevel;
    }

}

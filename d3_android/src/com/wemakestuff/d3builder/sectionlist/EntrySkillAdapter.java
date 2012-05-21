package com.wemakestuff.d3builder.sectionlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Skill;

public class EntrySkillAdapter extends ArrayAdapter<Item>
{

    private ArrayList<Item> items;
    private LayoutInflater inflater;
    
    public EntrySkillAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Item i = items.get(position);
        
        Log.i("Postion - " + i, "View Type =" + getItemViewType(position));
        if (i == null)
        {
            Log.e("List item " + position + " was null", "Not inflating view");
            return v;
        }

        v = inflater.inflate(i.getViewResource(), null);
        v = i.inflate(v, i);
        
        return v;
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
    
    public Item getFollowerSkillByUUID(UUID uuid)
    {
        for (Item i : items)
        {
            if (i instanceof EntryFollowerSkill)
                if (((EntryFollowerSkill) i).getSkill().getUuid().equals(uuid))
                        return i;
        }
        
        return null;
    }
    
    public void setList(ArrayList<Item> items)
    {
    	this.items = items;
    	notifyDataSetChanged();
    }
    
    public List<Item> getFollowers()
    {
        List<Item> ret = new ArrayList<Item>();
        for (Item i : items)
        {
            if (i instanceof EmptyFollower)
                ret.add(i);

        }
        
        return ret;
    }
    
    public Item getFollowerByName(String name)
    {
        for (Item i : items)
        {
            if (i instanceof EmptyFollower)
            {
                EmptyFollower e = (EmptyFollower) i;
                if (e.getName().equalsIgnoreCase(name))
                    return i;
            }

        }
        
        return null;
    }
    
    public ArrayList<Item> getItems()
    {
    	return this.items;
    }

    public int getFollowerMaxLevel(String followerName)
    {
        int requiredLevel = 1;
        List<Skill> followerSkills = D3Application.getInstance().getFollowerByName(followerName).getSkills();
        
        for (Item i : items)
        {
            if (i instanceof EntryFollowerSkill)
            {
                EntryFollowerSkill efs = (EntryFollowerSkill) i;
                Skill s = efs.getSkill();

                if (followerSkills.contains(s))
                {
                    int tempLevel = s.getRequiredLevel();
                    boolean isChecked = efs.isChecked();
                    
                    if (tempLevel > requiredLevel && isChecked)
                        requiredLevel = tempLevel;
                }
            }
            
        }
        
        return requiredLevel;
    }
    
    public int getMaxLevel(boolean isFollower) 
    {
        int requiredLevel = 1;
        
        for (Item i : items)
        {
            if (isFollower)
            {
                if (i instanceof EntryFollowerSkill)
                {
                    int tempLevel = ((EntryFollowerSkill) i).getSkill().getRequiredLevel();
                    boolean isChecked = ((EntryFollowerSkill) i).isChecked();
                    
                    if (tempLevel > requiredLevel && isChecked)
                        requiredLevel = tempLevel;
                }
            }
            else
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
            
        }
        
        return requiredLevel;
    }

}

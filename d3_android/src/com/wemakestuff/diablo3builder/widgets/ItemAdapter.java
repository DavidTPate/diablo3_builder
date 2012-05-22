package com.wemakestuff.diablo3builder.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.ParcelUuid;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wemakestuff.diablo3builder.model.D3Application;
import com.wemakestuff.diablo3builder.model.Skill;
import com.wemakestuff.diablo3builder.sectionlist.EmptyFollower;
import com.wemakestuff.diablo3builder.sectionlist.EntryFollowerSkill;
import com.wemakestuff.diablo3builder.sectionlist.EntryRune;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkill;
import com.wemakestuff.diablo3builder.sectionlist.Item;

public class ItemAdapter extends ArrayAdapter<Item>
{

    public enum RowType
    {
        SECTION_ITEM, EMPTY_FOLLOWER, EMPTY_FOLLOWER_SKILL, EMPTY_RUNE, ENTRY_RUNE, EMPTY_SKILL, ENTRY_SKILL
    }

    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items)
    {
        super(context, 0, items);
        this.items = items;
    }

    @Override
    public int getViewTypeCount()
    {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position)
    {
        return items.get(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return items.get(position).getView(convertView);
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

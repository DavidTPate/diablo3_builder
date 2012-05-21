package com.wemakestuff.d3builder.sectionlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.sectionlist.EntrySkillAdapter.RowType;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EmptyFollower implements Item
{
    private final String         name;
    private final String         iconName;
    private final String         description;
    private final UUID           followerUUID;
    private String               url;
    private List<ParcelUuid>     skills = new ArrayList<ParcelUuid>(); ;
    private final LayoutInflater inflater;
    
    public EmptyFollower(LayoutInflater inflater, String name, String shortDescription, String icon, UUID followerUUID, String url)
    {
        this.name = name;
        this.description = shortDescription;
        this.iconName = icon;
        this.followerUUID = followerUUID;
        this.url = url;
        this.inflater = inflater;
    }

    private static class ViewHolder
    {
        final LinearLayout emptyFollowerSkills;
        final TextView     emptyFollowerSkill1;
        final TextView     emptyFollowerSkill2;
        final TextView     emptyFollowerSkill3;
        final TextView     emptyFollowerSkill4;
        final TextView     emptyFollowerSkill1Description;
        final TextView     emptyFollowerSkill2Description;
        final TextView     emptyFollowerSkill3Description;
        final TextView     emptyFollowerSkill4Description;

        final ImageView    icon;
        final TextView     emptyItemName;
        final TextView     emptyFollowerDescription;

        public ViewHolder(LinearLayout emptyFollowerSkills, TextView emptyFollowerSkill1, TextView emptyFollowerSkill2, TextView emptyFollowerSkill3,
                TextView emptyFollowerSkill4, TextView emptyFollowerSkill1Description, TextView emptyFollowerSkill2Description,
                TextView emptyFollowerSkill3Description, TextView emptyFollowerSkill4Description, ImageView icon, TextView emptyItemName,
                TextView emptyFollowerDescription)
        {
            super();
            this.emptyFollowerSkills = emptyFollowerSkills;
            this.emptyFollowerSkill1 = emptyFollowerSkill1;
            this.emptyFollowerSkill2 = emptyFollowerSkill2;
            this.emptyFollowerSkill3 = emptyFollowerSkill3;
            this.emptyFollowerSkill4 = emptyFollowerSkill4;
            this.emptyFollowerSkill1Description = emptyFollowerSkill1Description;
            this.emptyFollowerSkill2Description = emptyFollowerSkill2Description;
            this.emptyFollowerSkill3Description = emptyFollowerSkill3Description;
            this.emptyFollowerSkill4Description = emptyFollowerSkill4Description;
            this.icon = icon;
            this.emptyItemName = emptyItemName;
            this.emptyFollowerDescription = emptyFollowerDescription;
        }

    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public String getIcon()
    {
        return iconName;
    }

    public String getName()
    {
        return name;
    }

    public UUID getFollowerUUID()
    {
        return followerUUID;
    }

    public List<ParcelUuid> getSkills()
    {
        return skills;
    }

    public String getFollowerUrl()
    {
        return url;
    }

    public void setSkills(List<ParcelUuid> list)
    {
        this.skills = list;
    }

    public ViewHolder setSkills(ViewHolder holder, List<ParcelUuid> list)
    {
        this.skills = list;

        holder.emptyFollowerSkill1.setVisibility(View.GONE);
        holder.emptyFollowerSkill2.setVisibility(View.GONE);
        holder.emptyFollowerSkill3.setVisibility(View.GONE);
        holder.emptyFollowerSkill4.setVisibility(View.GONE);
        holder.emptyFollowerSkill1Description.setVisibility(View.GONE);
        holder.emptyFollowerSkill2Description.setVisibility(View.GONE);
        holder.emptyFollowerSkill3Description.setVisibility(View.GONE);
        holder.emptyFollowerSkill4Description.setVisibility(View.GONE);
        
        int index = 0;
        for (ParcelUuid u : skills)
        {
            Skill s = D3Application.getInstance().getFollowerByName(name).getSkillByUUID(u.getUuid());

            if (s == null)
            {
                continue;
            }

            if (index == 0)
            {
                holder.emptyFollowerSkill1.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
                holder.emptyFollowerSkill1Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

                holder.emptyFollowerSkill1.setVisibility(View.VISIBLE);
                holder.emptyFollowerSkill1Description.setVisibility(View.VISIBLE);

            }
            else if (index == 1)
            {
                holder.emptyFollowerSkill2.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
                holder.emptyFollowerSkill2Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

                holder.emptyFollowerSkill2.setVisibility(View.VISIBLE);
                holder.emptyFollowerSkill2Description.setVisibility(View.VISIBLE);
            }
            else if (index == 2)
            {
                holder.emptyFollowerSkill3.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
                holder.emptyFollowerSkill3Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

                holder.emptyFollowerSkill3.setVisibility(View.VISIBLE);
                holder.emptyFollowerSkill3Description.setVisibility(View.VISIBLE);
            }
            else if (index == 3)
            {
                holder.emptyFollowerSkill4.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
                holder.emptyFollowerSkill4Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

                holder.emptyFollowerSkill4.setVisibility(View.VISIBLE);
                holder.emptyFollowerSkill4Description.setVisibility(View.VISIBLE);
            }
            index++;
        }

        if (list.size() > 0)
            holder.emptyFollowerSkills.setVisibility(View.VISIBLE);
        else
            holder.emptyFollowerSkills.setVisibility(View.GONE);
        
        
        return holder;
    }

    @Override
    public int getViewType()
    {
        return RowType.EMPTY_FOLLOWER.ordinal();
    }

    @Override
    public View getView(View convertView)
    {
        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_empty_follower, null);
            //@formatter:off
            holder = new ViewHolder((LinearLayout) v.findViewById(R.id.empty_follower_skills)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_1)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_2)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_3)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_4)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_1_description)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_2_description)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_3_description)
                                  , (TextView) v.findViewById(R.id.empty_follower_skill_4_description)
                                  , (ImageView) v.findViewById (R.id.empty_follower_icon)
                                  , (TextView) v.findViewById(R.id.empty_follower_name)
                                  , (TextView) v.findViewById(R.id.empty_follower_description));
            //@formatter:on
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        int iconImage = view.getContext().getResources().getIdentifier("drawable/" + iconName, null, view.getContext().getPackageName());
        holder.emptyItemName.setText(name);
        holder.emptyFollowerDescription.setText(description);
        holder.icon.setImageResource(iconImage);

        holder = setSkills(holder, skills);
        
        if (skills != null && skills.size() > 0)
            holder.emptyFollowerSkills.setVisibility(View.VISIBLE);
        else
            holder.emptyFollowerSkills.setVisibility(View.GONE);
        
        return view;
    }

}

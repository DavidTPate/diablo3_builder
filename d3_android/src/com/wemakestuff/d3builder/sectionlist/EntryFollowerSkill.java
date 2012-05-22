package com.wemakestuff.d3builder.sectionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.sectionlist.EntrySkillAdapter.RowType;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Replacer.D3Color;

public class EntryFollowerSkill implements Item
{

    private final Skill          skill;
    private final String         followerName;
    private boolean              isChecked;
    private ImageView            checkmark;
    private final LayoutInflater inflater;
    ViewHolder holder;

    public EntryFollowerSkill(LayoutInflater inflater, Skill skill, String followerName, boolean isChecked)
    {
        this.skill = skill;
        this.followerName = followerName;
        this.isChecked = isChecked;
        this.inflater = inflater;

    }

    public Skill getSkill()
    {
        return skill;
    }

    public void setIsChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
//        if (holder != null && holder.checkmark != null)
//        {
//            holder.checkmark.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//        }
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    private static class ViewHolder
    {
        final ImageView checkmark;
        final ImageView skillIcon;
        final TextView  skillName;
        final TextView  unlockedAt;
        final TextView  skillCooldown;
        final TextView  skillDescription;

        public ViewHolder(ImageView checkmark, ImageView skillIcon, TextView skillName, TextView unlockedAt, TextView skillCooldown, TextView skillDescription)
        {
            super();
            this.checkmark = checkmark;
            this.skillIcon = skillIcon;
            this.skillName = skillName;
            this.unlockedAt = unlockedAt;
            this.skillCooldown = skillCooldown;
            this.skillDescription = skillDescription;
        }

    }

    public View getView(View convertView)
    {
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_follower_skill, null);

            //@formatter:off
            holder = new ViewHolder((ImageView) v.findViewById(R.id.follower_checkmark)
                                  , (ImageView) v.findViewById(R.id.follower_skill_icon)
                                  ,  (TextView) v.findViewById(R.id.follower_skill_title)
                                  ,  (TextView) v.findViewById(R.id.follower_skill_unlocked_at)
                                  ,  (TextView) v.findViewById(R.id.follower_skill_cooldown)
                                  ,  (TextView) v.findViewById(R.id.follower_skill_description));
            
            //@formatter:on
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        // Is this a terrible hack?! I think so...
        String icon = followerName.toLowerCase() + "_" + skill.getName().replace(" ", "").toLowerCase();
        int skillImage = view.getContext().getResources().getIdentifier("drawable/" + icon, null, view.getContext().getPackageName());

        holder.skillIcon.setImageResource(skillImage);
        holder.skillName.setText(skill.getName());
        holder.unlockedAt.setText("Unlocked at level: " + skill.getRequiredLevel());

        if (skill.getCooldownText() == null || skill.getCooldownText().equals(""))
        {
            holder.skillCooldown.setText(Replacer.replace(skill.getCooldownText(), "\\d+%?", D3Color.DIABLO_GREEN));
            holder.skillCooldown.setVisibility(View.GONE);
        }
        else
        {
            holder.skillCooldown.setText(Replacer.replace(view.getContext().getString(R.string.Cooldown) + " " + skill.getCooldownText(), "\\d+%?",
                    D3Color.DIABLO_GREEN));
            holder.skillCooldown.setVisibility(View.VISIBLE);
        }

        if (skill.getDescription() == null || skill.getDescription().equals(""))
        {
            holder.skillDescription.setVisibility(View.GONE);
        }
        else
        {
            holder.skillDescription.setText(Replacer.replace(skill.getDescription().trim(), "\\d+%?", D3Color.DIABLO_GREEN));
            holder.skillDescription.setVisibility(View.VISIBLE);
        }

        holder.checkmark.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        return view;
    }

    @Override
    public int getViewType()
    {
        return RowType.EMPTY_FOLLOWER_SKILL.ordinal();
    }

}

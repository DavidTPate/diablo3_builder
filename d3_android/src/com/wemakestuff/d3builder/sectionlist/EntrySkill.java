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
import com.wemakestuff.d3builder.string.Vars;

public class EntrySkill implements Item
{

    private final Skill          skill;
    private final LayoutInflater inflater;

    public EntrySkill(LayoutInflater inflater, Skill skill)
    {
        this.skill = skill;
        this.inflater = inflater;
    }

    public Skill getSkill()
    {
        return skill;
    }

    @Override
    public int getViewType()
    {
        return RowType.ENTRY_SKILL.ordinal();
    }

    @Override
    public View getView(View convertView)
    {
        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_skill, null);

            //@formatter:off
            holder = new ViewHolder((ImageView) v.findViewById(R.id.list_skill_icon)
                                  , (TextView) v.findViewById(R.id.list_skill_title)
                                  , (TextView) v.findViewById(R.id.list_skill_cost_text)
                                  , (TextView) v.findViewById(R.id.list_skill_generates)
                                  , (TextView) v.findViewById(R.id.list_skill_cooldown)
                                  , (TextView) v.findViewById(R.id.list_skill_unlocked_at)
                                  , (TextView) v.findViewById(R.id.list_skill_description));
            //@formatter:on
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        int skillImage = view.getContext().getResources().getIdentifier("drawable/" + skill.getIcon(), null, view.getContext().getPackageName());

        holder.skillIcon.setImageResource(skillImage);
        holder.skillName.setText(skill.getName());

        if (skill.getCostText() == null || skill.getCostText().equals(""))
        {
            holder.skillCost.setVisibility(View.GONE);
        }
        else
        {
            holder.skillCost.setText(Replacer.replace(view.getContext().getString(R.string.Cost) + " " + skill.getCostText(), "\\d+%?", Vars.DIABLO_GREEN));
            holder.skillCost.setVisibility(View.VISIBLE);
        }

        if (skill.getGenerateText() == null || skill.getGenerateText().equals(""))
        {
            holder.skillGenerates.setVisibility(View.GONE);
        }
        else
        {
            holder.skillGenerates.setText(Replacer.replace(view.getContext().getString(R.string.Generate) + " " + skill.getGenerateText(), "\\d+%?",
                    Vars.DIABLO_GREEN));
            holder.skillGenerates.setVisibility(View.VISIBLE);
        }

        if (skill.getCooldownText() == null || skill.getCooldownText().equals(""))
        {
            holder.skillCooldown.setText(Replacer.replace(skill.getCooldownText(), "\\d+%?", Vars.DIABLO_GREEN));
            holder.skillCooldown.setVisibility(View.GONE);
        }
        else
        {
            holder.skillCooldown.setText(Replacer.replace(view.getContext().getString(R.string.Cooldown) + " " + skill.getCooldownText(), "\\d+%?",
                    Vars.DIABLO_GREEN));
            holder.skillCooldown.setVisibility(View.VISIBLE);
        }

        if (skill.getRequiredLevel() == 0)
        {
            holder.skillRequiredLevel.setVisibility(View.GONE);
        }
        else
        {
            holder.skillRequiredLevel.setText(Replacer.replace(
                    view.getContext().getString(R.string.Unlocked_at_level) + " " + String.valueOf(skill.getRequiredLevel()), "\\d+%?", Vars.DIABLO_GREEN));
            holder.skillRequiredLevel.setVisibility(View.VISIBLE);
        }

        if (skill.getDescription() == null || skill.getDescription().equals(""))
        {
            holder.skillDescription.setVisibility(View.GONE);
        }
        else
        {
            holder.skillDescription.setText(Replacer.replace(skill.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
            holder.skillDescription.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private static class ViewHolder
    {
        final ImageView skillIcon;
        final TextView  skillName;
        final TextView  skillCost;
        final TextView  skillGenerates;
        final TextView  skillCooldown;
        final TextView  skillRequiredLevel;
        final TextView  skillDescription;

        public ViewHolder(ImageView skillIcon, TextView skillName, TextView skillCost, TextView skillGenerates, TextView skillCooldown,
                TextView skillRequiredLevel, TextView skillDescription)
        {
            super();
            this.skillIcon = skillIcon;
            this.skillName = skillName;
            this.skillCost = skillCost;
            this.skillGenerates = skillGenerates;
            this.skillCooldown = skillCooldown;
            this.skillRequiredLevel = skillRequiredLevel;
            this.skillDescription = skillDescription;
        }
    }

}

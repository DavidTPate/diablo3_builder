package com.wemakestuff.d3builder.sectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EntrySkill implements Item
{

    private final Skill skill;
    private ImageView skillIcon;
    private TextView skillName;
    private TextView skillCost;
    private TextView skillGenerates;
    private TextView skillCooldown;
    private TextView skillRequiredLevel;
    private TextView skillDescription;


    public EntrySkill(Skill skill)
    {
        this.skill = skill;
    }

    public Skill getSkill()
    {
        return skill;
    }

    @Override
    public View inflate(View v, Item i)
    {

        Skill s = ((EntrySkill) i).getSkill();

        skillIcon = (ImageView) v.findViewById(R.id.list_skill_icon);
        skillName = (TextView) v.findViewById(R.id.list_skill_title);
        skillCost = (TextView) v.findViewById(R.id.list_skill_cost_text);
        skillGenerates = (TextView) v.findViewById(R.id.list_skill_generates);
        skillCooldown = (TextView) v.findViewById(R.id.list_skill_cooldown);
        skillRequiredLevel = (TextView) v.findViewById(R.id.list_skill_unlocked_at);
        skillDescription = (TextView) v.findViewById(R.id.list_skill_description);

        // Is this a terrible hack?! I think so...
        int skillImage = v.getContext().getResources().getIdentifier("drawable/" + s.getIcon(), null, v.getContext().getPackageName());

        //@formatter:off
                skillIcon         .setImageResource(skillImage);
                skillName         .setText(s.getName());

                if (s.getCostText() == null || s.getCostText().equals(""))
                {
                    skillCost.setVisibility(View.GONE);
                }
                else
                {
                    skillCost.setText(Replacer.replace(v.getContext().getString(R.string.Cost) + " " + s.getCostText(), "\\d+%?", Vars.DIABLO_GREEN));
                    skillCost.setVisibility(View.VISIBLE);
                }

                if (s.getGenerateText() == null || s.getGenerateText().equals(""))
                {
                    skillGenerates.setVisibility(View.GONE);
                }
                else
                {
                    skillGenerates.setText(Replacer.replace(v.getContext().getString(R.string.Generate) + " " + s.getGenerateText(), "\\d+%?", Vars.DIABLO_GREEN));
                    skillGenerates.setVisibility(View.VISIBLE);
                }

                if (s.getCooldownText() == null || s.getCooldownText().equals(""))
                {
                    skillCooldown.setText(Replacer.replace(s.getCooldownText(), "\\d+%?", Vars.DIABLO_GREEN));
                    skillCooldown.setVisibility(View.GONE);
                }
                else
                {
                    skillCooldown.setText(Replacer.replace(v.getContext().getString(R.string.Cooldown) + " " + s.getCooldownText(), "\\d+%?", Vars.DIABLO_GREEN));
                    skillCooldown.setVisibility(View.VISIBLE);
                }

                if (s.getRequiredLevel() == 0)
                {
                    skillRequiredLevel.setVisibility(View.GONE);
                }
                else
                {
                    skillRequiredLevel.setText(Replacer.replace(v.getContext().getString(R.string.Unlocked_at_level) + " " + String.valueOf(s.getRequiredLevel()), "\\d+%?", Vars.DIABLO_GREEN));
                    skillRequiredLevel.setVisibility(View.VISIBLE);
                }

                if (s.getDescription() == null || s.getDescription().equals(""))
                {
                    skillDescription.setVisibility(View.GONE);
                }
                else
                {
                    skillDescription.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
                    skillDescription.setVisibility(View.VISIBLE);
                }   
                
                v.setTag(s.getUuid());
        return v;
    }

    @Override
    public int getViewResource()
    {
        return R.layout.list_item_skill;
    }
	
}

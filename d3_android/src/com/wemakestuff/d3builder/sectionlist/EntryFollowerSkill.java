package com.wemakestuff.d3builder.sectionlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EntryFollowerSkill implements Item
{

    private final Skill  skill;
    private final String followerName;
    private boolean      isChecked;
    private ImageView    checkmark;
    private ImageView    skillIcon;
    private TextView     skillName;
    private TextView     unlockedAt;
    private TextView     skillCooldown;
    private TextView     skillDescription;

    public EntryFollowerSkill(Skill skill, String followerName, boolean isChecked)
    {
        this.skill = skill;
        this.followerName = followerName;
        this.isChecked = isChecked;
    }

    public Skill getSkill()
    {
        return skill;
    }

    public void setIsChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
        if (checkmark != null)
        {
            checkmark.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    @Override
    public int getViewResource()
    {
        return R.layout.list_item_follower_skill;
    }

    @Override
    public View inflate(View v, Item i)
    {

        Skill s = ((EntryFollowerSkill) i).getSkill();

        checkmark = (ImageView) v.findViewById(R.id.follower_checkmark);
        skillIcon = (ImageView) v.findViewById(R.id.follower_skill_icon);
        skillName = (TextView) v.findViewById(R.id.follower_skill_title);
        unlockedAt = (TextView) v.findViewById(R.id.follower_skill_unlocked_at);
        skillCooldown = (TextView) v.findViewById(R.id.follower_skill_cooldown);
        skillDescription = (TextView) v.findViewById(R.id.follower_skill_description);

        // Is this a terrible hack?! I think so...
        String icon = followerName.toLowerCase() + "_" + s.getName().replace(" ", "").toLowerCase();
        int skillImage = v.getContext().getResources().getIdentifier("drawable/" + icon, null, v.getContext().getPackageName());

        skillIcon.setImageResource(skillImage);
        skillName.setText(s.getName());
        unlockedAt.setText("Unlocked at level: " + s.getRequiredLevel());

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

        if (s.getDescription() == null || s.getDescription().equals(""))
        {
            skillDescription.setVisibility(View.GONE);
        }
        else
        {
            skillDescription.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
            skillDescription.setVisibility(View.VISIBLE);
        }

        checkmark.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        v.setTag(s.getUuid());
        return v;
    }

}

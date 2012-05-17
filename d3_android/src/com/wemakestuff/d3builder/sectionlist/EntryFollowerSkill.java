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

    private final Skill skill;
    private final String followerName;
    private boolean isChecked;
    private ImageView checkmark;
    
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
        if (checkmark != null)
        {
            Log.i("Setting checkmark to ", "" + isChecked);
            checkmark.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//            checkmark.postInvalidate();
        }
        else
        {
            Log.i("Checkmark was null, not setting it to", "" + isChecked);
        }
        
    }
    
    public boolean isChecked()
    { 
        return isChecked;
    }
    
    @Override
    public View inflate(Context c, Item i) {

        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Skill s = ((EntryFollowerSkill) i).getSkill();

        View v = vi.inflate(R.layout.list_item_follower_skill, null);

        checkmark = (ImageView) v.findViewById(R.id.follower_checkmark);
        final ImageView skillIcon = (ImageView) v.findViewById(R.id.follower_skill_icon);
        final TextView skillName = (TextView) v.findViewById(R.id.follower_skill_title);
        final TextView unlockedAt = (TextView) v.findViewById(R.id.follower_skill_unlocked_at);
        final TextView skillCooldown = (TextView) v.findViewById(R.id.follower_skill_cooldown);
        final TextView skillDescription = (TextView) v.findViewById(R.id.follower_skill_description);

        // Is this a terrible hack?! I think so...
        String icon = followerName.toLowerCase() + "_" + s.getName().replace(" ", "").toLowerCase();
        int skillImage = c.getResources().getIdentifier("drawable/" + icon, null, c.getPackageName());

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

package com.pate.diablo.sectionlist;

import java.util.UUID;

import com.pate.diablo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class EmptyRune implements Item
{
    private final String title;
    private final int level;
    private final String skillName;
    private final UUID skillUUID;
    
    public EmptyRune(String title, int level, String skillName, UUID skillUUID) {
        this.title = title;
        this.level = level;
        this.skillName = skillName;
        this.skillUUID = skillUUID;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public String getSkillName() {
        return skillName;
    }
    
    public UUID getSkillUUID() {
    	return skillUUID;
    }

    @Override
    public View inflate(Context c, Item i) {
        
        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        EmptyRune e = (EmptyRune) i;
        
        View v = vi.inflate(R.layout.list_item_empty, null);

        final TextView emptyItemTitle = (TextView) v.findViewById(R.id.list_empty_title);
        final TextView emptySkillType = (TextView) v.findViewById(R.id.list_empty_skill_type);
        
        // Is this a terrible hack?! I think so...
        emptyItemTitle.setText(e.getTitle());
        emptySkillType.setText(e.getSkillName());
        
        return v;
    }

}

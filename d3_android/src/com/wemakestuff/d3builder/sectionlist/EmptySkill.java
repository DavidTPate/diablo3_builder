package com.wemakestuff.d3builder.sectionlist;

import com.pate.diablo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class EmptySkill implements Item
{
    private final String title;
    private final int level;
    private final String skillType;
    
    public EmptySkill(String title, int level, String skillType) {
        this.title = title;
        this.level = level;
        this.skillType = skillType;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public String getSkillType() {
        return skillType;
    }

    @Override
    public View inflate(Context c, Item i) {
        
        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        EmptySkill e = (EmptySkill) i;
        
        View v = vi.inflate(R.layout.list_item_empty, null);

        final TextView emptyItemTitle = (TextView) v.findViewById(R.id.list_empty_title);
        final TextView emptySkillType = (TextView) v.findViewById(R.id.list_empty_skill_type);
        
        // Is this a terrible hack?! I think so...
        emptyItemTitle.setText(e.getTitle());
        emptySkillType.setText(e.getSkillType());
        
        return v;
    }

}
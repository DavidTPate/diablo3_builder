package com.wemakestuff.d3builder.sectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;

public class EmptySkill implements Item
{
    private final String title;
    private final int    level;
    private final String skillType;
    private TextView     emptyItemTitle;
    private TextView     emptySkillType;

    public EmptySkill(String title, int level, String skillType)
    {
        this.title = title;
        this.level = level;
        this.skillType = skillType;
    }

    public String getTitle()
    {
        return title;
    }

    public int getLevel()
    {
        return level;
    }

    public String getSkillType()
    {
        return skillType;
    }

    @Override
    public int getViewResource()
    {
        return R.layout.list_item_empty;
    }

    @Override
    public View inflate(View v, Item i)
    {

        EmptySkill e = (EmptySkill) i;

        emptyItemTitle = (TextView) v.findViewById(R.id.list_empty_title);
        emptySkillType = (TextView) v.findViewById(R.id.list_empty_skill_type);

        // Is this a terrible hack?! I think so...
        emptyItemTitle.setText(e.getTitle());
        emptySkillType.setText(e.getSkillType());

        return v;
    }

}

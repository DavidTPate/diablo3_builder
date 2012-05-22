package com.wemakestuff.diablo3builder.sectionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter.RowType;

public class EmptySkill implements Item
{
    private final String         title;
    private final int            level;
    private final String         skillType;
    private final LayoutInflater inflater;

    public EmptySkill(LayoutInflater inflater, String title, int level, String skillType)
    {
        this.title = title;
        this.level = level;
        this.skillType = skillType;
        this.inflater = inflater;
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
    public int getViewType()
    {
        return RowType.EMPTY_SKILL.ordinal();
    }

    @Override
    public View getView(View convertView)
    {
        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_empty, null);
            holder = new ViewHolder((TextView) v.findViewById(R.id.list_empty_title), (TextView) v.findViewById(R.id.list_empty_skill_type));
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.emptyItemTitle.setText(title);
        holder.emptySkillType.setText(skillType);

        return view;
    }

    private static class ViewHolder
    {
        final TextView emptyItemTitle;
        final TextView emptySkillType;

        private ViewHolder(TextView emptyItemTitle, TextView emptySkillType)
        {
            this.emptyItemTitle = emptyItemTitle;
            this.emptySkillType = emptySkillType;
        }
    }

}

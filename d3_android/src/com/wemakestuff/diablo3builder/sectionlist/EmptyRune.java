package com.wemakestuff.diablo3builder.sectionlist;

import java.util.UUID;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter.RowType;

public class EmptyRune implements Item {
	private final String title;
	private final int level;
	private final String skillName;
	private final UUID skillUUID;
	
	private TextView emptyItemTitle;
	private TextView emptySkillType;
	
	private final LayoutInflater inflater;

	public EmptyRune(LayoutInflater inflater, String title, int level, String skillName, UUID skillUUID) {
		this.title = title;
		this.level = level;
		this.skillName = skillName;
		this.skillUUID = skillUUID;
		this.inflater = inflater;
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

//	@Override
    public int getViewResource()
    {
        return R.layout.list_item_empty;
    }
	
    @Override
    public int getViewType()
    {
        return RowType.EMPTY_RUNE.ordinal();
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
        holder.emptySkillType.setText(skillName);
        holder.emptyItemTitle.setTextColor(Color.parseColor("#d49e43"));
        
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

package com.wemakestuff.d3builder.sectionlist;

import java.util.UUID;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.Rune;
import com.wemakestuff.d3builder.sectionlist.EntrySkillAdapter.RowType;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EntryRune implements Item
{

    private final Rune           rune;
    private final String         skillName;
    private final UUID           skillUUID;
    private final LayoutInflater inflater;

    public EntryRune(LayoutInflater inflater, Rune rune, String skillName, UUID skillUUID)
    {
        this.rune = rune;
        this.skillName = skillName;
        this.skillUUID = skillUUID;
        this.inflater = inflater;
    }

    public Rune getRune()
    {
        return rune;
    }

    public String getSkillName()
    {
        return skillName;
    }

    public UUID getSkillUUID()
    {
        return skillUUID;
    }

    @Override
    public int getViewType()
    {
        return RowType.ENTRY_RUNE.ordinal();
    }

    @Override
    public View getView(View convertView)
    {
        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_rune, null);
            //@formatter:off
            holder = new ViewHolder((ImageView) v.findViewById(R.id.list_rune_icon)
                                   , (TextView) v.findViewById(R.id.list_rune_title)
                                   , (TextView) v.findViewById(R.id.list_rune_unlocked_at)
                                   , (TextView) v.findViewById(R.id.list_rune_description));
            //@formatter:on
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        int runeImage = view.getContext().getResources().getIdentifier("drawable/" + rune.getIcon(), null, view.getContext().getPackageName());

        holder.runeIcon.setBackgroundResource(runeImage);
        holder.runeTitle.setText(getRune().getName());
        holder.runeUnlockedAt
                .setText(Replacer.replace(view.getContext().getString(R.string.Unlocked_at_level) + " " + rune.getRequiredLevel(), "\\d+%?", Vars.DIABLO_GREEN));
        holder.runeDescription.setText(Replacer.replace(rune.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

        //view.setTag(rune.getUuid());

        return view;
    }

    private static class ViewHolder {
        final ImageView runeIcon;
        final TextView runeTitle;
        final TextView runeUnlockedAt;
        final TextView runeDescription;
        
        public ViewHolder(ImageView runeIcon, TextView runeTitle, TextView runeUnlockedAt, TextView runeDescription)
        {
            super();
            this.runeIcon = runeIcon;
            this.runeTitle = runeTitle;
            this.runeUnlockedAt = runeUnlockedAt;
            this.runeDescription = runeDescription;
        }

    }
}

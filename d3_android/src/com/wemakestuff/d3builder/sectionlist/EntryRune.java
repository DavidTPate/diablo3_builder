package com.wemakestuff.d3builder.sectionlist;

import java.util.UUID;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.Rune;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EntryRune implements Item
{

    private final Rune   rune;
    private final String skillName;
    private final UUID   skillUUID;
    private ImageView runeIcon;
    private TextView runeTitle;
    private TextView runeUnlockedAt;
    private TextView runeDescription;

    public EntryRune(Rune rune, String skillName, UUID skillUUID) {
        this.rune = rune;
        this.skillName = skillName;
        this.skillUUID = skillUUID;
    }

    public Rune getRune() {
        return rune;
    }

    public String getSkillName() {
        return skillName;
    }

    public UUID getSkillUUID() {
        return skillUUID;
    }

    @Override
    public int getViewResource()
    {
        return R.layout.list_item_rune;
    }
    
    @Override
    public View inflate(View v, Item i) {


        Rune r = ((EntryRune) i).getRune();

        runeIcon = (ImageView) v.findViewById(R.id.list_rune_icon);
        runeTitle = (TextView) v.findViewById(R.id.list_rune_title);
        runeUnlockedAt = (TextView) v.findViewById(R.id.list_rune_unlocked_at);
        runeDescription = (TextView) v.findViewById(R.id.list_rune_description);

        // Is this a terrible hack?! I think so...
        int runeImage = v.getContext().getResources().getIdentifier("drawable/" + r.getIcon(), null, v.getContext().getPackageName());

        runeIcon.setBackgroundResource(runeImage);
        runeTitle.setText(getRune().getName());
        runeUnlockedAt.setText(Replacer.replace(v.getContext().getString(R.string.Unlocked_at_level) + " " + r.getRequiredLevel(), "\\d+%?", Vars.DIABLO_GREEN));
        runeDescription.setText(Replacer.replace(r.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

        v.setTag(r.getUuid());

        return v;
    }

}

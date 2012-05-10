package com.pate.diablo.sectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pate.diablo.R;
import com.pate.diablo.model.Rune;
import com.pate.diablo.model.Skill;


public class EntryRune implements Item 
{

    private final Rune rune;

	public EntryRune(Rune rune) 
	{
		this.rune = rune;
	}
	
	public Rune getRune() 
	{
	    return rune;
	}

	@Override
    public View inflate(Context c, Item i) {
        
        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        Rune r = ((EntryRune) i).getRune();
        View v = vi.inflate(R.layout.list_item_rune, null);

        final ImageView runeIcon = (ImageView) v.findViewById(R.id.list_rune_icon);
        final TextView runeUnlockedAt = (TextView) v.findViewById(R.id.list_rune_unlocked_at);
        final TextView runeDescription = (TextView) v.findViewById(R.id.list_rune_description);
        
        // Is this a terrible hack?! I think so...
        int runeImage = c.getResources().getIdentifier("drawable/" + r.getIcon().replaceAll("-", "_"), null, c.getPackageName());

        runeIcon.setBackgroundResource(runeImage);
        runeUnlockedAt.setText((v.getContext().getString(R.string.Unlocked_at_level) + " " + r.getRequiredLevel()));
        runeDescription.setText(r.getDescription());
        
        return v;
    }
	
}

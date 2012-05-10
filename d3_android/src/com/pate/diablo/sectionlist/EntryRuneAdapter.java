package com.pate.diablo.sectionlist;

import java.util.ArrayList;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pate.diablo.R;
import com.pate.diablo.model.Rune;
import com.pate.diablo.model.Skill;

public class EntryRuneAdapter extends ArrayAdapter<Item>
{

    private Context         context;
    private ArrayList<Item> items;
    private LayoutInflater  vi;

    public EntryRuneAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {
            if (i.isSection()) 
            {
                SectionItem si = (SectionItem) i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle());
            }
            else 
            {
                Rune r = ((EntryRune) i).getRune();
                v = vi.inflate(R.layout.list_item_rune, null);

                final ImageView runeIcon = (ImageView) v.findViewById(R.id.list_rune_icon);
                final TextView runeUnlockedAt = (TextView) v.findViewById(R.id.list_rune_unlocked_at);
                final TextView runeDescription = (TextView) v.findViewById(R.id.list_rune_description);
                
                // Is this a terrible hack?! I think so...
                int runeImage = context.getResources().getIdentifier("drawable/" + r.getIcon().replaceAll("-", "_"), null, context.getPackageName());

                runeIcon.setBackgroundResource(runeImage);
                runeUnlockedAt.setText((v.getContext().getString(R.string.Unlocked_at_level) + " " + r.getRequiredLevel()));
                runeDescription.setText(r.getDescription());
            }
        }
        return v;
    }

}

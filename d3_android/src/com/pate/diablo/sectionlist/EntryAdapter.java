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

public class EntryAdapter extends ArrayAdapter<Item>
{

    private Context         context;
    private ArrayList<Item> items;
    private LayoutInflater  vi;

    public EntryAdapter(Context context, ArrayList<Item> items) {
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
                Skill s = ((EntryItem) i).getSkill();
                Rune r = ((EntryItem) i).getRune();
                v = vi.inflate(R.layout.list_item_entry, null);

                final ImageView skillIcon = (ImageView) v.findViewById(R.id.list_skill_icon);
                final TextView skillName = (TextView) v.findViewById(R.id.list_skill_title);
                final TextView skillCost = (TextView) v.findViewById(R.id.list_skill_cost_text);
                final TextView skillGenerates = (TextView) v.findViewById(R.id.list_skill_generates);
                final TextView skillCooldown = (TextView) v.findViewById(R.id.list_skill_cooldown);
                final TextView skillRequiredLevel = (TextView) v.findViewById(R.id.list_skill_required_level);
                final TextView skillDescription = (TextView) v.findViewById(R.id.list_skill_description);

                final RelativeLayout runeLayout = (RelativeLayout) v.findViewById(R.id.list_rune_section);
                final ImageView runeIcon = (ImageView) v.findViewById(R.id.list_rune_icon);
                final TextView runeUnlockedAt = (TextView) v.findViewById(R.id.list_rune_unlocked_at);
                final TextView runeDescription = (TextView) v.findViewById(R.id.list_rune_description);
                
                // Is this a terrible hack?! I think so...
                int skillImage = context.getResources().getIdentifier("drawable/" + s.getIcon(), null, context.getPackageName());
                int runeImage = context.getResources().getIdentifier("drawable/" + r.getIcon().replaceAll("-", "_"), null, context.getPackageName());

                // Hide the run section if a rune isn't selected
                if (r == null)
                {
                    runeLayout.setVisibility(View.GONE);
                }
                else
                {
                    runeIcon.setBackgroundResource(runeImage);
                    runeUnlockedAt.setText((v.getContext().getString(R.string.Unlocked_at_level) + " " + r.getRequiredLevel()));
                    runeDescription.setText(r.getDescription());
                }
                
                
                //@formatter:off
				skillIcon         .setImageResource(skillImage);
				skillName         .setText(s.getName());
				
				if (s.getCostText() == null || s.getCostText().equals(""))
				{
					skillCost.setVisibility(View.GONE);
				}
				else
				{
					skillCost.setText(v.getContext().getString(R.string.Cost) + " " + s.getCostText());
					skillCost.setVisibility(View.VISIBLE);
				}
				
				if (s.getGenerateText() == null || s.getGenerateText().equals(""))
				{
					skillGenerates.setVisibility(View.GONE);
				}
				else
				{
					skillGenerates.setText(v.getContext().getString(R.string.Generate) + " " + s.getGenerateText());
					skillGenerates.setVisibility(View.VISIBLE);
				}
				
				if (s.getCooldownText() == null || s.getCooldownText().equals(""))
				{
					skillCooldown.setVisibility(View.GONE);
				}
				else
				{
					skillCooldown.setText(v.getContext().getString(R.string.Cooldown) + " " + s.getCooldownText());
					skillCooldown.setVisibility(View.VISIBLE);
				}
				
				if (s.getRequiredLevel() == 0)
				{
					skillRequiredLevel.setVisibility(View.GONE);
				}
				else
				{
					skillRequiredLevel.setText(v.getContext().getString(R.string.Required_level) + " " + String.valueOf(s.getRequiredLevel()));
					skillRequiredLevel.setVisibility(View.VISIBLE);
				}
				
				if (s.getDescription() == null || s.getDescription().equals(""))
				{
					skillDescription.setVisibility(View.GONE);
				}
				else
				{
					skillDescription.setText(s.getDescription());
					skillDescription.setVisibility(View.VISIBLE);
				}	
				
				//@formatter:on

            }
        }
        return v;
    }

}

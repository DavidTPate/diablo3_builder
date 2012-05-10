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

public class EntrySkillAdapter extends ArrayAdapter<Item>
{

    private Context         context;
    private ArrayList<Item> items;
    private LayoutInflater  vi;

    public EntrySkillAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);

        if (i == null)
            return v;

        if (i instanceof SectionItem)
        {
            SectionItem si = (SectionItem) i;
            v = vi.inflate(R.layout.list_item_section, null);

            v.setOnClickListener(null);
            v.setOnLongClickListener(null);
            v.setLongClickable(false);

            final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
            sectionView.setText(si.getTitle());
        }
        else if (i instanceof EmptyItem)
        {
            EmptyItem e = (EmptyItem) i;
            
            v = vi.inflate(R.layout.list_item_empty, null);

            final TextView emptyItemTitle = (TextView) v.findViewById(R.id.list_empty_skill_type);
            final TextView emptySkillType = (TextView) v.findViewById(R.id.list_empty_skill_type);
            
            // Is this a terrible hack?! I think so...
            emptyItemTitle.setText(e.getTitle());
            emptySkillType.setText(e.getSkillType().toString());
        }
        else if (i instanceof EntryRune)
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
        else if (i instanceof EntrySkill)
        {
            Skill s = ((EntrySkill) i).getSkill();
            v = vi.inflate(R.layout.list_item_skill, null);

            final ImageView skillIcon = (ImageView) v.findViewById(R.id.list_skill_icon);
            final TextView skillName = (TextView) v.findViewById(R.id.list_skill_title);
            final TextView skillCost = (TextView) v.findViewById(R.id.list_skill_cost_text);
            final TextView skillGenerates = (TextView) v.findViewById(R.id.list_skill_generates);
            final TextView skillCooldown = (TextView) v.findViewById(R.id.list_skill_cooldown);
            final TextView skillRequiredLevel = (TextView) v.findViewById(R.id.list_skill_required_level);
            final TextView skillDescription = (TextView) v.findViewById(R.id.list_skill_description);

            // Is this a terrible hack?! I think so...
                    int skillImage = context.getResources().getIdentifier("drawable/" + s.getIcon(), null, context.getPackageName());

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
    return v;
}

}

package com.pate.diablo.sectionlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pate.diablo.R;
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
                v = vi.inflate(R.layout.list_item_entry, null);

                final ImageView skillIcon = (ImageView) v.findViewById(R.id.list_skill_icon);
                final TextView skillName = (TextView) v.findViewById(R.id.list_skill_title);
                final TextView skillCost = (TextView) v.findViewById(R.id.list_skill_cost_text);
                final TextView skillGenerates = (TextView) v.findViewById(R.id.list_skill_generates);
                final TextView skillCooldown = (TextView) v.findViewById(R.id.list_skill_cooldown);
                final TextView skillRequiredLevel = (TextView) v.findViewById(R.id.list_skill_required_level);
                final TextView skillDescription = (TextView) v.findViewById(R.id.list_skill_description);

                // Is this a terrible hack?! I think so...
                int img = context.getResources().getIdentifier("drawable/" + s.getIcon(), null, context.getPackageName());

                //@formatter:off
				skillIcon         .setImageResource(img);
				skillName         .setText(s.getName());
				skillCost         .setText("Cost: " + s.getCost());
				skillGenerates    .setText("Generate: " + s.getGenerateDescription());
				skillCooldown     .setText("Cooldown: " + s.getCooldownDescription());
				skillRequiredLevel.setText("Required Level: " + s.getRequiredLevel());
				skillDescription  .setText(s.getDescription());
				//@formatter:on

            }
        }
        return v;
    }

}

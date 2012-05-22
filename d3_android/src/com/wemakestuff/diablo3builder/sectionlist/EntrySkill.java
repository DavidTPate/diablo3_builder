
package com.wemakestuff.diablo3builder.sectionlist;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.model.Skill;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter.RowType;
import com.wemakestuff.diablo3builder.string.Replacer;
import com.wemakestuff.diablo3builder.string.Replacer.D3Color;
import com.wemakestuff.diablo3builder.util.Util;

public class EntrySkill implements Item
{

    private final Skill          skill;
    private final LayoutInflater inflater;

    public EntrySkill(LayoutInflater inflater, Skill skill)
    {

        this.skill = skill;
        this.inflater = inflater;
    }

    public Skill getSkill()
    {

        return skill;
    }

    @Override
    public int getViewType()
    {

        return RowType.ENTRY_SKILL.ordinal();
    }

    @Override
    public View getView(View convertView)
    {

        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_skill, null);

            //@formatter:off
            holder = new ViewHolder((ImageView) v.findViewById(R.id.list_skill_icon)
                                  , (TextView) v.findViewById(R.id.list_skill_title)
                                  , (TextView) v.findViewById(R.id.list_skill_cost_text)
                                  , (TextView) v.findViewById(R.id.list_skill_generates)
                                  , (TextView) v.findViewById(R.id.list_skill_cooldown)
                                  , (TextView) v.findViewById(R.id.list_skill_unlocked_at)
                                  , (TextView) v.findViewById(R.id.list_skill_description));
            //@formatter:on
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        final int iconImg = Util.findImageResource(skill.getIcon());
        loadIconAsync(holder, iconImg);

        holder.skillName.setText(skill.getName());

        if (skill.getCostText() == null || skill.getCostText().equals(""))
        {
            holder.skillCost.setVisibility(View.GONE);
        }
        else
        {
            if (convertView == null)
                holder.skillCost.setText(view.getContext().getString(R.string.Cost) + " " + skill.getCostText());
            loadTextAsync(holder, holder.skillCost, view.getContext().getString(R.string.Cost) + " " + skill.getCostText(), "\\d+%?", D3Color.DIABLO_GREEN);
        }

        if (skill.getGenerateText() == null || skill.getGenerateText().equals(""))
        {
            holder.skillGenerates.setVisibility(View.GONE);
        }
        else
        {
            if (convertView == null)
                holder.skillGenerates.setText(view.getContext().getString(R.string.Generate) + " " + skill.getGenerateText());
            loadTextAsync(holder, holder.skillGenerates, view.getContext().getString(R.string.Generate) + " " + skill.getGenerateText(), "\\d+%?", D3Color.DIABLO_GREEN);
        }

        if (skill.getCooldownText() == null || skill.getCooldownText().equals(""))
        {
            holder.skillCooldown.setVisibility(View.GONE);
        }
        else
        {
            if (convertView == null)
                holder.skillCooldown.setText(view.getContext().getString(R.string.Cooldown) + " " + skill.getCooldownText());
            loadTextAsync(holder, holder.skillCooldown, view.getContext().getString(R.string.Cooldown) + " " + skill.getCooldownText(), "\\d+%?", D3Color.DIABLO_GREEN);
        }

        if (skill.getRequiredLevel() == 0)
        {
            holder.skillRequiredLevel.setVisibility(View.GONE);
        }
        else
        {
            if (convertView == null)
                holder.skillRequiredLevel.setText(view.getContext().getString(R.string.Unlocked_at_level) + " " + String.valueOf(skill.getRequiredLevel()));
            loadTextAsync(holder, holder.skillRequiredLevel, view.getContext().getString(R.string.Unlocked_at_level) + " " + String.valueOf(skill.getRequiredLevel()), "\\d+%?", D3Color.DIABLO_GREEN);
        }

        if (skill.getDescription() == null || skill.getDescription().equals(""))
        {
            holder.skillDescription.setVisibility(View.GONE);
        }
        else
        {
            if (convertView == null)
                holder.skillDescription.setText(skill.getDescription().trim());
            loadTextAsync(holder, holder.skillDescription, skill.getDescription().trim(), "\\d+%?", D3Color.DIABLO_GREEN);
        }

        return view;
    }

    private void loadIconAsync(ViewHolder holder, final int iconImg)
    {

        AsyncTask loadImage = new AsyncTask<ViewHolder, Void, Integer>()
        {

            private ViewHolder v;

            @Override
            protected Integer doInBackground(ViewHolder... params)
            {

                v = params[0];
                return iconImg;
            }

            protected void onPostExecute(Integer result)
            {

                super.onPostExecute(result);
                v.skillIcon.setImageResource(iconImg);
            }

        }.execute(holder);
    }

    private void loadTextAsync(ViewHolder holder, final TextView textView, final CharSequence text, final String regEx, final D3Color color)
    {

        AsyncTask<ViewHolder, Void, CharSequence> loadText = new AsyncTask<ViewHolder, Void, CharSequence>()
        {

            private ViewHolder v;

            @Override
            protected CharSequence doInBackground(ViewHolder... params)
            {

                v = params[0];
                return Replacer.replace(text, regEx, color);
            }

            protected void onPostExecute(CharSequence result)
            {

                super.onPostExecute(result);
                textView.setText(result);
                textView.setVisibility(View.VISIBLE);
            }

        }.execute(holder);
    }

    private static class ViewHolder
    {

        final ImageView skillIcon;
        final TextView  skillName;
        final TextView  skillCost;
        final TextView  skillGenerates;
        final TextView  skillCooldown;
        final TextView  skillRequiredLevel;
        final TextView  skillDescription;

        public ViewHolder(ImageView skillIcon, TextView skillName, TextView skillCost, TextView skillGenerates, TextView skillCooldown, TextView skillRequiredLevel, TextView skillDescription)
        {

            super();
            this.skillIcon = skillIcon;
            this.skillName = skillName;
            this.skillCost = skillCost;
            this.skillGenerates = skillGenerates;
            this.skillCooldown = skillCooldown;
            this.skillRequiredLevel = skillRequiredLevel;
            this.skillDescription = skillDescription;
        }
    }

}

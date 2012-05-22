
package com.wemakestuff.diablo3builder.sectionlist;

import java.util.UUID;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.model.Rune;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter.RowType;
import com.wemakestuff.diablo3builder.string.Replacer;
import com.wemakestuff.diablo3builder.string.Replacer.D3Color;
import com.wemakestuff.diablo3builder.util.Util;

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

        final int iconImg = Util.findImageResource(rune.getIcon());
        loadIconAsync(holder, iconImg);
        holder.runeTitle.setText(getRune().getName());
        if (convertView == null)
            holder.runeUnlockedAt.setText(view.getContext().getString(R.string.Unlocked_at_level) + " " + rune.getRequiredLevel());
        loadTextAsync(holder, holder.runeUnlockedAt, view.getContext().getString(R.string.Unlocked_at_level) + " " + rune.getRequiredLevel(), "\\d+%?", D3Color.DIABLO_GOLD);
        if (convertView == null)
            holder.runeDescription.setText(rune.getDescription().trim());
        loadTextAsync(holder, holder.runeDescription, rune.getDescription().trim(), "\\d+%?", D3Color.DIABLO_GREEN);

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
                v.runeIcon.setImageResource(iconImg);
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

        final ImageView runeIcon;
        final TextView  runeTitle;
        final TextView  runeUnlockedAt;
        final TextView  runeDescription;

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

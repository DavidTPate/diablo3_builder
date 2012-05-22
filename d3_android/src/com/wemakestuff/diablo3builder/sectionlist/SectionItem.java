package com.wemakestuff.diablo3builder.sectionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.sectionlist.EntrySkillAdapter.RowType;

public class SectionItem implements Item
{

    private final String   title;
    private final LayoutInflater inflater;

    public SectionItem(LayoutInflater inflater, String title)
    {
        this.title = title;
        this.inflater = inflater;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public int getViewType()
    {
        return RowType.SECTION_ITEM.ordinal();
    }

    @Override
    public View getView(View convertView)
    {
        ViewHolder holder;
        View view;
        if (convertView == null)
        {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.list_item_section, null);
            holder = new ViewHolder((TextView) v.findViewById(R.id.list_item_section_text));
            v.setTag(holder);
            view = v;
        }
        else
        {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sectionView.setText(title);

        view.setOnClickListener(null);
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        
        return view;
    }

    private static class ViewHolder
    {
        final TextView sectionView;

        private ViewHolder(TextView sectionView)
        {
            this.sectionView = sectionView;
        }
    }

}

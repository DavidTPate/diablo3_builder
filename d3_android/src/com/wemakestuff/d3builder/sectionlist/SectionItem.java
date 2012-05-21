package com.wemakestuff.d3builder.sectionlist;

import android.view.View;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;

public class SectionItem implements Item
{

    private String   title;
    private TextView sectionView;

    public SectionItem(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public int getViewResource()
    {
        return R.layout.list_item_section;
    }

    @Override
    public View inflate(View v, Item i)
    {

        SectionItem si = (SectionItem) i;

        v.setOnClickListener(null);
        v.setOnLongClickListener(null);
        v.setLongClickable(false);

        sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
        sectionView.setText(si.getTitle());

        return v;
    }

}

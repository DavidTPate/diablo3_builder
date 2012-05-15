package com.wemakestuff.d3builder.sectionlist;

import com.pate.diablo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SectionItem implements Item{

	private final String title;
	
	public SectionItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}

    @Override
    public View inflate(Context c, Item i) {
        
        LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SectionItem si = (SectionItem) i;
        View v = vi.inflate(R.layout.list_item_section, null);

        v.setOnClickListener(null);
        v.setOnLongClickListener(null);
        v.setLongClickable(false);

        final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
        sectionView.setText(si.getTitle());
        
        return v;
    }
	
}

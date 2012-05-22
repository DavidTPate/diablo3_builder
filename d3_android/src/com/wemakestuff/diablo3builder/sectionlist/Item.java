package com.wemakestuff.diablo3builder.sectionlist;

import android.view.View;

public interface Item
{
	public int getViewType();
	public View getView(View convertView);
}

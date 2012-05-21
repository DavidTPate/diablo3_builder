package com.wemakestuff.d3builder.sectionlist;

import android.os.Parcelable;
import android.view.View;

public interface Item
{
	public View inflate(View v, Item i);
	public int getViewResource();
	
}

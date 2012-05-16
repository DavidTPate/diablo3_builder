package com.wemakestuff.d3builder.sectionlist;

import java.util.UUID;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;

public class EmptyFollower implements Item {
	private final String name;
	private final String icon;
	private final String description;
	private final UUID followerUUID;

	public EmptyFollower(String name, String shortDescription, String icon, UUID followerUUID) {
		this.name = name;
		this.description = shortDescription;
		this.icon = icon;
		this.followerUUID = followerUUID;
	}

	public String getDescription()
	{
	    return description;
	}

	public String getIcon() {
	    return icon;
	}
	
	public String getName() {
		return name;
	}

	public UUID getSkillUUID() {
		return followerUUID;
	}

	@Override
	public View inflate(Context c, Item i) {

		LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		EmptyFollower e = (EmptyFollower) i;
		View v = vi.inflate(R.layout.list_item_empty_follower, null);

		final ImageView icon = (ImageView) v.findViewById (R.id.empty_follower_icon);
		final TextView emptyItemName = (TextView) v.findViewById(R.id.empty_follower_name);
		final TextView emptyFollowerDescription = (TextView) v.findViewById(R.id.empty_follower_description);

		int iconImage = c.getResources().getIdentifier("drawable/" + e.getIcon(), null, c.getPackageName());
		emptyItemName.setText(e.getName());
		emptyFollowerDescription.setText(e.getDescription());
		icon.setImageResource(iconImage);
		
		return v;
	}

}

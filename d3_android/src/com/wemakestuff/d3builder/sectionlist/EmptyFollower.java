package com.wemakestuff.d3builder.sectionlist;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Skill;

public class EmptyFollower implements Item {
	private final String name;
	private final String icon;
	private final String description;
	private final UUID followerUUID;

	private LinearLayout emptyFollowerSkills;
	private TextView emptyFollowerSkill1;
	private TextView emptyFollowerSkill2;
	private TextView emptyFollowerSkill3;
	private TextView emptyFollowerSkill4;
	private TextView emptyFollowerSkill1Description;
	private TextView emptyFollowerSkill2Description;
	private TextView emptyFollowerSkill3Description;
	private TextView emptyFollowerSkill4Description;
	
	
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

	public UUID getFollowerUUID() {
		return followerUUID;
	}
	
	public void setSkills(List<ParcelUuid> skills)
	{
	    hideAllSkills();
	    int index = 0;
	    for (ParcelUuid u : skills)
	    {
	        Skill s = D3Application.getInstance().getFollowerByName(name).getSkillByUUID(u.getUuid());
	        Log.i("Adding skill", s.getName() );
	        
	        if (index == 0)
	        {
    	        emptyFollowerSkill1.setText(s.getName());
    	        emptyFollowerSkill1Description.setText(s.getDescription());
    	        
    	        emptyFollowerSkill1.setVisibility(View.VISIBLE);
    	        emptyFollowerSkill1Description.setVisibility(View.VISIBLE);
    	        
	        }
	        else if (index == 1)
	        {
	            emptyFollowerSkill2.setText(s.getName());
	            emptyFollowerSkill2Description.setText(s.getDescription());
	            
	            emptyFollowerSkill2.setVisibility(View.VISIBLE);
	            emptyFollowerSkill2Description.setVisibility(View.VISIBLE);
	        }
	        else if (index == 2)
	        {
	            emptyFollowerSkill3.setText(s.getName());
	            emptyFollowerSkill3Description.setText(s.getDescription());

	            emptyFollowerSkill3.setVisibility(View.VISIBLE);
	            emptyFollowerSkill3Description.setVisibility(View.VISIBLE);
	        }
	        else if (index == 3)
	        {
	            emptyFollowerSkill4.setText(s.getName());
	            emptyFollowerSkill4Description.setText(s.getDescription());
	            
	            emptyFollowerSkill4.setVisibility(View.VISIBLE);
	            emptyFollowerSkill4Description.setVisibility(View.VISIBLE);
	        }
	        index++;
	    }
	    
	    emptyFollowerSkills.setVisibility(View.VISIBLE);
	}
	
	private void hideAllSkills()
	{
	    emptyFollowerSkill1.setVisibility(View.GONE);
	    emptyFollowerSkill2.setVisibility(View.GONE);
	    emptyFollowerSkill3.setVisibility(View.GONE);
	    emptyFollowerSkill4.setVisibility(View.GONE);
	    
	    emptyFollowerSkill1Description.setVisibility(View.GONE);
	    emptyFollowerSkill2Description.setVisibility(View.GONE);
	    emptyFollowerSkill3Description.setVisibility(View.GONE);
	    emptyFollowerSkill4Description.setVisibility(View.GONE);
	    
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
		
		emptyFollowerSkills = (LinearLayout) v.findViewById(R.id.empty_follower_skills);
		emptyFollowerSkills.setVisibility(View.GONE);
		
		emptyFollowerSkill1 = (TextView) v.findViewById(R.id.empty_follower_skill_1);
		emptyFollowerSkill2 = (TextView) v.findViewById(R.id.empty_follower_skill_2);
		emptyFollowerSkill3 = (TextView) v.findViewById(R.id.empty_follower_skill_3);
		emptyFollowerSkill4 = (TextView) v.findViewById(R.id.empty_follower_skill_4);

		emptyFollowerSkill1Description = (TextView) v.findViewById(R.id.empty_follower_skill_1_description);
		emptyFollowerSkill2Description = (TextView) v.findViewById(R.id.empty_follower_skill_2_description);
		emptyFollowerSkill3Description = (TextView) v.findViewById(R.id.empty_follower_skill_3_description);
		emptyFollowerSkill4Description = (TextView) v.findViewById(R.id.empty_follower_skill_4_description);
		
		return v;
	}

}

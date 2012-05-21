package com.wemakestuff.d3builder.sectionlist;

import java.util.ArrayList;
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
import com.wemakestuff.d3builder.followers.FollowerListFragment;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.string.Replacer;
import com.wemakestuff.d3builder.string.Vars;

public class EmptyFollower implements Item {
	private final String name;
	private final String iconText;
	private final String description;
	private final UUID followerUUID;
	private String url;
	private List<ParcelUuid> skills = new ArrayList<ParcelUuid>();;

	private LinearLayout emptyFollowerSkills;
	private TextView emptyFollowerSkill1;
	private TextView emptyFollowerSkill2;
	private TextView emptyFollowerSkill3;
	private TextView emptyFollowerSkill4;
	private TextView emptyFollowerSkill1Description;
	private TextView emptyFollowerSkill2Description;
	private TextView emptyFollowerSkill3Description;
	private TextView emptyFollowerSkill4Description;
	
	private ImageView icon;
    private TextView emptyItemName;
    private TextView emptyFollowerDescription;
	
	
	public EmptyFollower(String name, String shortDescription, String icon, UUID followerUUID, String url) {
		this.name = name;
		this.description = shortDescription;
		this.iconText = icon;
		this.followerUUID = followerUUID;
		this.url = url;
	}
	
	public void setUrl(String url)
	{
	    this.url = url;
	}

	public String getDescription()
	{
	    return description;
	}

	public String getIcon() {
	    return iconText;
	}
	
	public String getName() {
		return name;
	}

	public UUID getFollowerUUID() {
		return followerUUID;
	}
	
	public List<ParcelUuid> getSkills()
	{
	    return skills;
	}
	
	public String getFollowerUrl()
	{
	    return url;
	}
	
	public void setSkills(List<ParcelUuid> list)
	{
	    this.skills = list;
	    
	    if (!hideAllSkills())
	    {
	        Log.e("EmptyFollower - setSkills", "View was null, can't add skills!");
	        return;
	    }
	    
	    int index = 0;
	    for (ParcelUuid u : skills)
	    {
	        Skill s = D3Application.getInstance().getFollowerByName(name).getSkillByUUID(u.getUuid());
	        
	        if (s == null)
	        {
	            continue;
	        }
	        
	        if (index == 0)
	        {
    	        emptyFollowerSkill1.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
    	        emptyFollowerSkill1Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
    	        
    	        emptyFollowerSkill1.setVisibility(View.VISIBLE);
    	        emptyFollowerSkill1Description.setVisibility(View.VISIBLE);
    	        
	        }
	        else if (index == 1)
	        {
	            emptyFollowerSkill2.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
	            emptyFollowerSkill2Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
	            
	            emptyFollowerSkill2.setVisibility(View.VISIBLE);
	            emptyFollowerSkill2Description.setVisibility(View.VISIBLE);
	        }
	        else if (index == 2)
	        {
	            emptyFollowerSkill3.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
	            emptyFollowerSkill3Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));

	            emptyFollowerSkill3.setVisibility(View.VISIBLE);
	            emptyFollowerSkill3Description.setVisibility(View.VISIBLE);
	        }
	        else if (index == 3)
	        {
	            emptyFollowerSkill4.setText(Replacer.replace("Level " + s.getRequiredLevel() + ": " + s.getName(), ".+", Vars.DIABLO_GOLD));
	            emptyFollowerSkill4Description.setText(Replacer.replace(s.getDescription().trim(), "\\d+%?", Vars.DIABLO_GREEN));
	            
	            emptyFollowerSkill4.setVisibility(View.VISIBLE);
	            emptyFollowerSkill4Description.setVisibility(View.VISIBLE);
	        }
	        index++;
	    }
	    
	    emptyFollowerSkills.setVisibility(View.VISIBLE);
	}
	
	private boolean hideAllSkills()
	{
	    if (emptyFollowerSkill1 == null || emptyFollowerSkill2 == null || emptyFollowerSkill3 == null || emptyFollowerSkill4 == null ||
	        emptyFollowerSkill1Description == null || emptyFollowerSkill2Description == null || emptyFollowerSkill3Description == null || emptyFollowerSkill4Description == null)
	        return false;
	        
	    emptyFollowerSkill1.setVisibility(View.GONE);
	    emptyFollowerSkill2.setVisibility(View.GONE);
	    emptyFollowerSkill3.setVisibility(View.GONE);
	    emptyFollowerSkill4.setVisibility(View.GONE);
	    
	    emptyFollowerSkill1Description.setVisibility(View.GONE);
	    emptyFollowerSkill2Description.setVisibility(View.GONE);
	    emptyFollowerSkill3Description.setVisibility(View.GONE);
	    emptyFollowerSkill4Description.setVisibility(View.GONE);
	    
	    return true;
	    
	}

	@Override
    public int getViewResource()
    {
        return R.layout.list_item_empty_follower;
    }
	
	@Override
	public View inflate(View v, Item i) {
	    
		EmptyFollower e = (EmptyFollower) i;

		icon = (ImageView) v.findViewById (R.id.empty_follower_icon);
		emptyItemName = (TextView) v.findViewById(R.id.empty_follower_name);
		emptyFollowerDescription = (TextView) v.findViewById(R.id.empty_follower_description);

		int iconImage = v.getContext().getResources().getIdentifier("drawable/" + e.getIcon(), null, v.getContext().getPackageName());
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
		
		setSkills(skills);
		
		return v;
	}

}

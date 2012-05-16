package com.wemakestuff.d3builder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.d3builder.string.Vars;

public class Follower {
    
	@SerializedName(Vars.SKILLS)
	@Expose
	private List<Skill> Skills;
	
	@SerializedName(Vars.SHORT_DESCRIPTION)
	@Expose
	private String shortDescription;
	
	@SerializedName(Vars.ICON)
	@Expose
	private String icon;
	
	@SerializedName(Vars.FEATURES)
	@Expose
	private List<Feature> keyFeatures;
	
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	
	private UUID uuid = UUID.randomUUID();
	
	public boolean containsSkillByUUID(UUID uuid)
	{
		return containsSkillByUUID(Skills, uuid);
	}
	
	public boolean containsSkillsByRequiredLevel(int requiredLevel)
	{
		return containsSkillsByRequiredLevel(Skills, requiredLevel);
	}
	
	public boolean containsSkillByUUID(List<Skill> list, UUID uuid)
	{
		for (Skill skill : list)
		{
			if (skill.getUuid().equals(uuid))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean containsSkillsByRequiredLevel(List<Skill> list, int requiredLevel)
	{
		for (Skill skill : list)
		{
			if (skill.getRequiredLevel() <= requiredLevel)
			{
				return true;
			}
		}
		return false;
	}
	
	public Skill getSkillByUUID(UUID uuid)
	{
		return getSkillByUUID(Skills, uuid);
	}
	
	public List<Skill> getSkillsByRequiredLevel(int requiredLevel)
	{
		return getSkillsByRequiredLevel(Skills, requiredLevel);
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Feature> getKeyFeatures() {
		return keyFeatures;
	}

	public String getName() {
		return name;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public Skill getSkillByUUID(List<Skill> list, UUID uuid)
	{
		for (Skill skill : list)
		{
			if (skill.getUuid().equals(uuid))
			{
				return skill;
			}
		}
		return null;
	}
	
	public List<Skill> getSkillsByRequiredLevel(List<Skill> list, int requiredLevel)
	{
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		for (Skill skill: list)
		{
			if (skill.getRequiredLevel() <= requiredLevel)
			{
				skills.add(skill);
			}
		}
		return skills;
	}
	
	public List<Skill> getSkills() {
		return Skills;
	}

	public UUID getUuid() {
		return uuid;
	}

    public String getIcon() {
        return icon;
    }

}

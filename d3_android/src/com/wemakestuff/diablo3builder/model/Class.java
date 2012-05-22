package com.wemakestuff.diablo3builder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

public class Class {
	@SerializedName(Vars.ACTIVE_SKILLS)
	@Expose
	private List<Skill> activeSkills;
	
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	
	@SerializedName(Vars.EQUIPMENT)
	@Expose
	private String equipment;
	
	@SerializedName(Vars.FEATURES)
	@Expose
	private List<Feature> keyFeatures;
	
	@SerializedName(Vars.LORE)
	@Expose
	private String lore;
	
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	
	@SerializedName(Vars.PASSIVE_SKILLS)
	@Expose
	private List<Skill> passiveSkills;

	@SerializedName(Vars.RESOURCE)
	@Expose
	private String resourceText;

	@SerializedName(Vars.RESOURCE_TYPE)
	@Expose
	private String resourceType;

	@SerializedName(Vars.SCREENSHOTS_COUNT)
	@Expose
	private int screenshotCount;

	@SerializedName(Vars.SCREENSHOTS)
	@Expose
	private String[] screenshots;

	@SerializedName(Vars.SHORT_DESCRIPTION)
	@Expose
	private String shortDescription;

	@SerializedName(Vars.TIER_1)
	@Expose
	private String tier1Text;

	@SerializedName(Vars.TIER_2)
	@Expose
	private String tier2Text;

	@SerializedName(Vars.TIER_3)
	@Expose
	private String tier3Text;

	private UUID uuid = UUID.randomUUID();
	
	public boolean containsActiveSkillByUUID(UUID uuid)
	{
		return containsSkillByUUID(activeSkills, uuid);
	}
	
	public boolean containsActiveSkillsByRequiredLevel(int requiredLevel)
	{
		return containsSkillsByRequiredLevel(activeSkills, requiredLevel);
	}
	
	public boolean containsActiveSkillsByType(String type)
	{
		return containsSkillsByType(activeSkills, type);
	}
	
	public boolean containsPassiveSkillByUUID(UUID uuid)
	{
		return containsSkillByUUID(passiveSkills, uuid);
	}
	
	public boolean containsPassiveSkillsByRequiredLevel(int requiredLevel)
	{
		return containsSkillsByRequiredLevel(passiveSkills, requiredLevel);
	}
	
	public boolean containsPassiveSkillsByType(String type)
	{
		return containsSkillsByType(passiveSkills, type);
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
	
	public boolean containsSkillsByType(List<Skill> list, String type)
	{
		if (type.equals("Passive"))
		{
			return passiveSkills.size() > 0;
		}
		for (Skill skill : list)
		{
			if (skill.getType().equals(type))
			{
				return true;
			}
		}
		return false;
	}
	
	public Skill getActiveSkillByUUID(UUID uuid)
	{
		return getSkillByUUID(activeSkills, uuid);
	}
	
	public List<Skill> getActiveSkillsByRequiredLevel(int requiredLevel)
	{
		return getSkillsByRequiredLevel(activeSkills, requiredLevel);
	}
	
	public List<Skill> getActiveSkillsByType(String type)
	{
		return getSkillsByType(activeSkills, type);
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEquipment() {
		return equipment;
	}
	
	public List<Feature> getKeyFeatures() {
		return keyFeatures;
	}
	
	public String getLore() {
		return lore;
	}
	
	public String getName() {
		return name;
	}
	
	public Skill getPassiveSkillByUUID(UUID uuid)
	{
		return getSkillByUUID(passiveSkills, uuid);
	}
	

	
	public List<Skill> getPassiveSkills() {
		return passiveSkills;
	}

	public List<Skill> getPassiveSkillsByRequiredLevel(int requiredLevel)
	{
		return getSkillsByRequiredLevel(passiveSkills, requiredLevel);
	}

	public List<Skill> getPassiveSkillsByType(String type)
	{
		return getSkillsByType(passiveSkills, type);
	}

	public String getResourceText() {
		return resourceText;
	}

	public String getResourceType() {
		return resourceType;
	}

	public int getScreenshotCount() {
		return screenshotCount;
	}

	public String[] getScreenshots() {
		return screenshots;
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
	
	public List<Skill> getActiveSkillsByTypeAndRequiredLevel(String type, int requiredLevel)
	{
		return getSkillsByTypeAndRequiredLevel(activeSkills, type, requiredLevel);
	}
	
	public List<Skill> getPassiveSkillsByTypeAndRequiredLevel(String type, int requiredLevel)
	{
		return getSkillsByTypeAndRequiredLevel(passiveSkills, type, requiredLevel);
	}
	
	public List<Skill> getSkillsByTypeAndRequiredLevel(List<Skill> list, String type, int requiredLevel)
	{
		if (type.equals("Passive"))
		{
			return getPassiveSkillsByRequiredLevel(requiredLevel);
		}
		
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		for (Skill skill : list)
		{
			if (skill.getType().equals(type) && skill.getRequiredLevel() <= requiredLevel)
			{
				skills.add(skill);
			}
		}
		return skills;
	}
	
	public boolean containsActiveSkillsByTypeAndRequiredLevel(String type, int requiredLevel)
	{
		return containsSkillsByTypeAndRequiredLevel(activeSkills, type, requiredLevel);
	}
	
	public boolean containsPassiveSkillsByTypeAndRequiredLevel(String type, int requiredLevel)
	{
		return containsSkillsByTypeAndRequiredLevel(passiveSkills, type, requiredLevel);
	}
	
	public boolean containsSkillsByTypeAndRequiredLevel(List<Skill> list, String type, int requiredLevel)
	{
		if (type.equals("Passive"))
		{
			return containsPassiveSkillsByType(type);
		}
		
		for (Skill skill : list)
		{
			if (skill.getType().equals(type) && skill.getRequiredLevel() <= requiredLevel)
			{
				return true;
			}
		}
		return false;
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
	
	public List<Skill> getSkillsByType(List<Skill> list, String type)
	{
		if (type.equals("Passive"))
		{
			return passiveSkills;
		}
		
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		for (Skill skill : list)
		{
			if (skill.getType().equals(type))
			{
				skills.add(skill);
			}
		}
		return skills;
	}
	
	public List<Skill> getActiveSkills() {
		return activeSkills;
	}

	public String getTier1Text() {
		return tier1Text;
	}
	
	public String getTier2Text() {
		return tier2Text;
	}
	
	public String getTier3Text() {
		return tier3Text;
	}
	
	public UUID getUuid() {
		return uuid;
	}
}

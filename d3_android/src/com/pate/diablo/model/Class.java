package com.pate.diablo.model;

import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

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

	public List<Skill> getActiveSkills() {
		return activeSkills;
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

	public List<Skill> getPassiveSkills() {
		return passiveSkills;
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

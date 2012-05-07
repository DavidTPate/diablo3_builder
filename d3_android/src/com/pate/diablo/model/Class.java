package com.pate.diablo.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class Class {
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	@SerializedName(Vars.SHORT_DESCRIPTION)
	@Expose
	private String shortDescription;
	@SerializedName(Vars.FEATURES)
	@Expose
	private List<Feature> keyFeatures;
	@SerializedName(Vars.RESOURCE)
	@Expose
	private String resourceText;
	@SerializedName(Vars.RESOURCE_TYPE)
	@Expose
	private String resourceType;
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	@SerializedName(Vars.LORE)
	@Expose
	private String lore;
	@SerializedName(Vars.EQUIPMENT)
	@Expose
	private String equipment;
	@SerializedName(Vars.TIER_1)
	@Expose
	private String tier1Text;
	@SerializedName(Vars.TIER_2)
	@Expose
	private String tier2Text;
	@SerializedName(Vars.TIER_3)
	@Expose
	private String tier3Text;
	@SerializedName(Vars.ACTIVE_SKILLS)
	@Expose
	private List<Skill> activeSkills;
	@SerializedName(Vars.PASSIVE_SKILLS)
	@Expose
	private List<Skill> passiveSkills;
	@SerializedName(Vars.SCREENSHOTS_COUNT)
	@Expose
	private int screenshotCount;
	@SerializedName(Vars.SCREENSHOTS)
	@Expose
	private List<String> screenshots;
}

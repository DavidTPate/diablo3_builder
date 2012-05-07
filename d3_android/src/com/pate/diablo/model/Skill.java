package com.pate.diablo.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class Skill {
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	@SerializedName(Vars.REQUIRED_LEVEL)
	@Expose
	private int requiredLevel;
	@SerializedName(Vars.ICON)
	@Expose
	private String icon;
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	@SerializedName(Vars.GENERATE)
	@Expose
	private int generate;
	@SerializedName(Vars.GENERATE_UNITS)
	@Expose
	private String generateUnits;
	@SerializedName(Vars.GENERATE_DESCRIPTION)
	@Expose
	private String generateDescription;
	@SerializedName(Vars.COOLDOWN)
	@Expose
	private int cooldown;
	@SerializedName(Vars.COOLDOWN_UNITS)
	@Expose
	private String cooldownUnits;
	@SerializedName(Vars.COOLDOWN_DESCRIPTION)
	@Expose
	private String cooldownDescription;
	@SerializedName(Vars.COST)
	@Expose
	private int cost;
	@SerializedName(Vars.COST_UNITS)
	@Expose
	private String costUnits;
	@SerializedName(Vars.COST_DESCRIPTION)
	@Expose
	private String costDescription;
	@SerializedName(Vars.RUNES)
	@Expose
	private List<Rune> runes;
}

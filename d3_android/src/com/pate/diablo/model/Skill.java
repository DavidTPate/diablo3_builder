package com.pate.diablo.model;

import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class Skill {
	@SerializedName(Vars.COOLDOWN)
	@Expose
	private int cooldown;

	@SerializedName(Vars.COOLDOWN_DESCRIPTION)
	@Expose
	private String cooldownDescription;

	@SerializedName(Vars.COOLDOWN_UNITS)
	@Expose
	private String cooldownUnits;

	@SerializedName(Vars.COST)
	@Expose
	private int cost;

	@SerializedName(Vars.COST_DESCRIPTION)
	@Expose
	private String costDescription;

	@SerializedName(Vars.COST_UNITS)
	@Expose
	private String costUnits;

	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;

	@SerializedName(Vars.GENERATE)
	@Expose
	private int generate;

	@SerializedName(Vars.GENERATE_DESCRIPTION)
	@Expose
	private String generateDescription;

	@SerializedName(Vars.GENERATE_UNITS)
	@Expose
	private String generateUnits;

	@SerializedName(Vars.ICON)
	@Expose
	private String icon;

	@SerializedName(Vars.NAME)
	@Expose
	private String name;

	@SerializedName(Vars.REQUIRED_LEVEL)
	@Expose
	private int requiredLevel;

	@SerializedName(Vars.RUNES)
	@Expose
	private List<Rune> runes;

	private UUID uuid = UUID.randomUUID();

	public int getCooldown() {
		return cooldown;
	}

	public String getCooldownDescription() {
		return cooldownDescription;
	}

	public String getCooldownUnits() {
		return cooldownUnits;
	}

	public int getCost() {
		return cost;
	}

	public String getCostDescription() {
		return costDescription;
	}

	public String getCostUnits() {
		return costUnits;
	}

	public String getDescription() {
		return description;
	}

	public int getGenerate() {
		return generate;
	}

	public String getGenerateDescription() {
		return generateDescription;
	}

	public String getGenerateUnits() {
		return generateUnits;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public List<Rune> getRunes() {
		return runes;
	}

	public UUID getUuid() {
		return uuid;
	}
}

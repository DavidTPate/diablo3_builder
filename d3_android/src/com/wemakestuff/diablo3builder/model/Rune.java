package com.wemakestuff.diablo3builder.model;

import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

public class Rune {
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	
	@SerializedName(Vars.ICON)
	@Expose
	private String icon;
	
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	
	@SerializedName(Vars.REQUIRED_LEVEL)
	@Expose
	private int requiredLevel;
	
	private UUID uuid = UUID.randomUUID();

	public String getDescription() {
		return description;
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

	public UUID getUuid() {
		return uuid;
	}
}

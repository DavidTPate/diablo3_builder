package com.wemakestuff.d3builder.model;

import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.d3builder.string.Vars;

public class SkillLevel {
	@SerializedName(Vars.KEY)
	@Expose
	private String key;
	@SerializedName(Vars.REQUIRED_LEVEL)
	@Expose
	private int requiredLevel;
	private UUID uuid = UUID.randomUUID();

	public String getKey() {
		return key;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public UUID getUuid() {
		return uuid;
	}

}

package com.wemakestuff.diablo3builder.model;

import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

public class ClassAttribute {
	@SerializedName(Vars.NAME)
	@Expose
	private String name;

	@SerializedName(Vars.SKILL_TYPES)
	@Expose
	private String[] skillTypes;

	private UUID uuid = UUID.randomUUID();

	public String getName() {
		return name;
	}

	public String[] getSkillTypes() {
		return skillTypes;
	}

	public UUID getUuid() {
		return uuid;
	}

}

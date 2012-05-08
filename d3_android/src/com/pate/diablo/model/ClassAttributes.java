package com.pate.diablo.model;

import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class ClassAttributes {
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

package com.wemakestuff.diablo3builder.model;

import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

public class SkillAttribute {
	@SerializedName(Vars.FOLLOWER_SEPARATOR)
	@Expose
	private String followerSeparator;

	@SerializedName(Vars.FOLLOWER_LEVELS)
	@Expose
	private List<SkillLevel> followerSkillLevels;

	@SerializedName(Vars.MISSING_VALUE)
	@Expose
	private String missingValue;

	@SerializedName(Vars.PASSIVE_SEPARATOR)
	@Expose
	private String passiveSeparator;

	@SerializedName(Vars.RUNE_SEPARATOR)
	@Expose
	private String runeSeparator;

	@SerializedName(Vars.SKILL_LEVELS)
	@Expose
	private List<SkillLevel> skillLevels;

	@SerializedName(Vars.SKILL_MAPPING)
	@Expose
	private String[] skillMapping;

	private UUID uuid = UUID.randomUUID();

	public String getFollowerSeparator() {
		return followerSeparator;
	}

	public List<SkillLevel> getFollowerSkillLevels() {
		return followerSkillLevels;
	}

	public String getMissingValue() {
		return missingValue;
	}

	public String getPassiveSeparator() {
		return passiveSeparator;
	}

	public String getRuneSeparator() {
		return runeSeparator;
	}

	public List<SkillLevel> getSkillLevels() {
		return skillLevels;
	}

	public String[] getSkillMapping() {
		return skillMapping;
	}

	public UUID getUuid() {
		return uuid;
	}

}

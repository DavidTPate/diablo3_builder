package com.pate.diablo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class Rune {
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	@SerializedName(Vars.REQUIRED_LEVEL)
	@Expose
	private int requiredLevel;
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
	@SerializedName(Vars.ICON)
	@Expose
	private String icon;
}

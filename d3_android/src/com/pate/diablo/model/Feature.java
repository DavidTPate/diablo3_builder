package com.pate.diablo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class Feature {
	@SerializedName(Vars.NAME)
	@Expose
	private String name;
	@SerializedName(Vars.ICON)
	@Expose
	private String icon;
	@SerializedName(Vars.DESCRIPTION)
	@Expose
	private String description;
}

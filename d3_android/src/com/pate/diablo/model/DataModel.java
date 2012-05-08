package com.pate.diablo.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class DataModel {
	@SerializedName(Vars.CLASS_ATTRIBUTES)
	@Expose
	private List<ClassAttributes> classAttributes;
	
	@SerializedName(Vars.CLASSES)
	@Expose
	private List<Class> classes;

	public List<ClassAttributes> getClassAttributes() {
		return classAttributes;
	}

	public List<Class> getClasses() {
		return classes;
	}
}

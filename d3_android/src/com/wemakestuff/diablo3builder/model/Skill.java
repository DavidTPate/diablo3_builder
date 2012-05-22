package com.wemakestuff.diablo3builder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

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

	@SerializedName(Vars.TYPE)
	@Expose
	private String type;

	private UUID uuid = UUID.randomUUID();

	public boolean containsRuneByUUID(UUID uuid)
	{
	    return getRuneByUUID(uuid) != null;
	}
	
	public boolean containsRunesByRequiredLevel(int requiredLevel)
	{		
	    return ((List<Rune>) getRunesByRequiredLevel(requiredLevel)).size() > 0;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public String getCooldownDescription() {
		return cooldownDescription;
	}
	
	public String getCooldownText()
	{
		return (cooldown == 0 ? "" : String.valueOf(cooldown) + " ") + (cooldownUnits == null || cooldownUnits.equals("") ? "" : cooldownUnits + " ") + (cooldownDescription == null || cooldownDescription.equals("") ? "" : cooldownDescription);
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

	public String getCostText()
	{
		return (cost == 0 ? "" : String.valueOf(cost) + " ") + (costUnits == null || costUnits.equals("") ? "" : costUnits + " ") + (costDescription == null || costDescription.equals("") ? "" : costDescription);
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

	public String getGenerateText()
	{
		return (generate == 0 ? "" : String.valueOf(generate) + " ") + (generateUnits == null || generateUnits.equals("") ? "" : generateUnits + " ") + (generateDescription == null || generateDescription.equals("") ? "" : generateDescription);
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

	public Rune getRuneByUUID(UUID uuid)
	{		
	    for (Rune rune : getRunes())
	    {
	        if (rune.getUuid().equals(uuid))
	        {
	            return rune;
	        }
	        
	    }
	    
		return null;
	}
	
	public List<Rune> getRunes() {
		return runes;
	}
	
	public List<Rune> getRunesByRequiredLevel(int requiredLevel)
	{
		ArrayList<Rune> tempRunes = new ArrayList<Rune>();
		
		for (Rune rune : getRunes())
		{
		    if (rune.getRequiredLevel() <= requiredLevel)
		    {
		        tempRunes.add(rune);
		    }
		}
		
		return tempRunes;
	}
	
	public String getType() {
		return type;
	}
	
	public UUID getUuid() {
		return uuid;
	}
}

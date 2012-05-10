package com.pate.diablo.sectionlist;

import com.pate.diablo.model.Skill;


public class EntrySkill implements Item 
{

    private final Skill skill;

	public EntrySkill(Skill skill) 
	{
		this.skill = skill;
	}
	
	public Skill getSkill() 
	{
	    return skill;
	}
	
}

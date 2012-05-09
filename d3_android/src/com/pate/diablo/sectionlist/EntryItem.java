package com.pate.diablo.sectionlist;

import com.pate.diablo.model.Skill;


public class EntryItem implements Item 
{

    private final Skill skill;

	public EntryItem(Skill skill) 
	{
		this.skill = skill;
	}
	
	public Skill getSkill() 
	{
	    return skill;
	}
	
	@Override
	public boolean isSection() 
	{
		return false;
	}

}

package com.pate.diablo.sectionlist;

import com.pate.diablo.model.Rune;
import com.pate.diablo.model.Skill;


public class EntryItem implements Item 
{

    private final Skill skill;
    private final Rune rune;

	public EntryItem(Skill skill, Rune rune) 
	{
	    this.rune = rune;
		this.skill = skill;
	}
	
	public Skill getSkill() 
	{
	    return skill;
	}
	
	public Rune getRune()
	{
	    return rune;
	}
	
	@Override
	public boolean isSection() 
	{
		return false;
	}

}

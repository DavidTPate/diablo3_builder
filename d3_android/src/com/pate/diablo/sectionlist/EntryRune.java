package com.pate.diablo.sectionlist;

import com.pate.diablo.model.Rune;
import com.pate.diablo.model.Skill;


public class EntryRune implements Item 
{

    private final Rune rune;

	public EntryRune(Rune rune) 
	{
		this.rune = rune;
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

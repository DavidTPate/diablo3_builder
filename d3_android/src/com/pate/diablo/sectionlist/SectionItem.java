package com.pate.diablo.sectionlist;

public class SectionItem implements Item{

	private final String title;
	
	public SectionItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
}

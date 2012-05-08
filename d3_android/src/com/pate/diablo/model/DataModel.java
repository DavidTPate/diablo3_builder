package com.pate.diablo.model;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pate.diablo.string.Vars;

public class DataModel {
	@SerializedName(Vars.CLASS_ATTRIBUTES)
	@Expose
	private List<ClassAttribute> classAttributes;
	
	@SerializedName(Vars.CLASSES)
	@Expose
	private List<Class> classes;

	public List<ClassAttribute> getClassAttributes() {
		return classAttributes;
	}

	public List<Class> getClasses() {
		return classes;
	}
	
	public Class getClassByName(String name)
	{		
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext())
		{
			Class clss = itr.next();
			
			if (clss.getName().equalsIgnoreCase(name))
			{
				return clss;
			}
		}
		
		return null;
	}
	
	public boolean containsClassByName(String name)
	{		
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext())
		{
			Class clss = itr.next();
			
			if (clss.getName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Class getClassByUUID(UUID uuid)
	{		
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext())
		{
			Class clss = itr.next();
			
			if (clss.getUuid().equals(uuid))
			{
				return clss;
			}
		}
		
		return null;
	}
	
	public boolean containsClassByUUID(UUID uuid)
	{		
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext())
		{
			Class clss = itr.next();
			
			if (clss.getUuid().equals(uuid))
			{
				return true;
			}
		}
		
		return false;
	}

	public ClassAttribute getClassAttributesByName(String name)
	{
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext())
		{
			ClassAttribute classAttributes = itr.next();
			
			if (classAttributes.getName().equalsIgnoreCase(name))
			{
				return classAttributes;
			}
		}
		
		return null;
	}
	
	public boolean containsClassAttributesByName(String name)
	{
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext())
		{
			ClassAttribute classAttributes = itr.next();
			
			if (classAttributes.getName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public ClassAttribute getClassAttributesByUUID(UUID uuid)
	{
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext())
		{
			ClassAttribute classAttributes = itr.next();
			
			if (classAttributes.getUuid().equals(uuid))
			{
				return classAttributes;
			}
		}
		
		return null;
	}
	
	public boolean containsClassAttributesByUUID(UUID uuid)
	{
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext())
		{
			ClassAttribute classAttributes = itr.next();
			
			if (classAttributes.getUuid().equals(uuid))
			{
				return true;
			}
		}
		
		return false;
	}
	
}

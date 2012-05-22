package com.wemakestuff.diablo3builder.model;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wemakestuff.diablo3builder.string.Vars;

public class DataModel {
	@SerializedName(Vars.CLASS_ATTRIBUTES)
	@Expose
	private List<ClassAttribute> classAttributes;

	@SerializedName(Vars.CLASSES)
	@Expose
	private List<Class> classes;
	
	@SerializedName(Vars.FOLLOWERS)
	@Expose
	private List<Follower> followers;

	@SerializedName(Vars.SKILL_ATTRIBUTES)
	@Expose
	private SkillAttribute skillAttributes;

	public boolean containsClassAttributesByName(String name) {
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext()) {
			ClassAttribute classAttributes = itr.next();

			if (classAttributes.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsClassAttributesByUUID(UUID uuid) {
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext()) {
			ClassAttribute classAttributes = itr.next();

			if (classAttributes.getUuid().equals(uuid)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsClassByName(String name) {
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext()) {
			Class clss = itr.next();

			if (clss.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsClassByUUID(UUID uuid) {
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext()) {
			Class clss = itr.next();

			if (clss.getUuid().equals(uuid)) {
				return true;
			}
		}

		return false;
	}

	public List<ClassAttribute> getClassAttributes() {
		return classAttributes;
	}

	public ClassAttribute getClassAttributesByName(String name) {
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext()) {
			ClassAttribute classAttributes = itr.next();

			if (classAttributes.getName().equalsIgnoreCase(name)) {
				return classAttributes;
			}
		}

		return null;
	}

	public ClassAttribute getClassAttributesByUUID(UUID uuid) {
		Iterator<ClassAttribute> itr = getClassAttributes().iterator();
		while (itr.hasNext()) {
			ClassAttribute classAttributes = itr.next();

			if (classAttributes.getUuid().equals(uuid)) {
				return classAttributes;
			}
		}

		return null;
	}

	public Class getClassByName(String name) {
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext()) {
			Class clss = itr.next();

			if (clss.getName().equalsIgnoreCase(name)) {
				return clss;
			}
		}

		return null;
	}

	public Class getClassByUUID(UUID uuid) {
		Iterator<Class> itr = getClasses().iterator();
		while (itr.hasNext()) {
			Class clss = itr.next();

			if (clss.getUuid().equals(uuid)) {
				return clss;
			}
		}

		return null;
	}

	public List<Class> getClasses() {
		return classes;
	}

	public SkillAttribute getSkillAttributes() {
		return skillAttributes;
	}

    public List<Follower> getFollowers() {
        return followers;
    }

    public Follower getFollowerByUUID(UUID uuid) {
        for (Follower f : followers)
        {
            if (f.getUuid().equals(uuid))
                return f;
        }
        
        return null;
    }
    
    public Follower getFollowerByName(String name) {
        for (Follower f : followers)
        {
            if (f.getName().equalsIgnoreCase(name))
                return f;
        }
        
        return null;
    }

    

}

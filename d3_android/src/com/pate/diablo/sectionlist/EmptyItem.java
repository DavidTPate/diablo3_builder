package com.pate.diablo.sectionlist;

public class EmptyItem implements Item
{
    private final String title;
    private final int level;
    private final SkillType skillType;
    
    public enum SkillType { Primary, Secondary, Defensive, Might, Tactics, Rage, Passive };

    public EmptyItem(String title, int level, SkillType skillType) {
        this.title = title;
        this.level = level;
        this.skillType = skillType;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public SkillType getSkillType() {
        return skillType;
    }

}

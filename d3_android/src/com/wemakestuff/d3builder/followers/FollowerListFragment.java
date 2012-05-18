package com.wemakestuff.d3builder.followers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.wemakestuff.d3builder.OnLoadFragmentsCompleteListener;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Follower;
import com.wemakestuff.d3builder.model.Rune;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.model.SkillAttribute;
import com.wemakestuff.d3builder.sectionlist.EmptyRune;
import com.wemakestuff.d3builder.sectionlist.EmptySkill;
import com.wemakestuff.d3builder.sectionlist.EntryFollowerSkill;
import com.wemakestuff.d3builder.sectionlist.EntryRune;
import com.wemakestuff.d3builder.sectionlist.EntrySkill;
import com.wemakestuff.d3builder.sectionlist.EntrySkillAdapter;
import com.wemakestuff.d3builder.sectionlist.Item;
import com.wemakestuff.d3builder.sectionlist.SectionItem;

public class FollowerListFragment extends ListFragment
{

    private Context                         context;
    private String                          selectedFollower;
    private EntrySkillAdapter               listAdapter;
    private OnLoadFragmentsCompleteListener listener;
    private OnRequiredLevelUpdate requiredLevelListener; 
    private ArrayList<Item>                 items         = new ArrayList<Item>();

    interface OnRequiredLevelUpdate {
        void OnRequiredLevelUpdate(int level);
    }
    
    public static FollowerListFragment newInstance(String selectedFollower, Context c, OnLoadFragmentsCompleteListener listener) {

        FollowerListFragment fragment = new FollowerListFragment();

        fragment.context = c;
        fragment.selectedFollower = selectedFollower;
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            requiredLevelListener = (OnRequiredLevelUpdate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement RequiredLevelUpdate");
        }
    }
    
    public String getSelectedFollower()
    {
        return selectedFollower;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        if (savedInstanceState != null)
        {
            selectedFollower = savedInstanceState.getString("selectedFollower");
        }
        
        setRetainInstance(true);
        setListAdapter(getSkillListAdapter());
    }
    
    @Override
    public void onResume() {
        super.onResume();

        Log.i("OnResume", selectedFollower);
        
        requiredLevelListener.OnRequiredLevelUpdate(getRequiredLevel());
        
        if (listener != null)
            listener.OnLoadFragmentsComplete(selectedFollower);
    }
    
    private boolean isBlankBuild(String followerLink)
    {
        return followerLink.length() == 0 || followerLink.matches("http://.+.battle.net/d3/.+/calculator/.+#[\\.]+![\\.]+![\\.]+");
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedFollower", selectedFollower);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        SelectFollower sf = (SelectFollower) getActivity();
        EntrySkillAdapter skillAdapter = (EntrySkillAdapter) l.getAdapter();
        Item item = (Item) l.getItemAtPosition(position);
        EntryFollowerSkill pairedSkill = null;
        
        if (item instanceof EntryFollowerSkill)
        {
            Follower follower = D3Application.getInstance().getFollowerByName(selectedFollower);
            EntryFollowerSkill e = (EntryFollowerSkill) item;
            
            pairedSkill = getPairedFollowerSkill(skillAdapter, pairedSkill, follower, e);
            
            if (e.isChecked())
            {
                e.setIsChecked(false);
            }
            else
            {
                e.setIsChecked(true);
                pairedSkill.setIsChecked(false);
            }

        }
        
        if (selectedFollower.equals("Templar"))
        {
            sf.setTemplarSkills(getSelectedSkills());
        }
        else if (selectedFollower.equals("Scoundrel"))
        {
            sf.setScoundrelSkills(getSelectedSkills());
        }
        else if (selectedFollower.equals("Enchantress"))
        {
            sf.setEnchantressSkills(getSelectedSkills());
        }
        
        ((SelectFollower) getActivity()).setRequiredLevel(skillAdapter.getMaxLevel(true));
        ((SelectFollower) getActivity()).updateData();
    }
    
    public List<ParcelUuid> getSelectedSkills()
    {
        List<ParcelUuid> skills = new ArrayList<ParcelUuid>();
        ArrayList<Item> items = listAdapter.getItems();
        
        for (Item i : items)
        {
            if (i instanceof EntryFollowerSkill)
            {
                EntryFollowerSkill e = (EntryFollowerSkill) i;
                if (e.isChecked())
                    skills.add(new ParcelUuid(e.getSkill().getUuid()));
            }
        }
        
        return skills;
    }
    
    public int getRequiredLevel()
    {
        
        List<ParcelUuid> skills = getSelectedSkills();
        
        int returnVal = 1;
        
        for (ParcelUuid s : skills)
        {
            Skill j = D3Application.getInstance().getFollowerByName(selectedFollower).getSkillByUUID(s.getUuid());
            
            if (j.getRequiredLevel() > returnVal)
                returnVal = j.getRequiredLevel();
            
        }
        
        Log.i("Class " + selectedFollower, "Returning requiredLevel " + returnVal);
        return returnVal;
        
    }

    private EntryFollowerSkill getPairedFollowerSkill(EntrySkillAdapter skillAdapter, EntryFollowerSkill pairedSkill, Follower follower, EntryFollowerSkill e) {
        for (Skill s : follower.getSkillsByRequiredLevel(e.getSkill().getRequiredLevel()))
        {
            if (!s.getUuid().equals(e.getSkill().getUuid()))
            {
                pairedSkill = (EntryFollowerSkill) skillAdapter.getFollowerSkillByUUID(s.getUuid());
            }
            
        }
        return pairedSkill;
    }
    
    private EntrySkillAdapter getSkillListAdapter()
    {
        items = new ArrayList<Item>();

        Follower follower = D3Application.getInstance().getFollowerByName(selectedFollower);
        List<Integer> requiredLevels = follower.getRequiredLevels(); 

        for (Integer i : requiredLevels)
        {
            items.add(new SectionItem("Level " + i));
            List<Skill> skillsByLevel = follower.getSkillsByRequiredLevel(i.intValue());
            
            for (Skill s : skillsByLevel)
                items.add(new EntryFollowerSkill(s, selectedFollower, false));
            
        }

        listAdapter = new EntrySkillAdapter(context, items);

        return listAdapter;
    }

    public int getMaxLevel()
    {
        return ((EntrySkillAdapter) getListAdapter()).getMaxLevel(true);
    }
    
    public void clear()
    {
        setListAdapter(getSkillListAdapter());
    }

    public String linkifyClassBuild()
    {

        StringBuffer activeVal = new StringBuffer();
        StringBuffer passiveVal = new StringBuffer();
        StringBuffer runeVal = new StringBuffer();

        com.wemakestuff.d3builder.model.Class currClass = D3Application.getInstance().getClassByName(selectedFollower);
        List<Skill> activeSkills = currClass.getActiveSkills();
        List<Skill> passiveSkills = currClass.getPassiveSkills();
        ArrayList<Item> items = listAdapter.getItems();

        SkillAttribute skillAttrbs = D3Application.getInstance().getSkillAttributes();
        String[] skillMapping = skillAttrbs.getSkillMapping();

        for (Item item : items)
        {
            if (item instanceof EmptySkill)
            {
                EmptySkill e = (EmptySkill) item;

                if (e.getSkillType().equals("Passive"))
                {
                    passiveVal.append(skillAttrbs.getMissingValue());
                }
                else
                {
                    activeVal.append(skillAttrbs.getMissingValue());
                    runeVal.append(skillAttrbs.getMissingValue());
                }
            }
            else if (item instanceof EntrySkill)
            {
                Skill s = ((EntrySkill) item).getSkill();

                if (s.getType().equals("Passive"))
                {
                    passiveVal.append(skillMapping[passiveSkills.indexOf(s)]);
                }
                else
                {
                    activeVal.append(skillMapping[activeSkills.indexOf(s)]);
                }
            }
            else if (item instanceof EmptyRune)
            {
                EmptyRune e = (EmptyRune) item;
                runeVal.append(skillAttrbs.getMissingValue());

            }
            else if (item instanceof EntryRune)
            {
                EntryRune e = (EntryRune) item;
                Rune r = e.getRune();
                Skill s = currClass.getSkillByUUID(activeSkills, e.getSkillUUID());
                runeVal.append(skillMapping[s.getRunes().indexOf(r)]);
            }
        }

        return "http://us.battle.net/d3/en/calculator/" + selectedFollower.toLowerCase().replace(" ", "-") + "#" + activeVal.toString() + skillAttrbs.getPassiveSeparator() + passiveVal.toString() + skillAttrbs.getRuneSeparator() + runeVal.toString();
    }

    public void delinkifyClassBuild(String url)
    {

        Pattern p = Pattern.compile("^http://.*/calculator/(.*)#([a-zA-Z\\.]*)!?([a-zA-Z\\.]*)!?([a-zA-Z\\.]*)$");
        Matcher m = p.matcher(url);

        Pattern cap = Pattern.compile("\b([a-z])");

        String activeVal = "";
        String passiveVal = "";
        String runeVal = "";
        String tempClass = "";

        while (m.find())
        {
            if (m.groupCount() >= 1)
            {

                // Doesn't work :( Trying to capitalize each word with this, but
                // it's being a bitch.
                /*
                 * Matcher capital = cap.matcher(tempClass); while
                 * (capital.find()) { if (capital.groupCount() >= 1) { String g
                 * = capital.group(1); tempClass = tempClass.replace("\b" + g,
                 * g.toUpperCase()); } }
                 */

                if (selectedFollower.equalsIgnoreCase(m.group(1).replace("-", " ")))
                {
                    tempClass = m.group(1).replace("-", " ");
                }
                else
                {
                    // Uh-oh!
                }
            }

            if (m.groupCount() >= 2)
            {
                activeVal = m.group(2);
            }

            if (m.groupCount() >= 3)
            {
                passiveVal = m.group(3);
            }

            if (m.groupCount() >= 4)
            {
                runeVal = m.group(4);
            }
        }

        while (activeVal.length() < 6)
        {
            activeVal = activeVal + ".";
        }

        while (passiveVal.length() < 3)
        {
            passiveVal = passiveVal + ".";
        }

        while (runeVal.length() < 6)
        {
            runeVal = runeVal + ".";
        }

        // Reset list
        setListAdapter(getSkillListAdapter());

        com.wemakestuff.d3builder.model.Class currClass = D3Application.getInstance().getClassByName(selectedFollower);
        String[] skillTypes = D3Application.getInstance().getClassAttributesByName(selectedFollower).getSkillTypes();
        List<Skill> activeSkills = currClass.getActiveSkills();
        List<Skill> passiveSkills = currClass.getPassiveSkills();
        ArrayList<Item> items = listAdapter.getItems();

        SkillAttribute skillAttrbs = D3Application.getInstance().getSkillAttributes();

        List<String> skillMapping = Arrays.asList(skillAttrbs.getSkillMapping());

        int activeIndex = 0;
        int passiveIndex = 0;
        int listIndex = 0;

        ArrayList<Item> tempItems = new ArrayList<Item>();

        for (Item item : items)
        {
            if (item instanceof EmptySkill && !((EmptySkill) item).getSkillType().equals("Passive"))
            {
                if (activeVal.charAt(activeIndex) != skillAttrbs.getMissingValue().charAt(0))
                {
                    Skill s = activeSkills.get(skillMapping.indexOf(String.valueOf(activeVal.charAt(activeIndex))));
                    tempItems.add(listIndex, new EntrySkill(s));
                    listIndex++;
                }
                else
                {
                    tempItems.add(new EmptySkill("Choose Skill", 1, skillTypes[activeIndex]));
                    listIndex++;
                }
            }
            else if (item instanceof EmptySkill && ((EmptySkill) item).getSkillType().equals("Passive"))
            {
                if (passiveVal.charAt(passiveIndex) != skillAttrbs.getMissingValue().charAt(0))
                {
                    Skill s = passiveSkills.get(skillMapping.indexOf(String.valueOf(passiveVal.charAt(passiveIndex))));
                    tempItems.add(listIndex, new EntrySkill(s));
                    listIndex++;
                }
                else
                {
                    tempItems.add(new EmptySkill("Choose Skill", 1, "Passive"));
                    listIndex++;
                }
                passiveIndex++;
            }
            else if (item instanceof EmptyRune)
            {
                if (activeVal.charAt(activeIndex) != skillAttrbs.getMissingValue().charAt(0))
                {
                    Skill s = activeSkills.get(skillMapping.indexOf(String.valueOf(activeVal.charAt(activeIndex))));
                    if (runeVal.charAt(activeIndex) == skillAttrbs.getMissingValue().charAt(0))
                    {
                        tempItems.add(listIndex, new EmptyRune("Choose Rune", 1, s.getName(), s.getUuid()));

                    }
                    else
                    {
                        Rune r = s.getRunes().get(skillMapping.indexOf(String.valueOf(runeVal.charAt(activeIndex))));
                        tempItems.add(listIndex, new EntryRune(r, s.getName(), s.getUuid()));
                    }
                    listIndex++;
                }
                else
                {
                    // Don't add the rune, since no skill was picked.
                }
                activeIndex++;
            }
            else
            {
                tempItems.add(listIndex, item);
                listIndex++;
            }
        }
        
        items = tempItems;
        
        setListAdapter(new EntrySkillAdapter(context, items));
    }
}

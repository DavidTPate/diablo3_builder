package com.wemakestuff.d3builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.wemakestuff.d3builder.followers.SelectFollower;
import com.wemakestuff.d3builder.model.D3Application;
import com.wemakestuff.d3builder.model.Follower;
import com.wemakestuff.d3builder.model.Rune;
import com.wemakestuff.d3builder.model.Skill;
import com.wemakestuff.d3builder.model.SkillAttribute;
import com.wemakestuff.d3builder.sectionlist.EmptyFollower;
import com.wemakestuff.d3builder.sectionlist.EmptyRune;
import com.wemakestuff.d3builder.sectionlist.EmptySkill;
import com.wemakestuff.d3builder.sectionlist.EntryRune;
import com.wemakestuff.d3builder.sectionlist.EntrySkill;
import com.wemakestuff.d3builder.sectionlist.EntrySkillAdapter;
import com.wemakestuff.d3builder.sectionlist.Item;
import com.wemakestuff.d3builder.sectionlist.SectionItem;

public class ClassListFragment extends ListFragment
{

    private Context                         context;
    private String                          selectedClass;
    private EntrySkillAdapter               listAdapter;
    private OnLoadFragmentsCompleteListener listener;
    int                                     index;
    int                                     GET_SKILL     = 0;
    int                                     REPLACE_SKILL = 1;
    int                                     GET_RUNE      = 2;
    int                                     REPLACE_RUNE  = 3;
    int                                     NEW_FOLLOWER  = 4;
    int                                     REPLACE_FOLLOWER  = 5;

    ArrayList<Item>                         items         = new ArrayList<Item>();

    public static ClassListFragment newInstance(String selectedClass, Context c, OnLoadFragmentsCompleteListener listener) {

        ClassListFragment fragment = new ClassListFragment();

        fragment.context = c;
        fragment.selectedClass = selectedClass;
        fragment.listener = listener;
        return fragment;
    }

    public String getSelectedClass()
    {
        return selectedClass;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        if (savedInstanceState != null)
        {
            selectedClass = savedInstanceState.getString("selectedClass");
        }
        
        setRetainInstance(true);
        setListAdapter(getSkillListAdapter());
    }
    
    @Override
    public void onResume() {
        super.onResume();

        if (listener != null)
            listener.OnLoadFragmentsComplete(selectedClass);
    }
    
    private boolean isBlankBuild(String classLink)
    {
        return classLink.length() == 0 || classLink.matches("http://.+.battle.net/d3/.+/calculator/.+#[\\.]+![\\.]+![\\.]+");
    }
    
    @Override
    public void onPause() {
//        String classLink = linkifyClassBuild();
//        if (!(classLink.length() == 0) && !isBlankBuild(classLink))
//        {
//            Log.i("onPause - Saving", classLink);
//            SharedPreferences settings = getActivity().getSharedPreferences("classes", 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putString(selectedClass, classLink);
//            editor.commit();
//        }

        super.onPause();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedClass", selectedClass);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        EntrySkillAdapter listAdapter = (EntrySkillAdapter) getListAdapter();
        
        Item item = (Item) getListAdapter().getItem(position);
        int maxLevel = 60;// ((Main) getActivity()).getMaxLevel();
        Bundle b = new Bundle();
        
        if (item instanceof EmptySkill)
        {
            EmptySkill e = (EmptySkill) item;

            Intent intent = new Intent(v.getContext(), SelectSkill.class);
            b.putString("SkillType", e.getSkillType());
            b.putString("SelectedClass", selectedClass);
            b.putInt("RequiredLevel", maxLevel);
            b.putInt("Index", position);
            
            List<ParcelUuid> skills = listAdapter.getCurrentSkills();
            
            if (skills.size() > 0)
                b.putParcelableArrayList("UUIDs", (ArrayList<? extends Parcelable>) listAdapter.getCurrentSkills());
            
            intent.putExtras(b);

            startActivityForResult(intent, GET_SKILL);
        }
        else if (item instanceof EntrySkill)
        {
            EntrySkill e = (EntrySkill) item;

            Intent intent = new Intent(v.getContext(), SelectSkill.class);
            b.putString("SkillType", e.getSkill().getType());
            b.putString("SelectedClass", selectedClass);
            b.putInt("RequiredLevel", maxLevel);
            b.putInt("Index", position);
            
            List<ParcelUuid> skills = listAdapter.getCurrentSkills();
            
            if (skills.size() > 0)
                b.putParcelableArrayList("UUIDs", (ArrayList<? extends Parcelable>) listAdapter.getCurrentSkills());
            
            intent.putExtras(b);

            startActivityForResult(intent, REPLACE_SKILL);
        }
        else if (item instanceof EmptyRune)
        {
            EmptyRune e = (EmptyRune) item;

            Intent intent = new Intent(v.getContext(), SelectRune.class);
            b.putString("SkillName", e.getSkillName());
            b.putString("SelectedClass", selectedClass);
            b.putInt("RequiredLevel", maxLevel);
            b.putSerializable("SkillUUID", e.getSkillUUID());
            b.putInt("Index", position);
            intent.putExtras(b);

            startActivityForResult(intent, GET_RUNE);
        }
        else if (item instanceof EntryRune)
        {
            EntryRune e = (EntryRune) item;

            Intent intent = new Intent(v.getContext(), SelectRune.class);
            b.putString("SkillName", e.getSkillName());
            b.putString("SelectedClass", selectedClass);
            b.putInt("RequiredLevel", maxLevel);
            b.putSerializable("SkillUUID", e.getSkillUUID());
            b.putInt("Index", position);
            intent.putExtras(b);

            startActivityForResult(intent, REPLACE_RUNE);
        }
        else if (item instanceof EmptyFollower)
        {
            EmptyFollower e = (EmptyFollower) item;
            
            Intent intent = new Intent(v.getContext(), SelectFollower.class);
            b.putString("SkillName", e.getName());
            b.putString("SelectedClass", selectedClass);
            b.putInt("RequiredLevel", maxLevel);
            b.putSerializable("SkillUUID", e.getFollowerUUID());
            b.putInt("Index", position);
            b.putParcelable("UUID", new ParcelUuid(e.getFollowerUUID()));
            intent.putExtras(b);
            
            startActivityForResult(intent, NEW_FOLLOWER);
        }
        super.onListItemClick(l, v, position, id);
    }

    private EntrySkillAdapter getSkillListAdapter()
    {

        return getSkillListAdapter(false);
    }

    private EntrySkillAdapter getSkillListAdapter(boolean includeRunes)
    {
        items = new ArrayList<Item>();
        String[] skillTypes = D3Application.getInstance().getClassAttributesByName(selectedClass).getSkillTypes();

        items.add(new SectionItem("Left Click - Primary"));
        items.add(new EmptySkill("Choose Skill", 1, skillTypes[0]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));

        items.add(new SectionItem("Right Click - Secondary"));
        items.add(new EmptySkill("Choose Skill", 2, skillTypes[1]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));

        items.add(new SectionItem("Action Bar Skills"));
        items.add(new EmptySkill("Choose Skill", 4, skillTypes[2]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill("Choose Skill", 9, skillTypes[3]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill("Choose Skill", 14, skillTypes[4]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));
        items.add(new EmptySkill("Choose Skill", 19, skillTypes[5]));
        if (includeRunes)
            items.add(new EmptyRune("Choose Rune", 1, "Rune", null));

        items.add(new SectionItem("Passive Skills"));
        items.add(new EmptySkill("Choose Skill", 10, "Passive"));
        items.add(new EmptySkill("Choose Skill", 20, "Passive"));
        items.add(new EmptySkill("Choose Skill", 30, "Passive"));
        
        items.add(new SectionItem("Followers"));
        for (Follower f : D3Application.getInstance().getFollowers())
        {
            items.add(new EmptyFollower(f.getName(), f.getShortDescription(), f.getIcon(), f.getUuid()));
        }

        listAdapter = new EntrySkillAdapter(context, items);

        return listAdapter;
    }

    private void updateFollowerData(List<ParcelUuid> templar, List<ParcelUuid> scoundrel, List<ParcelUuid> enchantress)
    {
        List<Item> items = ((EntrySkillAdapter) getListAdapter()).getFollowers();
        
        for (Item i : items)
        {
            if (i instanceof EmptyFollower)
            {
                EmptyFollower e = (EmptyFollower) i;
                if (e.getName().equals("Templar"))
                {
                    ((EmptyFollower) i).setSkills(templar);
                }
                else if (e.getName().equals("Scoundrel"))
                {
                    ((EmptyFollower) i).setSkills(scoundrel);
                }
                else if (e.getName().equals("Enchantress"))
                {
                    ((EmptyFollower) i).setSkills(enchantress);
                }
            }
        }
        
    }
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == NEW_FOLLOWER)
        {
            Log.i("Got a new follower!", "Testing");
            Log.i("ResultCode", "" + resultCode);
            int t = Activity.RESULT_CANCELED;
            if (resultCode == Activity.RESULT_OK)
            {
                
                List<ParcelUuid> templar = null;
                List<ParcelUuid> scoundrel = null;
                List<ParcelUuid> enchantress = null;
                
                Log.i("Extras size", "" + data.getExtras().size());
                
                if (data.hasExtra("Templar"))
                {
                    templar = data.getParcelableArrayListExtra("Templar");
                    Log.i("ClassList - Got Templar Skills", "" + templar.size());
                }
                
                if (data.hasExtra("Scoundrel"))
                {
                    scoundrel = data.getParcelableArrayListExtra("Scoundrel");
                    Log.i("ClassList - Got Scoundrel Skills", "" + scoundrel.size());
                }
                
                if (data.hasExtra("Enchantress"))
                {
                    enchantress = data.getParcelableArrayListExtra("Enchantress");
                    Log.i("ClassList - Got Enchantress Skills", "" + enchantress.size());
                }
                
                updateFollowerData(templar, scoundrel, enchantress);
            }
        }
        
        if (requestCode == GET_SKILL || requestCode == REPLACE_SKILL)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Bundle b = data.getExtras();

                String skillUUID = null;
                int index = -1;

                if (b.containsKey("Skill_UUID"))
                {
                    skillUUID = b.getString("Skill_UUID");
                }

                if (b.containsKey("Index"))
                {
                    index = b.getInt("Index");
                }


                if (skillUUID != null && index >= 0 && D3Application.getInstance().getClassByName(selectedClass).containsActiveSkillByUUID(UUID.fromString(skillUUID)))
                {
                    Skill s = D3Application.getInstance().getClassByName(selectedClass).getActiveSkillByUUID(UUID.fromString(skillUUID));
                    items.set(index, new EntrySkill(s));
                    if (requestCode == GET_SKILL)
                    {
                        items.add(index + 1, new EmptyRune("Choose Rune", 1, s.getName(), s.getUuid()));
                    }
                    else if (requestCode == REPLACE_SKILL)
                    {
                        Item item = items.get(index + 1);
                        
                        if (item instanceof EntryRune)
                        {
                            EntryRune e = (EntryRune) item;
                            if (!e.getSkillUUID().equals(s.getUuid()))
                            {
                                items.set(index + 1, new EmptyRune("Choose Rune", 1, s.getName(), s.getUuid()));
                            }
                        }
                        else
                        {
                            items.set(index + 1, new EmptyRune("Choose Rune", 1, s.getName(), s.getUuid()));
                        }
                    }
                    listAdapter.setList(items);
                    ((SelectClass) getActivity()).setRequiredLevel(listAdapter.getMaxLevel(false));

                }
                else if (skillUUID != null && index >= 0 && D3Application.getInstance().getClassByName(selectedClass).containsPassiveSkillByUUID(UUID.fromString(skillUUID)))
                {
                    Skill s = D3Application.getInstance().getClassByName(selectedClass).getPassiveSkillByUUID(UUID.fromString(skillUUID));
                    items.set(index, new EntrySkill(s));
                    listAdapter.setList(items);
                    
                    ((SelectClass) getActivity()).setRequiredLevel(listAdapter.getMaxLevel(false));
                }
                else
                {
                    // Uh-Oh!
                }
            }
            else
            {
                // Do nothing?
            }
        }
        else if (requestCode == GET_RUNE || requestCode == REPLACE_RUNE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Bundle b = data.getExtras();

                String runeUUID = null;
                String skillUUID = null;
                int index = -1;

                if (b.containsKey("Rune_UUID"))
                {
                    runeUUID = b.getString("Rune_UUID");
                }

                if (b.containsKey("Skill_UUID"))
                {
                    skillUUID = b.getString("Skill_UUID");
                }

                if (b.containsKey("Index"))
                {
                    index = b.getInt("Index");
                }


                if (D3Application.getInstance().getClassByName(selectedClass).containsActiveSkillByUUID(UUID.fromString(skillUUID)))
                {
                    if (skillUUID != null && index >= 0 && D3Application.getInstance().getClassByName(selectedClass).getActiveSkillByUUID(UUID.fromString(skillUUID)).containsRuneByUUID(UUID.fromString(runeUUID)))
                    {
                        Rune s = D3Application.getInstance().getClassByName(selectedClass).getActiveSkillByUUID(UUID.fromString(skillUUID)).getRuneByUUID(UUID.fromString(runeUUID));
                        items.set(index, new EntryRune(s, D3Application.getInstance().getClassByName(selectedClass).getActiveSkillByUUID(UUID.fromString(skillUUID)).getName(), UUID.fromString(skillUUID)));
                        listAdapter.setList(items);
                        ((SelectClass) getActivity()).setRequiredLevel(listAdapter.getMaxLevel(false));
                    }
                    else
                    {
                        // Uh-Oh!
                    }
                }
                else
                {
                    // Uh-Oh
                }
            }
            else
            {
                // Do nothing?
            }
        }
    }

    public int getMaxLevel()
    {
        return listAdapter.getMaxLevel(false);
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

        com.wemakestuff.d3builder.model.Class currClass = D3Application.getInstance().getClassByName(selectedClass);
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

        return "http://us.battle.net/d3/en/calculator/" + selectedClass.toLowerCase().replace(" ", "-") + "#" + activeVal.toString() + skillAttrbs.getPassiveSeparator() + passiveVal.toString() + skillAttrbs.getRuneSeparator() + runeVal.toString();
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

                if (selectedClass.equalsIgnoreCase(m.group(1).replace("-", " ")))
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
        setListAdapter(getSkillListAdapter(true));

        com.wemakestuff.d3builder.model.Class currClass = D3Application.getInstance().getClassByName(selectedClass);
        String[] skillTypes = D3Application.getInstance().getClassAttributesByName(selectedClass).getSkillTypes();
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

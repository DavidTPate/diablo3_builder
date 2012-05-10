package com.pate.diablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pate.diablo.model.Class;
import com.pate.diablo.model.DataModel;
import com.pate.diablo.model.Skill;
import com.pate.diablo.sectionlist.EmptyItem;
import com.pate.diablo.sectionlist.EntrySkill;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;
import com.pate.diablo.sectionlist.SectionItem;
import com.pate.diablo.sectionlist.EmptyItem.SkillType;

public class Main extends SherlockListActivity {
    SpinnerAdapter  adapter;
    ArrayList<Item> items = new ArrayList<Item>();
    Spinner selectedClass;
    Spinner requiredLevel;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_dropdown_item_1line);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        
        actionBar.setCustomView(R.layout.actionbar_custom_view);
        
        selectedClass = (Spinner) findViewById(R.id.spinner_class);
        requiredLevel = (Spinner) findViewById(R.id.spinner_level);
        
        selectedClass.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (view != null)
                    Toast.makeText(view.getContext(),selectedClass.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                
            }

        });
        requiredLevel.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (view != null)
                    Toast.makeText(view.getContext(),requiredLevel.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                
            }
            
        });
        Log.i("SelectedClass", selectedClass.getSelectedItem().toString());
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getResources().openRawResource(R.raw.classes)));

		String fakeJson = "";
		String line;
		try {
			line = reader.readLine();
			while (line != null) {
				fakeJson = fakeJson + line;
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DataModel dm = gson.fromJson(fakeJson, DataModel.class);

        items.add(new SectionItem("Left Click - Primary"));
        items.add(new EmptyItem("Choose Skill", 1, SkillType.Primary));
        
        items.add(new SectionItem("Right Click - Secondary"));
        items.add(new EmptyItem("Choose Skill", 2, SkillType.Secondary));
        
        items.add(new SectionItem("Action Bar Skills"));
        items.add(new EmptyItem("Choose Skill", 4 , SkillType.Defensive));
        items.add(new EmptyItem("Choose Skill", 9 , SkillType.Might));
        items.add(new EmptyItem("Choose Skill", 14, SkillType.Tactics));
        items.add(new EmptyItem("Choose Skill", 19, SkillType.Rage));
        
        items.add(new SectionItem("Passive Skills"));
        items.add(new EmptyItem("Choose Skill", 10, SkillType.Passive));
        items.add(new EmptyItem("Choose Skill", 20, SkillType.Passive));
        items.add(new EmptyItem("Choose Skill", 30, SkillType.Passive));

        EntrySkillAdapter adapter = new EntrySkillAdapter(this, items);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Item item = items.get(position);

        if (item instanceof EmptyItem)
        {
            EmptyItem e = (EmptyItem) item;
            Intent intent = new Intent(v.getContext(), SelectSkill.class);

            Bundle b = new Bundle();
            b.putString("SkillType", e.getSkillType().toString());                
            b.putString("SelectedClass", selectedClass.getSelectedItem().toString());
            b.putInt("RequiredLevel", Integer.parseInt(requiredLevel.getSelectedItem().toString()));
            intent.putExtras(b);

            startActivity(intent);
        }
        //Toast.makeText(this, "You clicked " + item.title, Toast.LENGTH_SHORT).show();


        super.onListItemClick(l, v, position, id);
    }
}

package com.pate.diablo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pate.diablo.model.Class;
import com.pate.diablo.model.DataModel;
import com.pate.diablo.sectionlist.EntryAdapter;
import com.pate.diablo.sectionlist.EntryItem;
import com.pate.diablo.sectionlist.Item;
import com.pate.diablo.sectionlist.SectionItem;

public class Main extends SherlockListActivity {
    SpinnerAdapter  adapter;
    ArrayList<Item> items = new ArrayList<Item>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_dropdown_item_1line);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                Log.e("item position", String.valueOf(position));
                Log.i("ITEM", adapter.getItem(position).toString());
                return true;
            }
        });

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        File file = new File("Json.json");
        String fakeJson = "";
        try {
            fakeJson = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        DataModel dm = gson.fromJson(fakeJson, DataModel.class);
        List<Class> classes = dm.getClasses();

        items.add(new SectionItem("Mouse Skills"));
        items.add(new EntryItem("Left Click Skill", "Attack"));
        items.add(new EntryItem("Left Click Rune", "Some Rune"));
        items.add(new EntryItem("Right Click", "Fireball"));
        items.add(new EntryItem("Right Click Rune", "Another rune"));

        items.add(new SectionItem("Action Bar Skills"));
        items.add(new EntryItem("Skill 1", "Skill 1"));
        items.add(new EntryItem("Skill 1 Rune", "Skill 1 Rune"));
        items.add(new EntryItem("Skill 2", "Skill 2"));
        items.add(new EntryItem("Skill 2 Rune", "Skill 2 Rune"));
        items.add(new EntryItem("Skill 3", "Skill 3"));
        items.add(new EntryItem("Skill 3 Rune", "Skill 3 Rune"));
        items.add(new EntryItem("Skill 4", "Skill 4"));
        items.add(new EntryItem("Skill 4 Rune", "Skill 4 Rune"));

        EntryAdapter adapter = new EntryAdapter(this, items);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (!items.get(position).isSection()) {

            EntryItem item = (EntryItem) items.get(position);

            Toast.makeText(this, "You clicked " + item.title, Toast.LENGTH_SHORT).show();

        }

        super.onListItemClick(l, v, position, id);
    }
}

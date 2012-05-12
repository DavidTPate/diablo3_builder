package com.pate.diablo;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.pate.diablo.model.D3Application;
import com.pate.diablo.model.Rune;
import com.pate.diablo.sectionlist.EntryRune;
import com.pate.diablo.sectionlist.EntrySkill;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;

public class RuneListFragment extends ListFragment {
	Context context;
	String skillName;
	String selectedClass;
	int requiredLevel;
	OnClickListener listener;
	UUID skillUUID;

	ArrayList<Item> items = new ArrayList<Item>();
	private static final String KEY_CONTENT = "TestFragment:Content";

	public static RuneListFragment newInstance(String content, Context c,
			String skillName, UUID skillUUID, String selectedClass,
			int requiredLevel) {
		RuneListFragment fragment = new RuneListFragment();

		fragment.context = c;
		fragment.skillName = skillName;
		fragment.selectedClass = selectedClass;
		fragment.requiredLevel = requiredLevel;
		fragment.skillUUID = skillUUID;

		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		for (Rune s : D3Application.dataModel.getClassByName(selectedClass)
				.getActiveSkillByUUID(skillUUID)
				.getRunesByRequiredLevel(requiredLevel)) {
			items.add(new EntryRune(s, skillName, skillUUID));
		}
		EntrySkillAdapter adapter = new EntrySkillAdapter(context, items);

		setListAdapter(adapter);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	public void setOnListItemClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		listener.onClick(v);
		super.onListItemClick(l, v, position, id);
	}

}

package com.pate.diablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pate.diablo.model.D3Application;
import com.pate.diablo.model.DataModel;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class Main extends SherlockFragmentActivity {
	SpinnerAdapter mSpinnerAdapter;
	ArrayList<Item> items = new ArrayList<Item>();
	EntrySkillAdapter listAdapter;
	TitlePageIndicator indicator;
	String selectedClass;
	Spinner maxLevelSpinner;
	int maxLevel;
	ClassFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;
	int index;
	int GET_SKILL = 0;
	int REPLACE_SKILL = 1;
	int GET_RUNE = 2;
	int REPLACE_RUNE = 3;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest newAd = new AdRequest();
		newAd.addTestDevice("BDD7A55C1502190E502F14CBFDF9ABC7");
		newAd.addTestDevice("E85A995C749AE015AA4EE195878C0982");
		adView.loadAd(newAd);

		if (D3Application.dataModel == null) {
			Gson gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().create();
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

			D3Application
					.setDataModel(gson.fromJson(fakeJson, DataModel.class));
		}
		
		Bundle b = new Bundle();
		Intent intent = new Intent(Main.this, SelectClass.class);     
        intent.putExtras(b);
        startActivity(intent);
	}
	
}

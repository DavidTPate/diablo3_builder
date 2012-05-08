package com.pate.diablo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;

public class Main extends SherlockActivity {
    SpinnerAdapter      adapter;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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

    }
}

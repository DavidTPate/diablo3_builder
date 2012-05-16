package com.wemakestuff.d3builder.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.wemakestuff.d3builder.R;
import com.wemakestuff.d3builder.SelectClass;
import com.wemakestuff.d3builder.SelectSkill;

public class Dashboard extends SherlockActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        Button skillCalculator = (Button) findViewById(R.id.dashboard_btn_skill_calculator);
        Button followers = (Button) findViewById(R.id.dashboard_btn_followers);
        Button lore = (Button) findViewById(R.id.dashboard_btn_lore);
        
        skillCalculator.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, SelectClass.class);
                startActivity(intent);
                
            }
        });
        
        
        followers.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
        
    }
}

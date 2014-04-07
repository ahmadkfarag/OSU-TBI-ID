package com.tbi_id;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class HelpActivity extends FragmentActivity {

	TabsPagerAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator = indicator;
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(0xff971425); 
        //indicator.setFooterColor(0xeeef3d34);
        indicator.setFooterColor(0xffffffff);
        indicator.setFooterLineHeight(1 * density); //1dp
        indicator.setFooterIndicatorHeight(3 * density); //3dp
        indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        indicator.setTextColor(0xffffffff);
        indicator.setSelectedColor(0xffffffff);
        indicator.setSelectedBold(true);

        //Settings button
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
        //open up settings activity if the settings button is clicked
        settingsButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent i = new Intent(getApplicationContext(), com.tbi_id.SettingsActivity.class);
        		startActivity(i);				
        	}
        });
      		
        //About Button
        ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
        	//open up the start interview activity if clicked
        	@Override
			public void onClick(View v) {
        		Intent i = new Intent(getApplicationContext(), com.tbi_id.AboutActivity.class);
        		startActivity(i);
        	}
        });

        //Home Button
        ImageButton homeButton = (ImageButton) findViewById(R.id.home_button_main_screen);
        //if the home button is clicked, send the user back to the home screen
        homeButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
        		startActivity(i);
        	}
        });
        
    }
}

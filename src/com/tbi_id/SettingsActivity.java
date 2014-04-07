package com.tbi_id;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// set layout from xml
		setContentView(R.layout.settings);

		// get saved data for settings
		final SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		//the input field for entering the email address
		final EditText enterEmailHipaa = (EditText) findViewById(R.id.emailEnterHipaa);
		//a text block that tells user to enter their email address
		final TextView emailNotif = (TextView) findViewById(R.id.enterEmailNotif);

		//get checkbox
		final CheckBox checkBoxHipaa = (CheckBox) findViewById(R.id.hippaCompliance);
		
		//set the boolean false equal to the value of the checkbox when it was when previously run, if not found, set it to false
		Boolean checked = sharedPrefs.getBoolean("checkboxHipaa", false);
		// if the value was false, then they are not free from hipaa and cannot send the data so email is turned off
		if (checked == false) {
			enterEmailHipaa.setVisibility(View.GONE);
			emailNotif.setVisibility(View.GONE);
			checkBoxHipaa.setChecked(false);
		}
		
		else {
			
			String email = sharedPrefs.getString("emailHipaa", "Enter Email Here");
			enterEmailHipaa.setText(email);
			checkBoxHipaa.setChecked(true);
		}
		
		
		
		checkBoxHipaa.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
				SharedPreferences.Editor editor = sharedPrefs.edit();
				boolean checked = isChecked;
				editor.putBoolean("checkboxHipaa", checked);
				editor.apply();
				
				if (checked)
				{
					enterEmailHipaa.setVisibility(View.VISIBLE);
					emailNotif.setVisibility(View.VISIBLE);
				}
				
				else 
				{
					enterEmailHipaa.setVisibility(View.GONE);
					emailNotif.setVisibility(View.GONE);
				}		
			}
		});
		
		

		//get save button
		ImageButton saveButton = (ImageButton) findViewById(R.id.save_settings);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences.Editor editor = sharedPrefs.edit();
				boolean checked = checkBoxHipaa.isChecked();
				String email = enterEmailHipaa.getText().toString();
				editor.putString("emailHipaa", email);
				editor.putBoolean("checkboxHipaa", checked);
				editor.apply();
				
				//dialog notification			
				Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
				startActivity(i);			
				}
		});
		
		
		//Home Button
		ImageButton homeButton = (ImageButton) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
				startActivity(i);
			}
		});
		
		//Help Button
		ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), com.tbi_id.HelpActivity.class);
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
		
		//get the settings button and make it not pressable
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		settingsButton.setEnabled(false);		
		
		
	}
}

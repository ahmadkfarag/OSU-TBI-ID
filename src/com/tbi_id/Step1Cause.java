package com.tbi_id;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Step1Cause extends Activity {
	protected static int questionNum;
	final Context context = this;
	String cause;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml
		setContentView(R.layout.activity_step1_cause);
		
		//About Button
		ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), com.tbi_id.AboutActivity.class);
				startActivity(i);
			}
		});
		
		//Help Button
		ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), com.tbi_id.HelpActivity.class);
				startActivity(i);
			}
		});
		
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
		
		//Home Button
		ImageButton homeButton = (ImageButton) findViewById(R.id.home_button_main_screen);
		//if the home button is clicked, send the user back to the home screen
		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
				startActivity(i);
			}
		});		
		
		//get passed data to save the cause with the appopriate question for later use
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		questionNum = (Integer) bundle.get("questionNum");
		@SuppressWarnings("unchecked")
		final HashMap<String, String> data = (HashMap<String, String>) bundle.getSerializable("patientData");
		//get the input field where the patient will enter the case
		final EditText enterCause = (EditText) findViewById(R.id.enterCause);
		//button for adding an additional entry
		ImageButton addEntry = (ImageButton) findViewById(R.id.addEntry);
		//button for finishing adding causes
		ImageButton done = (ImageButton) findViewById(R.id.done);
		
		// if done is clicked, save the causes from the input field with the appropriate title for later use to a bundle then return to the step one question activity
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cause = enterCause.getText().toString();
				// the key for the data will be "cause" along side what question it is
				data.put("cause" + questionNum, cause);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
				Bundle b = new Bundle();
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				//add bundle to intent then start activity
				i.putExtras(b);
				startActivity(i);
				
				
			}
		});
		
		
		
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_cause, menu);
		return true;
	}

}

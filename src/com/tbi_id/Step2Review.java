package com.tbi_id;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Step2Review extends Activity {
	final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//get passed data from bundle which has patient info, questionNum, and cause in it and set them to variables for use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();	
		final Integer count = (Integer) b.get("causeCount");
		
		//if count == 0, go to step 3
		if (count == 0) {
			//Intent i = new Intent(getApplicationContext(), com.tbi_id.Step3Activity.class);
			//startActivity(i);
		}
		
		//set view from xml		
		setContentView(R.layout.activity_step2_review);
				
		//Settings Button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		settingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.SettingsActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			} 
	
		});
		
		//About Button
		ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.AboutActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		//Home Button
		ImageButton homeButton = (ImageButton) findViewById(R.id.home_button_main_screen);
		//if the home button is clicked, send the user back to the home screen
		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		//Help Button
		ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.HelpActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});		

		HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		
		//get values in data hashmap
		String interview_name = data.get("Interview Name");
		String interview_id = data.get("Interview Id");
		String interview_date = data.get("Interview Date");
		String interview_age = data.get("Interview Age");
		String causeN = "";
		
		//set values for variables used in determining data
		int youngest = 1000;
		String youngestCause = "";
		int recent = 0;
		String recentCause = "";
		int loc = 0;
		int tbi = 0;
		int modOrSev=0;
		boolean mild = false, moderate=false, severe=false;
		String worstTBI = "";
		String worstTBIage = "";

		//Get TextViews for the Review Page
		TextView causeValue = (TextView) findViewById(R.id.tbiCount_text);
		TextView tbiCount = (TextView) findViewById(R.id.tbiCount_text);
		TextView locValue = (TextView) findViewById(R.id.locCount_text);
		TextView first = (TextView) findViewById(R.id.first_age);
		TextView recentValue = (TextView) findViewById(R.id.recent);
		TextView badTBI = (TextView) findViewById(R.id.badCount_text);
		TextView worst = (TextView) findViewById(R.id.worst_text);
		TextView worstAge = (TextView) findViewById(R.id.worst_age);
		
		//check to make sure causes exist. If none, causeCount = 0
		if (count > 0) {		
			//for cause 1 to causeCount, append "cause" to "i"
			for (int i=1; i<=count; i++) {						
				String temp = data.get("cause"+i+"Age");	
				//Get Youngest Injury
				int test = Integer.parseInt(temp);
				if(test < youngest)
				{
					youngest = test;
					youngestCause = data.get("cause"+i);
				}
				//Get Oldest Injury
				if(test > recent)
				{
					recent = test;
					recentCause = data.get("cause" + i);
				}
				
			
				//If you said No in Step2Activity
				if(data.containsKey("cause"+i+"Dazed"))
				{
					String temp2 = data.get("cause"+i+"Dazed");
					//If they were dazed or have gap in memory
					if(temp2.equals("Yes"))
					{
						mild = true;
						//Was dazed/had a gap in memory
						tbi++;
						//if a moderate or severe TBI has not been set yet
						if(!moderate && !severe)
						{
							worstTBI=data.get("cause"+i);
							worstTBIage=data.get("cause"+i+"Age");
						}
					}
					else
					{
						//Had no gap in memory/dazed (not a TBI)
					}
				}
				//If you said Yes in Step2Activity
				else if (data.containsKey("cause"+i+"Length"))
				{
					//Injury is a TBI and LOC counter increased
					tbi++;
					loc++;
					String temp2 = data.get("cause"+i+"Length");
					
					//Lost consciousness for less than 30 minutes
					if(temp2.equals("<30"))
					{
						mild = true;
						//if a moderate or severe TBI has not been set yet
						if(!moderate && !severe)
						{
							worstTBI=data.get("cause"+i);
							worstTBIage=data.get("cause"+i+"Age");
						}
					}
					
					//Lost consciousness between 30 minutes and 24 hours
					else if(temp2.equals("30-24"))
					{
						moderate = true;
						//If a severe TBI has not been set yet
						if(!severe)
						{
							worstTBI=data.get("cause"+i);
							worstTBIage=data.get("cause"+i+"Age");
						}
						modOrSev++;
					}
					
					//Lost consciousness for longer than 24 hours
					else
					{
						severe = true;
						worstTBI=data.get("cause"+i);
						worstTBIage=data.get("cause"+i+"Age");
						modOrSev++;
					}
				}
			}
			//Set the count of TBI's
			String holder = String.valueOf(tbi);
			tbiCount.setText(holder);
			//Set the count of LOC's
			holder = String.valueOf(loc);
			locValue.setText(holder);
			//Set the age of the youngest
			holder = String.valueOf(youngest);
			first.setText(holder);
			//Calculate difference between most recent and current age
			int currentAge = Integer.parseInt(interview_age);
			int difference = currentAge - recent;
			holder = String.valueOf(difference)+" years";
			recentValue.setText(holder);
			//Set the Count of Mod/Severe TBI
			holder = String.valueOf(modOrSev);
			badTBI.setText(holder);
			//Set the Worst TBI
			if(mild)
			{
				worst.setText("Mild");
			}
			if (moderate)
			{
				worst.setText("Moderate");
			}
			if (severe)
			{
				worst.setText("Severe");
			}
			else if (!mild && !moderate && !severe)
			{
				worst.setText("None");
			}
			//Set the Worst TBI Age
			worstAge.setText(worstTBIage);
		}
				
		
		//Next Question Button
		ImageButton nextButton = (ImageButton) findViewById(R.id.step3);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), com.tbi_id.Step3Activity.class);
				i.putExtras(b);
				startActivity(i);
			}
		});	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step2_review, menu);
		return true;
	}

}
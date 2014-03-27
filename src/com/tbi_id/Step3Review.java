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
import android.widget.ImageButton;
import android.widget.TextView;

public class Step3Review extends Activity {
	final Context context = this;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_step3_review);
		
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
		
		//get passed data from bundle which has patient info, questionNum, and cause in it and set them to variables for use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();			
		final Integer step3count = (Integer) b.get("step3Count");
		
		HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		String interview_age = data.get("Interview Age");		
		
		int noLOCcountint = 0;
		int loccountint = 0;
		int lt30countint = 0;
		int btw30and24int = 0;
		int gt24countint =0;
		int worsteffectduration=0;
		int recent = 0;		
		boolean mild = false, moderate=false, severe=false;
		String worsteffectstring = "";
		String worsteffectagestring = "";
		
		//Get TextViews for Review Page
		TextView injurycount = (TextView) findViewById(R.id.injury_count);
		TextView nonloccount = (TextView) findViewById(R.id.nonloc_count);
		TextView loccount = (TextView) findViewById(R.id.loc_count);
		TextView loclt30 = (TextView) findViewById(R.id.lt30);
		TextView locbtw30and24 = (TextView) findViewById(R.id.a30to24);
		TextView locgt24 = (TextView) findViewById(R.id.gt24hrs);
		TextView worsteffect = (TextView) findViewById(R.id.worsteffect);
		TextView ageatworsteffect = (TextView) findViewById(R.id.ageatworst);
		TextView duration = (TextView) findViewById(R.id.duration);
		TextView timesincemostrecent = (TextView) findViewById(R.id.timesincemostrecent);
		
		//check to make sure causes exist.
		if (step3count >= 1) {		
			//for cause 1 to step3count, append "cause" to "i"
			for (int i=1; i<=step3count; i++) {	
				String temp = data.get("step3agebegan_cause"+i);	
				int test = Integer.parseInt(temp);
				//Get Most Recent Injury
				if(test > recent)
				{
					recent = test;
				}				
				//Get the count of non-LOC events
				if(data.containsKey("step3cause"+i+"Length"))
				{
					String temp2 = data.get("step3cause"+i+"Length");
					//If there was no LOC
					if(temp2.equals("noLOC"))
					{
						noLOCcountint++;
						//if a moderate or severe TBI has not been set yet
						if(!moderate && !severe)
						{
							worsteffectstring = data.get("step3cause"+i);
							worsteffectagestring = data.get("step3agebegan_cause"+i);
							worsteffectduration = Integer.valueOf(data.get("step3ageended_cause"+i)) - Integer.valueOf(data.get("step3agebegan_cause"+i));
									
						}
					}
					else if(temp2.equals("<30"))
					{
						lt30countint++;
						loccountint++;
						mild = true;
						//if a moderate or severe TBI has not been set yet
						if(!moderate && !severe)
						{
							worsteffectstring = data.get("step3cause"+i);
							worsteffectagestring = data.get("step3agebegan_cause"+i);
							worsteffectduration = Integer.valueOf(data.get("step3ageended_cause"+i)) - Integer.valueOf(data.get("step3agebegan_cause"+i));							
						}						
					}
					else if(temp2.equals("30-24"))
					{
						btw30and24int++;
						loccountint++;
						moderate = true;
						//if a moderate or severe TBI has not been set yet
						if(!severe)
						{
							worsteffectstring = data.get("step3cause"+i);
							worsteffectagestring = data.get("step3agebegan_cause"+i);
							worsteffectduration = Integer.valueOf(data.get("step3ageended_cause"+i)) - Integer.valueOf(data.get("step3agebegan_cause"+i));							
						}							
					}
					else if(temp2.equals(">24"))
					{
						gt24countint++;
						loccountint++;
						severe = true;
						worsteffectstring=data.get("step3cause"+i);
						worsteffectagestring=data.get("step3agebegan_cause"+i);	
						worsteffectduration = Integer.valueOf(data.get("step3ageended_cause"+i)) - Integer.valueOf(data.get("step3agebegan_cause"+i));						
					}
				}
			}
		}
		
		//set text for total injury count
		String injurycounttext = String.valueOf(step3count);
		injurycount.setText(injurycounttext);
		//set text for non loc events
		String nonLoccounttext = String.valueOf(noLOCcountint);
		nonloccount.setText(nonLoccounttext);
		//set text for total loc events
		String loccounttext = String.valueOf(loccountint);
		loccount.setText(loccounttext);
		//set text for loc < 30 mins
		String lt30counttext = String.valueOf(lt30countint);
		loclt30.setText(lt30counttext);
		//set text for loc between 30 mins and 24 hrs
		String btw30and24counttext = String.valueOf(btw30and24int);
		locbtw30and24.setText(btw30and24counttext);
		//set text for loc > 24 hrs
		String gt24counttext = String.valueOf(gt24countint);
		locgt24.setText(gt24counttext);
		//Set the Worst effect
		if(mild)
		{
			worsteffect.setText("Mild with LOC");
		}
		if (moderate)
		{
			worsteffect.setText("Moderate");
		}
		if (severe)
		{
			worsteffect.setText("Severe");
		}
		else if (!mild && !moderate && !severe)
		{
			worsteffect.setText("Mild without LOC");
		}
		//Set the Worst effect's Age
		ageatworsteffect.setText(worsteffectagestring);
		//set text for duration of worst effect
		String durationtext = String.valueOf(worsteffectduration);
		duration.setText(durationtext);
		//calculate time since most recent and set text
		int currentAge = Integer.parseInt(interview_age);
		int difference = currentAge - recent;
		String holder = String.valueOf(difference)+" years";
		timesincemostrecent.setText(holder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_review, menu);
		return true;
	}

}

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

public class Step1Review extends Activity {
	final Context context = this;
	protected static int loc=0;
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
			Intent i = new Intent(getApplicationContext(), com.tbi_id.Step3Activity.class);
			i.putExtras(b);			
			startActivity(i);
		}
		
		//set view from xml		
		setContentView(R.layout.activity_step1_review);
				
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
		StringBuilder causeappender = new StringBuilder();
		
		TextView causeValue = (TextView) findViewById(R.id.cause_value_text);
		
		//check to make sure causes exist. If none, causeCount = 0
		if (count > 0) {
			//for cause 1 to causeCount, append "cause" to "i"
			for (int i=1; i<=count; i++) {
				//data.get each causeN			
				causeN = data.get("cause" + i);	
				
				causeappender.append(causeN + "\n");
			}			
		}
		
		
		causeValue.setText(causeappender);			
		
		//Next Question Button
		ImageButton nextButton = (ImageButton) findViewById(R.id.step2);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				b.putSerializable("locCount", loc);
				Intent i = new Intent(getApplicationContext(), com.tbi_id.Step2Activity.class);
				i.putExtras(b);
				startActivity(i);
			}
		});		
		
		//get interviewName and set it to output
		TextView interviewName = (TextView) findViewById(R.id.interview_name_val);
		interviewName.setText(interview_name);
		
		//get interviewId and set it to output
		TextView interviewId = (TextView) findViewById(R.id.interview_id_val);
		interviewId.setText(interview_id);
		
		//get interviewDate and set it to output
		TextView interviewDate = (TextView) findViewById(R.id.interview_date_val);
		interviewDate.setText(interview_date);
		
		//get interviewAge and set it to output
		TextView interviewAge = (TextView) findViewById(R.id.interview_age_val);
		interviewAge.setText(interview_age);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_review, menu);
		return true;
	}

}

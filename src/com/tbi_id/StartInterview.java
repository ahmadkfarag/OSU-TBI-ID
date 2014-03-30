package com.tbi_id;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class StartInterview extends Activity {

	protected String interviewName;
	protected String interviewId;
	protected String interviewAge;
	protected String interviewDate;
	protected HashMap<String,String> data = new HashMap<String,String>();
	protected ArrayList<String> value = new ArrayList<String>();
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove notification and action bars
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set view from xml
		setContentView(R.layout.start_interview);

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
		
		//Start Interview Button
		ImageButton startInterviewButton = (ImageButton) findViewById(R.id.start_interview_button);
		//Set date to current date
		Calendar rightNow = Calendar.getInstance();
		TextView date = (TextView) findViewById(R.id.date);
		int month = rightNow.get(Calendar.MONTH);
		//Months are indexed from 0-11
		month++;
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		int year = rightNow.get(Calendar.YEAR);
		String today = String.valueOf(month)+"-"+String.valueOf(day)+"-"+String.valueOf(year);
		date.setText(today);
		//when the button is pressed, save all inputed data into variables and place them into hashmap for later retrieval
		startInterviewButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				final EditText interviewNameInput = (EditText) findViewById(R.id.interviewName);
				interviewName = interviewNameInput.getText().toString();
				final EditText interviewIdInput = (EditText) findViewById(R.id.interviewId);
				interviewId = interviewIdInput.getText().toString();
				final EditText interviewAgeInput = (EditText) findViewById(R.id.interviewAge);
				interviewAge = interviewAgeInput.getText().toString();
				final EditText interviewDateInput = (EditText) findViewById(R.id.date);
				interviewDate = interviewDateInput.getText().toString();
				if(interviewName.length()==0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please enter Interviewee Name");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							interviewNameInput.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				else if(interviewId.length()==0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please enter Interviewer ID");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							interviewIdInput.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				else if(interviewAge.length()==0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please enter Interviewee Age");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							interviewAgeInput.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				else if(interviewDate.length()==0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please enter Interview Date");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							interviewDateInput.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				else{
				
				data.put("Interview Name", interviewName);
				data.put("Interview Id", interviewId);
				data.put("Interview Date", interviewDate);
				data.put("Interview Age", interviewAge);
				data.put("FileName", "tbi"+interviewId+interviewDate);
				

				//start new intent and bundle, add the hashmap with the input data to the bundle along with the intialization of the questionNum
				Intent i = new Intent(getApplicationContext(), com.tbi_id.Step1Activity.class);
				Bundle b = new Bundle();
				b.putSerializable("patientData",data);
				b.putSerializable("questionNum", 1);
				b.putSerializable("causeCount", 0);
				b.putSerializable("step2Count", 1);
				b.putSerializable("step3Count", 1);
				i.putExtras(b);
				startActivity(i);
				}
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

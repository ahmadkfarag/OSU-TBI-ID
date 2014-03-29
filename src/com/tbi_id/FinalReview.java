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

public class FinalReview extends Activity {
	final Context context = this;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_final_review);
		
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
		
		//Get all TextView's from the XML page
		final TextView worst_value = (TextView) findViewById(R.id.value_worst);
		final TextView worst_flag = (TextView) findViewById(R.id.flag_worst);
		final TextView first_value = (TextView) findViewById(R.id.value_first);
		final TextView first_flag = (TextView) findViewById(R.id.flag_first);
		final TextView multiple_value = (TextView) findViewById(R.id.value_multiple);
		final TextView multiple_flag = (TextView) findViewById(R.id.flag_multiple);
		final TextView recent_value = (TextView) findViewById(R.id.value_recent);
		final TextView recent_flag = (TextView) findViewById(R.id.flag_recent);
		
		//Get all data from bundle
		String WorstStep2 = (String) b.getSerializable("WorstStep2");
		String WorstStep3;
		if(b.containsKey("WorstStep3"))
		{
			WorstStep3 = (String) b.getSerializable("WorstStep3");
		}
		else
		{
			WorstStep3 = "None";
		}
		int FirstStep2, FirstStep3;
		String temp = (String) b.getSerializable("YoungestStep2");
		FirstStep2 = Integer.parseInt(temp);
		if(b.containsKey("YoungestStep3"))
		{
			temp = (String) b.getSerializable("YoungestStep3");
			FirstStep3 = Integer.parseInt(temp);
		}
		else
		{
			FirstStep3 = 1000;
		}
		
		Boolean MultipleStep2 = (Boolean) b.getSerializable("MultipleStep2");
		Boolean MultipleStep3;
		if(b.containsKey("MultipleStep3"))
		{
			MultipleStep3 = (Boolean) b.getSerializable("MultipleStep3");
		}
		else
		{
			MultipleStep3 = false;
		}
		int RecentStep2, RecentStep3;
		temp = (String) b.getSerializable("RecentStep2");
		RecentStep2 = Integer.parseInt(temp);
		if(b.containsKey("RecentStep3"))
		{
			temp = (String) b.getSerializable("RecentStep3");
			RecentStep3 = Integer.parseInt(temp);
		}
		else
		{
			RecentStep3 = 1000;
		}
		
		
		//Compare Worsts from step 2 and 3
		if(WorstStep2.equals("None"))
		{
			worst_value.setText("None");
		}
		if(WorstStep2.equals("Mild") || WorstStep3.equals("Mild"))
		{
			worst_value.setText("Mild");
		}
		if(WorstStep2.equals("Moderate") || WorstStep3.equals("Moderate"))
		{
			worst_value.setText("Moderate");
			worst_flag.setText("+");
		}
		if(WorstStep2.equals("Severe") || WorstStep3.equals("Severe"))
		{
			worst_value.setText("Severe");
			worst_flag.setText("+");
		}
		
		//Compare Firsts from step 2 and 3
		String holder;
		if(FirstStep2 < FirstStep3)
		{
			holder = String.valueOf(FirstStep2);
			first_value.setText(holder);
			if(FirstStep2 < 15)
			{
				first_flag.setText("+");
			}
		}
		else
		{
			holder = String.valueOf(FirstStep3);
			first_value.setText(holder);
			if(FirstStep3 < 15)
			{
				first_flag.setText("+");
			}
		}
		
		//Compare Multiples from step 2 and 3
		if(MultipleStep2 || MultipleStep3)
		{
			multiple_value.setText("Yes");
			multiple_flag.setText("+");
		}
		else
		{
			multiple_value.setText("No");
		}
		
		//Compare Recents from step2 and 3
		if(RecentStep2 < RecentStep3)
		{
			holder = String.valueOf(RecentStep2);
			recent_value.setText(holder);
			if(RecentStep2 <= 1 && (WorstStep2.equals("Mild")||WorstStep2.equals("Moderate")||WorstStep2.equals("Severe")))
			{
				recent_flag.setText("+");
			}
		}
		else
		{
			holder = String.valueOf(RecentStep3);
			recent_value.setText(holder);
			if(RecentStep3 <= 1)
			{
				recent_flag.setText("+");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_review, menu);
		return true;
	}

}

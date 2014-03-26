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
import android.widget.RadioButton;
import android.widget.TextView;

public class Step3Effect extends Activity {
	final Context context = this;
	protected static int step3Count;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml
		setContentView(R.layout.activity_step3_effect);
		
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
		
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");		
		step3Count = (Integer) b.get("step3Count");		
		
		TextView causeView = (TextView) findViewById(R.id.step_3_cause);
		final RadioButton button1 = (RadioButton) findViewById(R.id.noLOC);
		final RadioButton button2 = (RadioButton) findViewById(R.id.lessthan30);
		final RadioButton button3 = (RadioButton) findViewById(R.id.btw30and24);
		final RadioButton button4 = (RadioButton) findViewById(R.id.greaterthan24hrs);

		String causeN = "";
		getCause(context, causeView, step3Count, b);
		
		//if Add another cause is pressed
		ImageButton addentryButton = (ImageButton) findViewById(R.id.addanotherentry);
		addentryButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step3AddCause.class);
				//Check to see  which radio button is pushed
				if(button1.isChecked())
				{
					//no LOC
					data.put("step3cause"+step3Count+"Length", "noLOC");
					step3Count++;
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button2.isChecked())
				{
					//Less than 30 mins
					data.put("step3cause"+step3Count+"Length", "<30");
					step3Count++;
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button3.isChecked())
				{
					//Between 30 mins and 24 hrs
					data.put("step3cause"+step3Count+"Length", "30-24");
					step3Count++;
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button4.isChecked())
				{
					//Greater than 24 hrs
					data.put("step3cause"+step3Count+"Length", ">24");
					step3Count++;
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);					
				}
				else
				{
					//At least one button is pushed
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please choose a time span");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}				
			}
		});
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.done);
		//if done button is pressed
		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step3Review.class);
				//Check to see  which radio button is pushed
				if(button1.isChecked())
				{
					//no LOC
					data.put("step3cause"+step3Count+"Length", "noLOC");
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button2.isChecked())
				{
					//Less than 30 mins
					data.put("step3cause"+step3Count+"Length", "<30");
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button3.isChecked())
				{
					//Between 30 mins and 24 hrs
					data.put("step3cause"+step3Count+"Length", "30-24");
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);
				}
				else if(button4.isChecked())
				{
					//Greater than 24 hrs
					data.put("step3cause"+step3Count+"Length", ">24");
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);					
				}
				else
				{
					//At least one button is pushed
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please choose a time span");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				
			}
		});
		
	}
	
	//sets the header of the page to the cause that is being questioned
	private void getCause(Context context, TextView cause, int count, Bundle b) {
		HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		String causeN = data.get("step3cause"+count);
		cause.setText(causeN);
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_effect, menu);
		return true;
	}

}

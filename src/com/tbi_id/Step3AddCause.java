package com.tbi_id;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class Step3AddCause extends Activity {	
	final Context context = this;	
	protected static int causeCount;
	protected static int step3Count;
	String step3agebegan;
	String step3ageended;
	String step3cause;
	
	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml			
		setContentView(R.layout.activity_step3_add_cause);
		
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
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {

				LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);  

				if (click)
				{
					popupView = layoutInflater.inflate(R.layout.step3helpactivity, null);  
					popupWindow = new PopupWindow(
							popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					popupWindow.showAsDropDown(helpButton, 50, -30);
//					popupWindow.update(helpButton, 60, 40, -1, 1400);
					popupWindow.update(helpButton, 60, 40, -1, -1);							
					popupWindow.setFocusable(true);
					click = false;
				}
				else {
					click = true;
					popupWindow.dismiss();
				}

				ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.Quit_help_button);
				btnDismiss.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});

				View stepOnelayout = findViewById(R.id.main);

				stepOnelayout.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});

			}
		});		
		
		//Step 3 edit text fields
		final EditText addCause = (EditText) findViewById(R.id.step_3_cause);
		final EditText ageBegan = (EditText) findViewById(R.id.step_3_age_began_edit);
		final EditText ageEnded = (EditText) findViewById(R.id.step_3_age_ended_edit);
		
		//get Intent and bundle
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		//step3Count init to 1 in StartActivity
		step3Count = (Integer) b.get("step3Count");	
		
		//step 3 next button
		ImageButton step3next = (ImageButton) findViewById(R.id.step_3_cause_next);
		step3next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step3Effect.class);
				//put cause into data
				step3cause = addCause.getText().toString();				
				data.put("step3cause"+step3Count, step3cause);
				//put ages into data
				step3agebegan = ageBegan.getText().toString();
				data.put("step3agebegan_cause"+step3Count, step3agebegan);
				step3ageended = ageEnded.getText().toString();
				data.put("step3ageended_cause"+step3Count, step3ageended);
				b.putSerializable("patientData", data);
				b.putSerializable("step3Count", step3Count);
				i.putExtras(b);
				startActivity(i);				

			}
		});
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_add_cause, menu);
		return true;
	}

}

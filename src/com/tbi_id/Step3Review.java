package com.tbi_id;

import java.util.HashMap;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Step3Review extends Activity {
	final Context context = this;	
	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;
	private View mainlayout;
	private View footer;
	private View header;	
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_step3_review);
		
		footer = findViewById(R.id.footer);
		header = findViewById(R.id.header);
		mainlayout = findViewById(R.id.main);
		
		final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		//Help Button
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
			public void onClick(View v) {

				popupView = layoutInflater.inflate(R.layout.step3_help_popup_window, null);

				//check if the pop up settings window
				//is already being displayed
				IsClicked(helpButton);

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

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		//Settings button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		//open up settings activity if the settings button is clicked
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				popupView = layoutInflater.inflate(R.layout.settings_popup_window, null);

				//check if the pop up settings window
				//is already being displayed
				IsClicked(helpButton);

				//get checkbox
				final CheckBox checkBoxHipaa = (CheckBox) popupView.findViewById(R.id.hippaCompliance); 

				//a text block that tells user to enter their email address
				final TextView emailNotif = (TextView) popupView.findViewById(R.id.enterEmailNotif);

				//the input field for entering the email address
				final EditText enterEmailHipaa = (EditText) popupView.findViewById(R.id.emailEnterHipaa);

				UploadSavedSettings (enterEmailHipaa, emailNotif, checkBoxHipaa);

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
				ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.save_settings);
				saveButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						SharedPreferences.Editor editor = sharedPrefs.edit();
						boolean checked = checkBoxHipaa.isChecked();
						String email = enterEmailHipaa.getText().toString();
						editor.putString("emailHipaa", email);
						editor.putBoolean("checkboxHipaa", checked);
						editor.apply();

						WasEmailEnter (enterEmailHipaa);
					}
				});

				//get the X quit setting button
				ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.Quit_help_button);
				btnDismiss.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}
				});
			}
		});
		
		//About Button
		ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
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
			@Override
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
		
		//get passed data from bundle which has patient info, questionNum, and cause in it and set them to variables for use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();			
		final Integer step3count = (Integer) b.get("step3Count");
		
		HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		String interview_age = data.get("Interview Age");		
		
		int youngest = 1000;
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
			b.putSerializable("MultipleStep3", true);
			//for cause 1 to step3count, append "cause" to "i"
			for (int i=1; i<=step3count; i++) {	
				String temp = data.get("step3agebegan_cause"+i);	
				int test = Integer.parseInt(temp);
				//Get Most Recent Injury
				if(test > recent)
				{
					recent = test;
				}
				//Get youngest began age
				if(test < youngest)
				{
					youngest = test;
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
		else
		{
			b.putSerializable("MultipleStep3", false);
		}
		
		//set text for total injury count
		String injurycounttext = String.valueOf(step3count);
		injurycount.setText(injurycounttext);
		b.putSerializable("countoftotal", injurycounttext);
		//set text for non loc events
		String nonLoccounttext = String.valueOf(noLOCcountint);
		nonloccount.setText(nonLoccounttext);
		b.putSerializable("nonloccount", nonLoccounttext);
		//set text for total loc events
		String loccounttext = String.valueOf(loccountint);
		loccount.setText(loccounttext);
		b.putSerializable("loccount", loccounttext);
		//set text for loc < 30 mins
		String lt30counttext = String.valueOf(lt30countint);
		loclt30.setText(lt30counttext);
		b.putSerializable("lt30count", lt30counttext);
		//set text for loc between 30 mins and 24 hrs
		String btw30and24counttext = String.valueOf(btw30and24int);
		locbtw30and24.setText(btw30and24counttext);
		b.putSerializable("btw3024count", btw30and24counttext);
		//set text for loc > 24 hrs
		String gt24counttext = String.valueOf(gt24countint);
		locgt24.setText(gt24counttext);
		b.putSerializable("gt24count", gt24counttext);
		//Set the Worst effect
		if(mild)
		{
			worsteffect.setText("Mild with LOC");
			b.putSerializable("WorstStep3", "Mild");
		}
		if (moderate)
		{
			worsteffect.setText("Moderate");
			b.putSerializable("WorstStep3", "Moderate");
		}
		if (severe)
		{
			worsteffect.setText("Severe");
			b.putSerializable("WorstStep3", "Severe");
		}
		else if (!mild && !moderate && !severe)
		{
			worsteffect.setText("Mild without LOC");
			b.putSerializable("WorstStep3", "Mild");
		}
		//Set the Worst effect's Age
		ageatworsteffect.setText(worsteffectagestring + " years old");
		b.putSerializable("ageatworststep3", worsteffectagestring);
		//set text for duration of worst effect
		String durationtext = String.valueOf(worsteffectduration);
		duration.setText(durationtext + " years");
		b.putSerializable("durationstep3", durationtext);
		//calculate time since most recent and set text
		int currentAge = Integer.parseInt(interview_age);
		int difference = currentAge - recent;
		String holder = String.valueOf(difference)+" years";
		timesincemostrecent.setText(holder);
		holder = String.valueOf(difference);
		b.putSerializable("RecentStep3", holder);
		//Record youngest start age for final review
		holder = String.valueOf(youngest);
		b.putSerializable("YoungestStep3", holder);
		
		//Get Next button
		ImageButton next = (ImageButton) findViewById(R.id.step3next);
		next.setOnClickListener(new View.OnClickListener() {
			//NOW THE CANCEL BUTTON
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.FinalReview.class);
				//add bundle to intent then start activity
				i.putExtras(b);
				startActivity(i);
				
				
			}
			
		});
	}

	/*
	 * 
	 */
	private void IsClicked (View helpButton){
		
		if (click)
		{
			//calculate the space between the footer and header in the screen
			int heightSpace = mainlayout.getHeight() - (footer.getHeight() + header.getHeight());
			
			//get the screen width
			int widthSpace =  footer.getWidth(); 
			
			//x offset from the view helpButton left edge
			int xoff = header.getHeight()/4;
			//y offset from the view helpButton left edge
			int yoff =  header.getHeight()/3;
			
			//instantiate popupWindow
			popupWindow = new PopupWindow(
					popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			popupWindow.showAsDropDown(helpButton, 50, -30);
			popupWindow.update(helpButton, xoff, yoff, widthSpace - 2*xoff, heightSpace - 2*yoff);
			popupWindow.setFocusable(true);
			
			click = false;
		}
		else {
			click = true;
			popupWindow.dismiss();
		}
	}

	
	
	/*
	 * Get and upload the settings saved as default.
	 */
	private void UploadSavedSettings (EditText enterEmailHipaa, TextView emailNotif, CheckBox checkBoxHipaa){
		
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
	}
	
	/*
	 * 
	 */
	private void WasEmailEnter (final EditText emailText){
		
		if((emailText.getText().length()==0) && (emailText.isShown()))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Error");
			builder.setMessage("Please enter a valid email");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					emailText.requestFocus();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		else{
			click = true;
			popupWindow.dismiss();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_review, menu);
		return true;
	}

}

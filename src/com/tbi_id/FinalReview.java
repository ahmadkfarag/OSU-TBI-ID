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

public class FinalReview extends Activity {
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
		setContentView(R.layout.activity_final_review);
		
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

				popupView = layoutInflater.inflate(R.layout.final_review_help_popup_window, null);

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
		
		//Get all TextView's from the XML page
		final TextView worst_value = (TextView) findViewById(R.id.value_worst);
		final TextView worst_flag = (TextView) findViewById(R.id.flag_worst);
		final TextView first_value = (TextView) findViewById(R.id.value_first);
		final TextView first_flag = (TextView) findViewById(R.id.flag_first);
		final TextView multiple_value = (TextView) findViewById(R.id.value_multiple);
		final TextView multiple_flag = (TextView) findViewById(R.id.flag_multiple);
		final TextView recent_value = (TextView) findViewById(R.id.value_recent);
		final TextView recent_flag = (TextView) findViewById(R.id.flag_recent);
		String putInBundle = "";
		
		//Get all data from bundle
		String WorstStep2;
		if(b.containsKey("WorstStep2"))
		{
			WorstStep2 = (String) b.getSerializable("WorstStep2");
		}
		else
		{
			WorstStep2 = "None";
		}
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
		String temp;
		if(b.containsKey("YoungestStep2"))
		{
			temp = (String) b.getSerializable("YoungestStep2");
			FirstStep2 = Integer.parseInt(temp);
		}
		else
		{
			FirstStep2 = 1000;
		}
		if(b.containsKey("YoungestStep3"))
		{
			temp = (String) b.getSerializable("YoungestStep3");
			FirstStep3 = Integer.parseInt(temp);
		}
		else
		{
			FirstStep3 = 1000;
		}
		
		Boolean MultipleStep2;
		if(b.containsKey("MultipleStep2"))
		{
			MultipleStep2 = (Boolean) b.getSerializable("MultipleStep2");
		}
		else
		{
			MultipleStep2 = false;
		}
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
		if(b.containsKey("RecentStep2"))
		{
			temp = (String) b.getSerializable("RecentStep2");
			RecentStep2 = Integer.parseInt(temp);
		}
		else
		{
			RecentStep2 = 1000;
		}
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
			b.putSerializable("CSVWorst", "None");
			b.putSerializable("CSVWorstFlag", "-");
		}
		if(WorstStep2.equals("Mild") || WorstStep3.equals("Mild"))
		{
			worst_value.setText("Mild");
			b.putSerializable("CSVWorst", "Mild");
			b.putSerializable("CSVWorstFlag", "-");
		}
		if(WorstStep2.equals("Moderate") || WorstStep3.equals("Moderate"))
		{
			worst_value.setText("Moderate");
			worst_flag.setText("+");
			b.putSerializable("CSVWorst", "Moderate");
			b.putSerializable("CSVWorstFlag", "+");
		}
		if(WorstStep2.equals("Severe") || WorstStep3.equals("Severe"))
		{
			worst_value.setText("Severe");
			worst_flag.setText("+");
			b.putSerializable("CSVWorst", "Severe");
			b.putSerializable("CSVWorstFlag", "+");
		}
		
		//Compare Firsts from step 2 and 3
		String holder;
		if(FirstStep2==1000 && FirstStep3==1000)
		{
			first_value.setText("None");
			b.putSerializable("CSVFirst", "None");
			b.putSerializable("CSVFirstFlag", "-");
		}
		else if(FirstStep2 < FirstStep3)
		{
			holder = String.valueOf(FirstStep2);
			first_value.setText(holder + " years old");
			b.putSerializable("CSVFirst", holder + " years old");
			if(FirstStep2 < 15)
			{
				first_flag.setText("+");
				b.putSerializable("CSVFirstFlag", "+");
			}
			else b.putSerializable("CSVFirstFlag", "-");
		}
		else
		{
			holder = String.valueOf(FirstStep3);
			first_value.setText(holder + " years old");
			b.putSerializable("CSVFirst", holder + " years old");
			if(FirstStep3 < 15)
			{
				first_flag.setText("+");
				b.putSerializable("CSVFirstFlag", "+");
			}
			else b.putSerializable("CSVFirstFlag", "-");
		}
		
		//Compare Multiples from step 2 and 3
		if(MultipleStep2 || MultipleStep3)
		{
			multiple_value.setText("Yes");
			multiple_flag.setText("+");
			b.putSerializable("CSVMultiple", "Yes");
			b.putSerializable("CSVMultipleFlag", "+");
		}
		else
		{
			multiple_value.setText("No");
			b.putSerializable("CSVMultiple", "No");
			b.putSerializable("CSVMultipleFlag", "-");
		}
		
		//Compare Recents from step2 and 3
		if(RecentStep2==1000 && RecentStep3==1000)
		{
			recent_value.setText("None");
			b.putSerializable("CSVRecent", "None");
			b.putSerializable("CSVRecentFlag", "-");
		}
		else if(RecentStep2 < RecentStep3)
		{
			holder = String.valueOf(RecentStep2);
			recent_value.setText(holder + " years since");
			b.putSerializable("CSVRecent", holder + " years since");
			if(RecentStep2 <= 1 && (WorstStep2.equals("Mild")||WorstStep2.equals("Moderate")||WorstStep2.equals("Severe")))
			{
				recent_flag.setText("+");
				b.putSerializable("CSVRecentFlag", "+");
			}
			else b.putSerializable("CSVRecentFlag", "-");
		}
		else
		{
			holder = String.valueOf(RecentStep3);
			recent_value.setText(holder + " years since");
			b.putSerializable("CSVRecent", holder + " years since");
			if(RecentStep3 <= 1)
			{
				recent_flag.setText("+");
				b.putSerializable("CSVRecentFlag", "+");
			}
			else b.putSerializable("CSVRecentFlag", "-");
		}
		
		
		ImageButton finishButton = (ImageButton)findViewById(R.id.finish);
		finishButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// see if they are hipaa compliant or not and send that info to the send/save activity
				Boolean checked = sharedPrefs.getBoolean("checkboxHipaa", false);
				
				Intent i = new Intent(getApplicationContext(), com.tbi_id.SaveActivity.class);
				b.putString("checked", checked.toString());
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

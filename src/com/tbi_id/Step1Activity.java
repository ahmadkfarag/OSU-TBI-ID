package com.tbi_id;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Step1Activity extends Activity {

	final Context context = this;
	protected static int questionNum;
	protected static int count;
	
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
		//set view from xml
		setContentView(R.layout.activity_step1);
	
		footer = findViewById(R.id.footer);
		header = findViewById(R.id.header);
		mainlayout = findViewById(R.id.main);
		
		//final View main_layout = mainlayout;
		
		final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		//Help Button
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
			public void onClick(View v) {
				
				popupView = layoutInflater.inflate(R.layout.step1_help_popup_window, null);
				
				//check if the pop up help window
				//is already being displayed
				IsClicked(helpButton);

				ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.Quit_help_button);
				btnDismiss.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}
				});

				View main_layout = findViewById(R.id.main);
				main_layout.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});
			}
		});	
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//Settings Button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
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

				View main_layout = findViewById(R.id.main);
				main_layout.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});
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


		//get passed data from bundle which has patient info and questionNum in it and set them to variables for use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		questionNum = b.getInt("questionNum");
		count = b.getInt("causeCount");
		//get text box that will have the question text in it
		final TextView question = (TextView) findViewById(R.id.step_1_question);
		
		//if questionNum > 5 go to step 2
		
		if (questionNum > 5)
		{
			Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Review.class);
			i.putExtras(b);
			startActivity(i);
		}
		
		
		//using the questionNum select the correct question to ask
		getQuestion(context, question, questionNum, b);
		@SuppressWarnings("unchecked")
		//get patient data from bundle
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		//get yes button
		ImageButton yesButton = (ImageButton) findViewById(R.id.step_1_question_yes);
		//if yes button is clicked..
		yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// check if this question was answered before since you can come back to this page using the back button
				boolean alreadyAnswered = data.containsKey("question"
						+ questionNum);
				//if it was not answered increase the question num and add the answer to the hashmap for later use then start activity to get the next question
				if (alreadyAnswered == false) {
					data.put("question" + questionNum, "yes");
					//questionNum++;
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Cause.class);
					//Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					b.putSerializable("causeCount", count);
					i.putExtras(b);
					startActivity(i);
				}
				//if this question has been answered before, remove the previous answer and replace it when the new one, then increment questionNum and add answer to hashmap for later use and start activity to get the next question
				else {
					data.remove("question" + questionNum);
					data.put("question" + questionNum, "yes");
					//questionNum++;
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Cause.class);
					//Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					b.putSerializable("causeCount", count);
					i.putExtras(b);
					startActivity(i);
				}
			}
		});

		
		//get the no button
		ImageButton noButton = (ImageButton) findViewById(R.id.step_1_question_no);
		noButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// check if this question was answered before since you can come back to this page using the back button
				boolean alreadyAnswered = data.containsKey("question"
						+ questionNum);
				//if it was not answered increase the question num and add the answer to the hashmap for later use then start activity to get the next question
				if (alreadyAnswered == false) {
					data.put("question" + questionNum, "no");
					questionNum++;
					//Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
					//Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					b.putSerializable("causeCount", count);
					//i.putExtras(b);
					//startActivity(i);
					getQuestion(context, question, questionNum, b);
				}
				//if this question has been answered before, remove the previous answer and replace it when the new one, then increment questionNum and add answer to hashmap for later use and start activity to get the next question
				else {
					data.remove("question" + questionNum);
					data.put("question" + questionNum, "no");
					questionNum++;
					//Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
					//Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					b.putSerializable("causeCount", count);
					//i.putExtras(b);
					//startActivity(i);
					getQuestion(context, question, questionNum, b);
				}
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
	
	
	// set question text based on what questionNum is equal to
	private void getQuestion(Context context, TextView question, int questionNum, Bundle b) {

		
		if( questionNum == 1)
		{
			question.setText(context.getString(R.string.step_1_question1));

		}
		else if( questionNum == 2)
		{
			question.setText(context.getString(R.string.step_1_question2));

		}
		else if( questionNum == 3)
		{
			question.setText(context.getString(R.string.step_1_question3));

		}
		else if( questionNum == 4)
		{
			question.setText(context.getString(R.string.step_1_question4));

		}
		else if( questionNum == 5)
		{
			question.setText(context.getString(R.string.step_1_question5));

		}
		else if( questionNum > 5)
		{
			Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Review.class);
			//Intent i = new Intent(getApplicationContext(),com.tbi_id.Step2Activity.class);
			i.putExtras(b);
			startActivity(i);
		}	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1, menu);
		return true;
	}

}

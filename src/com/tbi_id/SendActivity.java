package com.tbi_id;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

import com.tbi_id.R.id;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

public class SendActivity extends Activity {
	final Context context = this;
	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;
	private View mainlayout;
	private View footer;
	private View header;
	private SharedPreferences sharedPrefs;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml		
		setContentView(R.layout.activity_send);
		
		footer = findViewById(R.id.footer);
		header = findViewById(R.id.header);
		mainlayout = findViewById(R.id.main);
		
		
		
		final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);		
		
		
		Intent i = getIntent();
		final Bundle b = i.getExtras();
		final String path = b.getString("path");
		final HashMap<String, String> data = (HashMap<String, String>) b
				.getSerializable("patientData");
		String checked = b.getString("checked");
		ImageButton sendYes = (ImageButton) findViewById(id.sendYes);
		ImageButton sendNo = (ImageButton) findViewById(id.sendNo);
		TextView sendShow = (TextView) findViewById(id.sendTitle);
		final String interview_name = data.get("Interview Name");
		final String interview_date = data.get("Interview Date");
		final boolean didSave = b.getBoolean("didSave");
		final String timestamp = b.getString("timestamp");
		final File csv;
		//get timestamp for filename
//		java.util.Date date = new java.util.Date();
//		final String interview_time = new Timestamp(date.getTime());
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			
			if (didSave == true)
			{
				
				final String newPath = b.getString("newPath");
				  csv = new File(newPath);
			}
			
			else
			{
				  csv = new File(path, timestamp + "TBI_ID" + ".csv");
			}
		
		
		if(checked.equals("false")) // not compliant
		{
			sendYes.setVisibility(View.GONE);
			sendNo.setVisibility(View.GONE);
			if (didSave == true)
			{
				sendShow.setText("The report file has been saved to the device but cannot be sent at this time. \n\nPress Home to start another interview or to exit.");
			}
			
			else if (didSave == false)
			{
				sendShow.setText("The report file has not been saved and cannot be sent at this time. \n\nPress Home to start another interview or to exit.");
				csv.delete();

			}
			
		}
		
		else { // compliant
			sendYes.setVisibility(View.VISIBLE);
			sendNo.setVisibility(View.VISIBLE);
			sendShow.setText("Do you want to e-mail the data?");
		}
		
		
		//Help Button
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {
				popupView = layoutInflater.inflate(R.layout.send_help_popup_window, null);

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

		//initialize the sharedPrefs
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
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview?");
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
				builder.setMessage("Are you sure you want to leave the interview?");
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
		
	
		
		//Set action for Yes button
		sendYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				File csv = new File(path, interview_name + interview_date + timestamp + "TBI_ID"
						+ ".csv");
				Intent finish = new Intent(getApplicationContext(),com.tbi_id.FinishActivity.class);
				startActivity(finish);
				String email = sharedPrefs.getString("emailHipaa", " ");
				Uri uri = Uri.fromFile(csv);
				Intent intents = new Intent(Intent.ACTION_SEND);
				intents.setType("text/plain");
				intents.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
				intents.putExtra(Intent.EXTRA_SUBJECT, "OSU TBI-ID");
				intents.putExtra(Intent.EXTRA_TEXT, "Attached is the interviewee's file.");
				intents.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(intents, "Send email..."));

			}
		});

		//Set action for No button
		sendNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Return Home?");
				builder.setMessage("Your report has not been sent. Are you sure you want to leave the interview?");
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

	}	
	}/*
	 * 
	 */
	private void IsClicked(View helpButton) {

		if (click) {
			// calculate the space between the footer and header in the screen
			int heightSpace = mainlayout.getHeight()
					- (footer.getHeight() + header.getHeight());

			// get the screen width
			int widthSpace = footer.getWidth();

			// x offset from the view helpButton left edge
			int xoff = (int) header.getHeight() / 4;
			// y offset from the view helpButton left edge
			int yoff = (int) header.getHeight() / 3;

			// instantiate popupWindow
			popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, true);
			popupWindow.showAsDropDown(helpButton, 50, -30);
			popupWindow.update(helpButton, xoff, yoff, widthSpace - 2 * xoff,
					heightSpace - 2 * yoff);
			popupWindow.setFocusable(true);

			click = false;
		} else {
			click = true;
			popupWindow.dismiss();
		}
	}

	/*
	 * Get and upload the settings saved as default.
	 */
	private void UploadSavedSettings(EditText enterEmailHipaa,
			TextView emailNotif, CheckBox checkBoxHipaa) {

		// set the boolean false equal to the value of the checkbox when it was
		// when previously run, if not found, set it to false
		Boolean checked = sharedPrefs.getBoolean("checkboxHipaa", false);
		// if the value was false, then they are not free from hipaa and cannot
		// send the data so email is turned off
		if (checked == false) {
			enterEmailHipaa.setVisibility(View.GONE);
			emailNotif.setVisibility(View.GONE);
			checkBoxHipaa.setChecked(false);
		} else {
			String email = sharedPrefs.getString("emailHipaa",
					"Enter Email Here");
			enterEmailHipaa.setText(email);
			checkBoxHipaa.setChecked(true);
		}
	}

	/*
	 * 
	 */
	private void WasEmailEnter(final EditText emailText) {

		if ((emailText.getText().length() == 0) && (emailText.isShown())) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Error");
			builder.setMessage("Please enter a valid email");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							emailText.requestFocus();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			click = true;
			popupWindow.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
		return true;
	}

}

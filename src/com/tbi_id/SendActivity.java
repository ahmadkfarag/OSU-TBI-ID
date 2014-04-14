package com.tbi_id;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) { 
		final File csv = new File(path, interview_name + interview_date + "TBI_ID"
				+ ".csv");
		
		
		if(checked.equals("false"))
		{
			sendYes.setVisibility(View.GONE);
			sendNo.setVisibility(View.GONE);
			
			sendShow.setText("The report file has been saved to the device in the directory " + "TBI-ID " + "with the filename: " + interview_name + interview_date + "TBI_ID"+ ".csv \n\nPress Home to start another interview or to exit.");
			String interview_id = data.get("Interview Id");
			String interview_age = data.get("Interview Age");
			final Integer count = (Integer) b.get("causeCount");
			final Integer step3count = (Integer) b.get("step3Count");
			String tbicount = (String) b.getSerializable("tbicount");
			String tbiloc = (String) b.getSerializable("tbiloc");
			String YoungestStep2 = (String) b.getSerializable("YoungestStep2");
			String RecentStep2 = (String) b.getSerializable("RecentStep2");
			String modsevtbi = (String) b.getSerializable("modsevtbi");
			String WorstStep2 = (String) b.getSerializable("WorstStep2");
			String step2worstage = (String) b.getSerializable("step2worstage");
			

			FileWriter writer;
			try {
				writer = new FileWriter(csv);

				// Top of CSV with patient info
				writer.append("Name:,Age:,ID:,Date:\n");
				writer.append(interview_name + ',' + interview_age + ','
						+ interview_id + ',' + interview_date + "\n\n");
				//Final Review
				writer.append("Primary Indicators Summary:,Flag,Value\n");
				
				String csvworstflag = (String) b.getSerializable("CSVWorstFlag");
				String csvworst= (String) b.getSerializable("CSVWorst");
				String csvfirstflag= (String) b.getSerializable("CSVFirstFlag");
				String csvfirst= (String) b.getSerializable("CSVFirst");
				String csvmultipleflag= (String) b.getSerializable("CSVMultipleFlag");
				String csvmultiple= (String) b.getSerializable("CSVMultiple");
				String csvrecentflag= (String) b.getSerializable("CSVRecentFlag");
				String csvrecent= (String) b.getSerializable("CSVRecent");
				
				writer.append("Worst,"+ csvworstflag + "," + csvworst + "\n");
				writer.append("First,"+ csvfirstflag + "," + csvfirst + "\n");
				writer.append("Multiple,"+ csvmultipleflag  + "," + csvmultiple + "\n");
				writer.append("Recent,"+ csvrecentflag + "," + csvrecent + "\n\n");
	
				
				// Step 2 Review
				if(count > 0)
				{
					writer.append("Count of TBIs," + tbicount + ",Count of TBIs w/ LOC,"+tbiloc+",Count of moderate/severe TBIs  ,"+modsevtbi+",Worst TBI,"+ WorstStep2 + ",Age at First," + YoungestStep2 + " years old" + ",Age at Worst," + step2worstage+ " years old" + ",Time Since Most Recent  ," + RecentStep2 + " years\n");
				}
				
				
				// Step 3 Review
				if(step3count > 0)
				{
					String total = (String) b.getSerializable("countoftotal");
					String totalnonloc = (String) b.getSerializable("nonloccount");
					String totalloc = (String) b.getSerializable("loccount");
					String lt30 = (String) b.getSerializable("lt30count");
					String btw3024 = (String) b.getSerializable("btw3024count");
					String gt24 = (String) b.getSerializable("gt24count");
					String worst = (String) b.getSerializable("WorstStep3");
					String worstage = (String) b.getSerializable("ageatworststep3");
					String duration = (String) b.getSerializable("durationstep3");
					String recentstep3 = (String) b.getSerializable("RecentStep3");
					writer.append("Count of Repeated Injuries,"+ total + ",Count of non-LOC events  ," + totalnonloc + ",Count of LOC events," + totalloc + ",Count of LOC <30 mins  ,"+ lt30 + ",Count of LOC 30min-24hrs  ," + btw3024 + ",Count of LOC >24hrs  ," + gt24 + ",Worst Effect," + worst + ",Age at Worst  ,"+ worstage + " years old,Duration  ," + duration + " years,Time Since Most Recent  ," + recentstep3 + " years since\n");
				}
				
				// Step 1 and 2 Data
				
				if (count > 0) {
					// for every cause
					writer.append("\nImpact Event Causes:,No LOC,<30min,30min-24hrs,>24hrs,Dazed,Age\n");
					for (int i1 = 1; i1 <= count; i1++) {
						// get each cause
						String causeN = data.get("cause" + i1);
						String causeAge = data.get("cause" + i1 + "Age");
						writer.append(causeN + ',');
						if (data.containsKey("cause" + i1 + "Length")) {
							String length = data.get("cause" + i1 + "Length");
							if (length.equals("<30")) {

								writer.append(",X,,,," + causeAge + '\n');
							} else if (length.equals("30-24")) {

								writer.append(",,X,,," + causeAge + '\n');
							} else if (length.equals(">24")) {
								writer.append(",,,X,," + causeAge + '\n');

							}
						}

						else {
							String dazed = data.get("cause" + i1 + "Dazed");
							if (dazed.equals("Yes")) {
								writer.append("X,,,,Yes," + causeAge + "\n");
							}

							else {
								writer.append("X,,,,No," + causeAge + "\n");
							}
						}
					}
				}

				// Step 3 data

				// Repeated Injuries: Age BeganAge Ended Dazed/No LOC <30min
				// 30min-24hrs >24hrs
				
				if (step3count > 0) {
					writer.append("\nRepeated Injuries:,Age Began  ,Age Ended,Dazed/No LOC  ,<30min,30min-24hrs  ,>24hrs\n");
					for (int i1 = 1; i1 <= step3count; i1++) {
						String cause = data.get("step3cause" + i1);
						String beganage = data.get("step3agebegan_cause" + i1);
						String endage = data.get("step3ageended_cause" + i1);
						if (data.containsKey("step3cause" + i1 + "Length")) {
							String length = data.get("step3cause" + i1 + "Length");
							if (length.equals("<30")) {

								writer.append(cause + "," + beganage + "," + endage
										+ ",,X,,\n");
							} else if (length.equals("30-24")) {

								writer.append(cause + "," + beganage + "," + endage
										+ ",,,X,\n");
							} else if (length.equals(">24")) {
								writer.append(cause + "," + beganage + "," + endage
										+ ",,,,X\n");

							}

							else if (length.equals("noLOC")) {
								writer.append(cause + "," + beganage + "," + endage
										+ ",X,,,\n");
							}

						}

					}
				}

				writer.flush();
				writer.close();
		        MediaScannerConnection.scanFile(this, new String[] { csv.getAbsolutePath() }, null, null);

							
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
		else {
			sendYes.setVisibility(View.VISIBLE);
			sendNo.setVisibility(View.VISIBLE);
			sendShow.setText("The report file has been saved to the device in the directory " + "TBI-ID " + "with the filename: " + interview_name + interview_date + "TBI_ID"+ ".csv\n\n Do you want to e-mail the data?");
			String interview_id = data.get("Interview Id");
			String interview_age = data.get("Interview Age");
			final Integer count = (Integer) b.get("causeCount");
			final Integer step3count = (Integer) b.get("step3Count");
			String tbicount = (String) b.getSerializable("tbicount");
			String tbiloc = (String) b.getSerializable("tbiloc");
			String YoungestStep2 = (String) b.getSerializable("YoungestStep2");
			String RecentStep2 = (String) b.getSerializable("RecentStep2");
			String modsevtbi = (String) b.getSerializable("modsevtbi");
			String WorstStep2 = (String) b.getSerializable("WorstStep2");
			String step2worstage = (String) b.getSerializable("step2worstage");
			

			FileWriter writer;
			try {
				writer = new FileWriter(csv);

				// Top of CSV with patient info
				writer.append("Name:,Age:,ID:,Date:\n");
				writer.append(interview_name + ',' + interview_age + ','
						+ interview_id + ',' + interview_date + "\n\n");
				//Final Review
				writer.append("Primary Indicators Summary:,Flag,Value\n");
				
				String csvworstflag = (String) b.getSerializable("CSVWorstFlag");
				String csvworst= (String) b.getSerializable("CSVWorst");
				String csvfirstflag= (String) b.getSerializable("CSVFirstFlag");
				String csvfirst= (String) b.getSerializable("CSVFirst");
				String csvmultipleflag= (String) b.getSerializable("CSVMultipleFlag");
				String csvmultiple= (String) b.getSerializable("CSVMultiple");
				String csvrecentflag= (String) b.getSerializable("CSVRecentFlag");
				String csvrecent= (String) b.getSerializable("CSVRecent");
				
				writer.append("Worst,"+ csvworstflag + "," + csvworst + "\n");
				writer.append("First,"+ csvfirstflag + "," + csvfirst + "\n");
				writer.append("Multiple,"+ csvmultipleflag  + "," + csvmultiple + "\n");
				writer.append("Recent,"+ csvrecentflag + "," + csvrecent + "\n\n");
	
				
				// Step 2 Review
				if(count > 0)
				{
					writer.append("Count of TBIs," + tbicount + ",Count of TBIs w/ LOC,"+tbiloc+",Count of moderate/severe TBIs  ,"+modsevtbi+",Worst TBI,"+ WorstStep2 + ",Age at First," + YoungestStep2 + " years old" + ",Age at Worst," + step2worstage+ " years old" + ",Time Since Most Recent  ," + RecentStep2 + " years\n");
				}
				
				
				// Step 3 Review
				if(step3count > 0)
				{
					String total = (String) b.getSerializable("countoftotal");
					String totalnonloc = (String) b.getSerializable("nonloccount");
					String totalloc = (String) b.getSerializable("loccount");
					String lt30 = (String) b.getSerializable("lt30count");
					String btw3024 = (String) b.getSerializable("btw3024count");
					String gt24 = (String) b.getSerializable("gt24count");
					String worst = (String) b.getSerializable("WorstStep3");
					String worstage = (String) b.getSerializable("ageatworststep3");
					String duration = (String) b.getSerializable("durationstep3");
					String recentstep3 = (String) b.getSerializable("RecentStep3");
					writer.append("Count of Repeated Injuries,"+ total + ",Count of non-LOC events  ," + totalnonloc + ",Count of LOC events," + totalloc + ",Count of LOC <30 mins  ,"+ lt30 + ",Count of LOC 30min-24hrs  ," + btw3024 + ",Count of LOC >24hrs  ," + gt24 + ",Worst Effect," + worst + ",Age at Worst  ,"+ worstage + " years old,Duration  ," + duration + " years,Time Since Most Recent  ," + recentstep3 + " years since\n");
				}
				
				// Step 1 and 2 Data
				
				if (count > 0) {
					// for every cause
					writer.append("\nImpact Event Causes:,No LOC,<30min,30min-24hrs,>24hrs,Dazed,Age\n");
					for (int i1 = 1; i1 <= count; i1++) {
						// get each cause
						String causeN = data.get("cause" + i1);
						String causeAge = data.get("cause" + i1 + "Age");
						writer.append(causeN + ',');
						if (data.containsKey("cause" + i1 + "Length")) {
							String length = data.get("cause" + i1 + "Length");
							if (length.equals("<30")) {

								writer.append(",X,,,," + causeAge + '\n');
							} else if (length.equals("30-24")) {

								writer.append(",,X,,," + causeAge + '\n');
							} else if (length.equals(">24")) {
								writer.append(",,,X,," + causeAge + '\n');

							}
						}

						else {
							String dazed = data.get("cause" + i1 + "Dazed");
							if (dazed.equals("Yes")) {
								writer.append("X,,,,Yes," + causeAge + "\n");
							}

							else {
								writer.append("X,,,,No," + causeAge + "\n");

							}

						}
					}
				}


				// Step 3 data

				// Repeated Injuries: Age BeganAge Ended Dazed/No LOC <30min
				// 30min-24hrs >24hrs
	
				if (step3count > 0) {
					writer.append("\nRepeated Injuries:,Age Began  ,Age Ended,Dazed/No LOC  ,<30min,30min-24hrs  ,>24hrs\n");
					for (int i1 = 1; i1 <= step3count; i1++) {
						String cause = data.get("step3cause" + i1);
						String beganage = data.get("step3agebegan_cause" + i1);
						String endage = data.get("step3ageended_cause" + i1);
						if (data.containsKey("step3cause" + i1 + "Length")) {
							String length = data.get("step3cause" + i1 + "Length");
							if (length.equals("<30")) {

								writer.append(cause + "," + beganage + "," + endage
										+ ",,X,,\n");
							} else if (length.equals("30-24")) {

								writer.append(cause + "," + beganage + "," + endage
										+ ",,,X,\n");
							} else if (length.equals(">24")) {
								writer.append(cause + "," + beganage + "," + endage
										+ ",,,,X\n");

							}

							else if (length.equals("noLOC")) {
								writer.append(cause + "," + beganage + "," + endage
										+ ",X,,,\n");
							}
						}
					}
				}

				writer.flush();
				writer.close();
		        MediaScannerConnection.scanFile(this, new String[] { csv.getAbsolutePath() }, null, null);

				// generate whatever data you want

				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
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
				File csv = new File(path, interview_name + interview_date + "TBI_ID"
						+ ".csv");
				Intent finish = new Intent(getApplicationContext(),com.tbi_id.FinishActivity.class);
				startActivity(finish);
				String email = sharedPrefs.getString("emailHipaa", " ");
				Uri uri = Uri.fromFile(csv);
				Intent intents = new Intent(Intent.ACTION_SEND);
				intents.setType("text/plain");
				intents.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
				intents.putExtra(Intent.EXTRA_SUBJECT, "TBI ID");
				intents.putExtra(Intent.EXTRA_TEXT, "Attached is the patient's TBI-ID File");
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
			int xoff = (int) header.getHeight()/4;
			//y offset from the view helpButton left edge
			int yoff =  (int) header.getHeight()/3;
			
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
		getMenuInflater().inflate(R.menu.send, menu);
		return true;
	}

}

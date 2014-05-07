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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Step3AddCause extends Activity {	
	final Context context = this;	
	protected static int causeCount;
	protected static int step3Count;
	private EditText addCause;
	private EditText ageBegan;
	private EditText ageEnded;
	String cause;	
	String beginage;
	String endage;
	String step3agebegan;
	String step3ageended;
	String step3cause;
	
	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;
	private View mainlayout;
	private View footer;
	private View header;
	private SharedPreferences sharedPrefs;
	
	private TextWatcher mTextWatcher = new TextWatcher() {
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	    }

	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	    }

	    @Override
	    public void afterTextChanged(Editable editable) {
	        // check Fields For Empty Values
	        checkFieldsForEmptyValues();
	    }

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml			
		setContentView(R.layout.activity_step3_add_cause);
		
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
		
		
		//Step 3 edit text fields
		addCause = (EditText) findViewById(R.id.step_3_cause);
		ageBegan = (EditText) findViewById(R.id.step_3_age_began_edit);
		ageEnded = (EditText) findViewById(R.id.step_3_age_ended_edit);
		
		addCause.addTextChangedListener(mTextWatcher);
		checkFieldsForEmptyValues();				
		ageBegan.addTextChangedListener(mTextWatcher);
		ageEnded.addTextChangedListener(mTextWatcher);
		
		//get Intent and bundle
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		//step3Count init to 1 in StartActivity
		step3Count = (Integer) b.get("step3Count");	
		final String patientAge = data.get("Interview Age");
		//Cancel button is default to invisible.
		//Cancel button will only show if step3Count > 1 (if user adds another cause)
		ImageButton step3cancel = (ImageButton) findViewById(R.id.step_3_cause_cancel);
		if(step3Count>1) {
			//set step3cancel button to visible
			step3cancel.setVisibility(0);
			step3cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step3Review.class);
					step3Count--;
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);						
					i.putExtras(b);
					startActivity(i);
				
				}
			});
		}
		
		//step 3 next button
		ImageButton step3next = (ImageButton) findViewById(R.id.step_3_cause_next);
		step3next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step3Effect.class);
				//put cause into data
				step3cause = addCause.getText().toString();				
				data.put("step3cause"+step3Count, step3cause);
				//put ages into data
				step3agebegan = ageBegan.getText().toString();
				step3ageended = ageEnded.getText().toString();
				int began = Integer.parseInt(step3agebegan);
				int end = Integer.parseInt(step3ageended);
				int enteredatstart = Integer.parseInt(patientAge);

				
				if(began > end)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Begining age is Greater than Ending Age");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							ageBegan.setText("");
							ageEnded.setText("");
							ageBegan.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
				
				else if (began > enteredatstart || end > enteredatstart )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Error");
					builder.setMessage("Please enter an age that is less than the age entered at the begining of the interview");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							ageBegan.setText("");
							ageEnded.setText("");
							ageBegan.requestFocus();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();
					
				}
				
				else
				{
				
					data.put("step3agebegan_cause"+step3Count, step3agebegan);
					data.put("step3ageended_cause"+step3Count, step3ageended);
					b.putSerializable("patientData", data);
					b.putSerializable("step3Count", step3Count);
					i.putExtras(b);
					startActivity(i);	
				}

			}
		});
			
	}
	
	void checkFieldsForEmptyValues(){
	    ImageButton step3next = (ImageButton) findViewById(R.id.step_3_cause_next);

	    String cause = addCause.getText().toString();
	    String beginage = ageBegan.getText().toString();
	    String endage = ageEnded.getText().toString();

	    if(cause.length()>0 && beginage.length()>0 && endage.length()>0){
	        step3next.setEnabled(true);
	        step3next.setImageResource(R.drawable.next_50);

	    } else {
	        step3next.setEnabled(false);
	        step3next.setImageResource(R.drawable.next_50);
	    }
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
		getMenuInflater().inflate(R.menu.step3_add_cause, menu);
		return true;
	}

}

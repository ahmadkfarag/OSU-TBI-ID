package com.tbi_id;

import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class Step1Cause extends Activity {
	protected static int questionNum;
	protected static int count;
	final Context context = this;
	private EditText enterCause;
	String cause;

	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;

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
		//remove action bar and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml
		setContentView(R.layout.activity_step1_cause);
		
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
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			public void onClick(View v) {

				LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);  

				if (click)
				{
					popupView = layoutInflater.inflate(R.layout.step1helpactivity, null);  
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
		
		//Settings button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		//open up settings activity if the settings button is clicked
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
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
		
		//get passed data to save the cause with the appopriate question for later use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		questionNum = (Integer) b.get("questionNum");
		count = (Integer) b.get("causeCount");
		@SuppressWarnings("unchecked")
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		//get the input field where the patient will enter the case
		enterCause = (EditText) findViewById(R.id.enterCause);
		enterCause.addTextChangedListener(mTextWatcher);
		checkFieldsForEmptyValues();
		//button for adding an additional entry
		ImageButton addEntry = (ImageButton) findViewById(R.id.addEntry);
		//button for finishing adding causes
		ImageButton done = (ImageButton) findViewById(R.id.done);
		
		// if cancel is clicked, save the causes from the input field with the appropriate title for later use to a bundle then return to the step one question activity
		done.setOnClickListener(new View.OnClickListener() {
			//NOW THE CANCEL BUTTON
			@Override
			public void onClick(View v) {
				//cause = enterCause.getText().toString();
				//count++;
				// the key for the data will be "cause" along side what question it is
				//data.put("cause"+count, cause);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
				//Bundle b = new Bundle();
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				b.putSerializable("causeCount", count);
				//add bundle to intent then start activity
				i.putExtras(b);
				startActivity(i);
				
				
			}
			
		});
		addEntry.setOnClickListener(new View.OnClickListener() {
			//NOW THE ADD BUTTON
			@Override
			public void onClick(View v) {
				cause = enterCause.getText().toString();
				count++;
				questionNum++;
				// the key for the data will be "cause" along side what question it is
				data.put("cause"+count, cause);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Cause2.class);
				//Bundle b = new Bundle();
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				b.putSerializable("causeCount", count);
				enterCause.setText("");
				i.putExtras(b);
				startActivity(i);
				
			}
		});
		
	}
	
	

	void checkFieldsForEmptyValues(){
	    ImageButton addButton = (ImageButton) findViewById(R.id.addEntry);

	    String cause = enterCause.getText().toString();

	    if(cause.length()>0){
	        addButton.setEnabled(true);
	        addButton.setImageResource(R.drawable.add50text);

	    } else {
	        addButton.setEnabled(false);
	        addButton.setImageResource(R.drawable.add50text);
	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_cause, menu);
		return true;
	}

}

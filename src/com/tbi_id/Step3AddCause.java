package com.tbi_id;

import java.util.HashMap;

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
					//calculate the space between the footer and header in the screen
					int heightSpace = mainlayout.getHeight() - (footer.getHeight() + header.getHeight());
					
					//get the screen width
					int widthSpace =  footer.getWidth(); 
					
					int xoff = (int) header.getHeight()/4;
					int yoff =  (int) header.getHeight()/3;
					
					popupView = layoutInflater.inflate(R.layout.step3helpactivity, null);  
					popupWindow = new PopupWindow(
							popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					popupWindow.showAsDropDown(helpButton, 50, -30);
					popupWindow.update(helpButton, xoff, yoff, widthSpace - 2*xoff, heightSpace - 2*(header.getHeight()/3));
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
		
		//Cancel button is default to invisible.
		//Cancel button will only show if step3Count > 1 (if user adds another cause)
		ImageButton step3cancel = (ImageButton) findViewById(R.id.step_3_cause_cancel);
		if(step3Count>1) {
			//set step3cancel button to visible
			step3cancel.setVisibility(0);
			step3cancel.setOnClickListener(new View.OnClickListener() {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_add_cause, menu);
		return true;
	}

}

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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Step2No extends Activity {

	final Context context = this;
	protected static int questionNum;
	protected static int causeCount;
	protected static int step2Count;
	
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
		setContentView(R.layout.activity_step2_no);
		
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
					popupView = layoutInflater.inflate(R.layout.step2helpactivity, null);  
					popupWindow = new PopupWindow(
							popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					popupWindow.showAsDropDown(helpButton, 50, -30);
					popupWindow.update(helpButton, 60, 40, -1, 1400);
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
				
		TextView causeView = (TextView) findViewById(R.id.step_2_cause);
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");	
		step2Count = (Integer) b.get("step2Count");
		String causeN = "";

		getCause(context, causeView, step2Count, b);
	
		//get yes button
		ImageButton yesButton = (ImageButton) findViewById(R.id.step_2_2_yes);
		//if yes button is clicked..
		yesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.put("cause"+step2Count+"Dazed", "Yes");
				step2Count++;
				b.putSerializable("patientData", data);
				b.putSerializable("step2Count", step2Count);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step2Activity.class);
				i.putExtras(b);
				startActivity(i);

			}

		});
		//get no button
		ImageButton noButton = (ImageButton) findViewById(R.id.step_2_2_no);
		//if no button is clicked
		noButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				data.put("cause"+step2Count+"Dazed", "No");
				step2Count++;
				b.putSerializable("patientData", data);
				b.putSerializable("step2Count", step2Count);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step2Activity.class);
				i.putExtras(b);
				startActivity(i);

			}

	});
		
	}
//sets the header of the page to the cause that is being questioned
private void getCause(Context context, TextView cause, int count, Bundle b) {
	HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
	String causeN = data.get("cause" + count);
	cause.setText(causeN);
}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step2, menu);
		return true;
	}

}

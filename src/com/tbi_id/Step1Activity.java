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

public class Step1Activity extends Activity {

	final Context context = this;
	protected static int questionNum;
	protected static int count;
	
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
		setContentView(R.layout.activity_step1);
	
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
					}
				});

				View stepOnelayout = findViewById(R.id.main);

				stepOnelayout.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});
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

package com.tbi_id;

import java.util.HashMap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class Step1Activity extends Activity {

	final Context context = this;
	protected static int questionNum;

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
				Intent i = new Intent(getApplicationContext(), com.tbi_id.SettingsActivity.class);
				startActivity(i);
			} 
	
		});
		
		//get passed data from bundle which has patient info and questionNum in it and set them to variables for use
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		questionNum = b.getInt("questionNum");
		//get text box that will have the question text in it
		TextView question = (TextView) findViewById(R.id.step_1_question);
		//using the questionNum select the correct question to ask
		getQuestion(context, question, questionNum);
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
					questionNum++;
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Cause.class);
					Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					i.putExtras(b);
					startActivity(i);
				}
				//if this question has been answered before, remove the previous answer and replace it when the new one, then increment questionNum and add answer to hashmap for later use and start activity to get the next question
				else {
					data.remove("question" + questionNum);
					data.put("question" + questionNum, "yes");
					questionNum++;
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Cause.class);
					Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
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
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
					Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					i.putExtras(b);
					startActivity(i);
				}
				//if this question has been answered before, remove the previous answer and replace it when the new one, then increment questionNum and add answer to hashmap for later use and start activity to get the next question
				else {
					data.remove("question" + questionNum);
					data.put("question" + questionNum, "no");
					questionNum++;
					Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
					Bundle b = new Bundle();
					b.putSerializable("patientData", data);
					b.putSerializable("questionNum", questionNum);
					i.putExtras(b);
					startActivity(i);
				}

			}

	});
	}

	// set question text based on what questionNum is equal to
	private void getQuestion(Context context, TextView question, int questionNum) {

		
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
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1, menu);
		return true;
	}

}

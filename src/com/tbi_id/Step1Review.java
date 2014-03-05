package com.tbi_id;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Step1Review extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notifcation bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml		
		setContentView(R.layout.activity_step1_review);
		
		//get passed data from bundle which has patient info, questionNum, and cause in it and set them to variables for use
		Intent intent = getIntent();
		Bundle b = intent.getExtras();

		HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		
		//get values in data hashmap
		String interview_name = data.get("Interview Name");
		String interview_id = data.get("Interview Id");
		String interview_date = data.get("Interview Date");
		String interview_age = data.get("Interview Age");
		//String cause = data.get("cause2");
		//Integer count = (Integer) b.get("causeCount");
		//String temp = count.toString();
		
		//get interviewName and set it to output
		TextView interviewName = (TextView) findViewById(R.id.interview_name_val);
		interviewName.setText(interview_name);
		
		//get interviewId and set it to output
		TextView interviewId = (TextView) findViewById(R.id.interview_id_val);
		interviewId.setText(interview_id);
		
		//get interviewDate and set it to output
		TextView interviewDate = (TextView) findViewById(R.id.interview_date_val);
		interviewDate.setText(interview_date);
		
		//get interviewAge and set it to output
		TextView interviewAge = (TextView) findViewById(R.id.interview_age_val);
		interviewAge.setText(interview_age);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_review, menu);
		return true;
	}

}

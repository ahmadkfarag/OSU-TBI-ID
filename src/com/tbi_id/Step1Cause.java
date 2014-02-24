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
import android.widget.Button;
import android.widget.EditText;

public class Step1Cause extends Activity {
	protected static int questionNum;
	final Context context = this;
	String cause;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml
		setContentView(R.layout.activity_step1_cause);
		
		//get passed data to save the cause with the appopriate question for later use
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		questionNum = (Integer) bundle.get("questionNum");
		final HashMap<String, String> data = (HashMap<String, String>) bundle.getSerializable("patientData");
		//get the input field where the patient will enter the case
		final EditText enterCause = (EditText) findViewById(R.id.enterCause);
		//button for adding an additional entry
		Button addEntry = (Button) findViewById(R.id.addEntry);
		//button for finishing adding causes
		Button done = (Button) findViewById(R.id.done);
		
		// if done is clicked, save the causes from the input field with the appropriate title for later use to a bundle then return to the step one question activity
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cause = enterCause.getText().toString();
				// the key for the data will be "cause" along side what question it is
				data.put("cause" + questionNum, cause);
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
				Bundle b = new Bundle();
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				i.putExtras(b);
				startActivity(i);
				
				
			}
		});
		
		
		
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_cause, menu);
		return true;
	}

}

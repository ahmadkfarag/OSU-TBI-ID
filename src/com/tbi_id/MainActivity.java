package com.tbi_id;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String state = Environment.getExternalStorageState();
		final Bundle b = new Bundle();

		if (Environment.MEDIA_MOUNTED.equals(state)) {

			
			/*
			String path;
			if (new File("/storage/sdcard").exists()) {
				path = "/storage/sdcard/TBI-ID";
				File newDirectory = new File(path);
				newDirectory.mkdir();
				MediaScannerConnection.scanFile(this,
						new String[] { newDirectory.getAbsolutePath() }, null,
						null);
				b.putString("path", path);

			} if (new File("/storage/sdcard0").exists()) {
				path = "/storage/sdcard0/TBI-ID";
				File newDirectory = new File(path);
				newDirectory.mkdir();
				MediaScannerConnection.scanFile(this,
						new String[] { newDirectory.getAbsolutePath() }, null,
						null);
				b.putString("path", path);


			} if (new File("/storage/sdcard1").exists()) {
				path = "/storage/sdcard1/TBI-ID";
				File newDirectory = new File(path);
				newDirectory.mkdir();
				MediaScannerConnection.scanFile(this,
						new String[] { newDirectory.getAbsolutePath() }, null,
						null);
				b.putString("path", path);


			}
			
			else{
				path = Environment.getExternalStoragePublicDirectory("TBI-ID").toString();
				File newDirectory = new File(path);
				newDirectory.mkdir();
				MediaScannerConnection.scanFile(this,
						new String[] { newDirectory.getAbsolutePath() }, null,
						null);
				b.putString("path", path);
			}
			*/
			//String ext = System.getenv("EXTERNAL_STORAGE");
			//String path = ext + "/TBI-ID";
			String path = Environment.getExternalStorageDirectory().toString() + "/TBI-ID";
			File newDirectory = new File(path);
			newDirectory.mkdir();
			MediaScannerConnection.scanFile(this,
					new String[] { newDirectory.getAbsolutePath() }, null,
					null);
			b.putString("path", path);

		}

		// remove actionbar and notification bars
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set layout from xml
		setContentView(R.layout.activity_main);

		// get the home button and make it not pressable
		ImageButton homeButton = (ImageButton) findViewById(R.id.home_button_main_screen);
		homeButton.setEnabled(false);

		// Settings button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		// open up settings activity if the settings button is clicked
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						com.tbi_id.SettingsActivity.class);
				startActivity(i);
			}
		});

		// Start Interview button
		ImageButton startButton = (ImageButton) findViewById(R.id.start_interview_button);
		// open up the start interview activity if the start button is clicked
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
			
				Intent i = new Intent(getApplicationContext(),
						com.tbi_id.StartInterview.class);
				i.putExtras(b);
				startActivity(i);
			}
		});

		// Help Button
		ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			// open up the start interview activity if clicked
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						com.tbi_id.HelpActivity.class);
				startActivity(i);
			}
		});

		// About Button
		ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			// open up the start interview activity if clicked
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						com.tbi_id.AboutActivity.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

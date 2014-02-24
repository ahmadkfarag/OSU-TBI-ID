package com.tbi_id;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// set layout from xml
		setContentView(R.layout.settings);

		// get saved data for settings
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		//the input field for entering the email address
		EditText enterEmailHipaa = (EditText) findViewById(R.id.emailEnterHipaa);
		//a text block that tells user to enter their email address
		TextView emailNotif = (TextView) findViewById(R.id.enterEmailNotif);

		//set the boolean false equal to the value of the checkbox when it was when previously run, if not found, set it to false
		Boolean checked = sharedPrefs.getBoolean("checkboxHipaa", false);
		// if the value was false, then they are not free from hipaa and cannot send the data so email is turned off
		if (checked == false) {
			enterEmailHipaa.setVisibility(View.GONE);
			emailNotif.setVisibility(View.GONE);

		}
		
		// the value is true so they are free from hipaa and can send the data
		else

		{
		
			SharedPreferences.Editor editor = sharedPrefs.edit();
			String email = sharedPrefs.getString("hipaaEmail", "ahmad.k.farag@gmail.com");
			enterEmailHipaa.setText(email);
			editor.apply();
			

		}

		CheckBox checkBoxHipaa = (CheckBox) findViewById(R.id.hippaCompliance);
		checkBoxHipaa.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				
			}
		});
		
		
		
	}
}

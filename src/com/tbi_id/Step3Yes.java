package com.tbi_id;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Step3Yes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step3_yes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step3_yes, menu);
		return true;
	}

}

package com.tbi_id;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.tbi_id.R.id;

import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class SendActivity extends Activity {

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		Intent i = getIntent();
		final Bundle b = i.getExtras();
		final SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final HashMap<String, String> data = (HashMap<String, String>) b
				.getSerializable("data");
		ImageButton sendYes = (ImageButton) findViewById(id.sendYes);
		ImageButton sendNo = (ImageButton) findViewById(id.sendNo);

		sendYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				createCSV(data, b);
			}
		});

	}

	protected void createCSV(HashMap<String, String> data, Bundle b) {
		String interview_name = data.get("Interview Name");
		String interview_date = data.get("Interview Date");
		String interview_id = data.get("Interview Id");
		String interview_age = data.get("Interview Age");
		final Integer count = (Integer) b.get("causeCount");
		final Integer step3count = (Integer) b.get("step3Count");
		String tbicount = (String) b.getSerializable("tbicount");
		String tbiloc = (String) b.getSerializable("tbiloc");
		String YoungestStep2 = (String) b.getSerializable("YoungestStep2");
		String RecentStep2 = (String) b.getSerializable("RecentStep2");
		String modsevtbi = (String) b.getSerializable("modsevtbi");
		String WorstStep2 = (String) b.getSerializable("WorstStep2");
		String step2worstage = (String) b.getSerializable("step2worstage");

		File csv = new File(Environment.getExternalStorageDirectory()
				.toString(), interview_name + interview_date + "TBI_ID"
				+ ".csv");
		String csvPath = csv.getAbsolutePath();

		FileWriter writer;
		try {
			writer = new FileWriter(csv);

			// Top of CSV with patient info
			writer.append("Name:,Age:,ID:,Date:\n");
			writer.append(interview_name + ',' + interview_age + ','
					+ interview_id + ',' + interview_date + "\n");

			// Step 1 and 2 Data
			writer.append("Causes:,No LOC,<30min,30min-24hrs,>24hrs,Dazed,Age\n");
			if (count > 0) {
				// for every cause
				for (int i = 1; i <= count; i++) {
					// get each cause
					String causeN = data.get("cause" + i);
					String causeAge = data.get("cause" + i + "Age");
					writer.append(causeN + ',');
					if (data.containsKey("cause" + i + "Length")) {
						String length = data.get("cause" + i + "Length");
						if (length.equals("<30")) {

							writer.append(",X,,,," + causeAge + '\n');
						} else if (length.equals("30-24")) {

							writer.append(",,X,,," + causeAge + '\n');
						} else if (length.equals(">24")) {
							writer.append(",,,X,," + causeAge + '\n');

						}
					}

					else {
						String dazed = data.get("cause" + i + "Dazed");
						if (dazed.equals("Yes")) {
							writer.append("X,,,Yes," + causeAge + "\n");
						}

						else {
							writer.append("X,,,No," + causeAge + "\n");

						}

					}
				}
			}

			// Step 2 Review

			writer.append("Count of TBIs,,Count of TBIs w/ LOC,,Count of moderate/severe TBIs,,Worst TBI,,Age at First,,Age at Worst,,Time Since Most Recent\n");
			writer.append(tbicount + "," + tbiloc + "," + modsevtbi + ","
					+ WorstStep2 + YoungestStep2 + "years old," + step2worstage
					+ "years old," + RecentStep2 + "years" + "\n");

			// Step 3 data

			// Repeated Injuries: Age BeganAge Ended Dazed/No LOC <30min
			// 30min-24hrs >24hrs
			writer.append("Repeated Injuries:,Age Began,Age Ended,Dazed/No LOC,<30min,30min-24hrs,>24hrs\n");

			if (step3count > 0) {
				for (int i = 1; i <= step3count; i++) {
					String cause = data.get("step3cause" + i);
					String beganage = data.get("step3agebegan_cause" + i);
					String endage = data.get("step3ageended_cause" + i);
					if (data.containsKey("step3cause" + i + "Length")) {
						String length = data.get("step3cause" + i + "Length");
						if (length.equals("<30")) {

							writer.append(cause + "," + beganage + "," + endage
									+ ",,X,,\n");
						} else if (length.equals("30-24")) {

							writer.append(cause + "," + beganage + "," + endage
									+ ",,,X,\n");
						} else if (length.equals(">24")) {
							writer.append(cause + "," + beganage + "," + endage
									+ ",,,,X\n");

						}

						else if (length.equals("noLOC")) {
							writer.append(cause + "," + beganage + "," + endage
									+ ",X,,,\n");
						}

					}

				}
			}

			// Step 3 Review

			writer.append("Count of repeated injuries,,Count of non-LOC events,,Count of LOC events,,Count of LOC <30 mins,,Count of LOC 30min-24hrs,,Count of LOC >24hrs,,Worst Effect,,Age at Worst,Duration,Time Since Most Recent\n");
			String total = b.getString("countoftotal");
			String totalnonloc = b.getString("nonloccount");
			String totalloc = b.getString("loccount");
			String lt30 = b.getString("countoftotal");
			String btw3024 = b.getString("countoftotal");
			String gt24 = b.getString("countoftotal");
			String worst = b.getString("WorstStep3");
			String worstage = b.getString("ageatworst");
			String duration = b.getString("duration");
			String recentstep3 = b.getString("RecentStep3");
			writer.append(total + ",," + totalnonloc + ",," + totalloc + ",,"
					+ lt30 + ",," + btw3024 + ",," + gt24 + ",," + worst + ",,"
					+ worstage + ",," + duration + ",," + recentstep3 + "\n");
			
			//final Review
			
			writer.append("Primary Indicators Summary:");
			writer.append(",Flag,Value\n");
			
			
			
			
			
			String filelocation= csv.getAbsolutePath();    
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("vnd.android.cursor.dir/email");
			final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
			
			String to[] = sharedPrefs.getString("emailHipaa", "ahmadfarag")
			sharingIntent.putExtra(Intent.EXTRA_EMAIL, to);
			sharingIntent.putExtra(Intent.EXTRA_STREAM,filelocation);
			sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"subject");
			startActivity(Intent.createChooser(sharingIntent, "Send email"));
			

			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
		return true;
	}

}

/*
 * User Interface Intro Page by SRUTI BHAGAVATULA
 * 
 * This page contains buttons for navigation into survey or NFC history
 * 
 */

package cs340.nfc.smoking.survey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Start extends Activity {

	//Include the singleton class
	SharedVars shared;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		//Instantiate instance of singleton class
		shared = (SharedVars)getApplicationContext();
		
		//Button to view survey (for testing purposes)
		Button survey_btn = (Button) findViewById(R.id.view_survey);
		survey_btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				//Call the activity that starts the survey
				Intent view_survey = new Intent(getApplicationContext(), SurveyPage.class);
				startActivity(view_survey);			
			}
		});
		
		//Button to view NFC history
		Button history_button = (Button) findViewById(R.id.history_btn);
		history_button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent homePage = new Intent(getApplicationContext(), NfcWriterActivity.class);		
				startActivity(homePage);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.menu_settings:
				//Toast.makeText(Start.this, "Settings selected", Toast.LENGTH_LONG).show();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	    //respond to menu item selection
	}
}
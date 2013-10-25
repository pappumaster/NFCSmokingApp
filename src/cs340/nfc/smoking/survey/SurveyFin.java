/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * Activity to indicate end of survey
 * and enable saving of answers to SD card
 * 
 */

package cs340.nfc.smoking.survey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SurveyFin extends Activity {

	Button finished;
	Calendar ci;
	String fileName;

	SharedVars shared;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_fin);

		shared = (SharedVars)getApplicationContext();

		ci = Calendar.getInstance();
		String CiDate = "" + ci.get(Calendar.YEAR) + "-" + 
				(ci.get(Calendar.MONTH) + 1) + "-" +
				ci.get(Calendar.DAY_OF_MONTH);
		String CiTime = " " +
				(ci.get(Calendar.HOUR)+12 )+ ":" +
				ci.get(Calendar.MINUTE);

		fileName = CiDate + ";" + CiTime + ".txt";

		finished = (Button) findViewById(R.id.saveBtn);

		finished.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				//Write the arrayList of answerTexts to a new file on the SD card
				try {

					File myFile = new File("/sdcard/mysdfile"+shared.getCounter()+".txt");
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter = 
							new OutputStreamWriter(fOut);
					for (int i = 0; i < shared.answerList.size(); ++i)
					{
						answerText t = shared.answerList.get(i);
						myOutWriter.append((i+1) + ": " + t.getQuestion() + "\n");
						myOutWriter.append(t.getAnswer() + "\n");
					}
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							("Done writing SD 'mysdfile"+shared.getCounter()+".txt'"),
							Toast.LENGTH_SHORT).show();
					shared.counter++;
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}});
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_survey_fin, menu);
		return true;
	}
}
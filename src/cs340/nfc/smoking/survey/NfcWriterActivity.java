package cs340.nfc.smoking.survey;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import cs340.nfc.smoking.survey.R;

import java.io.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import cs340.nfc.creator.NdefWriter;
import cs340.nfc.creator.NdefWriter.NdefWriterListener;

/* Implemented by DEVINA DHAWAN
 * 
 * Creates a functioning NFC application which creates a database of NFC intents. 
 * These intents are rows in a table with the following data: the date and time
 * Also, this class includes a clearing of the data method. 
 * 
 * This class includes the main NFC writing code. 
 * 
 * MISSING: The only thing this class is currently missing is the calling of the survey after 5 minutes of an intent. 
 */

public class NfcWriterActivity extends NfcDetectorActivity implements NdefWriterListener {
	// Field variables
	EditText txtData;						//Appended text
	protected NdefWriter writer;			//NFC code writer
	private int mRowCount;					//counting of rows
	NotesDbAdapter ndb;						//Database
	SharedVars shared;						//Use of singleton
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writer);
		
		ndb = new NotesDbAdapter(getApplicationContext());
		ndb.open();
		Cursor cursor = ndb.fetchAllNotes();
		while (cursor.moveToNext()){
			mRowCount++;
			
			
			TableLayout date_table = (TableLayout)findViewById(R.id.date_table);		//Creates date table
			TableRow date_row = new TableRow(this);
			TextView date_tv = new TextView(this);
			TableLayout time_table = (TableLayout)findViewById(R.id.time_table);		//Creates time table
			TableRow time_row = new TableRow(this);
			TextView time_tv = new TextView(this);
			String datetime = cursor.getString(cursor.getColumnIndex("title"));
			String[] arr = datetime.split(" ");
			date_tv.setText(arr[0]);
			date_row.addView(date_tv);
			date_table.addView(date_row);
			time_tv.setText(arr[2]);
			time_row.addView(time_tv);
			time_table.addView(time_row);
		}
		Button clearapp = (Button) findViewById(R.id.clear);							//Clearing of all intents
        clearapp.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				ndb.deleteAll();														//Deletes all of the data in the database
			}
        });

		
	}

	
	@Override
	protected void onNfcFeatureFound() {
		writer = new NdefWriter(this);												//IF nfc found then use this class
		writer.setListener(this);
		
      //  toast(getString(R.string.nfcMessage));
	}

	
	@Override
	protected void onNfcFeatureNotFound() {
      //  toast(getString(R.string.noNfcMessage));
	}
	
	@Override
	public void nfcIntentDetected(Intent intent, String action) {
		byte[] messagePayload; 
		
		Calendar ci = Calendar.getInstance();													//Use calendar to get date and time
		mRowCount++;
		TableLayout date_table = (TableLayout)findViewById(R.id.date_table);					//place to keep dates
		TableRow date_row = new TableRow(this);
		TextView date_tv = new TextView(this);
		TableLayout time_table = (TableLayout)findViewById(R.id.time_table);					//place to keep times
		TableRow time_row = new TableRow(this);
		TextView time_tv = new TextView(this);
		
		String CiDate = "" + ci.get(Calendar.YEAR) + "-" + 										//append the date
				(ci.get(Calendar.MONTH) + 1) + "-" +
				ci.get(Calendar.DAY_OF_MONTH);
		String CiTime = " " +																	//append the time
				(ci.get(Calendar.HOUR_OF_DAY))+ ":" +
				ci.get(Calendar.MINUTE) +  ":" +
				ci.get(Calendar.SECOND);
		
		
		
		date_tv.setText(CiDate);															//Set the date and time
		date_row.addView(date_tv);
		date_table.addView(date_row);
		time_tv.setText(CiTime);
		time_row.addView(time_tv);
		time_table.addView(time_row);

		ndb.createNote(CiDate + " " + CiTime, "");												//add date and time to database
		
		
		try {
	        Resources res = getResources();
	        
	        InputStream in = res.openRawResource(R.raw.aar);									//code to write tag to Start application

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	        byte[] buffer = new byte[1024];
	        int read;
	        do {
	        	read = in.read(buffer, 0, buffer.length);
	        	
	        	if(read == -1) {
	        		break;
	        	}
	        	
	        	byteArrayOutputStream.write(buffer, 0, read);
	        } while(true);

	        messagePayload = byteArrayOutputStream.toByteArray();
	    } catch (Exception e) {
	    	throw new RuntimeException("Cannot access resource", e);
	    }
		
	
	}
	
	// autogenerated... 
	@Override
	public void writeNdefFormattedFailed(Exception e) {
      //  toast(getString(R.string.ndefFormattedWriteFailed) + ": " + e.toString());
	}

	@Override
	public void writeNdefUnformattedFailed(Exception e) {
     //   toast(getString(R.string.ndefUnformattedWriteFailed, e.toString()));
	}

	@Override
	public void writeNdefNotWritable() {
     //   toast(getString(R.string.tagNotWritable));
	}

	@Override
	public void writeNdefTooSmall(int required, int capacity) {
	//	toast(getString(R.string.tagTooSmallMessage,  required, capacity));
	}

	@Override
	public void writeNdefCannotWriteTech() {
     //   toast(getString(R.string.cannotWriteTechMessage));
	}

	@Override
	public void wroteNdefFormatted() {
	
	   // toast(getString(R.string.wroteFormattedTag));
	}

	@Override
	public void wroteNdefUnformatted() {
	  //  toast(getString(R.string.wroteUnformattedTag));
	}
	
	
	public void toast(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
}

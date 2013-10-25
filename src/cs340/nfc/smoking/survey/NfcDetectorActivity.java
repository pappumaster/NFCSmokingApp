package cs340.nfc.smoking.survey;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.os.Bundle;
import android.util.Log;

import cs340.nfc.creator.NfcDetector;


public abstract class NfcDetectorActivity extends Activity implements NfcDetector.NfcIntentListener {
	
    private static final String TAG = NfcDetectorActivity.class.getSimpleName();
    
	protected NfcDetector detector;
	
	protected boolean intentProcessed = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	Log.d(TAG, "onCreate");

    	// Check for available NFC Adapter
    	PackageManager pm = getPackageManager();
    	if(!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
    		//Not found
    		onNfcFeatureNotFound();
    	} else {
    		//Found NFC
    		detector = new NfcDetector(this);
    		detector.setListener(this);

    		onNfcFeatureFound();
    	}
    }
    
    protected abstract void onNfcFeatureNotFound();
    
    protected abstract void onNfcFeatureFound();

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	if(detector != null) {
    		detector.enableForeground();
    		
    		if(!intentProcessed) {
    			//Processed intent 
    			intentProcessed = true;
    			
    			detector.processIntent();
    		}
    	}
    }
	  
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	if(detector != null) {
    		detector.disableForeground();
    	}
    }
    
    @Override
    public void onNewIntent(Intent intent) {
    	
    	Log.d(TAG, "onNewIntent");

        // onResume gets called after this to handle the intent
    	intentProcessed = false;
    	
        setIntent(intent);
    }

	public void nfcIntentDetected(Intent intent, String action) {
		// TODO Auto-generated method stub
		
	}

	public void readNdefMessages(NdefMessage[] messages) {
		// TODO Auto-generated method stub
		
	}

	public void readNdefEmptyMessage() {
		// TODO Auto-generated method stub
		
	}

	public void readNonNdefMessage() {
		// TODO Auto-generated method stub
		
	}

	public void writeNdefFormattedFailed(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public void writeNdefUnformattedFailed(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public void writeNdefNotWritable() {
		// TODO Auto-generated method stub
		
	}

	public void writeNdefTooSmall(int required, int capacity) {
		// TODO Auto-generated method stub
		
	}

	public void writeNdefCannotWriteTech() {
		// TODO Auto-generated method stub
		
	}

	public void wroteNdefFormatted() {
		// TODO Auto-generated method stub
		
	}

	public void wroteNdefUnformatted() {
		// TODO Auto-generated method stub
		
	}
	
}

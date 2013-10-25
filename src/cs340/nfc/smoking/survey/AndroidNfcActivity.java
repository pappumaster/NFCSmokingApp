package cs340.nfc.smoking.survey;


import cs340.nfc.smoking.survey.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//Implemented by DEVINA DHAWAN
public class AndroidNfcActivity extends Activity {
	
    private static final String TAG = AndroidNfcActivity.class.getSimpleName();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.activity_home);
    }
    
    //Open up the writer class
    public void writer(View view) {
    	Log.d(TAG, "Show writer");
    	
    	Intent intent = new Intent(this, NfcWriterActivity.class);
    	startActivity(intent);
    }
    // open up reader class
    public void reader(View view) {
    	Log.d(TAG, "Show reader");
    	
    	Intent intent = new Intent(this, NfcReaderActivity.class);
    	startActivity(intent);
    }
    

}

/*
 *	IMPLEMENTED BY SRUTI BHAGAVATULA
 *
 * 	Tabs for History and Survey history
 * 
 */

package cs340.nfc.smoking.survey;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Home extends Activity implements TabListener {

	ActionBar actionBar;
	Tab historyTab;
	Tab surveyTab;
	Tab reportTab;
	TextView whatSelected;
	SharedVars shared;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shared = (SharedVars)getApplicationContext();
        
        whatSelected = (TextView) findViewById(R.id.whatSelected);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true); 
        
      historyTab = actionBar.newTab().setText("History").setTabListener(this); 
        actionBar.addTab(historyTab);
        surveyTab = actionBar.newTab().setText("Surveys").setTabListener(this);
        actionBar.addTab(surveyTab);
        reportTab = actionBar.newTab().setText("Report").setTabListener(this);
        actionBar.addTab(reportTab);
        
        //Read from file: NFC information and write it into a table view that is visible when History is selected
        //Declare all necessary info in this activity or in activity SharedVars (For global variables)
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		if (arg0 == historyTab)
		{
			whatSelected.setText("");
			whatSelected.setText("History Selected\nNFC Recordings Go Here");
			
			
			//Make the table view visible here and invisible for other tabs
		}
		else if (arg0 == surveyTab)
		{
			whatSelected.setText("");
			whatSelected.setText("Survey Selected\nList of past surveys Go Here");
		}
		else if (arg0 == reportTab)
		{
			whatSelected.setText("");
			whatSelected.setText("Report Selected\nBloop");
		}
	}

	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}
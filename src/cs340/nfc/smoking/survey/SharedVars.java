/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * SINGLETON CLASS for the project
 * 
 * Contains information about:
 * -input file
 * -questions
 * -answers to questions
 * -counter for while survey
 * 
 */

package cs340.nfc.smoking.survey;

import java.util.ArrayList;

import android.app.Application;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SharedVars extends Application{
	
	private String surveyFile = "test_survey_final.xml";
	
	public ArrayList<EachSurveyQuestion> questions;
	
	public int counter;
	public boolean bol;
	
	public ArrayList<answerText> answerList;
	public void setSurveyFile(String fileName)
	{
		surveyFile = fileName;
	}
	public String getSurveyFile()
	{
		return surveyFile;
	}
	
	public Integer getCounter(){
		return counter;
	}
	
	/*public void getIntent(){
		
		if (bol){
			Intent view_survey = new Intent(getApplicationContext(), SurveyPage.class);
			startActivity(view_survey);	
		}
		
	}*/
}
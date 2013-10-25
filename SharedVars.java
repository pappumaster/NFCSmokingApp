package cs340.nfc.smoking.survey;

import java.util.ArrayList;

import android.app.Application;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SharedVars extends Application{
	
	private String surveyFile = "John_survey.xml";
	
	public ArrayList<EachSurveyQuestion> questions;
	
	public ArrayList<answerText> answerList;
	public void setSurveyFile(String fileName)
	{
		surveyFile = fileName;
	}
	public String getSurveyFile()
	{
		return surveyFile;
	}
}
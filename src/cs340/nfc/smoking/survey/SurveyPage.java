/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * Activity that populates the survey based on the questions generated from the input file of questions
 *
 */

package cs340.nfc.smoking.survey;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyPage extends Activity implements OnClickListener {

	SharedVars shared;
	ScrollView mainScroll;
	
	HashMap<Integer, CheckBox[]> boxes;
	HashMap<Integer, RadioGroup> buttons;
	
	Button []nextButtons;
	Button []undoButtons;
	TextView []quesLabels;

	int whichQuestion;
	int whichCheck, whichRadio;
	int numCheck, numRadio, numDivisions;

	int IDs;
	int clickNumber;
	
	ArrayList<RelativeLayout> layoutList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Singleton Initialization
		shared = (SharedVars) getApplicationContext();

		//Extract questions into the questions arrayList global to the project
		shared.questions = new ArrayList<EachSurveyQuestion>();
		ExtractQuestions obj = new ExtractQuestions(this);
		obj.getAllQuestions();
		
		//Initialize the array of answers used to store the answers of questions the user answers when "Next" is clicked
		shared.answerList = new ArrayList<answerText>();

		//TESTING PURPOSES: MANUALLY CREATE AN ARRAYLIST OF QUESTIONS
		/*
		//Create temporary questions
		EachSurveyQuestion q1 = new EachSurveyQuestion();
		q1.setQuestion("How long ago did you finish smoking?");
		q1.setQuesID("1");
		q1.setQuesType(qType.button);
		q1.addToChoices("1 minute");
		q1.addToChoices("2-5 minutes");
		q1.addToChoices("6-15 minutes");
		q1.addToChoices("16-30 minutes");
		q1.addToChoices("31-45 minutes");
		q1.addToChoices("46-60 minutes");
		q1.addToChoices("60+ minutes");
		q1.setNumChoices(7);
		q1.setDefaultNext("2");

		shared.questions.add(q1);

		EachSurveyQuestion q2 = new EachSurveyQuestion();
		q2.setQuestion("When Smoking: How much did you smoke?");
		q2.setQuesID("2");
		q2.setQuesType(qType.button);
		q2.addToChoices("Less than one cigarette");
		q2.addToChoices("One cigarette");
		q2.addToChoices("More than one cigarette");
		q2.setNumChoices(3);
		q2.setDefaultNext("3");

		shared.questions.add(q2);

		EachSurveyQuestion q3 = new EachSurveyQuestion();
		q3.setQuestion("Right now: What are you doing (check all that apply)?");
		q3.setQuesID("3");
		q3.setQuesType(qType.check);
		q3.addToChoices("Hanging out");
		q3.addToChoices("TV/Music/Movie");
		q3.addToChoices("Telephone");
		q3.addToChoices("Texting");
		q3.addToChoices("Computer");
		q3.addToChoices("Reading");
		q3.addToChoices("Class work");
		q3.addToChoices("Job");
		q3.addToChoices("Eat/Drink");
		q3.addToChoices("Alcohol Use");
		q3.addToChoices("Marijuana Use");
		q3.addToChoices("Party");
		q3.addToChoices("Physical Activity/Sports");
		q3.addToChoices("Walking");
		q3.addToChoices("Organized Club");
		q3.addToChoices("Transit/Driving");
		q3.addToChoices("Nothing");
		q3.addToChoices("Other");
		q3.setNumChoices(18);
		q3.setDefaultNext("4");

		shared.questions.add(q3);
		
		EachSurveyQuestion q4 = new EachSurveyQuestion();
		q4.setQuestion("Right now: I feel Happy");
		q4.setQuesID("4");
		q4.setQuesType(qType.slider);
		q4.getSlider().setStart(1);
		q4.getSlider().setStartLabel("Not At All");
		q4.getSlider().setMiddleLabel("Somewhat");
		q4.getSlider().setEnd(10);
		q4.getSlider().setEndLabel("Very Much");
		q4.getSlider().setNumDiv(10);
		q4.setDefaultNext("5");
		
		shared.questions.add(q4);
		*/
		
		//Calculate the amount of radio Buttons and check Boxes to allocate memory for
		largestSizeOfChoices();

		//The main content view is a scroll view
		mainScroll = new ScrollView(this);
		mainScroll.setBackgroundResource(R.drawable.background);

		setContentView(mainScroll);

		//global ID variable that keeps incrementing for dynamic views
		IDs = 0;
		clickNumber = 0;
		whichQuestion = 0;
		whichCheck = 0;
		whichRadio = 0;

		//Allocate memory for new hashmaps to store box or button sets per questions
		boxes = new HashMap<Integer, CheckBox[]>();
		buttons = new HashMap<Integer, RadioGroup>();
		
		//ArrayList to store layouts for each question
		layoutList = new ArrayList<RelativeLayout>();
		
		//Initialize all other views
		nextButtons = new Button[shared.questions.size()];
		undoButtons = new Button[shared.questions.size()];
		quesLabels = new TextView[shared.questions.size()];

		//Create the first question
		createQuestion();
	}
	void createQuestion()
	{
		//This method populates a layout based on the question in the arrayList at position: "whichQuestion"
		//Check type of question => CheckBox
		//Button or Slider question => RadioButton

		//Remove existing views in MainScroll to place new layout for new question
		mainScroll.removeAllViewsInLayout();
		
		//Current Question
		EachSurveyQuestion q = shared.questions.get(whichQuestion);
		
		//Layout for this question
		RelativeLayout layout = new RelativeLayout(this);
		
		//Set the question Label
		quesLabels[whichQuestion] = new TextView(this);
		quesLabels[whichQuestion].setText(q.getQuestion());
		quesLabels[whichQuestion].setId(IDs++);
		quesLabels[whichQuestion].setGravity(Gravity.CENTER);
		quesLabels[whichQuestion].setTextSize(20);
		quesLabels[whichQuestion].setTextColor(Color.WHITE);
		RelativeLayout.LayoutParams quesParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		quesParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		quesParams.setMargins(0, 40, 0, 0);
		quesLabels[whichQuestion].setLayoutParams(quesParams);
		layout.addView(quesLabels[whichQuestion]);
		
		
		if (q.getQuesType() == qType.check)
		{
			RelativeLayout.LayoutParams[] checkParams = new RelativeLayout.LayoutParams[q.getNumChoices()];
			CheckBox[] array = new CheckBox[q.getNumChoices()];
			boxes.put(whichCheck, array);
			
			for (int i = 0; i < q.getNumChoices(); ++i)
			{
				array[i] = new CheckBox(this);
				array[i].setText(q.getChoices().get(i));
				array[i].setTextColor(Color.GRAY);
				array[i].setHighlightColor(Color.WHITE);
				array[i].setId(IDs++);
				
				checkParams[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
				if (i == 0) {
					checkParams[i].addRule(RelativeLayout.BELOW, quesLabels[whichQuestion].getId());
				} else {
					checkParams[i].addRule(RelativeLayout.BELOW, array[i-1].getId());
				}
				checkParams[0].setMargins(0, 80, 0, 0);
				array[i].setLayoutParams(checkParams[i]);
				
				layout.addView(array[i]);
			}
		}
		else if (q.getQuesType() == qType.button)
		{
			RadioButton []array = new RadioButton[q.getNumChoices()];
			RadioGroup group = new RadioGroup(this);
			buttons.put(whichRadio, group);
			for (int i = 0; i < q.getNumChoices(); ++i) 
			{
				array[i] = new RadioButton(this);
				array[i].setText(q.getChoices().get(i));
				array[i].setTextColor(Color.GRAY);
				array[i].setHighlightColor(Color.WHITE);
				array[i].setId(IDs++);
				
				group.addView(array[i]);
			}
			group.setId(IDs++);
			group.setOrientation(RadioGroup.VERTICAL);
			RelativeLayout.LayoutParams radioParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			radioParams.addRule(RelativeLayout.BELOW, quesLabels[whichQuestion].getId());
			radioParams.setMargins(0, 100, 0, 0);
			group.setLayoutParams(radioParams);
			layout.addView(group);
		}
		else if (q.getQuesType() == qType.slider)
		{
			RadioButton []array = new RadioButton[q.getSlider().getNumDiv()];
			RadioGroup group = new RadioGroup(this);
			buttons.put(whichRadio, group);
			for (int i = 0; i < q.getSlider().getNumDiv(); ++i)
			{
				array[i] = new RadioButton(this);
				array[i].setTextColor(Color.GRAY);
				array[i].setHighlightColor(Color.WHITE);
				array[i].setId(IDs++);
				//array[i].setText(String.valueOf(q.getSlider().getStart()+i));
				if (i == 0) {
					array[i]
					.setText(String.valueOf(
							q.slider.getStart()
							+ ": "
							+ q.slider
							.getStartLabel()));
				} else if (i == q.slider.getEnd() - 1) {
					array[i]
					.setText(String.valueOf(
							q.slider.getEnd()
							+ ": "
							+ q.slider
							.getEndLabel()));
				} else if (i == (q.slider.getNumDiv() / 2)-1) {
					array[i]
					.setText(String.valueOf(
							q.slider.getStart()+ (q.slider.getNumDiv()/2))
							+ ": "
							+ q.slider
							.getMiddleLabel());
				} else {
					array[i]
					.setText(String.valueOf(q.slider.getStart()+i));
				}
				group.addView(array[i]);
			}
			group.setId(IDs++);
			group.setOrientation(RadioGroup.VERTICAL);
			RelativeLayout.LayoutParams radioParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			radioParams.addRule(RelativeLayout.BELOW, quesLabels[whichQuestion].getId());
			radioParams.setMargins(0, 100, 0, 0);
			group.setLayoutParams(radioParams);
			layout.addView(group);
		}
		
		//Set Next Buttons Layout Parameters
		nextButtons[whichQuestion] = new Button(this);
		nextButtons[whichQuestion].setId(IDs++);
		nextButtons[whichQuestion].setText("Next");
		nextButtons[whichQuestion].setOnClickListener(this);
		//nextButtons[whichQuestion].setGravity(Gravity.RIGHT);
		RelativeLayout.LayoutParams nextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		if (q.getQuesType() == qType.check)
			nextParams.addRule(RelativeLayout.BELOW, boxes.get(whichCheck)[q.getNumChoices()-1].getId());
		else
			nextParams.addRule(RelativeLayout.BELOW, buttons.get(whichRadio).getId());
		//nextParams.addRule(RelativeLayout.ALIGN_RIGHT);
		nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		nextButtons[whichQuestion].setLayoutParams(nextParams);

		//Set Undo Buttons Layout Params
		undoButtons[whichQuestion] = new Button(this);
		undoButtons[whichQuestion].setId(IDs++);
		undoButtons[whichQuestion].setText("Undo");
		undoButtons[whichQuestion].setOnClickListener(this);
		//undoButtons[whichQuestion].setGravity(Gravity.LEFT);
		RelativeLayout.LayoutParams undoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		if (q.getQuesType() == qType.check)
			undoParams.addRule(RelativeLayout.BELOW, boxes.get(whichCheck)[q.getNumChoices()-1].getId());
		else 
			undoParams.addRule(RelativeLayout.BELOW, buttons.get(whichRadio).getId());
		//undoParams.addRule(RelativeLayout.ALIGN_LEFT);
		undoParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		undoButtons[whichQuestion].setLayoutParams(undoParams);
		
		layout.addView(nextButtons[whichQuestion]);
		layout.addView(undoButtons[whichQuestion]);
		
		//Add the current layout to the global arrayList
		layoutList.add(layout);   
		
		//Add this layout to the main view
		mainScroll.addView(layout);
	}
	public void largestSizeOfChoices() 
	{
		//Set the number of RadioButton arrays and CheckBox arrays 
		
		numCheck = 0;
		numRadio = 0;
		numDivisions = 0;

		for (int i = 0; i < shared.questions.size(); ++i)
		{
			EachSurveyQuestion q = shared.questions.get(i);
			if (q.getQuesType() == qType.check)
			{
				++numCheck;
			}
			else if (q.getQuesType() == qType.button)
			{
				++numRadio;
			}
			else if (q.getQuesType() == qType.slider)
			{
				++numDivisions;
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_survey_page, menu);
		return true;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//On Click method for the whole Activity
		
		//If "Next" is clicked
		if (v == nextButtons[whichQuestion])
		{
			//Make sure user has selected an answer
			boolean answered = false;
			if (shared.questions.get(whichQuestion).getQuesType() == qType.check)
			{ 
				CheckBox [] b = boxes.get(whichCheck);
				for (int i = 0; i < b.length; ++i)
				{
					if (b[i].isChecked() == true)
					{
						answered = true;
					}
				}
			}
			else
			{
				RadioGroup bg = buttons.get(whichRadio);
				int count = bg.getChildCount();
				RadioButton []b = new RadioButton[count];
				for (int i = 0; i < count; ++i)
				{
					b[i] = new RadioButton(this);
					View o = bg.getChildAt(i);
					if (o instanceof RadioButton)
					{
						b[i] = (RadioButton)o;
					}
				}
				for (int i = 0; i < b.length; ++i)
				{
					if (b[i].isChecked() == true)
					{
						answered = true;
					}
				}
			}
			if (answered == false)
			{
				Toast.makeText(SurveyPage.this, "Please answer the question.", Toast.LENGTH_LONG).show();
				return;
			}
			
			//Saving answer to an answerList object and add it to the arrayList
			answerText ans = new answerText();
			ans.setQuestion(shared.questions.get(whichQuestion).getQuestion());
			String answerString = "";
			if (shared.questions.get(whichQuestion).getQuesType() == qType.check)
			{
				CheckBox [] b = boxes.get(whichCheck);
				for (int i = 0; i < b.length; ++i)
				{
					if (b[i].isChecked() == true)
					{
						answerString = answerString + "-" + b[i].getText() + "\n";
					}
				}
			}
			else
			{
				RadioGroup bg = buttons.get(whichRadio);
				int count = bg.getChildCount();
				RadioButton []b = new RadioButton[count];
				for (int i = 0; i < count; ++i)
				{
					b[i] = new RadioButton(this);
					View o = bg.getChildAt(i);
					if (o instanceof RadioButton)
					{
						b[i] = (RadioButton)o;
					}
				}
				for (int i = 0; i < b.length; ++i)
				{
					if (b[i].isChecked() == true)
					{
						answerString = answerString + "-" + b[i].getText() + "\n";
						break;
					}
				}
			}
			ans.setAnswer(answerString);
			shared.answerList.add(ans);
			
			whichQuestion++;
			
			//If LAST Question
			if (whichQuestion == shared.questions.size())
			{
				//Toast.makeText(SurveyPage.this, "DONE", Toast.LENGTH_LONG).show();
				nextButtons[whichQuestion-1].setEnabled(false);
				Intent fin = new Intent(getApplicationContext(), SurveyFin.class);
				startActivity(fin);
				//Go to an ending screen
			}
			else
			{
				if (shared.questions.get(whichQuestion-1).getQuesType() == qType.check)
					++whichCheck;
				else
					++whichRadio;
				createQuestion();
			}
		}
		//If "Undo" Button clicked
		else if (v == undoButtons[whichQuestion])
		{
			//Remove latest layout in layoutList and display the layout before that
			if (whichQuestion == 0)
			{
				Toast.makeText(SurveyPage.this, "Nothing to undo.", Toast.LENGTH_LONG).show();
				return;
			}
			mainScroll.removeAllViewsInLayout();
			--whichQuestion;
			if (shared.questions.get(whichQuestion).getQuesType() == qType.check)
				--whichCheck;
			else
				--whichRadio;
			layoutList.remove(whichQuestion+1);
			shared.answerList.remove(whichQuestion);
			mainScroll.addView(layoutList.get(whichQuestion));
		}
	}
}
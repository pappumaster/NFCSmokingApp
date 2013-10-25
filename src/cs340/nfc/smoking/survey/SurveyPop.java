/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * Original activity to populate survey
 * Not currently used
 * 
 */

package cs340.nfc.smoking.survey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import cs340.nfc.smoking.survey.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class SurveyPop extends Activity {

	SharedVars shared;

	ScrollView mainScroll;

	TextView quesLabel;
	Button nextButton;
	Button undoButton;

	String currentQuestion;
	String prevQuestion;

	HashMap<String, EachSurveyQuestion> questionMap;
	RelativeLayoutList layoutList;
	ArrayList<EachSurveyQuestion> questions;

	int commID;
	boolean answered;
	TextView doneLabel;
	Button doneButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		commID = 3;
		answered = false;

		shared = (SharedVars)getApplicationContext();
		layoutList = new RelativeLayoutList();
		questionMap = new HashMap<String, EachSurveyQuestion>();
		questions = new ArrayList<EachSurveyQuestion>();

		mainScroll = new ScrollView(this);

		setContentView(mainScroll);

		mainScroll.setBackgroundResource(R.drawable.background);

		//File can be read in.
		//Put parsing code here
		try {
			InputStream is = getAssets().open(shared.getSurveyFile());
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		//Making temporary arrayList of questions until parsing code
		EachSurveyQuestion q1 = new EachSurveyQuestion();
		q1.setQuesID("1");
		q1.setQuesType((qType.button));
		q1.setQuestion("How long ago did you finish smoking?");
		q1.setNumChoices(7);
		q1.setDefaultNext("4");

		ArrayList<String> qChoices = new ArrayList<String>();
		qChoices.add("1 minute");
		qChoices.add("2-5 minutes");
		qChoices.add("6-15 minutes");
		qChoices.add("16-30 minutes");
		qChoices.add("31-45 minutes");
		qChoices.add("46-60 minutes");
		qChoices.add("60+ minutes");

		q1.setChoices(qChoices);
		questions.add(q1);

		/*EachSurveyQuestion q2 = new EachSurveyQuestion();
		q2.setQuesID("2a");
		q2.setQuesType((qType.slider));
		q2.setQuestion("Right now: I feel Happy");
		q2.setDefaultNext("2b");

		//FIX SLIDER

		q2.slider.setStart(1);
		q2.slider.setEnd(10);
		q2.slider.setStartLabel("Not At All");
		q2.slider.setMiddleLabel("Somewhat");
		q2.slider.setEndLabel("Very Much");
		q2.slider.setNumDiv(10);

		questions.add(q2);
		*/
		EachSurveyQuestion q3 = new EachSurveyQuestion();
		q3.setQuesID("4");
		q3.setQuesType(qType.button);
		q3.setQuestion("When smoking: How much did you smoke?");
		q3.setDefaultNext("5");
		
		ArrayList<String> qChoices1 = new ArrayList<String>();
		qChoices1.add("Less than one cigarette");
		//qChoices1.addOne(" cigarette");
		//	<choice>More than once cigarette
		
		//End the arrayList parsing
		 
		//Put arrayList into HashMap
		createHashMap();

		quesLabel = new TextView(this);

		nextButton = new Button(this);
		nextButton.setText("Next");
		nextButton.setId(1);
		nextButton.setWidth(35);

		undoButton = new Button(this);
		undoButton.setText("Undo");
		undoButton.setId(2);
		undoButton.setWidth(35);

		currentQuestion = questions.get(0).getQuesID();
		prevQuestion = null;
		startDisplayingQuestions();


		//THIS STUFF IN THE METHOD


		/*
        question = new EachSurveyQuestion();
        question.setQuesID("1");
        question.setQuesType((qType.button));
        question.setQuestion("How long ago did you finish smoking?");
        question.setNumChoices(7);
        question.setDefaultNext("2a");

        String array[] = {"1 minute", "2-5 minutes", "6-15 minutes", "16-30 minutes", "31-45 minutes", "46-60 minutes", "60+ minutes"};
        question.setChoices(array);
		 */

		//	RelativeLayout layout = new RelativeLayout(this);
		/*  quesLabel = new TextView(this);
        quesLabel.setText(question.getQuestion());
        quesLabel.setTextColor(Color.WHITE);
        quesLabel.setId(1);
        quesLabel.setGravity(Gravity.CENTER);
        quesLabel.setTextSize(20);

        RelativeLayout.LayoutParams quesParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        quesParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //quesParams.addRule(RelativeLayout.CENTER_VERTICAL);
        quesParams.setMargins(0, 40, 0, 0);
        quesLabel.setLayoutParams(quesParams);
		 */        
		/*   TextView label = new TextView(this);
        label.setText("HIIIIII");
        label.setTextColor(Color.WHITE);
        label.setId(2);
        label.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams labelParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        labelParams.addRule(RelativeLayout.BELOW, quesLabel.getId());
        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //labelParams.setMargins(20, 20, 20, 20);

        label.setLayoutParams(labelParams);
        label.setVisibility(View.VISIBLE);
		 */
		/*int numChoices = question.getNumChoices();
        RadioButton []choices = new RadioButton[numChoices];
        RadioGroup choiceGroup = new RadioGroup(this);
        choiceGroup.setId(3);
        choiceGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < question.getNumChoices(); ++i)
        {
        	choices[i] = new RadioButton(this);
        	//choices[i].setText(question.choices[i]);
        	choices[i].setTextColor(Color.GRAY);
        	choiceGroup.addView(choices[i]);
        }

        RelativeLayout.LayoutParams radioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        radioParams.addRule(RelativeLayout.BELOW, quesLabel.getId());
        radioParams.setMargins(0, 80, 0, 0);
        choiceGroup.setLayoutParams(radioParams);

        RelativeLayout.LayoutParams nextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
       // nextParams.addRule(RelativeLayout.ALIGN_BASELINE, RelativeLayout.ALIGN_PARENT_RIGHT);
        nextParams.addRule(RelativeLayout.BELOW, choiceGroup.getId());
        nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        nextButton.setLayoutParams(nextParams);
        layout.addView(quesLabel);
        layout.addView(choiceGroup);
        layout.addView(nextButton);
        // layout.addView(label);
        mainScroll.addView(layout);*/
	}

	void startDisplayingQuestions()
	{
		displayOneQuestion();

		doneLabel = new TextView(this);
		doneLabel.setText("You have finished the survey!\n");
		doneLabel.setTextSize(30);
		doneLabel.setTextColor(Color.WHITE);
		
		doneButton = new Button(this);
		doneButton.setText("Save Results");
		doneButton.setTextSize(25);
		doneButton.setTextColor(Color.CYAN);
		
		nextButton.setOnClickListener(new View.OnClickListener() {
		EachSurveyQuestion cQuestion = questionMap.get(currentQuestion);
		public void onClick(View arg0) {
				if (answered == false)
				{
					Toast.makeText(SurveyPop.this, "You have not answered the question yet.\nPlease pick an answer first.",
							Toast.LENGTH_LONG).show();
					return;
				}
				prevQuestion = currentQuestion;

				//USE CONDITION TO DETERMINE NEXT QUESTION
				//IMPLEMENT HERE
		
				//FOR DEFAULT NEXT
				currentQuestion = cQuestion.getDefaultNext();
				mainScroll.removeAllViewsInLayout();
				if (currentQuestion == "0" || questionMap.containsKey(currentQuestion) == false)
				{
					RelativeLayout.LayoutParams doneParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
					doneParams.addRule(RelativeLayout.CENTER_VERTICAL);
					//doneParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					doneParams.setMargins(0, 130, 0, 0);
					doneLabel.setLayoutParams(doneParams);
					
					RelativeLayout.LayoutParams doneButParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
					doneButParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					doneButParams.setMargins(0, 200, 0, 0);	
					doneButton.setLayoutParams(doneButParams);
					
					doneButton.setOnClickListener(new View.OnClickListener(){

						public void onClick(View arg0) {
							finish();
							
						}
						
					});
					mainScroll.addView(doneLabel);
					mainScroll.addView(doneButton);
					
					
					//DISPLAY "SURVEY DONE"
					//PUT BUTTON TO EXIT APP (WRITE ANSWERS TO FILE)
				}
				displayOneQuestion();
				return;
			}
		});
		undoButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

			}
		});
	}
	void displayOneQuestion()
	{
		RelativeLayout layout = new RelativeLayout(this);
		EachSurveyQuestion cQuestion = questionMap.get(currentQuestion);
		quesLabel.setText(cQuestion.getQuestion());
		quesLabel.setId(commID);
		quesLabel.setTextColor(Color.WHITE);
		quesLabel.setGravity(Gravity.CENTER);
		quesLabel.setTextSize(20);

		RelativeLayout.LayoutParams quesParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

		quesParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		quesParams.setMargins(0, 40, 0, 0);
		quesLabel.setLayoutParams(quesParams);
		layout.addView(quesLabel);

		int numChoices;
		if (cQuestion.getQuesType() == qType.button)
		{
			numChoices = cQuestion.getNumChoices();
			
			RadioButton []choices = new RadioButton[numChoices];
			RadioGroup choiceGroup = new RadioGroup(this);
			choiceGroup.setId(++commID);
			choiceGroup.setOrientation(RadioGroup.VERTICAL);
			for (int i = 0; i < cQuestion.getNumChoices(); ++i)
			{
				choices[i] = new RadioButton(this);
				choices[i].setText(cQuestion.choices.get(i));
				choices[i].setTextColor(Color.GRAY);
				choices[i].setId(++commID);
				choices[i].setHighlightColor(Color.WHITE);

				choices[i].setOnClickListener(new View.OnClickListener(){

					public void onClick(View arg0) {
						answered = true;
					}
				});
				choiceGroup.addView(choices[i]);
			}

			RelativeLayout.LayoutParams radioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			radioParams.addRule(RelativeLayout.BELOW, quesLabel.getId());
			radioParams.setMargins(0, 80, 0, 0);
			choiceGroup.setLayoutParams(radioParams);

			RelativeLayout.LayoutParams nextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			nextParams.addRule(RelativeLayout.BELOW, choiceGroup.getId());
			nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			nextButton.setLayoutParams(nextParams);

			RelativeLayout.LayoutParams undoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			undoParams.addRule(RelativeLayout.BELOW, nextButton.getId());
			undoParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			undoButton.setLayoutParams(undoParams);
			
			
			
			layout.addView(choiceGroup);
			layout.addView(nextButton);
			layout.addView(undoButton);
		}
		if (cQuestion.getQuesType() == qType.check)
		{
			int checkCount = 0;
			numChoices = cQuestion.getNumChoices();
			CheckBox []choices = new CheckBox[numChoices];

			RelativeLayout.LayoutParams []checkParams = new RelativeLayout.LayoutParams[numChoices];

			//choiceGroup.setOrientation(RadioGroup.VERTICAL);
			for (int i = 0; i < numChoices; ++i)
			{
				choices[i] = new CheckBox(this);
				choices[i].setText(cQuestion.choices.get(i));
				choices[i].setTextColor(Color.GRAY);
				choices[i].setHighlightColor(Color.WHITE);
				choices[i].setId(++commID);

				checkParams[i] = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
				if (i == 0)
				{
					checkParams[i].addRule(RelativeLayout.BELOW, quesLabel.getId());
				}
				else
				{
					checkParams[i].addRule(RelativeLayout.BELOW, choices[i-1].getId());
				}
				checkParams[0].setMargins(0, 80, 0, 0);
				choices[i].setLayoutParams(checkParams[i]);

				layout.addView(choices[i]);
			}
			RelativeLayout.LayoutParams nextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			nextParams.addRule(RelativeLayout.BELOW, choices[numChoices-1].getId());
			nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			nextButton.setLayoutParams(nextParams);

			RelativeLayout.LayoutParams undoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			undoParams.addRule(RelativeLayout.BELOW, nextButton.getId());
			undoParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			undoButton.setLayoutParams(undoParams);

			layout.addView(nextButton);
			layout.addView(undoButton);
		}

		//DO SLIDER
		if (cQuestion.getQuesType() == qType.slider)
		{
			//MAKE A RADIO GROUP
			//JUST LIKE RADIO BUTTON QUESTION
		}

		layoutList.addLayout(layout);
		mainScroll.addView(layout);
	}
	void createHashMap()   //Put the questions into a hash map
	{
		for(int i = 0; i < questions.size(); ++i)
		{
			questionMap.put(questions.get(i).getQuesID(), questions.get(i));
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_survey_pop, menu);
		return true;
	}
}
class RelativeLayoutList
{
	RelativeLayoutNode start;
	RelativeLayoutNode end;

	public RelativeLayoutList()
	{
		start = null;
		end = null;
	}
	public void addLayout(RelativeLayout nLayout)
	{
		RelativeLayoutNode newNode = new RelativeLayoutNode(nLayout);
		if (start == null)
		{
			start = newNode;
			end = newNode;
		}
		else
		{
			newNode.setPrev(end);
			end.setNext(newNode);
			end = newNode;
		}
	}
	//Node Class
	class RelativeLayoutNode
	{
		private RelativeLayout node;
		private RelativeLayoutNode prev;
		private RelativeLayoutNode next;

		RelativeLayoutNode()
		{
			prev = null;
			next = null;
		}
		RelativeLayoutNode(RelativeLayout node)
		{
			this.node = node;
			prev = null;
			next = null;
		}
		void setPrev(RelativeLayoutNode prev)
		{
			this.prev = prev;
		}
		void setNext(RelativeLayoutNode next)
		{
			this.next = next;
		}
		void setNode(RelativeLayout node)
		{
			this.node = node;
		}
		RelativeLayoutNode getPrev()
		{
			return prev;
		}
		RelativeLayoutNode getNext()
		{
			return next;
		}
		RelativeLayout getNode()
		{
			return node;
		}
	}
}

//LINKED LIST OF ANSWERS TO QUESTIONS
class AnswerList
{
	answerNode start;
	answerNode end;
	class answerNode
	{
		String ques;
		String ans;

		answerNode prev;
		answerNode next;
	}
}
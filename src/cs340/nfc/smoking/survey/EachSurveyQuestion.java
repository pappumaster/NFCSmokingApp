/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * Class to store each question's DATA
 * 
 */

package cs340.nfc.smoking.survey;

import java.util.ArrayList;
import java.util.Arrays;

enum qType {button, check, slider};

class EachSurveyQuestion
{
	String QuesID;
	qType QuesType;
	String question;
	int numChoices;
	ArrayList<String> choices = new ArrayList<String>();
	
	// getters and setters
	
	public String getQuesID() {
		return QuesID;
	}
	public void setQuesID(String quesID) {
		QuesID = quesID;
	}
	public qType getQuesType() {
		return QuesType;
	}
	public void setQuesType(qType quesType) {
		QuesType = quesType;
	}
	
	public void setQuesType(String quesType) {
		 if(quesType.equalsIgnoreCase("button"))
			QuesType = qType.button;
		else if(quesType.equalsIgnoreCase("check"))
			QuesType = qType.check;
		else if(quesType.equalsIgnoreCase("slider"))
			QuesType = qType.slider;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getNumChoices() {
		return numChoices;
	}
	public void setNumChoices(int numChoices) {
		this.numChoices = numChoices;
	}
	
	// note: changed data member this.choices from String[] to ArrayList<String>
	public ArrayList<String> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	
	public int getChoicesSize() {
		return this.choices.size();
	}
	
	public void addToChoices(String myChoice)
	{
		this.choices.add(myChoice);
	}
	
	
	class sliderInfo
	{
		int start;
		int end;
		int numDiv;
		String startLabel;
		String middleLabel;
		String endLabel;
		
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public int getNumDiv() {
			return numDiv;
		}
		public void setNumDiv(int numDiv) {
			this.numDiv = numDiv;
		}
		public String getStartLabel() {
			return startLabel;
		}
		public void setStartLabel(String startLabel) {
			this.startLabel = startLabel;
		}
		public String getMiddleLabel() {
			return middleLabel;
		}
		public void setMiddleLabel(String middleLabel) {
			this.middleLabel = middleLabel;
		}
		public String getEndLabel() {
			return endLabel;
		}
		public void setEndLabel(String endLabel) {
			this.endLabel = endLabel;
		}

	} 
	
	//sliderInfo slider;
	sliderInfo slider = new sliderInfo();
	
	public sliderInfo getSlider() {
		return this.slider;
	}
	
	class buttonCond
	{
		String cond;
		String nextQues;
		
		public String getCond() {
			return cond;
		}
		public void setCond(String cond) {
			this.cond = cond;
		}
		public String getNextQues() {
			return nextQues;
		}
		public void setNextQues(String nextQues) {
			this.nextQues = nextQues;
		}	
	}
	ArrayList<buttonCond> buttonCondition = new ArrayList<buttonCond>();
	
	class checkCond
	{
		String cond;
		String nextQues;	
		
		public String getCond() {
			return cond;
		}
		public void setCond(String cond) {
			this.cond = cond;
		}
		public String getNextQues() {
			return nextQues;
		}
		public void setNextQues(String nextQues) {
			this.nextQues = nextQues;
		}
	}
	ArrayList<checkCond> checkConditions = new ArrayList<checkCond>();
	
	
	
	public ArrayList<buttonCond> getButtonCondition() {
		return buttonCondition;
	}
	public void setButtonCondition(ArrayList<buttonCond> buttonCondition) {
		this.buttonCondition = buttonCondition;
	}
	public ArrayList<checkCond> getCheckConditions() {
		return checkConditions;
	}
	public void setCheckConditions(ArrayList<checkCond> checkConditions) {
		this.checkConditions = checkConditions;
	}
	public sliderCond getSliderConditions() {
		return sliderConditions;
	}
	public void setSliderConditions(sliderCond sliderConditions) {
		this.sliderConditions = sliderConditions;
	}


	class sliderCond
	{
		int upperLim;     //-1 for no upper limit
		int lowerLim;     //-1 for no lower limit
		String nextQues;		
		
		public int getUpperLim() {
			return upperLim;
		}
		public void setUpperLim(int u) {
			this.upperLim = u;
		}
		
		public int getLowerLim() {
			return lowerLim;
		}
		public void setLowerLim(int w) {
			this.lowerLim = w;
		}
		
		public String getNextQues() {
			return nextQues;
		}
		public void setNextQues(String nextQues) {
			this.nextQues = nextQues;
		}
	}
	sliderCond sliderConditions = new sliderCond();
	String defaultNext;
	
	public String getDefaultNext() {
		return this.defaultNext;
	}
	
	public void setDefaultNext(String n1) {
		this.defaultNext = n1;
	}
	
	/* Given a condition type, condition string, and nextQuestion string 
	 * associated with the condition, extracts and stores the conditions 
	 * and next questions based on the type (button, check, or slider).
	 */

	public void parseCondition(String condType, String bCond, String nextQ) {	
		char div = '?';
		
		//button or slider question: split condition string by "|"
		if(condType.equals("button")|| condType.equals("radio")|| condType.equals("slider"))
		{
			div = '|';
		}
		// check question: split condition string by "*"
		else if(condType.equals("check"))
		{
			div = '*';
		}
		
		bCond.trim();
		ArrayList<String> conditionList = new ArrayList<String>(Arrays.asList(bCond.split("|")));
		
		ArrayList<String> buttonCondCodes = new ArrayList<String>();
		int startIndex = -1;
		int endIndex = -2;
		String currentCond = "";

		// parse out individual condition codes from the condition code string
		for(int r = 0; r < conditionList.size(); r++)
		{				
			if(conditionList.get(r).length() > 0)
			{
				char c = conditionList.get(r).charAt(0);
				
				if(!(c != div && c != '0' && c != '1' && c != '2' && c != '3' && c != '4'
					&& c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != '+' && c!= '-'))
					{
						//System.out.println("The "+r+"th element is: "+conditionList.get(r));
						
						// first condition code
						if( c != div && startIndex < 0)
						{
							startIndex = r;
							endIndex = r;
							currentCond += conditionList.get(r);
						}
						// continue tracking the length of the current condition code
						else if(c != div)
						{
							endIndex = endIndex + 1;
							currentCond += conditionList.get(r);
						}
						// current condition code is ended; need to reset for the next one
						else if(c == div)
						{
							// store the current completed condition code
							buttonCondCodes.add(currentCond);
							currentCond = "";
							
							startIndex = r+1;
							endIndex = r+1;
						}
											
					} // end of if char statement
				
					// add the very last condition code
					else if(r == conditionList.size()-1)
					{
						buttonCondCodes.add(currentCond);
					}
			
			} // end of if statement	
			
		} // end of for statement
		
		
		// add the individual condition codes to the appropriate data structures 
		ArrayList<buttonCond> bc = new ArrayList<buttonCond>();
		ArrayList<checkCond> chc = new ArrayList<checkCond>();		
		sliderCond s1 = new sliderCond();
		
		char condSign  = '|';
		
		//System.out.println("here are all of the stored condition codes:");
		for(int z = 0; z < buttonCondCodes.size(); z++)
		{
			//System.out.println("Condition code "+z+": "+buttonCondCodes.get(z));
			
			if(condType.equals("radio")|| condType.equals("button"))
			{	
				buttonCond b1 = new buttonCond();
				b1.setCond(buttonCondCodes.get(z));
				b1.setNextQues(nextQ);		
				bc.add(b1);
			}
			else if(condType.equals("check"))
			{
				checkCond c1 = new checkCond();
				c1.setCond(buttonCondCodes.get(z));
				c1.setNextQues(nextQ);
				chc.add(c1);
			}
			else if(condType.equals("slider"))
			{		
				//System.out.println("for slider, button code "+z+" is: "+buttonCondCodes.get(z))
				condSign = buttonCondCodes.get(0).charAt(0);
				
				if(buttonCondCodes.size()==1)
				{
					if(condSign == '+')
					{
						s1.setLowerLim(Integer.parseInt(buttonCondCodes.get(0).substring(1,2)));
						s1.setUpperLim(-1);
					}
					else if(condSign == '-')
					{				
						s1.setUpperLim(Integer.parseInt(buttonCondCodes.get(0).substring(1,2)));
						s1.setLowerLim(-1);
					}
				}
				else if(buttonCondCodes.size()==2)
				{
					if(condSign == '+')
					{
						s1.setLowerLim(Integer.parseInt(buttonCondCodes.get(0).substring(1,2)));
						s1.setUpperLim(Integer.parseInt(buttonCondCodes.get(1).substring(1,2)));
					}
					else if(condSign == '-')
					{				
						s1.setUpperLim(Integer.parseInt(buttonCondCodes.get(0).substring(1,2)));
						s1.setLowerLim(Integer.parseInt(buttonCondCodes.get(1).substring(1,2)));
					}
				}
				
				s1.setNextQues(nextQ);					
				
			} // end of if(slider)
			
		} // end of for loop
		
		// store condition codes & related info into data structures (ArrayList, etc.)
		setButtonCondition(bc);
		setCheckConditions(chc);
		setSliderConditions(s1);

	} // end of parseCondition()
	
	
} // end of Question class
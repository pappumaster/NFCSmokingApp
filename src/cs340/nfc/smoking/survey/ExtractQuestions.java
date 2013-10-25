/*
 * Implemented by SRUTI BHAGAVATULA and CELESTE WALLACE
 * 
 * Parsing of XML input file using DOM (Document Object Model) Parsing Technique
 * 
 * Initially started by Sruti Bhagavatula, completed by Celeste Wallace
 * 
 */

package cs340.nfc.smoking.survey;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;

public class ExtractQuestions {

	SharedVars shared;
	Activity act;
	ExtractQuestions(Activity act)
	{

		this.act = act;
		shared = (SharedVars) act.getApplicationContext();
		getAllQuestions();
	}
	public void getAllQuestions() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			//File file = new File(shared);

			InputStream is = act.getApplicationContext().getAssets().open(shared.getSurveyFile());

			if (is != null) {
				Document doc = db.parse(is);
				Element docEle = doc.getDocumentElement();

				int answerCounter = 0;
				int numOfChoices = 0;
				int numOfDigits = 0;
				int lengthOfDigits = 0;

				// Print root element of the document
				System.out.println("Root element of the document: "
						+ docEle.getNodeName());

				NodeList quesList = docEle.getElementsByTagName("Question");

				/*// Print total question elements in document
				System.out.println("Total number of questions: " + quesList.getLength());*/

				if (quesList != null && quesList.getLength() > 0) {
					for (int i = 0; i < quesList.getLength(); i++) {

						Node node = quesList.item(i);
						NodeList choices;

						if (node.getNodeType() == Node.ELEMENT_NODE) {

							System.out.println("=====================");

							Element e = (Element) node;
							NodeList nodeList = e.getElementsByTagName("QuesID");

							// create a new Question
							EachSurveyQuestion thisQues = new EachSurveyQuestion();
							shared.questions.add(thisQues);
							//printElementText(e,"QuesID");
							thisQues.setQuesID(extractElementText(e,"QuesID"));
							System.out.println("QuesID is: "+thisQues.getQuesID());

							//note: extraction of <SubID> </SubID> skipped for all questions

							//printElementText(e,"QuestionString");
							thisQues.setQuestion(extractElementText(e,"QuestionString"));
							System.out.println("QuesString is: "+thisQues.getQuestion());

							//printElementText(e,"QuesType");
							thisQues.setQuesType(extractElementText(e,"QuesType"));
							System.out.println("QuesType is: "+thisQues.getQuesType());

							String quesType = ""+thisQues.getQuesType();

							// only non-slider questions have a NumOfChoices
							if(!quesType.equalsIgnoreCase("slider"))	
							{
								nodeList = e.getElementsByTagName("NumOfChoices");

								try {
									//String[] choiceElements = nodeList.item(0).getChildNodes().item(0).getNodeValue().split(" ");
									ArrayList<String> choiceList = new ArrayList<String>(Arrays.asList(nodeList.item(0).getChildNodes().item(0).getNodeValue().split(" ")));

									// numOfChoices is the 0th element of choiceElements[]
									//numOfChoices = Integer.parseInt(choiceElements[0].trim());
									numOfChoices = Integer.parseInt(choiceList.get(0).trim());
								}

								catch (Exception nc)
								{
									lengthOfDigits = (nodeList.item(0).getChildNodes().item(0)
											.getNodeValue() ).length();
									//System.out.println("the length of the NumOfChoices string is: "+lengthOfDigits);

									numOfDigits = lengthOfDigits - 4;
									numOfChoices = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue().substring(1,2));			
								}						
								//System.out.println("NumOfChoices: "+numOfChoices);
								thisQues.setNumChoices(numOfChoices);
								System.out.println("numChoices is: "+thisQues.getNumChoices());

								choices = docEle.getElementsByTagName("choice");

								/* Need to only print/store the relevant answers for a particular question, but 
								 * the answers for all of the questions are all stored in choices in 
								 * sequential order.
								 * 
								 * Use an answerCounter to track the starting index of the answers 
								 * for each question. 
								 */

								System.out.println("Available choices: ");
								for(int k = answerCounter; k < numOfChoices+answerCounter; k++)
								{
									thisQues.addToChoices(choices.item(k).getChildNodes().item(0).getNodeValue());

									// double-check: print the item just added to ArrayList<String> choices 
									System.out.println(thisQues.getChoices().get(thisQues.getChoicesSize()-1));
								}

								answerCounter += numOfChoices;


								/*for (int j = 0; j< choices.getLength(); j++) 
								{	
									//System.out.println("Choice: " + choices.item(j).toString());

									Node nodech = choices.item(i);
									NodeList nodeListch = e.getElementsByTagName("choice");

									if (nodech.getNodeType() == Node.ELEMENT_NODE) {
										System.out.println("Choice: "
												+ nodeListch.item(0).getChildNodes().item(0)
												.getNodeValue());
									}
								}*/

							} // end of if(notSlider)

							else 
							{			
								// if QuesType == slider, need to extract min & max value fields and increment (numDiv) for slider

								//nodeList = e.getElementsByTagName("QuesType");
								//if(nodeList.item(0).getChildNodes().item(0).getNodeValue().equalsIgnoreCase("slider")){
								if(thisQues.getQuesType()==qType.slider) {

									//printElementText(e,"StartNum");

									thisQues.getSlider().setStart(Integer.parseInt(extractElementText(e,"StartNum")));
									System.out.println("starting number: "+ thisQues.getSlider().getStart());

									//printElementText(e,"StartLabel");

									thisQues.getSlider().setStartLabel(extractElementText(e,"StartLabel"));
									System.out.println("starting label: "+ thisQues.getSlider().getStartLabel());

									try {
										//printElementText(e,"MiddleLabel");

										thisQues.getSlider().setMiddleLabel(extractElementText(e,"MiddleLabel"));
										System.out.println("middle label: "+ thisQues.getSlider().getMiddleLabel());
									}
									catch (Exception ml)
									{
										;
									}

									//printElementText(e,"EndNum");

									thisQues.getSlider().setEnd(Integer.parseInt(extractElementText(e,"EndNum")));
									System.out.println("end num: "+ thisQues.getSlider().getEnd());

									//printElementText(e,"EndLabel");

									thisQues.getSlider().setEndLabel(extractElementText(e,"EndLabel"));
									System.out.println("end label: "+ thisQues.getSlider().getEndLabel());

									try {
										//printElementText(e,"NumOfDivisions");

										thisQues.getSlider().setNumDiv(Integer.parseInt(extractElementText(e,"NumOfDivisions")));
										System.out.println("number of divisions: "+thisQues.getSlider().getNumDiv());			
									}
									catch (Exception nd)
									{
										;
									}
								}						
							}

							// for all questions, need to extract DefaultNext, Condition, and NextQuestion fields 

							// DefaultNext is specified for all questions
							//printElementText(e,"DefaultNext");

							thisQues.setDefaultNext(extractElementText(e,"DefaultNext"));
							System.out.println("default next question is: "+thisQues.getDefaultNext());

							// not all questions have a Condition and corresponding NextQuestion
							// (Radio) Buttons Condition, CheckBoxes Condition, Slider Condition
							nodeList = e.getElementsByTagName("Condition");

							//String firstCond = nodeList.item(0).getFirstChild().getNodeValue();
							String condLine = "";
							String condLineTrim = "";

							try {
								//Integer firstCondNum = Integer.parseInt(firstCond.substring(0,1));

								String condString = "";
								String nextQuesString = "";
								qType currentQues = thisQues.getQuesType();

								for(int y = 0; y < nodeList.getLength(); y++)
								{												
									// extract each condition line
									condLine = nodeList.item(y).getChildNodes().item(0).getNodeValue();
									condLineTrim = condLine.trim();

									if(condLine.length() > 4) {
										System.out.println("Condition code: "+condLine);

										//get NextQuestion that corresponds to the condition
										// RadioButtons NextQuestion, CheckBoxes NextQuestion, Slider NextQuestion
										nodeList = e.getElementsByTagName("NextQuestion");
										nextQuesString = nodeList.item(y).getChildNodes().item(0).getNodeValue();

										// parse condition string according to appropriate parsing scheme

										if(currentQues==qType.button)
										{
											System.out.println("button condition");
											thisQues.parseCondition("button", condLine, nextQuesString);

											for(int d = 0; d < thisQues.getButtonCondition().size(); d++)
											{
												System.out.println("these conditions are stored in buttonCondition: "+thisQues.getButtonCondition().get(d).getCond());
												System.out.println("and the next question is: "+thisQues.getButtonCondition().get(d).getNextQues());
											}
										}
										else if(currentQues==qType.check)
										{
											System.out.println("check condition");
											thisQues.parseCondition("check", condLine, nextQuesString);

											for(int j = 0; j < thisQues.getCheckConditions().size(); j++)
											{
												System.out.println("these conditions are stored in checkCondition: "+thisQues.getCheckConditions().get(j).getCond());
												System.out.println("and the next question is: "+thisQues.getCheckConditions().get(j).getNextQues());
											}
										}
										else if(currentQues==qType.slider)
										{
											System.out.println("slider condition");
											thisQues.parseCondition("slider", condLine, nextQuesString);
											System.out.println("these conditions are stored in sliderCondition: ");
											System.out.println("lowerLimit: "+thisQues.getSliderConditions().getLowerLim());
											System.out.println("upperLimit: "+thisQues.getSliderConditions().getUpperLim());
											System.out.println("next question: "+thisQues.getSliderConditions().getNextQues());

										}

										//System.out.println("next question is: "+nextQuesString);

										break;

									} // end of if statement

								//	shared.questions.add(thisQues);
								} // end of for loop


							} // end of try block
							catch (Exception cc)
							{
								System.out.println("this question has no condition codes and no NextQuestion");
								// set values for Conditions to null
							}


						}

					}
				} else {
					System.exit(1);
				}
				is.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	// given an Element e and a String tagName, prints the text
	// of the tag from the Element.
	public void printElementText(Element e, String tagName)
	{
		NodeList nodeList = e.getElementsByTagName(tagName);
		System.out.println(tagName + ": "
				+ nodeList.item(0).getChildNodes().item(0)
				.getNodeValue());
	}

	// given an Element e and a String tagName, returns the text
	// of the tag from the Element as a String.
	public String extractElementText(Element e, String tagName)
	{
		NodeList nodeList = e.getElementsByTagName(tagName);
		return ""+nodeList.item(0).getChildNodes().item(0)
				.getNodeValue();
	}


}



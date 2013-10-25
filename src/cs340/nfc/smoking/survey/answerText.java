/*
 * Implemented by SRUTI BHAGAVATULA
 * 
 * Class to store for each question, the question string and the answer
 * 
 */

package cs340.nfc.smoking.survey;

public class answerText {
	private String question;
	private String answer;
	
	public void setQuestion(String question)
	{
		this.question = question;
	}
	public String getQuestion()
	{
		return this.question;
	}
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	public String getAnswer()
	{
		return answer;
	}
}

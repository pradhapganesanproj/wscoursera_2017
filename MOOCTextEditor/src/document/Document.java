package document;

import java.text.DecimalFormat;
/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/** This is a helper function that returns the number of syllables
	 * in a word.  You should write this and use it in your 
	 * BasicDocument class.
	 * 
	 * You will probably NOT need to add a countWords or a countSentences 
	 * method here.  The reason we put countSyllables here because we'll 
	 * use it again next week when we implement the EfficientDocument class.
	 * 
	 * For reasons of efficiency you should not create Matcher or Pattern 
	 * objects inside this method. Just use a loop to loop through the 
	 * characters in the string and write your own logic for counting 
	 * syllables.
	 * 
	 * @param word  The word to count the syllables in
	 * @return The number of syllables in the given word, according to 
	 * this rule: Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 *       You should consider y a vowel.
	 */
	protected int countSyllables(String word)
	{
		// TODO: Implement this method so that you can call it from the 
	    // getNumSyllables method in BasicDocument (module 2) and 
	    // EfficientDocument (module 3).
		if(null == word || word.length()==0){
			return 0;
		}
		 int countSullables = 0;
		 // I just gave a try using regEx - just learning purpose
		 //countSullables = countUsingRegEx(word.toLowerCase());
		 
		 word = word.toLowerCase();

		 //if the given word endswith e then remove the e and count syllable later consider e.
		 String wordTmp = word.endsWith("e")?word.substring(0, word.length() > 1 ? word.length() - 1 : 0):word;

		 boolean conseq = false;		
		 for(char chr : wordTmp.toCharArray()){
			 if("aeiouy".indexOf(chr) > -1){
				 if(!conseq){
					 countSullables = countSullables+1;
					 conseq = true;
				 }
			 }else{
				 conseq = false;
			 }
		 }

		 //already removed e and counted syllable; consider words ends with only one e
		 if(word.endsWith("e")) {
			 if(countSullables==0){
				 countSullables=1;
			 }
		 }

	    return countSullables;
	}

	private int countUsingRegEx(String word) {
		int countSullables;
		if(word.endsWith("e")){
			 String tmpWord = word.substring(0, word.length() > 1 ? word.length() - 1 : 0);
			 countSullables = getTokens(tmpWord,"[aeiouy]+").size();
			 if(countSullables == 0 ){
				 countSullables = countSullables + 1;
			 }
		 }else{
			 countSullables = getTokens(word,"[aeiouy]+").size();
		 }
		return countSullables;
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
	    // TODO: You will play with this method in week 1, and 
		// then implement it in week 2
		
		double numWords = getNumWords();
		double numSentences = getNumSentences();
		double numSyllables = getNumSyllables();
		
		double getFleshScore = 206.835 - 1.015 * (numWords/numSentences) - 84.6 * (numSyllables/numWords);
		
		getFleshScore = tuncToOneDecimal(getFleshScore);
		
	    return getFleshScore;
	}

	private double tuncToOneDecimal(double getFleshScore) {
		try {
			DecimalFormat df = new DecimalFormat("#.#");      
			getFleshScore = Double.valueOf(df.format(getFleshScore));
		} catch (NumberFormatException e) {	}
		return getFleshScore;
	}
	//Overloaded
	private List<String> getTokens(String text, String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}	
	
}

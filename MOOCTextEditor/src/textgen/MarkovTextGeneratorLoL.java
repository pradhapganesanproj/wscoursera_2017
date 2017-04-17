package textgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if(null == wordList || null == sourceText ) throw new NullPointerException("encounter Null");
		
		List<String> wordStrList = getWordStrList(sourceText);
		int wordStrListSz = wordStrList.size();
		for(int index=0; index < wordStrList.size(); index++){
			
			final String currWord = wordStrList.get(index);
			final String nextWord = index+1<wordStrListSz?wordStrList.get(index+1):null;
		
			ListNode currListNode = getListNodeForWord(currWord);
			
			if(null != currListNode && null != nextWord){
				currListNode.addNextWord(nextWord);
			}else{
				ListNode listNode = new ListNode(currWord);
				if(null != nextWord){
					listNode.addNextWord(nextWord);
				}
				wordList.add(listNode);
			}
		}
		this.starter = null!=wordList&&wordList.size()>0?wordList.get(0).getWord():""; 
	}

	private ListNode getListNodeForWord(String word){
		if(null == word){	return null; }
		ListNode listNode = null;
		Optional<ListNode> optListNode = wordList.stream().filter(lNode -> word.equals(lNode.getWord())).findFirst();
		listNode = optListNode.isPresent()?optListNode.get():null;
		return listNode;
	}
	private List<String> getWordStrList(String sourceText) {
		List<String> wordStrList = new ArrayList<String>();
		/*Pattern pattern = Pattern.compile("[a-zA-Z']+");
		Matcher m = pattern.matcher(sourceText);
		
		while (m.find()) {
			wordStrList.add(m.group());
		}*/
		wordStrList = Arrays.asList(sourceText.split("[,.?! ]+"));
		return wordStrList;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    String generateText = "";
	    
	    validateInput(numWords);
	    if(wordList.size()==0){
	    	return "";
	    }

	    StringBuilder strBuild = new StringBuilder();
	    int count = 0;
	    String currWord = null;
	    
	    while(count < numWords){
	    	if(currWord==null || "".equals(currWord)){
	    		currWord = starter;
	    	}
	    	strBuild.append(currWord+" ");
	    	ListNode currListNode = getListNodeForWord(currWord);
	    	if(null == currListNode){
	    		return "";
	    	}
	    	currWord = currListNode.getRandomNextWord(rnGenerator);
	    	count++;
	    }
	    generateText = strBuild.toString();
		return generateText;
	}


	private void validateInput(int numWords) {
		if(numWords<0 || wordList == null){
	    	throw new NullPointerException("Could not process..");
	    }
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList = new LinkedList<ListNode>();
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		String genStr = gen.generateText(20);
		System.out.println(genStr);
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		if(null == nextWords || nextWords.size() == 0){
			return "";
		}
		
	    return getRandomNextWordImpl(generator);
	}

	private String getRandomNextWordImpl(Random generator) {
		String randomNextWord="";
		int wordListSz = nextWords.size();
		int randomIndex = generator.nextInt(nextWords.size());
		randomIndex = randomIndex > wordListSz ? randomIndex % wordListSz : randomIndex;
		randomNextWord = nextWords.get(randomIndex);
		if("".equals(randomNextWord)){
			randomNextWord = getRandomNextWord(generator);
		}
		return randomNextWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}



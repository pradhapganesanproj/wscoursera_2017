package spelling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Pradhap Ganesan
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		validateInput(word);
		
		boolean addWord = addWordImpl(word.toLowerCase(),0,root);
		size = addWord?size+1:size;
	    return addWord;
	}


	private void validateInput(String word) {
		if(null == word){
			throw new NullPointerException("Invalid input");
		}
	}
	
	private boolean addWordImpl(String word, int index, TrieNode currTrieNode){
		if (index < 0 || index == word.length()) {
			if (null != currTrieNode && false == currTrieNode.endsWord()) {
				currTrieNode.setEndsWord(true);
				return true;
			}else{
				return false;
			}
		}
		
		if(null == currTrieNode.getChild(word.charAt(index))){
			currTrieNode = currTrieNode.insert(word.charAt(index));
		}else{
			currTrieNode = currTrieNode.getChild(word.charAt(index));
		}

		return addWordImpl(word,index+1,currTrieNode);
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		validateInput(s);
		return isWordImpl(s.toLowerCase(),0,root);
	}
	
	private boolean isWordImpl(String word, int index, TrieNode currTrieNode){
		if(index == word.length()){
			if(null != currTrieNode){
				return currTrieNode.endsWord();
			}
			return false;
		}
		if( (currTrieNode = currTrieNode.getChild(word.charAt(index))) != null){
			return isWordImpl(word,index+1,currTrieNode);
		}else{
			return false;
		}
	}
	
	private TrieNode getPrefixWord(String word, int index, TrieNode currTrieNode){
		if(null == word){
			return null;
		}
		if(index == word.length()){
			return currTrieNode;
		}
		if( (currTrieNode = currTrieNode.getChild(word.charAt(index))) != null){
			return getPrefixWord(word,index+1,currTrieNode);
		}else{
			return null;
		}
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 List<String> predictCompletionList = new ArrayList<String>(0);
    	 TrieNode prefixTrieNode = this.getPrefixWord(prefix,0,this.root);
    	 if(prefixTrieNode == null || numCompletions==0){
    		 return predictCompletionList;
    	 }

    	 List<TrieNode> bfsTrieNodeQueue = new LinkedList<TrieNode>();
    	 int count = numCompletions;
    	 enQueue(prefixTrieNode, bfsTrieNodeQueue);
    	 
    	 if(null != prefixTrieNode && prefixTrieNode.endsWord()){
			 predictCompletionList.add(prefixTrieNode.getText());
			 count--;
		 }
    	 
     	 while(!bfsTrieNodeQueue.isEmpty() && count!=0){
    		 
    		 TrieNode trieNode = bfsTrieNodeQueue.remove(0);
    		 
    		 if(null != trieNode && trieNode.endsWord()){
    			 predictCompletionList.add(trieNode.getText());
    			 count--;
    		 }
    		 enQueue(trieNode, bfsTrieNodeQueue);	 
    	 }
    	 
         return predictCompletionList;
     }


	private void enQueue(TrieNode prefixTrieNode, List<TrieNode> bfsTrieNodeQueue) {
		Set<Character> nextChars = prefixTrieNode.getValidNextCharacters();
    	 Iterator<Character> charsIter = nextChars.iterator();
    	 while(charsIter.hasNext()){
    		 char chr = charsIter.next();
    		 bfsTrieNodeQueue.add(prefixTrieNode.getChild(chr));
    	 }
	}

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}
package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict;
	
    // TODO: Implement the dictionary interface using a TreeSet.  
 	// You'll need a constructor here
   public DictionaryBST() {
	   dict = new TreeSet<String>();
   }
	
    
    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	if(!isValidateInput(word)){return false;}
    	word = word.toLowerCase();
    	if(!isWordExist(word)){
    		dict.add(word);
    		return true;
    	}
    	
        return false;
    }


    private boolean isWordExist(final String word) {
    	//Optional<String> filtOpt = dict.stream().filter(wrd -> wrd.equals(word)).findAny();
    	//return filtOpt.isPresent();
    	return dict.contains(word);
	}


	private boolean isValidateInput(String word) {
		return !(null == word || "".equals(word));
	}


	/** Return the number of words in the dictionary */
    public int size()
    {
        return null==dict?0:dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
    	if(!isValidateInput(s)){return false;}
    	return isWordExist(s.toLowerCase());
    }

}

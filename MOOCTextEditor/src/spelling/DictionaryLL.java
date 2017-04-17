package spelling;

import java.util.LinkedList;
import java.util.Optional;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	
    public DictionaryLL() {
    	dict = new LinkedList<String>();
    }


    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	
    	if(!isValidateWord(word))return false;
    	
    	word = word.toLowerCase();
    	
        if(!isWordExists(word)){
        	dict.add(word);
        	return true;
        }
        
    	return false;
    }


	private boolean isWordExists(final String word) {
       /* Optional<String> filtOpt = dict.stream().filter(w -> w.equals(word)).findAny();
        return filtOpt.isPresent();*/
		return dict.contains(word);
	}


	private boolean isValidateWord(String word) {
		if(null == word || "".equals(word)){
        	return false;
        }
        	return true;
	}


    /** Return the number of words in the dictionary */
    public int size()
    {
        return dict==null?0:dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
    	if(!isValidateWord(s))return false;
        return isWordExists(s.toLowerCase());
    }

    
}

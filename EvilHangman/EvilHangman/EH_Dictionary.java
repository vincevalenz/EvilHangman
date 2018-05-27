import java.util.Arrays;
import java.util.ArrayList;

/**
 * EH_Dictionary is a customized dictionary ADT for the game Evil Hangman. 
 * It can only accept an ArrayList<String> as a Key and an ArrayList<ArrayList<String>> as a Value
 * 
 * @author Vincent Valenzuela 
 * @version 5/21/18
 */
public class EH_Dictionary
{
    private ArrayList<String> keys;
    private ArrayList<ArrayList<String>> words;
    
    /**
     * Constructor for EH_Dictionary
     * Set keys and words to new ArrayLists
     */
    public EH_Dictionary()
    {
        keys = new ArrayList<String>();
        words = new ArrayList<ArrayList<String>>();
    }
    
    /**
     * Add a key and associated ArrayList of words that match the key
     * Replace old list if key already exists
     * @param String New key
     * @param ArrayList<String> List of words that match the key
     */
    public void add(String key, ArrayList<String> newList)
    {
        if (keys.contains(key)){
            // Replace old list associated with key
            int index = keys.indexOf(key);
            words.add(index, newList);
        }
        else {
            // Add new key and list
            keys.add(key);
            words.add(newList);
        }
    }
    
    /**
     * Return an ArrayList associated with a given key
     * @param String Associated key
     * @return ArrayList<String>
     */
    public ArrayList<String> getValue(String key)  
    {
        if (keys.contains(key)){
            return words.get(keys.indexOf(key));
        }
        else {
            return null;
        }          
    }
    
    /**
     * Return a key associated with the given ArrayList
     * @param ArrayList<String> List of associated words
     * @return String 
     */
    public String getKey(ArrayList<String> list)  
    {
        if (words.contains(list)){
            return keys.get(words.indexOf(list));
        }
        else {
            return null;
        }          
    }
    
    /**
     * Appends a new word to the list of the associated key
     * @param String the associated key
     * @param String the word to be appended
     * @return boolean
     */
    public boolean appendValue(String key, String newWord)
    {
        if (keys.contains(key)){
            int index = keys.indexOf(key);
            words.get(index).add(newWord);
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * Iterates through the entire dictionary to find the key with the largest associated ArrayList and returns that key.
     * @return String
     */
    public String largestGroupsKey()
    {
        int size = 0;
        int index = 0;
        for (int i=0;i<words.size();i++){
            if (words.get(i).size() >= size){
                index = i;
                size = words.get(i).size();
            }
        }
        if (!keys.get(index).contains("-") && keys.size() == 2){
            keys.remove(index);
            index = 0;
        }
        return keys.get(index);
    }
    
    /**
     * Clear the dictionary
     */
    public void clear()
    {
        keys.clear();
        words.clear();
    }
    
    /**
     * Check if the given key is in the dictionary
     * @param String Key to be checked
     */
    public boolean contains(String key)
    {
        return keys.contains(key);
    }
    
    /**
     * Returns the size of the words list
     * @param String Associated key
     */
    public int getListSize(String key)
    {
        int index = keys.indexOf(key);
        return words.get(index).size();
    }
    
    /**
     * Check if dictionary is empty
     */
    public boolean isEmpty()
    {
        return keys.size() == 0;
    }
    
    public int getSize()
    {
        return keys.size();
    }
    
    /**
     * Prints key and associated array
     */
    public void print()
    {
        for (int i=0;i<keys.size();i++){
            System.out.println(keys.get(i));
            System.out.println(words.get(i).toString());
        }
    }
}

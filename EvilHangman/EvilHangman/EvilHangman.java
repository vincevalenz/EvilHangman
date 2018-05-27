import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Evil Hangman doesn't choose a word to be guessed. 
 * The program reduces an ArrayList of words until the player runs
 * out of guesses and loses or the list is reduced to one word and the player wins.
 * 
 * @author Vincent Valenzuela
 * @version 5/21/2018
 */
public class EvilHangman
{
    public static void main(String[] args){
       // Determine if player wants to keep playing
       while (EvilHangmanGame());
    }
   
    /**
     * The Evil Hangman Game. 
     * Returns a boolean to determine if player wants to repeat the game or quit.
     * @return boolean
     */
    public static boolean EvilHangmanGame()
    {
       EH_Dictionary categories = new EH_Dictionary();              // Dictionary for all combinations of keys and corresponding words that match
   
       ArrayList<String> words = new ArrayList<String>();           // ArrayList to hold all words that fit the pattern
       
       ArrayList<Character> guesses = new ArrayList<Character>();   // ArrayList to hold all players guesses
       
       ArrayList<Slide> hangman = new ArrayList<Slide>();           // ArrayList for each slide of hangman ASCII art
       
       File dict = new File("dictionary.txt"),                      // Files for the dictionary and hangman art
            hang = new File("hangmanslides.txt");
       Slide currSlide;                                             // Slide object for loading ArrayList<Slide>
       Scanner inputFile, keyboard = new Scanner(System.in);        // Scanner for files and user input
       String matches = "",                                         // "matches" to display user matches
              oldMatch = "",                                        // "oldMatch" for comparing
              key = "",                                             // "key" used for generating a patter
              yn = "";                                              // "yn" for determining if the players wants to view the number of words left in the list
       char guess;                                                  // Character to store users guess
       boolean validLength = false,                                 // Booleans for determining win, loss, valid word length, 
               displayListSize = false,                             // and if the player wants to view the list size
               won = false, 
               lost = false,
               keepPlaying = false;
       int length = 0,                                              // Integer to store the length of a word (difficulty)
           chances = 0,                                             // Number of chances user has left
           slideNum = 0;                                            // Keep track of which slide to print
           
       /*************************************** OPENNING SCREEN *****************************************/
           
       System.out.print("\f"+
       "  ________      _______ _        _    _          _   _  _____ __  __          _   _ \n"+
       " |  ____\\ \\    / /_   _| |      | |  | |   /\\   | \\ | |/ ____|  \\/  |   /\\   | \\ | | \n"+
       " | |__   \\ \\  / /  | | | |      | |__| |  /  \\  |  \\| | |  __| \\  / |  /  \\  |  \\| | \n"+
       " |  __|   \\ \\/ /   | | | |      |  __  | / /\\ \\ | . ` | | |_ | |\\/| | / /\\ \\ | . ` | \n"+
       " | |____   \\  /   _| |_| |____  | |  | |/ ____ \\| |\\  | |__| | |  | |/ ____ \\| |\\  | \n"+
       " |______|   \\/   |_____|______| |_|  |_/_/    \\_\\_| \\_|\\_____|_|  |_/_/    \\_\\_| \\_| \n");
       /*************************************************************************************************/
       
       
       /******************************* LOAD ARRAY WITH WORDS OF LENGTH *********************************/
       
       try{
           while (!validLength){
              // Set dictionary.txt to inputFile
              inputFile = new Scanner(dict);
              // Ask user for word length (difficulty)
              System.out.println("Choose a word length");
              length = keyboard.nextInt();
              // Put all words of length in words ArrayList
              while (inputFile.hasNext()){
                  String temp = inputFile.next();
                  if (length == temp.length()){
                      words.add(temp);
                  }
              }
              if (words.size() > 0) validLength = true;
              else System.out.println("Please choose another word length");
           }
           // If the word lenght exists, ask player for number of chances. Else ask for another length
           System.out.println("How many chances do you want?");
           chances = keyboard.nextInt();
           while (chances <= 0){
               System.out.println("Please choose a number greater than 0");
               chances = keyboard.nextInt();
           }
           // Ask user if they would like to the number or words left in the list
           System.out.println("Would you like to view the remaining number of words in the list? (y/n)");
           yn = keyboard.next();
           while (true){
               if (Character.toLowerCase(yn.charAt(0)) == 'y'){
                   displayListSize = true;
                   break;
               }
               else if (Character.toLowerCase(yn.charAt(0)) == 'n'){
                   break;
               }
               else {
                   System.out.println("Please choose \"y\" or \"n\"");
                   yn = keyboard.next();
               }
           }
       }
       catch (FileNotFoundException e){
               System.out.println("dictionary.txt needs to be in the folder");
               return false;
       }
       /********************************************************************************************************/
       
       // Set matches to a blank string of "-"
       for (int i=0;i<length;i++) matches += "-";
       
       /************************************** LOAD HANGMAN SLIDES ********************************************/
       
       try{
           inputFile = new Scanner(hang);
           currSlide = new Slide();
           while (currSlide.read(inputFile)){
               hangman.add(currSlide);
               currSlide = new Slide();
            }
       }
       catch (FileNotFoundException e){
           System.out.println("hangmanslides.txt needs to be in the folder");
           return false;
       }
       /********************************************************************************************************/
       
       /*************************************** EVIL HANGMAN LOOP **********************************************/
       
       while (!won && !lost){
           // Clear the screen
           System.out.print("\f");
           // Print slide, matches, and gusses to the screen 
           hangman.get(slideNum).display();
           System.out.println(matches);
           if (displayListSize) System.out.println("Words left: "+words.toString());
           System.out.println("Chances left: "+chances+"\n"+guesses.toString());
           // Extract char from players guess and add to guesses list
           System.out.println("\nMake a guess");
           guess = keyboard.next().charAt(0);
           guesses.add(guess);
           // Create new keys and array lists for players guess
           for (String word : words){
               key = pattern(word, matches, guess);
               if (categories.contains(key)){
                   categories.appendValue(key, word);
               }
               else {
                   categories.add(key, new ArrayList<>(Arrays.asList(word)));
               }
           }
           // Set variables to new values
           oldMatch = matches;
           matches = categories.largestGroupsKey();
           words = categories.getValue(matches);
           // Progress slide and number of chances
           if (oldMatch.equals(matches)) chances--;
           if (slideNum < hangman.size()-1 && oldMatch.equals(matches)) slideNum++;
           // Check to see if user won or lost
           if (!matches.contains("-")) won = true;
           if (chances == 0) lost = true;
           // Clear the dictionary before starting over
           categories.clear();
       }
       /********************************************************************************************************/
       
       /************************************** DISPLAY WIN OR LOSS *********************************************/
       // Print winning display
       if (won){
           System.out.print("\f");
           hangman.get(slideNum).display();
           System.out.println(matches);
           System.out.println("Chances Remaining: "+chances+"\n"+guesses.toString());
           System.out.println("\nCongratulations! The word was "+matches.toUpperCase());
       }
       // Print losing display
       if (lost){
           System.out.print("\f");
           hangman.get(slideNum).display();
           System.out.println(matches);
           System.out.println("Chances Remaining: "+chances+"\n"+guesses.toString());
           System.out.println("\nThe word was "+words.get(0).toUpperCase()+". Better luck next time :(");  // Grabs first word from the ArrayList as the missed word
       }
       
       // Ask player if they would like to keep playing
       System.out.println("\nWould you like to keep playing? (y/n)");  
       while (true){
           yn = keyboard.next();
           if (Character.toLowerCase(yn.charAt(0)) == 'y'){
               return true;
           }
           else if (Character.toLowerCase(yn.charAt(0)) == 'n'){
               return false;
           }
           else{
               System.out.println("Please choose \"y\" or \"n\"");
           }
       }
    }
   
    /**
    * Pattern definition method. Takes a word from the current list 
    * to be checked for the guess and compare agaisnt current matches
    * 
    * @param String Word to be checked
    * @param String Pattern to be compared against
    * @param char Players guess
    * @return String
    */
    public static String pattern(String word, String match, char guess)
    {
       // Create an empty String "key" to return
       String key = "";
       // Iterate through each char in word and compare it to the players guess
       for (int i=0;i<word.length();i++){
           // Check if char at i of word equals guess
           if (word.charAt(i) == guess){
               key += guess;            // Add guess to the key 
           }
           else {
               key += match.charAt(i);  // Copy match char into key
           }
       }
       return key;
    }
}

import java.util.Scanner;

/**
 * Slides for hangman art progression. 
 * An Array of type String to store each line of text in the Slide.
 *
 * @author Vincent Valenzuela
 * @version 5/21/18
 */
public class Slide
{
    static final int SLIDE_HEIGHT = 13;   
    private String [] image = new String [SLIDE_HEIGHT];
    
    public boolean read (Scanner inputFile) 
    {
        try{ 
            for (int k=0;k<image.length;k++) image[k] = inputFile.nextLine();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void display()                 
    {
        for (int i=0;i<image.length;i++) System.out.println(image[i]);
    }
}

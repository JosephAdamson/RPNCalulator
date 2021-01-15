import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Joseph Adamson
 * @version June 2020
 * 
 * Minimal class for handling the calculator's 'memory'
 * (ability to store previous answer), in order to make it
 * persistent.
 */
public class MiniMemEdit {

    /**
     * Write the result parameter to a simple .txt file for
     * later use (retrieved using ANS)
     * 
     * @param result: last computed expression.
     */
    public static void store(String result) {
        try {
            PrintWriter output = 
                    new PrintWriter(System.getProperty("user.dir") + "/src/MiniMem.txt");
            output.println(result);
            output.close();
        } catch (FileNotFoundException e) {
            System.err.println("Sorry, memory file cannot be found");
        }
    }

    /**
     * Retrieve the result of the previously computed expression.
     * 
     * @return value stored in MiniMem.txt
     */
    public static String retrieve() {
        
        // If nothing is logged an empty string is returned.
        String result = "";
        try {
            Scanner scan = 
                    new Scanner(new File(System.getProperty("user.dir") + "/src/MiniMem.txt"));
            result = scan.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println("Sorry, memory file cannot be found");
        }
        return result;
    }
}

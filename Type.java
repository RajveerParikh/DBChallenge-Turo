import java.io.*;
import java.util.*;

/**
 * This class is the main class that runs the program. It checks first if there is a file that has been passed as an argument. 
 * If so, it will read this entire file and display the input as appropriate. If not, it will wait for input from the user.
 * The program ends, when the line read from the file or user input is "END". This class does no do anything else.
 * @author Rajveer
 *
 */

public class Type {
	   public static void main (String [] args) throws java.io.IOException{
		   BufferedReader br;
		   try{
			   if (args.length > 0){
				   FileReader fr = new FileReader(args[0]);
				   br = new BufferedReader (fr);
			   }
			   else{
				   br = new BufferedReader(new InputStreamReader(System.in));
			   }
			   String input;
			   DBChallenge x = new DBChallenge();	
			   while(!(input=br.readLine()).equals("END")){
				   String[] inputArr = input.split(" ");
				   x.getCommand(inputArr);
				   }			   
		   } catch (IOException io){
				io.printStackTrace();
		   }

		   }
}

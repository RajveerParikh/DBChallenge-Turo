import java.util.*;

/**
 * This class is the actual class that does all the work. The main method in class Type, creates an object of this class and uses that to access its
 * different methods. The class will call an appropriate method depending on the type of command that has been sent to this class.
 * It uses a Stack<HashMap<String, String>> as the data structure which stores various stacks of variable and value mappings. 
 * Each stack represents one transaction block.
 * @author Rajveer Parikh
 *
 */

public class DBChallenge {
	public Stack<HashMap<String, String>> varMapStack = new Stack<HashMap<String, String>>(); 	//Stack of HashMaps to store each transaction blocks values
	static int beginCount = 0; // This variable is used to check if the number of times the BEGIN command has been executed. This is because the if the first BEGIN (according to the examples) comes after a SET command, it stays in the same transaction block
	static int anyCommit = 0; // This variable is used to check if any final commits have been made. According to the examples, if a SET command is the first command, the value is automatically committed, however if the first command is BEGIN, it needs an explicit COMMIT
	/**
	 * This is the method that the Type class main method calls.
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable) 
	 */
	public void getCommand (String[] input){
		String input1 = "SET";
		String input2 = "GET";
		String input3 = "UNSET";
		String input4 = "NUMEQUALTO";
		String input5 = "BEGIN";
		String input6 = "ROLLBACK";
		String input7 = "COMMIT";
		if (input[0].equals(input1) && anyCommit == 0){	// Checks if SET command has come before the first BEGIN command
			anyCommit = 1;
			setInput(input);
		}
		else if (input[0].equals(input1)){
			setInput(input);
		}
		else if (input[0].equals(input2)){
			getInput(input);
		}
		else if(input[0].equals(input3)){
			unsetInput(input);
		}
		else if(input[0].equals(input4)){
			numEqualto(input);
		}
		else if(input[0].equals(input5) && anyCommit != 1){	// Checks to see if the BEGIN command has come before the first SET command
			anyCommit = 2;
			beginCount++;
			beginInput(input, beginCount);
		}
		else if(input[0].equals(input5)){
			beginCount++;
			beginInput(input, beginCount);
		}
		else if(input[0].equals(input6)){
			rollbackInput(input);
		}
		else if(input[0].equals(input7)){
			beginCount = commitInput(input, beginCount);
		}
	}
	
	/**
	 * This method deals with the SET command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 */
	public void setInput(String[] input){
		String name = input[1];
		String val = input[2];
		if (varMapStack.isEmpty()){
			HashMap<String, String> varMap = new HashMap<String, String>();
			varMap.put(name, val);
			varMapStack.push(varMap);	
		}
		else{
			HashMap<String, String> tempVarMap = varMapStack.pop();
			tempVarMap.put(name, val);
			varMapStack.push(tempVarMap);
		}
		
	}
	
	/**
	 * This method deals with the GET command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 */
	public void getInput(String[] input){
		String name = input[1];
		String result = null;
		if (varMapStack.isEmpty()){
			System.out.println(result);
		}
		else{
			HashMap<String, String> tempVarMap = varMapStack.pop();
			if (!tempVarMap.containsKey(name)){
				tempVarMap.put(name, null);
			}
			result = tempVarMap.get(name);
			System.out.println(result);
			varMapStack.push(tempVarMap);
		}
	}
	
	/**
	 * This method deals with the UNSET command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 */
	public void unsetInput(String[] input){
		String name = input[1];
		HashMap<String, String> tempVarMap = varMapStack.pop();
		tempVarMap.put(name, null);
		varMapStack.push(tempVarMap);
	}
	
	/**
	 * This method deals with the NUMEQUALTO command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 */
	public void numEqualto(String[] input){
		String val = input[1];
		int count = 0;
		HashMap<String, String> tempVarMap = varMapStack.peek();
		for (String key: tempVarMap.keySet()){
			if (tempVarMap.get(key) != null){
				if (tempVarMap.get(key).equals(val)){
					count++;
				}				
			}
		}
		System.out.println(count);
	}
	
	/**
	 * This method deals with the BEGIN command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 * @param numBegin To see how many times the BEGIN command has been executed. The first BEGIN does not create a new HashMap until it is committed
	 */
	public void beginInput(String[] input, int numBegin){
		if (numBegin > 1){
			HashMap<String, String> newMap = new HashMap<String, String>(varMapStack.peek());
			varMapStack.push(newMap);
		}
	}
	
	/**
	 * This method deals with the ROLLBACK command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 */
	public void rollbackInput(String[] input){
		if (varMapStack.size() <= 1 && anyCommit != 2){
			System.out.println("NO TRANSACTION \n");
		}
		else{
			varMapStack.pop();
		}
	}
	
	/**
	 * This method deals with the COMMIT command
	 * @param input It is one line of commands from the file/user input being executed. It uses an array to separate the command and the value (if applicable)
	 * @param numBegin This is used to keep track of the number of transaction blocks before and after the COMMIT
	 * @return numBegin This is used to keep track of the number of transaction blocks remaining
	 */
	public int commitInput(String[] input, int numBegin){
		if (anyCommit == 2){	//Checks if the first command entered was BEGIN and will make sure the value still gets saved permanently now
			anyCommit = 1;
		}
		if (varMapStack.size() > 1){
			HashMap<String, String> toCommit = varMapStack.pop();
			HashMap<String, String> toChange = varMapStack.pop();
			for (String key: toCommit.keySet()){
				String val = toCommit.get(key);
				toChange.put(key, val);
			}
			varMapStack.push(toChange);
			numBegin--;
		}
		return numBegin;
	}
	
}

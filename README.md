Rajveer Parikh Read Me File for Thumbtack's Simple Database Challenge


->Objective
The objective behind this was to create an in-memory database similar to Redis which receives commands from standard input and displays results to standard output. It should be capable to implementing the following commands:
1. SET name value - Set the variable name to the value 'value'.
2. GET name - Print out the value of the variable name, or NULL if that variable is not set.
3. UNSET name – Unset the variable name, making it just like that variable was never set.
4. NUMEQUALTO value – Print out the number of variables that are currently set to value. If no variables equal that value, print 0.
5. BEGIN – Open a new transaction block. Transaction blocks can be nested; a BEGIN can be issued inside of an existing block.
6. ROLLBACK – Undo all of the commands issued in the most recent transaction block, and close the block. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
7. COMMIT – Close all open transaction blocks, permanently applying the changes made in them. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
8. END - Exit the program. Your program will always receive this as its last command.


->Files
Type.java - Contains the main method which reads user input (through input file or user input).
DBChallenge.java - Contains all the methods including the method which gets the input from the main method in Type.java, parses it and calls the appropriate method.

-> Thought process
The thought process behind my algorithm was to be able to set and get the variables as easily as possible. Since a variable can only have one value at a time, it made sense to use a HashMap to store the most up to date value (or the value of that transaction block) for each variable. Additionally, I used a stack of these HashMaps to simulate a transaction block. As a new transaction block is created, it adds to the stack and thus accessible in O(1). As a transaction block commits, it is popped out of the stack and the values of all its variables are stored in the transaction block that encloses it. The only process that needs to iterate through all the keys in the HashMap is the NUMEQUALTO command. To see how many variables have a given value, we must iterate through all the variables (keys) in that transaction block.

-> How to run
This program can be run just like any other java program from the command line. Navigate to the directory which has the files. Please ensure that both Type.java and DBChallenge.java are in the same directory. Use the following command to compile the program:
javac Type.java   // This will compile all necessary files
To run the program, you can do either one of two things:
1. To run program with a file, use java Type nameOfFile.txt		// Where nameOfFle is your file with the commands. Please ensure it is also in the same directory as the rest of the files. The program will stop when it reads the line with the "END" command.
2. To run the program interactively, simply type java type 		// Type each command as desired and press enter at the end of the command. Enter "END" to end the program.

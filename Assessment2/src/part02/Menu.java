package part02;

import java.util.Scanner;

/**
 * This class represents a console-based menu
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class Menu {
	private String options[];	// array of strings representing user options
	private String title;		// menu title
	private Scanner input;		// for KB input
	
	/**
	 * Constructor for class
	 * @param title - the menu title
	 * @param options - the options for user selection
	 */
	public Menu(String title, String[] options) {
		this.title = title;
		this.options = options;
		copyOptions(options);
		input = new Scanner(System.in);
	}
	
	/**
	 * Requests and read a user choice
	 * @return - the user choice
	 */
	public int getChoice() {
		display();
		int choice;
		System.out.print("Enter choice (number): ");
		try {choice = input.nextInt();
		input.nextLine();}
		
		//catches input mismatch exception if user enters a non integer value. Returns nonsense choice.
		catch (Exception InputMismatchException) {
			choice = 91929394;
			input.nextLine();
		}
		return choice;
	}
	
	/**
	 * displays the menu title followed by the user
	 * option for selection
	 */
	private void display() {
		if (title != null && options !=null) {
			// title and options are valid
			// display title and underline with '+'
			System.out.println("\n" + title);
			for(int i=0;i<title.length();i++) {
				System.out.print("+");
			}
			System.out.println();
			// display the menu options
			// prefix each with an int starting at 1
			int count = 1;
			for(String str : options) {
				System.out.println(count+". "+str);
				count++;
			}
			System.out.println();
		}
		else {
			// title and/or options are not valid
			System.out.println("Menu not defined.");
		}
	}
	
	/**
	 * will initialise the options array by copying
	 * contents of data
	 * @param data - array of Strings - options for user to select from
	 */
	private void copyOptions(String data[]) {
		if ( data != null) {
			options = new String[data.length];
			for(int index=0;index<data.length;index++) {
				options[index] = data[index];
			}
		}
		else {
			options = null;
		}
	}
	
}
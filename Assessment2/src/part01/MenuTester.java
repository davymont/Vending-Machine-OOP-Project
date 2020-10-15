package part01;
/**
 * Class to test basic functionality of Menu
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class MenuTester {
	
	public static void main(String[] args) {
		
		testMenu();
	}

	/**
	 * test that menu options display correctly and reads user input from scanner
	 * Test Case: Menu, Menu2
	 */
	public static void testMenu() {

		System.out.println("Testing menu display & getChoice()");
		System.out.println("----------------------------\n");

		//creating a new menu with 2 options
		String[] options = {"Option 1", "Option 2"};
		String title = "Test Menu Title";
		Menu testMenu = new Menu(title, options);
		
		System.out.println("* Expected: menu title = Test Menu Title, menu options = 1) Option 1, 2) Option 2 *");
		System.out.println("* Expected: program waits for user input (1) *");
		System.out.println("* Expected: program returns user input (1) *");
		//displaying menu and getting choice that user has entered
		System.out.println(testMenu.getChoice());
		
		System.out.println("* Expected: menu title = Test Menu Title, menu options = 1) Option 1, 2) Option 2 *");
		System.out.println("* Expected: program waits for user input (x) *");
		System.out.println("* Expected: program returns nonsense value (91929394) that can be processed by application to alert user that there has been an error *");
		//displaying menu and getting choice that user has entered
		System.out.println(testMenu.getChoice());
	}
	
}


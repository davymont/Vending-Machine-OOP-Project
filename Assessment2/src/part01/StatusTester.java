package part01;

/**
 * Class to test basic functionality of Status enum
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class StatusTester {

	public static void main(String[] args) {
		
		testStat();
	}
	
	/**
	 * Test that Status enum class works, and vending machine status can be set to VENDING MODE or SERVICE MODE and switched between the two.
	 * Test Cases: Status1, Status2, Status3
	 */
	public static void testStat() {
		
		System.out.println("Testing Enum class Status");
		System.out.println("-------------------------\n");

		// initiate Status testStat and give value
		System.out.println("\n* Initiate Status to Service Mode *");
		Status testStat = Status.SERVICE_MODE;
		// display Status
		System.out.print("* Expected: (Status: SERVICE MODE) * ");
		print(testStat);
	
		System.out.println("\n* Change Status to Vending Mode *");
		testStat = Status.VENDING_MODE;
		System.out.print("* Expected: (Status: VENDING MODE) * ");
		// display Status
		print(testStat);
		
		System.out.println("\n* Change Status to Service Mode *");
		testStat = Status.SERVICE_MODE;
		System.out.print("* Expected: (Status: SERVICE MODE) * ");
		// display Status
		print(testStat);
		
		System.out.println("\nEnd of testing Enum class Status.\n");
	}
	
	/**
	 * method to display current status in console
	 * @param stat current status
	 */
	private static void print(Status stat) {
		System.out.println("("+stat.toString()+")");
	}

}

package part01;

/**
 * Class to test basic functionality of VendItem
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class VendItemTester {

	static VendItem snickers = new VendItem("Snickers", 0.65, 6);
	static VendItem mars = new VendItem("Mars Bar", 0.65, 8);
	static VendItem bounty = new VendItem("Bounty", 0.75, 3);
	static VendItem tracker = new VendItem("Tracker", 0.85, 5);

	public static void main(String[] args) {
		testGetters();
		testForId();
		testRestock();
		testDeliver();
		testDecrement();
	}

	/**
	 * Test for getters - grouped in one method 
	 * Test Cases: Name, Price, Qty
	 */
	public static void testGetters() {
		System.out.println("Testing Getters for VendItem");
		System.out.println("----------------------------\n");

		String expected = "Snickers";
		String actual = snickers.getName();
		System.out.println("VendItem name: Expected (" + expected + ") " + "Actual (" + actual + ")");
		double expectedPrice = 0.65;
		double actualPrice = snickers.getPrice();
		System.out.println("VendItem price: Expected (" + expectedPrice + ") " + "Actual (" + actualPrice + ")");
		int expectedQty = 6;
		int actualQty = snickers.getQty();
		System.out.println("Track Genre: Expected (" + expectedQty + ") " + "Actual (" + actualQty + ")");
		System.out.println("\nEnd of testing Getters.\n");

	}

	/**
	 * Test for unique incremented Item Ids 
	 * Test cases Id1, Id2, Id3
	 */
	public static void testForId() {
		System.out.println("Testing ItemId for VendItem");
		System.out.println("---------------------------\n");
		int expected = 1;
		int actual = snickers.getitemId();
		System.out.println("Item Id: Expected (" + expected + ") " + "Actual (" + actual + ")");
		expected = 2;
		actual = mars.getitemId();
		System.out.println("Item Id: Expected (" + expected + ") " + "Actual (" + actual + ")");
		expected = 3;
		actual = bounty.getitemId();
		System.out.println("Item Id: Expected (" + expected + ") " + "Actual (" + actual + ")");
		System.out.println("\nEnd of testing ItemId.\n");
	}

	/**
	 * Test for restock with good data and bad data (restock brings total quantity
	 * of item above maximum capacity of Vending Machine) 
	 * Test cases Restock1,
	 * Restock 2
	 */
	public static void testRestock() {
		System.out.println("Testing restock for VendItem");
		System.out.println("-------------------------------\n");
		int initial = snickers.getQty();
		int add = 4;
		boolean status = snickers.restock(add);
		boolean expectedStatus = true;
		int expected = 10;
		int actual = snickers.getQty();
		System.out.println("Inital Quantity: (" + initial + ") + (" + add + ") Expected (" + expected + ", "
				+ expectedStatus + ") " + "Actual (" + actual + ", " + status + ")");
		initial = mars.getQty();
		add = 4;
		status = mars.restock(add);
		expectedStatus = false;
		expected = 8;
		actual = mars.getQty();
		System.out.println("Inital Quantity: (" + initial + ") + (" + add + ") Expected (" + expected + ", "
				+ expectedStatus + ") " + "Actual (" + actual + ", " + status + ")");

		System.out.println("\nEnd of testing restock.\n");
	}

	/**
	 * Test for delivering specific item to user 
	 * Test cases deliver1, deliver2
	 */
	public static void testDeliver() {
		System.out.println("Testing deliver method for VendItem");
		System.out.println("---------------------------\n");
		String expected = "Thanks for purchasing: Snickers";
		String actual = snickers.deliver();
		System.out.println("Purchase confirmation: Expected (" + expected + ") " + "Actual (" + actual + ")");
		expected = "Thanks for purchasing: Mars Bar";
		actual = mars.deliver();
		System.out.println("Purchase confirmation: Expected (" + expected + ") " + "Actual (" + actual + ")");
		System.out.println("\nEnd of testing deliver method.\n");
	}

	/**
	 * Test for decrementing item quantity in Vending Machine when item is
	 * successfully purchased & delivered. 
	 * Test case Decrement
	 */
	public static void testDecrement() {
		System.out.println("Testing item quantity decrement for VendItem");
		System.out.println("---------------------------\n");
		int initial = tracker.getQty();
		int expected = 4;
		int actual = tracker.decrement();
		System.out.println("Inital Quantity: (" + initial + ") Expected (" + expected + "), Actual (" + actual + ")");

		System.out.println("\nEnd of testing decrement.\n");
	}
}

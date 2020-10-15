package part01;

/**
 * Class to test basic functionality of VendItem
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class VendingMachineTester {

	static VendingMachine testVendingMachine = new VendingMachine("test", 3);

	public static void main(String[] args) {
		testAddListGetReset();
		testInsertCoin();
		testPurchaseAndChange();
		testLastPurchase();
	}

	/**
	 * Logical testing sequence of addNewItem(), listItems(), getSystemInfo()/toString(), reset()
	 * Test Cases: add1, add2, add3, list, duplicate, invalidQTY, toString, Reset1
	 */
	public static void testAddListGetReset() {
		System.out.println("Testing addNewItem() & listItems() & getSystemInfo()/toString() & reset() for Vending Machine");
		System.out.println("---------------------------------------------------------------------------------------------\n");

		// display initial list of items in vending machine
		System.out.print("* Initial items in machine: *");
		testVendingMachine.listItems();
		System.out.println();

		// an item to add ...
		VendItem snickers = new VendItem("Snickers", 0.65, 6);
		System.out.println("* Expected - a message to confirm Snickers has been successfully added *");
		testVendingMachine.addNewItem(snickers);

		// display updated list of items in vending machine
		System.out.println("\n* Updated items in machine: *");
		System.out.print("* Expected - A list of the data for each item in the vending machine *");
		testVendingMachine.listItems();

		// add duplicate item
		System.out.println("\n* Expected - a message to alert user that item has not been added due to duplicate item error *");
		testVendingMachine.addNewItem(snickers);
		
		// display initial list of items in vending machine
		System.out.println("\n* Initial items in machine: *");
		System.out.print("* Expected - 1) Snickers, price: £0.65, quantity available: 6 *");
		testVendingMachine.listItems();
		System.out.println();

		// another item to add (with quantity not specified)
		System.out.println("* Expected - a message to confirm Bounty has been successfully added *");
		VendItem bounty = new VendItem("Bounty", 0.75);
		testVendingMachine.addNewItem(bounty);

		// display updated list of items in vending machine
		System.out.println("\n* Updated items in machine: *");
		System.out.print("* Expected - A list of the data for each item in the vending machine *");
		testVendingMachine.listItems();
		
		// another item to add (with quantity greater than MAX quantity limit)
		VendItem mars = new VendItem("Mars Bar", 0.65, 20);
		VendItem lion = new VendItem("Lion", 0.75, -5);
		System.out.println("\n* Expected - a message to alert user that Mars Bar has not been added due to invalid quantity error *");
		testVendingMachine.addNewItem(mars);
		System.out.println("\n* Expected - a message to alert user that Lion has not been added due to invalid quantity error *");
		testVendingMachine.addNewItem(lion);
		
		// add VendItems beyond specified max capacity (3)
		VendItem tracker = new VendItem("Tracker", 0.80, 5);
		VendItem milky = new VendItem("Milky Bar",0.85);
		System.out.println("* Expected - a message to confirm Tracker has been successfully added *");
		testVendingMachine.addNewItem(tracker);
		System.out.println("\n* Expected - a message to alert user that Milky Bar has not been added due to VendItem max capacity error *");
		testVendingMachine.addNewItem(milky);
		
		// display updated list of items in vending machine
		System.out.println("\n* Updated items in machine: *");
		System.out.print("* Expected - A list of the data for each item in the vending machine (Snickers, Bounty, Tracker) *");
		testVendingMachine.listItems();
		
		System.out.print("\n* Expected - a String representation of machine information: Owner test, Max Items 3, Item Count 3, Total Money 50, User Money 0, Machine Status VENDING MODE *");
		System.out.println(testVendingMachine);
		
		
		System.out.println("\nEnd of testing addNewItem() & listItems() & getSystemInfo()/toString() & reset().\n");

	}
	
	/**
	 * Testing sequence of getuserMoney() method and insertCoin() method
	 * Test Cases: insertCoin
	 */
	public static void testInsertCoin() {
		System.out.println("\nTesting insertCoin() & getuserMoney()");
		System.out.println("-------------------------------------\n");
		double initial = testVendingMachine.getuserMoney();
		System.out.printf("Initial user money: £%.2f", initial);
		
		
		int add = 100;
		System.out.println("\nMoney added: " +add+ "p");
		//add 100p aka £1.00
		testVendingMachine.insertCoin(add);
		double updated = testVendingMachine.getuserMoney();
		System.out.printf("Updated user money: £%.2f", updated);
		
		
		System.out.println("\n\nEnd of testing insertCoin() & getuserMoney()\n");
		testVendingMachine.reset();
	}
	
	/**
	 * Testing that purchase item takes the purchase cost from user money, decrements the item quantity, and delivers the item
	 * Test Cases: Purchase1, Purchase2, Purchase 3, change, gettotalMoney
	 */
	public static void testPurchaseAndChange() {
		
		 System.out.println("\nTesting Purchase item method");
		 System.out.println("------------------------------\n");
		 VendItem snickers = new VendItem("Snickers", 0.65, 6);
		 VendItem tracker = new VendItem("Tracker", 0.80, 5);
	
		 //insert coin before you purchase item
		 testVendingMachine.insertCoin(100);
		 //try to purchase item when vending machine is empty : Purchase1 test case
		 System.out.println("\n* Expected - a message to alert user that purchase was unsuccessful due to invalid item reference *");
		 System.out.println(testVendingMachine.purchaseItem(1));
		 
		 testVendingMachine.addNewItem(snickers);
		 testVendingMachine.addNewItem(tracker);
		 //try to purchase item when vending machine has valid VendItems in stock : Purchase2 test case
		 System.out.println(testVendingMachine.purchaseItem(1));
		 //return correct change to user
		 System.out.println("* Expected change from £1.00 - £0.65 = £0.35 *");
		 System.out.printf("Actual change is: £%.2f", testVendingMachine.giveChange());
		 System.out.println("\n");
		 System.out.println("* Expected quantity of Snickers = 6 initially, -1 due to purchase = 5 *");
		 System.out.println("Actual quantity: "+snickers.getQty());
		 System.out.println("* Expected total money now in machine = £0.65 * ");
		 System.out.println("Actual "+testVendingMachine.getTotalMoney());
		 System.out.println();
		 
		 //try to purchase item when coin has not been inserted first : Purcase3 test case
		 System.out.println("* Attempted purchase without inserting coin first *");
		 System.out.println("\n* Expected - a message to alert user that purchase was unsuccessful due to insufficent funds *");
		 System.out.println(testVendingMachine.purchaseItem(2));
		 
		 System.out.println("\n\nEnd of testing Purchase\n");
		 testVendingMachine.reset();
	}
	
	/**
	 * Testing machine goes into service mode after last item is purchased
	 * Test Case: Last, Outofstock
	 */
	public static void testLastPurchase() {
		
		System.out.println("\nTesting last purchase");
		System.out.println("----------------------\n");
		
		//create and add single item to vending machine
		VendItem snickers = new VendItem("Snickers", 0.65, 1);
		testVendingMachine.addNewItem(snickers);
		//insert coin 
		testVendingMachine.insertCoin(100);
		//purchase the single item
		System.out.println("\n* Expected - item purchase confirmation and notification that machine is now empty *");
		System.out.println(testVendingMachine.purchaseItem(1));
		testVendingMachine.giveChange();
		//get system info for vending machine to check Status
		System.out.println("\n* Expected - Machine Status to have changed to SERVICE MODE *");
		System.out.println(testVendingMachine);
		
		//try to purchase item when it is out of stock
		testVendingMachine.insertCoin(100);
		//purchase the sold out item
		System.out.println("\n* Expected - a message to alert user that purchase was unsuccessful due to item being out of stock *"); 
		System.out.println(testVendingMachine.purchaseItem(1));
		//check total money in machine
		System.out.println("* Expected - Total money should not have increased as it has been returned to user *");
		System.out.println(testVendingMachine.getTotalMoney());
		//check user money in machine
		System.out.println("* Expected - User money should be £0 as it has been returned to user *");
		System.out.println("User Money in machine: £"+testVendingMachine.getuserMoney());
		
	}
}

/* VendItem examples
 * --------------------------------------------------
 * VendItem snickers = new VendItem("Snickers", 0.62);
 * VendItem mars = new VendItem("Mars Bar", 0.65, 11);
 * VendItem bounty = new VendItem("Bounty", 0.70, 0);
 * VendItem lion = new VendItem("Lion", 0.75);
 * VendItem tracker = new VendItem("Tracker", 0.80, 5);
 * VendItem milky = new VendItem("Milky Bar",0.85);
 * VendItem dairy = new VendItem("Dairy Milk", 0.90);
 * VendItem fudge = new VendItem("Fudge", 0.95);
 * VendItem yorkie = new VendItem("Yorkie", 1.00);
 * VendItem wispa = new VendItem("Wispa", 1.05);
 * VendItem aero = new VendItem("Aero", 1.10);
 * ------------------------------------------
 */
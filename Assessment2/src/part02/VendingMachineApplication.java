package part02;

import java.util.Scanner;

/**
 * Vending Machine example with valid VendItems (Part 2 with additional
 * features)
 * 
 * @author QUB 40210913 David Montgomery
 * 
 *         README for marker
 * 
 *         Have shown the concept of using try catch loops for input mismatch
 *         exception - didn’t feel this was a priority on every part as normal
 *         vending machines don’t have the option to type in
 *         letters/symbols/decimal points.
 *
 *         Service mode is accessed by pressing 0 on main menu, then typing
 *         password “90210” and hitting enter. Was going to make the password
 *         letters, but a normal vending machine doesn’t have letter buttons.
 * 
 *         It is also possible to enter the service mode when the last item is
 *         sold. The user will be notified that the machine is now empty. If the
 *         password is typed after this, it is another secret way to get into
 *         Service mode, otherwise the application will default back to the main
 *         menu - in which they can view that all item quantities are 0, and if
 *         they try to purchase they will be notified that the machine is empty.
 * 
 */
public class VendingMachineApplication {

	public static void main(String[] args) {

		// initiate new instance of vending machine
		VendingMachine qubVendingMachine = new VendingMachine("QUB-1", 6);

		// add stock to machine
		populate(qubVendingMachine);

		// start vending machine application menu
		vendMenu(qubVendingMachine);

	}

	/**
	 * 
	 * @param vend
	 */
	private static void populate(VendingMachine vend) {
		// initiate new instances of VendItems
		VendItem snickers = new VendItem("Snickers", 0.65, 6);
		VendItem mars = new VendItem("Mars Bar", 0.65, 10);
		VendItem bounty = new VendItem("Bounty", 0.75, 3);
		VendItem lion = new VendItem("Lion", 0.75);
		VendItem tracker = new VendItem("Tracker", 0.85, 5);
		VendItem milky = new VendItem("Milky Bar", 0.85);

		// add VendItems to vending machine 'qub'
		vend.addNewItem(snickers);
		vend.addNewItem(mars);
		vend.addNewItem(bounty);
		vend.addNewItem(lion);
		vend.addNewItem(tracker);

	}

	/**
	 * method to create Vending Menu
	 * 
	 * @param vend the Vending Machine instance
	 */
	private static void vendMenu(VendingMachine vend) {
		String[] vendModeOptions = { "List Items", "Insert Coin", "Purchase Item", "View Balance", "Quit" };
		Menu vendModeMenu = new Menu("Vending Machine", vendModeOptions);
		int vendChoice = 5;
		boolean validInput = false;

		while (vendChoice != 0) {
			vendChoice = vendModeMenu.getChoice();
			processVendChoice(vend, vendChoice);
			if (vendChoice == 5) {
				break;
			}
		}

	}

	/**
	 * processes the choice in the vending machine menu
	 * 
	 * @param vend   the vending machine instance
	 * @param choice - number user has selected
	 */
	private static void processVendChoice(VendingMachine vend, int choice) {
		Scanner in = new Scanner(System.in);

		switch (choice) {
		case 0:
			// enter service mode if correct password entered
			if (vend.checkPassword(in.nextLine())) {
				servMenu(vend);
			} else {
				System.out.println("Error: Incorrect Password");
				vendMenu(vend);
			}
			break;
		case 1:
			// list items in vending machine
			vend.listItems();
			break;
		case 2:
			// insert coins
			boolean validInput = true;
			do {
				System.out.println("Only 5p, 10p, 20p, 50p, 100p & 200p accepted");
				System.out.println("Please enter value of coin in pence: ");

				try { // try catch loop to catch input mismatch exception
					int coinValue = in.nextInt();
					// if coin is not a value as specified as above return error message to user
					if (!vend.insertCoin(coinValue)) {
						System.out.println("Unsuccessful: Please enter a valid value as above!");
					}
					System.out.printf("Total inserted: £%.2f", vend.getuserMoney());
					System.out.println();
					break;
				} catch (Exception InputMisMatchException) {
					System.out.println("Error: Input type incorrect. Please enter an integer as above!");
					in.nextLine();
					validInput = false;
				}
			} while (!validInput);
			break;
		case 3:
			// purchase item by number
			System.out.println("Which item would you like to purchase? (number)");
			int itemChoice = in.nextInt();
			// purchaseItem() method checks if item is available for purchase & enough money
			// entered 
			String purchaseStatus = vend.purchaseItem(itemChoice);
			// returnChangeCoins() method checks if there is enough change in the machine to
			// allow the item to be purchased & change returned
			System.out.println(vend.returnChangeCoins(itemChoice, vend.getuserMoney()));
			
			System.out.printf("Your change is: £%.2f", vend.giveChange());

			// if machine empty
			if (purchaseStatus.contains("*")) {
				System.out.println("\n\nMachine Out of Order");

				// go to service mode if correct password entered.

				in.nextLine();
				vend.setStatus(Status.SERVICE_MODE);
				if (vend.checkPassword(in.nextLine())) {
					servMenu(vend);
				} else {
					System.out.println("Error: Incorrect Password");
					vendMenu(vend);
				}

				break;
			}
			break;
		case 4:
			// check balance
			System.out.printf("Balance: £%.2f", vend.getuserMoney());
			break;
		case 5:
			// quit application
			System.out.println("You have selected to quit.\nThank-you, goodbye!");
			in.close();
			break;
		case 91929394:
			// returned if input mismatch exception thrown
			System.out.println("Error: Input type incorrect. Please enter a choice by number (1-5)!");
			break;
		default:
			System.out.println("Option " + choice + " is invalid");
		}
		System.out.println();
	}

	/**
	 * method to create Service Menu
	 * 
	 * @param vend the vending machine instance
	 */
	private static void servMenu(VendingMachine vend) {

		vend.setStatus(Status.SERVICE_MODE);
		String[] serviceModeOptions = { "Get System Info", "List Items", "View Total Funds", "Switch Status", "Restock", "Add New Item", 
				"Reset Machine", "Return to Vend Menu" }; // add new item. restock item.
		Menu serviceModeMenu = new Menu("Service Mode", serviceModeOptions);
		int servChoice = 0;
		boolean validInput;

		while (servChoice != 8) {
			servChoice = serviceModeMenu.getChoice();
			processServChoice(vend, servChoice);
			continue;
		}
	}

	/**
	 * processes the choice in the service menu
	 * 
	 * @param vend   the vending machine instance
	 * @param choice - number user has selected
	 */
	private static void processServChoice(VendingMachine vend, int choice) {
		Scanner in = new Scanner(System.in);

		switch (choice) {
		case 1:
			// get system info
			System.out.println(vend);
			System.out.println(vend.listItems());
			break;
		case 2:
			// list items
			System.out.println(vend.listItems());
			break;
		case 3:
			// view total funds
			System.out.println(vend.getTotalMoney());
			break;
		case 4:
			// switch status
			System.out.println("Press 0 for Service Mode");
			System.out.println("Press 1 for Vending Mode");
			System.out.println(vend.changeMode(in.nextInt()));
			break;
		case 5:
			// restock VendItem(s)
			boolean validInput = true;
			do {
				System.out.println("Which Item would you like to restock?");
				try {
					int restockItem = in.nextInt();
					System.out.println("How many of this item are you adding?");
					int qtyAdded = in.nextInt();

					if (vend.restock(restockItem, qtyAdded)) {
						System.out.println("Successfully restocked!");
						System.out.println(vend.listItem(restockItem));
						break;
					} else {
						System.out.println("Error: restocking exceeds max quantity allowed, please try again");
						System.out.println(vend.listItem(restockItem));
						break;
					}
					// catch exceptions - e.g null pointer exception from an item number not in the
					// array, or input mismatch exception
					// inform user that restock wasn't successful and return to service menu
				} catch (Exception e) {
					System.out.println("Error: invalid item, please try again");
					validInput = false;
					break;
				}
			} while (!validInput);
			break;
		case 6:
			//add new item
			System.out.println("Item name: ");
			String name = in.nextLine();
			System.out.println("Item price: ");
			double price = in.nextDouble();
			System.out.println("Item quantity: ");
			int quantity = in.nextInt();
			VendItem added = new VendItem (name, price, quantity);
			vend.addNewItem(added);
		case 7:
			// reset machine
			vend.reset();
			System.out.println(vend.getSystemInfo());
			break;
		case 8:
			// return to vend menu
			vend.setStatus(Status.VENDING_MODE);
			vendMenu(vend);
			break;
		case 91929394:
			// returned if input mismatch exception thrown
			System.out.println("Error: Input type incorrect. Please enter a choice by number (1-7)!");
			break;
		default:
			System.out.println("Option " + choice + " is invalid");
		}
		System.out.println();

	}

}
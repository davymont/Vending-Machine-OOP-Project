package part02;

import java.util.Scanner;

import part01.Menu;

/**
 * Vending Machine Class
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class VendingMachine {

	private String owner; // Vending Machine name
	private int maxItems; // maximum number of items that can be stored in Vending Machine
	private int itemCount; // number of items for sale
	private VendItem[] stock; // stock array
	private double totalMoney; // total amount of cash in the machine
	private double userMoney; // money entered by user for purchase
	private Status vmStatus; // status of vending machine - service or vending
	private Coin vmCoin; // test

	private int fiveCount = 0; // count of 5ps in machine
	private int tenCount = 0; // count of 10ps in machine
	private int twentyCount = 0; // count of 20ps in machine
	private int fiftyCount = 0; // count of 50ps in machine
	private int poundCount = 0; // count of pounds in machine
	private int twoPoundCount = 0; // count of twopounds in machine

	/**
	 * this constructs a Vending Machine with a specified owner name and max items
	 * it can store. Machine initiated with 10 of each coin in it as default to
	 * allow a range of change to be given.
	 * 
	 * @param owner    the name of the vending machine
	 * @param maxItems the maximum number of each item that the vending machine can
	 *                 store
	 */
	public VendingMachine(String owner, int maxItems) {
		this.owner = owner;
		this.maxItems = maxItems;
		VendItem[] stock = new VendItem[maxItems];
		this.stock = stock;
		this.itemCount = 0;
		this.totalMoney = initiateCoins(10); // adds 10 of each valid coin to machine upon construction
		this.userMoney = 0.0;
		this.vmStatus = Status.VENDING_MODE;

	}

	/**
	 * this gets an overview of the system information
	 * 
	 * @return a string containing all information about the vending machine
	 */
	public String getSystemInfo() {
		String info = "\n* System Information *\n----------------------";
		info += "\nOwner: " + owner;
		info += "\nMax Items: " + maxItems;
		info += "\nItem Count: " + itemCount;
		info += "\nTotal Money: " + totalMoney;
		info += "\nUser Money: " + userMoney;
		info += "\nMachine Status: " + vmStatus;
		return info;
	}

	/**
	 * this returns the string representation of the vending machine object
	 */
	public String toString() {
		return getSystemInfo();
	}

	/**
	 * this allows an engineer to reset the machine to factory default, empty of
	 * money and items
	 */
	public void reset() {
		VendItem[] stock = new VendItem[maxItems];
		this.stock = stock;
		this.itemCount = 0;
		this.totalMoney = 0.0;
		this.userMoney = 0.0;

	}

	/**
	 * allows engineer to restock an item with a certain number of new items
	 * 
	 * @param restockItem the item number to be restocked
	 * @param qtyAdded    the quantity of new stock added
	 * @return true/false depending on whether the restock was successful or not
	 */
	public boolean restock(int restockItem, int qtyAdded) {
		boolean restockSuccess = false;
		VendItem chosen = stock[restockItem - 1];

		if (chosen.getName() != null) {
			if (chosen.restock(qtyAdded)) {
				restockSuccess = true;
			}
		}

		return restockSuccess;
	}

	/**
	 * this allows a user to purchase a selected item
	 * 
	 * @param chosenItemId the item the user has selected
	 * @return the status of the purchase; whether it was successful or not, and if
	 *         not, why.
	 */
	public String purchaseItem(int chosenItemId) {
		VendItem chosen = stock[chosenItemId - 1];
		String purchaseStatus = "Error: Insufficient funds!";
		// if item exists
		if (chosen != null) {
			if (chosen.getQty() > 0) {
				// if enough money inserted
				if (userMoney >= chosen.getPrice()) {

					// take money from userMoney
					userMoney -= chosen.getPrice();
					// dispense item
					chosen.decrement();
					// return purchase confirmation
					purchaseStatus = chosen.deliver();

					totalMoney += chosen.getPrice();
				}
			}

			// return error message if item qty < 1
			else {
				purchaseStatus = "Error: \"" + chosen.getName() + "\" out of stock!";
				giveChange();
			}
		} else {
			// return error message if item does not exist
			purchaseStatus = "Error: Item selected does not exist! Please try again.";
		}
		if (itemCount > 0) {
			int totalItemQty = 0;
			for (VendItem i : stock) {
				if (i != null) {
					totalItemQty += i.getQty();
				}
			}
			// if machine is empty, go into service mode
			if (totalItemQty < 1) {
				purchaseStatus += "\n* Machine now empty *\n";
				this.vmStatus = Status.SERVICE_MODE;
			}
		}
		return purchaseStatus;
	}

	/**
	 * to 'dispense' userMoney / bring back to 0.
	 * 
	 * @return residual money from purchase aka change given back to user
	 */
	public double giveChange() {
		double change = userMoney;
		userMoney -= change;
		return change;
	}

	/**
	 * allows engineer to check the total value of money currently stored in machine
	 * 
	 * @return moneyInMachine
	 */
	public String getTotalMoney() {
		String moneyInMachine = "Total Money in machine: £";
		moneyInMachine += totalMoney;
		return moneyInMachine;
	}

	/**
	 * method to mutate money that user has inserted in machine
	 * 
	 * @param userMoney money inserted by user
	 */
	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;

	}

	/**
	 * method to return money that user has inserted in machine
	 * 
	 * @return userMoney money inserted by user
	 */
	public double getuserMoney() {
		return userMoney;
	}

	/**
	 * stocks machine with user specified number of each coin
	 * 
	 * @return updated total money in machine
	 */
	private double initiateCoins(int noOfCoins) {

		for (int five = 0; five < noOfCoins; five++) {
			insertCoinService(5);
		}
		for (int ten = 0; ten < noOfCoins; ten++) {
			insertCoinService(10);
		}
		for (int twenty = 0; twenty < noOfCoins; twenty++) {
			insertCoinService(20);
		}
		for (int fifty = 0; fifty < noOfCoins; fifty++) {
			insertCoinService(50);
		}
		for (int pound = 0; pound < noOfCoins; pound++) {
			insertCoinService(100);
		}
		for (int twopound = 0; twopound < noOfCoins; twopound++) {
			insertCoinService(200);
		}

		return totalMoney;
	}

	/**
	 * method to break down total change to be returned user into individual coins
	 * also to return an error message if this is not possible due to there not being enough coins in machine
	 * if it is possible, then reduce the values of the stored coins in machine to account for the coins that are given as change
	 * 
	 * @param chosenItemId item user has chosen to purchase
	 * @param userMoney expected change from the purchase calculated in purchaseItem() method
	 * @return
	 */
	public String returnChangeCoins(int chosenItemId, double userMoney) {
		String changeString = "";
		VendItem chosen = stock[chosenItemId - 1];

		// Constant declarations to hold values for each coin
		final int PENCE_IN_TWOPOUND = 200;
		final int PENCE_IN_POUND = 100;
		final int PENCE_IN_FIFTY = 50;
		final int PENCE_IN_TWENTY = 20;
		final int PENCE_IN_TEN = 10;
		final int PENCE_IN_FIVE = 5;

		final double initialUserMoney = chosen.getPrice() + userMoney;
		double change = userMoney;

		// convert the change from pounds to pence
		int changeInt = (int) Math.round(change * 100);

		// The number of £2 coins equals the change divided by 200 if change is > 200
		// if the number of £2 coins required for change is greater than number of £2
		// coins in machine, flag error
		int twoPoundCoins = changeInt / PENCE_IN_TWOPOUND;
		int changeRemaining = changeInt - (twoPoundCoins * PENCE_IN_TWOPOUND);
		if (twoPoundCoins > twoPoundCount) {
			changeString = "error";
		}

		// The number of £1 coins equals the remaining change divided by 100 if change
		// is > 100
		// if the number of £1 coins required for change is greater than number of £1
		// coins in machine, flag error
		int poundCoins = changeRemaining / PENCE_IN_POUND;
		changeRemaining -= poundCoins * PENCE_IN_POUND;
		if (poundCoins > poundCount) {
			changeString = "error";
		}

		// The number of 50 pence coins equals the remaining change divided by 50 if
		// change is > 50
		// if the number of 50p coins required for change is greater than number of 50p
		// coins in machine, flag error
		int fiftyPenceCoins = changeRemaining / PENCE_IN_FIFTY;
		changeRemaining -= fiftyPenceCoins * PENCE_IN_FIFTY;
		if (fiftyPenceCoins > fiftyCount) {
			changeString = "error";
		}

		// The number of 20 pence coins equals the change divided by 20 if change is >
		// 20
		// if the number of 20p coins required for change is greater than number of 20p
		// coins in machine, flag error
		int twentyPenceCoins = changeRemaining / PENCE_IN_TWENTY;
		changeRemaining -= (twentyPenceCoins * PENCE_IN_TWENTY);
		if (twentyPenceCoins > twentyCount) {
			changeString = "error";
		}

		// The number of 10 pence coins equals the change divided by 10 if change is >
		// 10
		// if the number of 10p coins required for change is greater than number of 10p
		// coins in machine, flag error
		int tenPenceCoins = changeRemaining / PENCE_IN_TEN;
		changeRemaining -= (tenPenceCoins * PENCE_IN_TEN);
		if (tenPenceCoins > tenCount) {
			changeString = "error";
		}

		// The number of 5 pence coins equals the change divided by 5 if change is > 5
		// if the number of 5p coins required for change is greater than number of 5p
		// coins in machine, flag error
		int fivePenceCoins = changeRemaining / PENCE_IN_FIVE;
		changeRemaining -= (fivePenceCoins * PENCE_IN_FIVE);
		if (fivePenceCoins > fiveCount) {
			changeString = "error";
		}

		// if there are sufficient coins in machine to give change
		if (!changeString.equals("error")) {
			// decrement relevant coin counters by amount of coins given
			twoPoundCount -= twoPoundCoins;
			poundCount -= poundCoins;
			fiftyCount -= fiftyPenceCoins;
			twentyCount -= twentyPenceCoins;
			tenCount -= tenPenceCoins;
			fiveCount -= fivePenceCoins;

			changeString += chosen.getName() + " successfully purchased!\n";

			// create string to display coins returned as change
			changeString += "Dispensing £2 Coins: " + twoPoundCoins;
			changeString += " £1 Coins: " + poundCoins;
			changeString += " 50p coins: " + fiftyPenceCoins;
			changeString += " 20p coins: " + twentyPenceCoins;
			changeString += " 10p coins: " + tenPenceCoins;
			changeString += " 5p coins: " + fivePenceCoins;
		}
		// otherwise, if an error has been flagged, there aren't enough coins to give
		// change, alert the user and return all of their money
		else {
			changeString = "Error: not enough change in machine to purchase " + chosen.getName();
			this.userMoney = initialUserMoney;
			this.itemCount++;
		}

		return changeString;
	}

	/**
	 * allows engineer to insert coins, limited to those specified
	 * 
	 * @param value
	 * @return true/false depending on whether a coin was successfully inserted or
	 *         not
	 */
	public boolean insertCoinService(int value) {
		boolean validCoin = true;

		switch (value) {
		case 5:
			vmCoin = vmCoin.five; // test
			fiveCount++;
			break;
		case 10:
			tenCount++;
			break;
		case 20:
			twentyCount++;
			break;
		case 50:
			fiftyCount++;
			break;
		case 100:
			poundCount++;
			break;
		case 200:
			twoPoundCount++;
			break;
		default:
			validCoin = false;
		}

		if (validCoin) {
			double temp = (value * 0.01);
			totalMoney += temp;
		}
		return validCoin;
	}

	/**
	 * allows user to insert coins, limited to those specified
	 * 
	 * @param value
	 * @return true/false depending on whether a coin was successfully inserted or
	 *         not
	 */
	public boolean insertCoin(int value) {
		boolean validCoin = true;

		switch (value) {
		case 5:
			fiveCount++;
			break;
		case 10:
			tenCount++;
			break;
		case 20:
			twentyCount++;
			break;
		case 50:
			fiftyCount++;
			break;
		case 100:
			poundCount++;
			break;
		case 200:
			twoPoundCount++;
			break;
		default:
			validCoin = false;
		}

		if (validCoin) {
			double temp = (value * 0.01);
			userMoney += temp;
		}
		return validCoin;
	}

	/**
	 * allows new VendItems to be added to a Vending Machine instance
	 * 
	 * @param newItem
	 * @return true/false depending on whether the item has been successfully added
	 *         or not
	 */
	public boolean addNewItem(VendItem newItem) {
		boolean itemAdded = true;

		// check to see if already instance of item in array
		if (itemCount > 0) {
			for (VendItem i : stock) {
				if (i != null) {
					if (i.getName().equals(newItem.getName())) {
						System.out.println("Unable to add: \"" + newItem.getName() + "\", duplicate item!");
						itemAdded = false;
						break;
					}
					if (newItem.getQty() == -1) {
						System.out.println("Unable to add: \"" + newItem.getName() + "\", invalid quantity!");
						itemAdded = false;
						break;
					}
				}
			}
		}

		// check to see array is not already full of items
		if (itemAdded == true) {
			if (itemCount < maxItems) {
				stock[itemCount] = newItem;
				itemCount++;
				System.out.println("Item: \"" + newItem.getName() + "\" successfully added.");
			} else {
				System.out.println("Unable to add: \"" + newItem.getName() + "\", no more room to add items!");
				itemAdded = false;
			}
		}

		return itemAdded;
	}

	/**
	 * Public method to allow Vending Machine status to be changed from application
	 * menu
	 * 
	 * @param currentStatus
	 */
	public void setStatus(Status currentStatus) {
		this.vmStatus = currentStatus;
	}

	/**
	 * allow engineer to switch machine status
	 * 
	 * @param mode
	 * @return current status
	 */
	public Status changeMode(int mode) {
		Status changeStatusTo = Status.SERVICE_MODE;

		if (mode == 1) {
			changeStatusTo = Status.VENDING_MODE;
		}

		return changeStatusTo;
	}

	/**
	 * allows user to view details of a list of items in machine
	 * 
	 * @return each instance of item that is not null
	 */
	public String listItems() {
		String itemName = "";
		System.out.println("\nItems currently in vending machine are: ");
		for (VendItem item : stock) {
			if (item != null) {
				System.out.println(item);
			}

		}
		return itemName;
	}

	/**
	 * allows user to view details of a single item in machine
	 * 
	 * @param chosenItem item chosen by user
	 * @return itemDetails
	 */
	public VendItem listItem(int chosenItem) {

		VendItem itemDetails = stock[chosenItem - 1];

		return itemDetails;
	}

	/**
	 * tests password entered to allow/deny access to Service Mode
	 * 
	 * @param password what the user has entered
	 * @return true/false depending on whether password entered matches password
	 *         here
	 */
	public boolean checkPassword(String password) {
		boolean passwordCorrect = false;
		if (password.trim().equals("90210")) {
			passwordCorrect = true;
		}
		return passwordCorrect;
	}

}

/*
 * public int drop(String coins, int total) {
 * 
 * if(coins.equals("NICKLE")){ total = total + Coin.NICKLE.getDenom(); }else
 * if(coins.equals("DIME")){ total = total + Coin.DIME.getDenom(); }else
 * if(coins.equals("QUARTER")){ total = total + Coin.QUARTER.getDenom(); }else
 * if(coins.equals("DOLLAR")){ total = total + Coin.DOLLAR.getDenom(); }else{
 * System.out.println("Wrong Input Coin"); }
 * 
 * return total; }
 */
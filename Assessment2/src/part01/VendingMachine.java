package part01;

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

	/**
	 * this constructs a Vending Machine with a specified owner name and max items
	 * it can store. Machine initiated with £50 in it as default to allow a range of change to be given.
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
		this.totalMoney = 50.0;
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
	 * @param restockItem the item number to be restocked
	 * @param qtyAdded the quantity of new stock added
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
	 * @param userMoney money inserted by user
	 */
	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
		
	}
	
	/**
	 * method to return money that user has inserted in machine
	 * @return userMoney money inserted by user
	 */
	public double getuserMoney() {
		return userMoney;
	}

	/**
	 * allows user to insert coins, limited to those specified
	 * 
	 * @param value
	 * @return true/false depending on whether a coin was successfully inserted or
	 *         not
	 */
	public boolean insertCoin(int value) {
		boolean inserted = false;
		double temp = (value * 0.01);

		if (value == 5 || value == 10 || value == 20 || value == 50 || value == 100 || value == 200) {
			userMoney += temp;
			inserted = true;
		}
		return inserted;
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
	 * allows user to view a list of items in machine
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
	
	public VendItem listItem(int chosenItem) {
		
		VendItem itemDetails = stock[chosenItem-1];
		
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
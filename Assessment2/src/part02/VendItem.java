package part02;

/**
 * Constructor class for a VendItem (item to be stored & sold in vending machine)
 * 
 * @author QUB 40210913 David Montgomery
 *
 */
public class VendItem implements Vendible {
	private int itemId; //uniquely assigned 
	private static int nextId; //next unique ID
	private String name; //item name
	private double unitPrice; //item price in £0.00 format
	private int qtyAvailable; //item quantity available
	private final int MAX = 10; //item max quantity 

	public VendItem(String name, double unitPrice) {
		this.name = name.trim();
		this.qtyAvailable = MAX;
		this.itemId = nextItemId();

		// ensure unitPrice is valid, insert nonsense value if not valid to alert
		// engineer
		if (unitPrice < 0.05 || unitPrice > 2 || ((unitPrice * 100) % 5 != 0)) {
			this.unitPrice = 999;
		} else
			this.unitPrice = unitPrice;
	}

	public VendItem(String name, double unitPrice, int qtyAvailable) {
		this(name, unitPrice);
		
		// ensure qtyAvailable is valid, insert nonsense value if not valid to alert
		// engineer
		if (qtyAvailable < 1 || qtyAvailable > MAX) {
			this.qtyAvailable = -1;
		} else
			this.qtyAvailable = qtyAvailable;

	}

	/**
	 * @return name of item
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return price of item
	 */
	public double getPrice() {
		return unitPrice;
	}

	/**
	 * @return quantity of item
	 */
	public int getQty() {
		return qtyAvailable;
	}

	/**
	 * @return unique Id of item
	 */
	public int getitemId() {
		return itemId;
	}

	/**
	 * allows item to be 'restocked' - more items added to current quantity. Ensures item quantity does not exceed maximum
	 * @param qtyAdded quantity of items to be added
	 * @return boolean depending if restock is successful or not
	 */
	public boolean restock(int qtyAdded) {
		boolean restockSuccess = false;
		
		if (qtyAdded > 0) {
			if (qtyAvailable + qtyAdded <= MAX) {
				this.qtyAvailable += qtyAdded;
				restockSuccess = true;
			} 
		} 
		
		return restockSuccess;
	}

	/**
	 * increments itemId for each new item added to ensure unique itemId for each item
	 * @return nextId unique Id
	 */
	private static int nextItemId() {
		nextId++;
		return nextId;
	}

	/**
	 * allows string representation of object - displays all object details to console
	 * @return details String
	 */
	private String getDetails() {
		String details = "";
		details += itemId + ") ";
		details += name;
		details += ", price: £" + unitPrice;
		details += ", quantity available: " + qtyAvailable;
		return details;
	}

	/**
	 * overrides getDetails
	 */
	public String toString() {
		return getDetails();
	}

	/**
	 * allows item name to be set to new value
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * allows item price to be set to new value
	 * @param unitPrice
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	/**
	 * allows item quantity to be set to new value
	 * @param qtyAvailable
	 */
	public void setQtyAvailable(int qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	/**
	 * deliver method returns String to user to confirm delivery of item that they have purchased
	 * @return delivered confirmation of successful purchase/delivery
	 */
	@Override
	public String deliver() {
		String delivered = "Thanks for purchasing: " + name;
		return delivered;
	}

	/**
	 * decrement method reduces the item quantity available by 1 after an item is delivered to the user after purchase
	 * @return updated quantity available of item
	 */
	public int decrement() {
		setQtyAvailable(qtyAvailable-1);
		return qtyAvailable;
	}
}

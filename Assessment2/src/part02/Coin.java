package part02;

/**
 * an Enum to store valid values of coins that can be inserted into machine
 * 
 * @author QUB 40210913 David Montgomery
 */
public enum Coin {
	
	five(5),
	ten(10),
	twenty(20),
	fifty(50),
	pound(100),
	twopound(200);

	private int value;
	
	/**
	 * the value of the coin in pence
	 * @param value
	 */
	private Coin(int value) {
		this.value = value;
	}

}

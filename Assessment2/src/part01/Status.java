package part01;

public enum Status {
	VENDING_MODE(0), SERVICE_MODE(1);

	private int value;
	
	private String mode[] = { "Status: VENDING MODE", "Status: SERVICE MODE"};
	
	private Status(int value) {
		this.value = value;
	}
	
	public String getStatus() {
		return mode[value];
	}
	
	@Override 
	public String toString() {
		return getStatus();
	}

}

//to get in service mode enter 90210

abstract class Bill {

	private boolean paidStatus = false;
	private static int totalBills = 0;
	protected String billNo;
	private double amount;
	
	public Bill(double amount) {
		this.amount = amount;
		totalBills++;
	}
	public void setPaid() {
		paidStatus = true;
		amount = 0;
	}
	public double getAmount() {
		return amount;
	}
	public boolean getStatus() {
		return paidStatus;
	}
	public static int getTotalBills() {
		return totalBills;
	}
	public String getBillNo() {
		return billNo;
	}
	abstract String getDetails();

}

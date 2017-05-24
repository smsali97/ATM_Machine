import java.util.Date;

public class Transaction {
	private int amount;
	private String type;
	private Date date;
	
	public Transaction(int amount, String type) {
		this.amount = amount;
		this.type = type;
		date = new Date();
	}
	
	@Override
	public String toString() {
		return "A transaction of " + type + "  with an amount of "
				+ amount + " was done on the following date: \n" + date + "\n";
	}
}

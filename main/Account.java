import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
	private static int noOfAccounts = 0;
	private String name;
	private String username;
	private int balance;
	private String pinCode;
	public static final int INITIAL_BALANCE = 10000;
	public static final int MAX_BALANCE = 1_000_000_000;
	// private ArrayList<Transaction> transactionHistory = new ArrayList<>();
	
	public static int getNoOfAccounts() {
		return noOfAccounts;
	}
	public static void setNoOfAccounts(int noOfAccounts) {
		Account.noOfAccounts = noOfAccounts;
	}

	public Account(String name, String pinCode) throws IOException {
		this(name, pinCode, INITIAL_BALANCE);
	}

	public Account(String name, String pinCode, int balance) throws IOException {
		this.name = name;
		this.pinCode = pinCode;
		this.balance = balance;

		String lastName = name.substring(name.indexOf(' ') + 1);

		int r1 = ((int) name.charAt(0)) % 10;
		int r2 = ((int) name.charAt(1)) % 10;
		// String acc = String.format("%03d", noOfAccounts);
		username = name.substring(0, 3).toLowerCase() + "_" + lastName.substring(0, 3).toLowerCase() + r1 + r2;
		initTransactions();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username;
	}

	public int getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void initTransactions() throws IOException {
		File file = null;
		
		file = new File("transactions/" + this.username + ".txt");
		
		if (!file.exists()) {
			FileWriter fw = new FileWriter(file);

			String start = "***********************************\n" + this.name + "'s Transaction history as follows\n"
					+ "***********************************\n";
			fw.write(start);

			fw.close();
		}
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public String getPinCode() {
		return pinCode;
	}

}

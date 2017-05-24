import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class AccountWriter {
	private static FileWriter fw;
	private static BufferedReader br;
	static InputStreamReader i1 = new InputStreamReader(
			AccountWriter.class.getClassLoader().getResourceAsStream(
					"accounts.csv"));
	static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	static URL url = classLoader.getResource("accounts.csv");
	
	
	public static int getTotalAccounts() throws IOException {
		
		int x = 0;
		br = new BufferedReader(new FileReader("credentials/accounts.csv"));
		br.readLine();
		
		while ( (br.readLine()) != null) {
			x++;
		}
		return x;
	}
	
	public static int getBalance(String username) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("credentials/accounts.csv"));
		String line = "";
		
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens[1].equals(username)) {
				br.close();
				return Integer.parseInt(tokens[3]);
			}
		}
		br.close();
		return -1;
	}
	
	public static void writeAccount(Account acc) throws IOException{
		File file = null;
		file = new File("credentials/accounts.csv");
		fw = new FileWriter(file,true);		
		fw.append(acc.getName());
		fw.append(",");
		fw.append(acc.getUsername());
		fw.append(",");
		fw.append(acc.getPinCode());
		fw.append(",");
		fw.append(String.valueOf(acc.getBalance()));
		fw.append("\n");
		
		fw.flush();
		fw.close();
	}
	
	public static ArrayList<Account> loadAccounts() throws IOException {
		
		ArrayList<Account> accounts = new ArrayList<>();
		
		br = new BufferedReader(new FileReader("credentials/accounts.csv"));
		br.readLine();
		String line = "";
		
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens.length > 0) {
				Account a = new Account(tokens[0], tokens[2], Integer.parseInt(tokens[3]));
				accounts.add(a);
			}
		}
		return accounts;
	}
	
	public static void modifyBalance(String username, int balance) throws IOException {
		
		
		File file = new File("credentials/accounts.csv");		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		StringBuilder str = new StringBuilder();
		String line = "";
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens[1].equals(username)) {
				str.append(tokens[0] + ",");
				str.append(tokens[1] + ",");
				str.append(tokens[2] + ",");
				str.append(balance);
			}
			else {
			str.append(line);
			}
			str.append('\n');
		}
		br.close();
		
		FileWriter fw = new FileWriter(file);
		fw.write(str.toString());
		fw.close();
	}
	
	public static boolean accountExists(String username) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("credentials/accounts.csv"));		
		String line = "";
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens[1].equals(username)) {
				br.close();
				return true;
			}
		}
		br.close();
		return false;
	}
	
	public static void deleteAccount(String username) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("credentials/accounts.csv"));		
		StringBuilder str = new StringBuilder();
		String line = "";
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (!tokens[1].equals(username)) {
			str.append(line);
			str.append('\n');
			}
			
		}
		br.close();

		File file = new File("credentials/accounts.csv");
		FileWriter fw = new FileWriter(file);
		fw.write(str.toString());
		fw.close();
	}	
	
	
	public static void modifyPIN(String username, String PIN) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("credentials/accounts.csv"));
		
		StringBuilder str = new StringBuilder();
		String line = "";
		while ( (line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			if (tokens[1].equals(username)) {
				str.append(tokens[0] + ",");
				str.append(tokens[1] + ",");
				str.append(PIN + ",");
				str.append(tokens[2] + ",");
				str.append(tokens[3]);
			}
			else {
			str.append(line);
			}
			str.append('\n');
		}
		br.close();
		File file = new File("credentials/accounts.csv");
		FileWriter fw = new FileWriter(file);
		fw.write(str.toString());
		fw.close();
	}
	
	public static void main(String[] args) throws IOException {
	/*	Scanner in = new Scanner(System.in);
		Account[] acc = new Account[10];
		for (Account account : acc) {
			account = new Account(in.next(), in.next(), in.nextInt());
			AccountWriter.writeAccount(account);
		}
		in.close();
	*/
		modifyBalance("XYZ890", 1500);
	}
	
	public static void writeTransaction(Transaction t) throws IOException {
		String s = "transactions/" + Main.getCurrentUser().getUsername() + ".txt";
		File file = new File(s);
		FileWriter fw = new FileWriter(file, true);
		
		fw.write(t.toString() + "\n");
		
		fw.close();
		
	}
	
	public static String readTransactions() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("transactions/" + Main.getCurrentUser().getUsername() + ".txt"));
		
		StringBuilder s = new StringBuilder();
		String line = "";
		while ( (line = br.readLine()) != null ) {
			s.append(line);
			s.append('\n'); s.append('\n');
		}
		br.close();
		return s.toString();
	}

}

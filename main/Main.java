import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private static Account currentUser = null;
	private static ArrayList<Account> accountList = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(
			   new Image(getClass().getClassLoader().getResourceAsStream("atm.png")));
		
		Account.setNoOfAccounts(AccountWriter.getTotalAccounts());
		currentUser = null;
		primaryStage = Question.display("Has account been created?");
		primaryStage.showAndWait();
		boolean answer = Question.getFlag();

		if (!answer) {
			// Fill the form until you do it correctly
			boolean notDoneCorrectly = true;
			while (notDoneCorrectly) {
				AccountCreationView.launch();
				if (notDoneCorrectly = !AccountCreationView.getdoneCorrectly()) {
					Message.display("Please fill the required details to create an account");
				}
			}
			AccountWriter.writeAccount(AccountCreationView.createAccount());
			accountList = AccountWriter.loadAccounts();
			Message.display("Your username is: " + accountList.get(accountList.size() - 1).getUsername()
					+ ", please remember it!");
		}
		// load all the accounts into a current list
		accountList = AccountWriter.loadAccounts();
		
		// open view for user to enter credentials
	    LoginView.launch();
		boolean doesntMatch = true;
		boolean triedOnce = false;
		do {
			if (triedOnce && doesntMatch) {
				LoginView.launch();
			}
			for (Account acc : accountList) {
				if (acc.getUsername().equals(LoginView.getUsername())
						&& acc.getPinCode().equals(LoginView.getPassword())) {
					currentUser = acc;
					break;
				}
			}
			
			doesntMatch = currentUser == null;
			triedOnce = true;
			if (triedOnce && doesntMatch)
				Message.display("Password and/or username is incorrect!");
			
			triedOnce = true;
		} while (doesntMatch);
		primaryStage.close();
		
		final Stage s2 = LoadingScreen.start();
		s2.show();
		
		PauseTransition delay = new PauseTransition(Duration.seconds(7));
		
		delay.setOnFinished( event -> {
			s2.close();
			final Stage s = new Stage();
			s.getIcons().add(
					   new Image(getClass().getClassLoader().getResourceAsStream("cash-machine.png")));
			launchHome(s);
		});
		delay.play();
	}
	
	public static void launchHome(Stage primaryStage) {

		primaryStage = HomeView.launch();
		primaryStage.show();
	}
	
	public static Account getCurrentUser() {
		return currentUser;
	}

}

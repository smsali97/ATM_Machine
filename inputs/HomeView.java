import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeView {

	public static Stage launch() {
		Stage stage = new Stage();

		stage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		// Create Home Page
		ImageView home = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("atm.png")));
		home.setFitHeight(80);
		home.setFitWidth(80);
		// Create Home text
		Label homeBanner = new Label("My ATM");
		homeBanner.setAlignment(Pos.CENTER_RIGHT);
		homeBanner.setFont(Font.font("Franklin Gothic Demi", 70));
		homeBanner.setTextFill(Color.web("#ffffff"));
		// Create Account
		ImageView cA = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("user.png")));
		cA.setFitHeight(70);
		cA.setFitWidth(70);
		Button deleteAccount = new Button("Delete Account", cA);
		addDropShadow(deleteAccount);
		deleteAccount.setOnAction( e-> {
			Stage stage2 = Question.display("Are you sure you want to delete your account?");
			stage2.showAndWait();
			boolean flag1 = Question.getFlag();
			stage2 = Question.display("Think of all the money you could lose?!");
			stage2.showAndWait();
			boolean flag2 = Question.getFlag();
			
			if (flag1 && flag2) {
				try {
					Message.display("Deleted Account! :( ");;
					AccountWriter.deleteAccount(Main.getCurrentUser().getUsername());
					stage.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// Deposit
		ImageView d = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("deposit.png")));
		d.setFitHeight(70);
		d.setFitWidth(70);
		Button deposit = new Button("Deposit Amount", d);
		addDropShadow(deposit);
		// Withdraw
		ImageView w = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("withdraw.png")));
		w.setFitHeight(70);
		w.setFitWidth(70);
		Button withdraw = new Button("Withdraw Amount", w);
		addDropShadow(withdraw);
		// Transfer
		ImageView t = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("transfer.png")));
		Button transfer = new Button("Transfer Amount", t);
		addDropShadow(transfer);
		PauseTransition delay = new PauseTransition();
		delay.setDuration(Duration.seconds(7.5));
		transfer.setOnAction(e -> {
			TransferView.start();
		});
		t.setFitHeight(70);
		t.setFitWidth(70);
		// Pay Bills
		ImageView pB = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("bill.png")));
		Button payBills = new Button("Pay Bills", pB);
		addDropShadow(payBills);
		pB.setFitHeight(70);
		pB.setFitWidth(70);
		payBills.setOnAction(e -> {
			BillView.launch();
		});
		// Balance Inquiry
		ImageView bI = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("balance.png")));
		Button balanceInquiry = new Button("Balance Inquiry", bI);
		balanceInquiry.setOnAction( e-> {
			Message.display("Mr./Mrs." + Main.getCurrentUser().getName() + ", your "
					+ "available balance is: "  + Main.getCurrentUser().getBalance() );
		});
		addDropShadow(balanceInquiry);
		bI.setFitHeight(70);
		bI.setFitWidth(70);
		// View Transactions
		ImageView tr = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("transactions.png")));
		Button transactions = new Button("View Transactions", tr);
		addDropShadow(transactions);
		tr.setFitHeight(70);
		tr.setFitWidth(70);
		transactions.setOnAction( e-> {
			try {
				TransactionView.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Message.display("You know the drill.");
			}
		});
		// Change PIN
		ImageView cP = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("changepin.png")));
		Button changePin = new Button("Change PIN", cP);
		addDropShadow(changePin);
		cP.setFitHeight(70);
		cP.setFitWidth(70);
		// Exit
		ImageView ex = new ImageView(new Image(HomeView.class.getClassLoader().getResourceAsStream("exit.png")));
		Button exit = new Button("Exit Account", ex);
		addDropShadow(exit);
		ex.setFitHeight(70);
		ex.setFitWidth(70);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		grid.add(home, 0, 0);
		grid.add(homeBanner, 1, 0, 2, 1);

		grid.add(deposit, 1, 1);
		grid.add(withdraw, 2, 1);
		grid.add(deleteAccount, 0, 1);

		grid.add(payBills, 0, 2);
		grid.add(changePin, 1, 2);
		grid.add(balanceInquiry, 2, 2);

		grid.add(transfer, 0, 3);
		grid.add(transactions, 1, 3);
		grid.add(exit, 2, 3);
		grid.setBackground(new Background(new BackgroundFill(Color.web("#8c230c"), null, null)));
		// grid.setGridLinesVisible(true);
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(HomeView.class.getResource("HomeView.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Home");


		exit.setOnAction(e -> {
			stage.close();
		});

		changePin.setOnAction(e -> {
			PinView.openPinWindow();
			try {
				if (PinView.getPin()!=null) {
				AccountWriter.modifyPIN(Main.getCurrentUser().getUsername(), PinView.getPin() );
				Message.display("Pin change successful!");
				}
				else {
					Message.display("Pin Change unsucessful");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Message.display("Pin Change unsucessful");
			}				
		});

		withdraw.setOnAction(e -> {
			WithdrawView.openWithdrawal();
			int x = 0;
			try {
				x = Integer.parseInt(WithdrawView.getAmount());
			} catch (IllegalArgumentException except) {
				x = 0;
			}

			int c = Main.getCurrentUser().getBalance();
			System.out.println(c);
			System.out.println(x);
			if (x == 0)
				Message.display("No withdrawal ocurred! Try again!");
			else if (x >= Account.MAX_BALANCE) {
				Message.display("Amount greater than limit to withdraw");
			} else if (c - x < 1000) {
				Message.display("You must leave at least Rs.1000 in the bank");
			} else if (x % 500 != 0) {
				Message.display("Withdrawals must be in multiples of Rs.500");
			} else {
				Main.getCurrentUser().setBalance(c - x);
				try {
					AccountWriter.modifyBalance(Main.getCurrentUser().getUsername(),
							Main.getCurrentUser().getBalance());
					Message.display("Successful withdrawal");
					
					AccountWriter.writeTransaction(new Transaction(x, "Withdrawal"));
				} catch (IOException except) {
					Message.display("Error occurred! :O ");
				}
			}

		});

		deposit.setOnAction(e -> {
			DepositView.openDeposit();
			int x = 0;
			try {
				x = Integer.parseInt(DepositView.getAmount());
			} catch (IllegalArgumentException except) {
				x = 0;
			}

			if (x == 0)
				Message.display("No deposit ocurred!");
			else if (x >= Account.MAX_BALANCE || x + Main.getCurrentUser().getBalance() >= Account.MAX_BALANCE) {
				Message.display("Amount greater than limit to deposit");
			} else if (x % 500 != 0) {
				Message.display("Deposits must be in multiples of Rs.500");
			} else {
				Main.getCurrentUser().setBalance(Main.getCurrentUser().getBalance() + x);
				try {
					AccountWriter.modifyBalance(Main.getCurrentUser().getUsername(),
							Main.getCurrentUser().getBalance());
					Message.display("Successful deposit");
					AccountWriter.writeTransaction(new Transaction(x, "Deposit"));
				} catch (IOException except) {
					Message.display("Error occurred! :O ");
				}
			}
		});

		return stage;
	}

	public static void addDropShadow(Button b) {

		b.setOnMouseEntered(e -> {
			b.setEffect(new Glow());;
		});

		b.setOnMouseExited(e -> {
			((Glow)b.getEffect()).setLevel(0);
		});
	}
}

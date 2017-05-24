import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransferView {
	final static BooleanProperty firstTime = new SimpleBooleanProperty(true);

	public static void start() {
		Stage stage = new Stage();

		stage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		
		stage.initModality(Modality.APPLICATION_MODAL);
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.CENTER);

		TextField tf = new TextField();
		tf.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                vbox.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		tf.setPromptText("Type the username to whom you want to transfer the funds to");
		Button OK = new Button("OK");

		OK.setOnAction(e -> {
			boolean flag = false;
			try {
				flag = (AccountWriter.accountExists(tf.getText()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (!flag) {
				Message.display("Username doesnt exist. Note: usernames are case-sensitive");
			} else {
				String username = tf.getText();
				DepositView.openDeposit();
				int x = 0;
				try {
					x = Integer.parseInt(DepositView.getAmount());
				} catch (IllegalArgumentException except) {
					x = 0;
				}

				if (x == 0)
					Message.display("No transfer ocurred!");
				else if (x >= Account.MAX_BALANCE) {
					Message.display("Amount greater than limit to transfer");
				} else if (x % 500 != 0) {
					Message.display("Transfers must be in multiples of Rs.500");
				} else if (Main.getCurrentUser().getBalance() - x < 1000) {
					Message.display("You must leave at least Rs.1000 in the bank");
				} else {
					Main.getCurrentUser().setBalance(Main.getCurrentUser().getBalance() - x);
					try {
						AccountWriter.modifyBalance(Main.getCurrentUser().getUsername(), Main.getCurrentUser().getBalance());
						AccountWriter.modifyBalance(username, AccountWriter.getBalance(username) + x);
						Message.display("Successful transfer");
						flag = true;
						AccountWriter.writeTransaction(new Transaction(x, "Transfer Fund"));
						stage.close();
					} catch (IOException except) {
						Message.display("Error occurred! :O ");
					}
				}
			}
		});
		ImageView iv = new ImageView(new Image(TransferView.class.getClassLoader().getResourceAsStream("transfer2.png")));
		iv.setOnMouseEntered( e-> {
			iv.setEffect(new DropShadow());
		});
		iv.setOnMouseExited ( e-> {
			iv.setEffect(new DropShadow(0, null));
		});
		
		iv.setFitHeight(80);
		iv.setFitWidth(80);
		
		vbox.setPrefWidth(300);
		vbox.requestFocus();
		vbox.setStyle("-fx-background-color: #b0f442;");
		vbox.getChildren().addAll(iv,tf,OK);
		stage.setScene(new Scene(vbox));
		stage.setTitle("Transfer Funds!");
		stage.show();
	}
}

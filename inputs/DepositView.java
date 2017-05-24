import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepositView {
	private static String amount = "";
	
	public static void openDeposit() {
		amount = "";
		Stage DepositStage = new Stage();
		DepositStage.initModality(Modality.APPLICATION_MODAL);
		
		TextField tf = new TextField();
		tf.setEditable(false);
		tf.setPrefSize(300,100);
		tf.setStyle(" -fx-font-size: 20px;");
		tf.setAlignment(Pos.CENTER);
		tf.setPromptText("Enter Amount to Deposit");
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10));
		gp.setAlignment(Pos.CENTER);
		gp.resize(400, 800);
		
		
		Label label = new Label("Or Use this for quick Deposit");
		label.setTextFill(Color.INDIANRED);
		label.setAlignment(Pos.CENTER);
		//gp.setGridLinesVisible(true);
		Button[] buttons = new Button[12];
		
		for (int i = 0; i < 9; i++) {
			buttons[i] = new Button(String.valueOf(i+1));
		}
		buttons[9] = new Button("C");
		buttons[10] = new Button("0");
		buttons[11] = new Button("OK");
		
		
		int x = 0;
		for (Button button : buttons) {
			button.resize(30, 30);
			gp.getChildren().add(button);
			GridPane.setConstraints(button, x%3, x/3);
			x++;
			button.setOnAction( e -> {
				tf.setText( tf.getText() + button.getText() );
				
			});
		}
		
		buttons[9].setOnAction( e-> {
			tf.setText("");
		});
		
		ComboBox<String> cbo = new ComboBox<>();
		cbo.getItems().addAll("Rs. 1000", "Rs. 5000",
				"Rs. 10000", "Rs. 20000");
				cbo.setStyle("-fx-color: red");
				cbo.setValue("Rs.1000");
		
		cbo.setOnAction( e-> {
			String numericValue = cbo.getValue().substring(4);
			tf.setText(numericValue);
		});		
		
		VBox view = new VBox(tf,gp,label,cbo);
		view.setStyle("-fx-background-color: #efbf13");
		view.setAlignment(Pos.CENTER);
		Scene scene = new Scene(view);
		scene.getStylesheets().add(PinView.class.getResource("PinView.css").toExternalForm());
		DepositStage.setScene(scene);
		DepositStage.setTitle("Deposit Amount");
		
		
		
		buttons[11].setOnAction( e -> {
			amount = tf.getText();	
			DepositStage.close();
			
		});
		

		DepositStage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		
		DepositStage.showAndWait();
		// OK means to send text
	}
	
	public static String getAmount() {
		return amount;
	}
}

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PinView {
	
	private static String pin = null;
	
	public static void openPinWindow() {
		pin = null;

		Stage pinCode = new Stage();
		pinCode.initModality(Modality.APPLICATION_MODAL);

		pinCode.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		
		PasswordField pf = new PasswordField();
		pf.setEditable(false);
		pf.setPrefSize(300,100);
		pf.setStyle(" -fx-font-size: 28px;");
		pf.setAlignment(Pos.CENTER);
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10));
		gp.setAlignment(Pos.CENTER);
		gp.resize(400, 800);
		
		
		Label label = new Label("Type your 4 digit unique PINCODE here");
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
				pf.setText( pf.getText() + button.getText() );
				
				if (pf.getText().length() > 4) {
					String text = pf.getText();
					pf.setText(text.substring(0, 4));
					label.setText("Maximum length is of 4");
					label.setTextFill(Color.RED);
				}
				else {
					label.setText("Type your 4 digit unique PINCODE here");
					label.setTextFill(Color.INDIANRED);
				}
			});
		}
		
		buttons[9].setOnAction( e-> {
			pf.setText("");
		});
		
		
		VBox view = new VBox(pf,gp,label);
		view.setStyle("-fx-background-color: #efbf13");
		view.setAlignment(Pos.CENTER);
		Scene scene = new Scene(view);
		scene.getStylesheets().add(PinView.class.getResource("PinView.css").toExternalForm());
		pinCode.setScene(scene);
		pinCode.setTitle("Enter 4 digit PIN");
		
		buttons[11].setOnAction( e -> {
			if ( pf.getText().length() == 4) {
				PinView.pin = pf.getText();
				pinCode.close();
			}
		});
		pinCode.showAndWait();
		// OK means to send text
				
	}
	
	public static String getPin() {
		return pin;
	}
	
}

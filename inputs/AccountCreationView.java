import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AccountCreationView {
	private static boolean doneCorrectly = false;
	private static GridPane grid;
	private static TextField name;
	private static TextField lastName;
	private static PasswordField pw;
	private static TextField balance;
	private static Button submit;
	private static Button clear;
	private static Label prompt;
	
	public static void launch() {

				
		doneCorrectly = false;
		Stage stage = new Stage();
		stage.setTitle("Create your account!");

		stage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("warning (1).png")));
		
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(event -> stage.close());

		
		grid = new GridPane();
		grid.setPrefSize(400,100);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setStyle("-fx-background-color: ffcd44; ");

		// Defining the Name text field
		name = new TextField();
		name.setPromptText("Enter your name:");
		name.setPrefColumnCount(10);
		name.getText();
		GridPane.setConstraints(name, 0, 0);
		grid.getChildren().add(name);

		// Defining the Last Name text field
		lastName = new TextField();
		lastName.setPromptText("Enter your last name:");
		GridPane.setConstraints(lastName, 0, 1);
		grid.getChildren().add(lastName);

		// Defining the Comment text field
		balance = new TextField();
		balance.setPrefColumnCount(25);
		balance.setPromptText("Enter your initial balance: (optional)");
		
		balance.textProperty().addListener( e-> {
			if ( (balance.getText().length() >= 2) && !balance.getText().matches("[0-9]+") ) {
				prompt.setText("Please enter numeric digits only for balance :( ");
				prompt.setTextFill(Color.DARKRED);
			        Platform.runLater(() -> { 
			            balance.clear(); 
			        }); 
			}
		});
		
		
		
		GridPane.setConstraints(balance, 0, 2);
		grid.getChildren().add(balance);

		// Defining the Secret PINCODE field
		pw = new PasswordField();
		pw.setPromptText("Enter your unique 4 digit pincode");
		pw.setPrefColumnCount(15);
		pw.setEditable(true);
		GridPane.setConstraints(pw, 0, 3);
		grid.getChildren().add(pw);
		//addTextLimiter(pw, 4);
		pw.textProperty().addListener( e-> {
			
			
			if (!pw.getText().matches("[0-9]+") && pw.getText().length() >= 2) {
				prompt.setText("Please enter numeric digits only for PIN :( ");
				prompt.setTextFill(Color.DARKRED);
				doneCorrectly = false;
				Platform.runLater(() -> { 
		            pw.clear(); 
		        }); 
				
			}
			
			if (pw.getText().length() > 4) {
				prompt.setText("Length of PIN is 4! ");
				prompt.setTextFill(Color.DARKRED);
				String s = pw.getText().substring(0, 4);
				pw.setText(s);
			}
		});
		
		// Defining the Submit button
		submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 0);
		grid.getChildren().add(submit);

		// Defining the Clear button
		clear = new Button("Clear");
		GridPane.setConstraints(clear, 1, 1);
		grid.getChildren().add(clear);

		// Adding a Label
		prompt = new Label();
		GridPane.setConstraints(prompt, 0, 4);
		GridPane.setColumnSpan(prompt, 2);
		grid.getChildren().add(prompt);

		grid.setOnKeyPressed( e-> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				if ((balance.getText() == null || name.getText().isEmpty()) || lastName.getText().isEmpty()
						|| pw.getText().isEmpty()) {
					prompt.setText("Fields left blank! :(");
					prompt.setTextFill(Color.DARKRED);
					doneCorrectly = false;
				}
				
				else if ( pw.getText().length() != 4) {
					prompt.setText("PIN must be 4 digits long");
					prompt.setTextFill(Color.DARKRED);
					doneCorrectly = false;
				}

				else {
					prompt.setText(name.getText() + " " + lastName.getText() + ", " + "thank you for creating an account with us!");
					prompt.setTextFill(Color.MEDIUMSEAGREEN);
					doneCorrectly = true;
					delay.play();
				}
				
			}
		});
		
		submit.setOnAction(e -> {
			if ((balance.getText() == null || name.getText().isEmpty()) || lastName.getText().isEmpty()
					|| pw.getText().isEmpty()) {
				prompt.setText("Fields left blank! :(");
				prompt.setTextFill(Color.DARKRED);
				doneCorrectly = false;
			}
			
			else if ( pw.getText().length() != 4) {
				prompt.setText("PIN must be 4 digits long");
				prompt.setTextFill(Color.DARKRED);
				doneCorrectly = false;
			}

			else {
				prompt.setText(name.getText() + " " + lastName.getText() + ", " + "thank you for creating an account with us!");
				prompt.setTextFill(Color.MEDIUMSEAGREEN);
				doneCorrectly = true;
				delay.play();
			}
		});

		clear.setOnAction(e -> {
			name.clear();
			lastName.clear();
			balance.clear();
			prompt.setText(null);
			pw.clear();
		});
		

		stage.setScene(new Scene(grid));
		stage.setTitle("Create your account!");
		
		stage.showAndWait();
	}

	public static void addTextLimiter(final PasswordField pf, final int maxLength) {
		pf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (pf.getText().length() > maxLength) {
					prompt.setText("Length of PIN is 4! ");
					prompt.setTextFill(Color.DARKRED);
					String s = pf.getText().substring(0, maxLength);
					pf.setText(s);
				}
				
				Platform.runLater(() -> { 
		            pf.clear(); 
		        }); 
			}
		});

	}

	public static boolean getdoneCorrectly() {
		return doneCorrectly;
	}
	
	public static Account createAccount() throws IOException {
		if (balance.getText().isEmpty() || balance.getText() == null) {
			return new Account(name.getText() + " " + lastName.getText(), pw.getText());
		}
		else return new Account(name.getText() + " " + lastName.getText(), pw.getText(), Integer.parseInt(balance.getText()));
	}
}
	

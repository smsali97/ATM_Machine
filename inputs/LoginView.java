import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginView {
	private static boolean flag = false;	
	private static TextField tf;
	private static PasswordField pf;
	
	public static boolean getFlag() {
		return flag;
	}
	public static String getUsername() {
		return tf.getText();
	}
	
	public static String getPassword() {
		return pf.getText();
	}
	
	public static void launch() {
		Stage stage = new Stage(StageStyle.DECORATED);

		stage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		
		Button OK = new Button("OK");
		tf = new TextField();
		tf.setPromptText("Type your username");
		
		/*
		tf.textProperty().addListener( e-> {
			tf.setText(tf.getText().toUpperCase());
		});*/
		
		Label prompt = new Label();
		
		pf = new PasswordField();
		pf.setPromptText("Type your password");
		pf.textProperty().addListener( e-> {
			if (!pf.getText().matches("[0-9]+") && pf.getText().length() >= 1) {
				prompt.setText("Please enter numeric digits only for PIN :( ");
				prompt.setTextFill(Color.DARKORANGE);
				flag = false;
				
				Platform.runLater(() -> { 
		            pf.clear(); 
		        }); 
			}
			if (pf.getText().length() > 4) {
				prompt.setText("Length of PIN is 4! ");
				prompt.setTextFill(Color.DARKORANGE);
				String s = pf.getText().substring(0, 4);
				pf.setText(s);
			}
		});
		pf.setOnKeyPressed( e-> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				if ( pf.getText().length() != 4) {
					prompt.setText("PIN must be 4 digits long");
					prompt.setTextFill(Color.DARKORANGE);
					flag = false;
				}
				else {
					flag = true;
					stage.close();
				}
			}	
		
		});
		
		OK.setOnAction( e-> {
			if ( pf.getText().length() != 4) {
				prompt.setText("PIN must be 4 digits long");
				prompt.setTextFill(Color.DARKORANGE);
				flag = false;
			}
			else {
				flag = true;
				stage.close();
			}
		});
		
		Label msg = new Label("Press Escape Key to exit! ");
		msg.setTextFill(Color.DARKSEAGREEN);
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		vbox.setPrefSize(250, 250);
		vbox.setStyle("-fx-background-color: #1a2470;");
		vbox.getChildren().addAll(new ImageView(new Image(LoginView.class.getClassLoader().getResourceAsStream("login.png"))),tf,pf,OK,prompt,msg);
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		
		scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					System.exit(0);
				}
				
			}
		});
		
		stage.setTitle("Please login");
		stage.showAndWait();
	}
}

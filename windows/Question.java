import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Question {
	private static boolean flag = false;
	private static Button yes = new Button("Yes");
	private static Button no = new Button("No");
	private static Label label;
	private static Image yellowSign = new Image(Question.class.getClassLoader().getResourceAsStream("question.png"));
	private static ImageView yellowLabel = new ImageView(yellowSign);

	public static Stage display(String text) {
		label = new Label(text);
		label.setTextFill(Color.ANTIQUEWHITE);
		label.setFont(Font.font("Helvetica"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Message");

		stage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		flag = false;
				yellowLabel.setFitHeight(50);
		yellowLabel.setFitWidth(50);

		HBox hb = new HBox(10);
		hb.getChildren().addAll(yellowLabel, label, yes, no);
		hb.setPadding(new Insets(10, 10, 10, 10));
		hb.setAlignment(Pos.CENTER);

		hb.setStyle("-fx-background-color: #336699;");

		yes.setStyle("-fx-background-color: #ffbb02;");
		no.setStyle("-fx-background-color: #ffbb02;");
		
		Scene scene = new Scene(hb);

		// if button clicked
		yes.setOnAction(e -> {
			flag = true;
			stage.close();
		});
		
		no.setOnAction(e -> {
			flag = false;
			stage.close();
		});

		yes.setOnMouseEntered(e -> {
			yes.setEffect(new DropShadow());
		});

		yes.setOnMouseExited(e -> {
			yes.setEffect(new DropShadow(0, null));
		});
		
		no.setOnMouseEntered(e -> {
			no.setEffect(new DropShadow());
		});

		no.setOnMouseExited(e -> {
			no.setEffect(new DropShadow(0, null));
		});

		yellowLabel.setOnMouseEntered(e -> {
			yellowLabel.setEffect(new DropShadow());
		});

		yellowLabel.setOnMouseExited(e -> {
			yellowLabel.setEffect(new DropShadow(0, null));
		});

		stage.setScene(scene);
		stage.setTitle(text);
		return stage;
	}
	
	public static boolean getFlag() {
		return flag;
	}
}

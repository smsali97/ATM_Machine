
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {
	private static Image image = new Image(LoadingScreen.class.getClassLoader().getResourceAsStream("animation.gif"));


	public static Stage start() {
		Stage primaryStage = new Stage();

		primaryStage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		// loading text
		Label label = new Label("Processing your requests");
		label.setFont(Font.font("Showcard Gothic", 40));
		// loading bar
		ProgressBar pb = new ProgressBar();
		pb.setPrefSize(200, 40);
		pb.setStyle("-fx-accent: yellow");
		// image container
		ImageView iv = new ImageView(image);

		BorderPane bp = new BorderPane();
		VBox vbox = new VBox(label, pb);
		vbox.setAlignment(Pos.CENTER);
		vbox.setBackground(new Background(new BackgroundFill(Color.KHAKI, null, null)));
		bp.setCenter(iv);
		bp.setBottom(vbox);
		BorderPane.setAlignment(vbox, Pos.CENTER);

		Scene scene = new Scene(bp);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Loading");
		primaryStage.show();

		PauseTransition time = new PauseTransition(Duration.seconds(1));
		time.setOnFinished(e -> {
			if (label.getText().equals("Processing your requests...")) {
				label.setText(label.getText().replaceAll(".", ""));
			} else {
				label.setText(label.getText() + "..");

			}
		});
		time.setCycleCount(3);
		time.play();
		
		return primaryStage;
	}

}

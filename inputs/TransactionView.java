import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TransactionView {

	
	private static ScrollPane sp;

	public static void start() throws IOException{
		Stage primaryStage = new Stage();

		primaryStage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
		// TODO Auto-generated method stub
		BorderPane bp = new BorderPane();
		
		TextArea ta = new TextArea();
		ta.setPrefColumnCount(10);
		ta.setPrefRowCount(TextArea.DEFAULT_PREF_ROW_COUNT);
		ta.setEditable(false);
		ta.setWrapText(true);
		ta.setFont(Font.font(19));
		ta.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas;"
				+ " -fx-highlight-fill: #00ff00;"
				+ " -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");
		ta.setText(AccountWriter.readTransactions());
		
		sp = new ScrollPane(ta);
		sp.setId("Scrolly");
		bp.setCenter(ta);
		Label l = new Label("Your transactions History: ");
		l.setFont(Font.font("Helvetica", 30));
		l.setTextFill(Color.WHITE);
		bp.setTop(l);
		BorderPane.setAlignment(ta, Pos.CENTER);
		BorderPane.setAlignment(l, Pos.CENTER);
		bp.setStyle("-fx-background-color: black;");
		Scene scene = new Scene(bp);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Transaction History");
		primaryStage.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("warning (1).png")));
		
		primaryStage.show();
	}
	


}

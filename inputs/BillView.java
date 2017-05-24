import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillView {
	private static int payment = -1;
	private static String[] billTypes = { "Mobile", "Water", "Electricity" };
	static ComboBox<String> cbo = new ComboBox<>();
	private static Label prompt = new Label("Select the type of bill you wish to pay");
	private static Bill[] bills = BillingSystem.getBills();

	private static FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
	
	private static TextArea tA = new TextArea("");
	private static Label displayText = new Label("");
	
	private static ToggleGroup tg = new ToggleGroup();
	ArrayList<RadioButton> list = new ArrayList<>();
	private static Button submit = new Button("Yes");
	private static int selectedBillId = -1;

	public static void launch() {
		tA.setFont(Font.font("Cambria",18));
		tA.setStyle("-fx-control-inner-background:#e54b4b; -fx-text-fill: #ffffff;");
		tA.setEditable(true);
		tA.setWrapText(true);		

		//tA.setMaxSize(100, s170);
		ScrollPane sp = new ScrollPane(tA);
		sp.setId("Hullo");
		selectedBillId = -1;
		Stage s = new Stage();
		s.initModality(Modality.APPLICATION_MODAL);
		HBox top = new HBox(10);
		top.setPadding(new Insets(10));
		top.getChildren().addAll(prompt, cbo);

		cbo.setPrefWidth(400);
		cbo.setValue("Mobile");

		ObservableList<String> items = FXCollections.observableArrayList(billTypes);
		cbo.getItems().clear();
		cbo.getItems().addAll(items);

		cbo.setOnAction(e -> {
			if ( cbo.getValue() != null) {
			flowPane.getChildren().clear();
			String x = cbo.getValue();
			if (x.equals("Mobile"))
				viewMobile();
			else if (x.equals("Electricity"))
				viewElectricity();
			else if (x.equals("Water"))
				viewWater();
			}
		});

		BorderPane bp = new BorderPane();
		bp.setTop(top);
		flowPane.setVgap(20);
		bp.setLeft(flowPane);
		BorderPane.setAlignment(flowPane, Pos.CENTER);
		BorderPane.setMargin(flowPane, new Insets(20, 40, 20, 40));
		bp.setRight(tA);
		BorderPane.setMargin(tA, new Insets(10));
		BorderPane.setAlignment(tA, Pos.CENTER);
		tA.setMaxHeight(120);
		tA.setMaxWidth(350);
		BorderPane.setMargin(tA, new Insets(40, 100, 60, 100));
		tg.selectedToggleProperty().addListener(e -> {
			selectedBillId = Integer.parseInt(((RadioButton) tg.getSelectedToggle()).getText().substring(9)) - 1;
		});

		submit.setOnAction(e -> {
			if (payment != -1 && selectedBillId != -1) {
				int x = Main.getCurrentUser().getBalance();
				if (x > payment) {
					bills[selectedBillId].setPaid();

					try {
						AccountWriter.modifyBalance(Main.getCurrentUser().getUsername(),
								Main.getCurrentUser().getBalance());
						AccountWriter.writeTransaction(new Transaction(payment, "Utility Bill"));
						Message.display("Bill Paid!");
					} catch (IOException e1) {
						Message.display("Error! 404! ");
						e1.printStackTrace();
					}
					Main.getCurrentUser().setBalance(x - payment);
				} else {
					Message.display("Insufficient funds!");
				}
			}
		});

	
		HBox panel = new HBox(10);
		panel.setAlignment(Pos.CENTER);
		panel.getChildren().addAll(new Label("Confirm payment of this bill?"), submit);
		panel.setPadding(new Insets(10));
		bp.setBottom(panel);
		BorderPane.setAlignment(panel, Pos.CENTER);
		bp.setStyle("-fx-background-color: #ffe568;");

		Scene scene = new Scene(bp);
		s.setTitle("Pay your bills");
		s.setScene(scene);
		s.show();

		s.getIcons().add(
				   new Image(Question.class.getClassLoader().getResourceAsStream("cash-machine.png")));
		
	}

	public static void viewMobile() {
		for (Bill bill : bills) {
			if (bill instanceof MobileBill) {
				RadioButton button = new RadioButton(bill.getBillNo());
				button.setToggleGroup(tg);
				button.setGraphic(new ImageView(new Image(BillView.class.getClassLoader().getResourceAsStream("smartphone.png"))));
				button.setTextFill(Color.DARKRED);
				flowPane.getChildren().add(button);
				setValue(button);
			}
		}
	}

	public static void viewElectricity() {
		for (Bill bill : bills) {
			if (bill instanceof ElectricityBill) {
				RadioButton button = new RadioButton(bill.getBillNo());
				flowPane.getChildren().add(button);
				button.setTextFill(Color.DARKRED);
				button.setToggleGroup(tg);
				button.setGraphic(new ImageView(new Image(BillView.class.getClassLoader().getResourceAsStream("wifi-signal.png"))));
				setValue(button);
			}
		}
	}

	public static void viewWater() {

		for (Bill bill : bills) {
			if (bill instanceof WaterBill) {
				RadioButton button = new RadioButton(bill.getBillNo());
				flowPane.getChildren().add(button);
				button.setToggleGroup(tg);
				button.setTextFill(Color.DARKRED);
				button.setGraphic(new ImageView(new Image(LoadingScreen.class.getClassLoader().getResourceAsStream("drop (1).png"))));
				setValue(button);
			}
		}
	}

	public static void setValue(RadioButton button) {
		button.setOnAction(e -> {
			if (button.isSelected()) {
				String number = button.getText().substring(9);
				int n = Integer.parseInt(number) - 1;
				if (bills[n].getStatus()) {
					button.setTextFill(Color.MEDIUMSEAGREEN);
				}
				tA.setText(bills[n].getDetails() + "\n");
				payment = (int) Math.ceil(bills[n].getAmount());
				displayText.setText("Bill Amount:\n" + "Rs." + payment);
				tA.setText(tA.getText() + "\n" + displayText.getText());
			}
		});
	}

}

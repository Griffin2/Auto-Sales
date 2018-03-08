package HOT10and11;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Auto extends Application {
	final double OIL_SERVICE = 26;
	final double LUBE_SERVICE = 18;
	final double RADIATOR_SERVICE = 30;
	final double TRANSMISSION_SERIVCE = 80;
	final double INSPECTION_SERIVCE = 15;
	final double MUFFLER_REPLACEMENT = 100;
	final double TIRE_ROTATION = 20;

	final double HOURLY_FEE = 20;

	private CheckBox[] boxes;
	private TextField input;
	private TextField discount;
	private TextField charges;
	private double total = 0.00;
	private Label lblTotal;
	private RadioButton rdbtn1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage gui) throws Exception {
		GridPane gp = new GridPane();
		Label lbl = new Label("Ranken's Automotive Maintenance");
		Label lblSelect = new Label("-------------------------------------------\n" + "Routine Services"
				+ "\n-------------------------------------------");
		lblTotal = new Label("Total: " + total);
		lblTotal.setPadding(new Insets(20));

		HBox hb = new HBox(lblSelect);
		hb.setPadding(new Insets(10));

		gp.add(hb, 1, 0);
		gp.setPadding(new Insets(10, 20, 5, 10));

		rdbtn1 = new RadioButton("Discount");
		gp.add(rdbtn1, 2, 0);
		discount = new TextField();
		discount.setMaxWidth(50);
		discount.setDisable(true);

		rdbtn1.setOnAction(event -> {
			if (!rdbtn1.isSelected()) {
				discount.setDisable(true);
			} else if (rdbtn1.isSelected()) {
				discount.setDisable(false);
			} else {
				discount.setDisable(true);
			}
		});

		gp.add(discount, 2, 1);
		boxes = new CheckBox[7];
		boxes[0] = new CheckBox();
		boxes[0].setText("Oil Change ($" + OIL_SERVICE + ")");
		gp.add(boxes[0], 1, 1);

		boxes[1] = new CheckBox();
		boxes[1].setText("Lube Job ($" + LUBE_SERVICE + ")");
		gp.add(boxes[1], 1, 2);

		boxes[2] = new CheckBox();
		boxes[2].setText("Raidator Flush ($" + RADIATOR_SERVICE + ")");
		gp.add(boxes[2], 1, 3);

		boxes[3] = new CheckBox();
		boxes[3].setText("Transmission Flush ($" + TRANSMISSION_SERIVCE + ")");
		gp.add(boxes[3], 1, 4);

		boxes[4] = new CheckBox();
		boxes[4].setText("Inspection ($" + INSPECTION_SERIVCE + ")");
		gp.add(boxes[4], 1, 5);

		boxes[5] = new CheckBox();
		boxes[5].setText("Muffler Replacement ($" + MUFFLER_REPLACEMENT + ")");
		gp.add(boxes[5], 1, 6);

		boxes[6] = new CheckBox();
		boxes[6].setText("Tire Rotation ($" + TIRE_ROTATION + ")");
		gp.add(boxes[6], 1, 7);

		Label parts = new Label("Parts Charges: $");
		parts.setPadding(new Insets(5, 5, 5, 2));

		charges = new TextField();
		charges.setMaxWidth(30);
		HBox hb1 = new HBox(parts, charges);
		hb1.setPadding(new Insets(4));
		gp.add(hb1, 1, 9);

		Label hr = new Label("Hours of Labor: ");
		hr.setPadding(new Insets(5, 5, 5, 2));

		input = new TextField();
		input.setMaxWidth(30);
		HBox hb2 = new HBox(hr, input);
		hb2.setPadding(new Insets(4));
		gp.add(hb2, 1, 10);

		Label nonRoutines = new Label("\n-------------------------------------------\n" + "Nonroutine Services"
				+ "\n-------------------------------------------");
		gp.add(nonRoutines, 1, 8);

		Button btnClr = new Button("Exit");
		gp.add(btnClr, 9, 12);
		btnClr.setOnAction(e -> {
			gui.close();
		});

		Button btnBuy = new Button("Calculate Charges");
		btnBuy.setMinWidth(30);
		gp.add(btnBuy, 1, 12);
		btnBuy.setOnAction(new CheckoutHandler());

		Scene container = new Scene(gp, 400, 400);
		gui.setTitle(lbl.getText());
		gui.setScene(container);
		gui.show();
	}

	class CheckoutHandler implements EventHandler<ActionEvent> {
		double[] serviceCosts = { OIL_SERVICE, LUBE_SERVICE, RADIATOR_SERVICE, TRANSMISSION_SERIVCE, INSPECTION_SERIVCE,
				MUFFLER_REPLACEMENT, TIRE_ROTATION };

		@Override
		public void handle(ActionEvent e) {
			double rate = 0;
			double parts = 0;
			double disct = 0;
			try {
				disct = (Double.parseDouble(discount.getText()));
				rate = (Double.parseDouble(input.getText()) * HOURLY_FEE);
				parts = (Double.parseDouble(charges.getText()));
			} catch (NumberFormatException error) {
				charges.setText("err");
				input.setText("err");
				rate = 0;
				parts = 0;
				disct = 0;
				// error.printStackTrace();
			}

			for (int i = 0; i < serviceCosts.length; i++) {
				if (boxes[i].isSelected()) {
					total += serviceCosts[i];
				}
			}

			lblTotal.setText("Total : $" + (total += ((rate + parts) - disct)));

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Customer Checkout");
			alert.setHeaderText("Total Charges");
			alert.setContentText(lblTotal.getText());
			alert.showAndWait();

			rdbtn1.setSelected(false);
			total = 0;
			for (int i = 0; i < serviceCosts.length; i++) {
				if (boxes[i].isSelected()) {
					boxes[i].setSelected(false);
				}
			}
			input.setText("0");
			charges.setText("0");
		}
	}
}

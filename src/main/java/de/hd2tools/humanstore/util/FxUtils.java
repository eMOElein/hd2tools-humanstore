package de.hd2tools.humanstore.util;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FxUtils {

	public static void editText(StringProperty string, String title, boolean modal) {
		Stage stage = new Stage();
		stage.setTitle(title);

		if (modal) {
			stage.initModality(Modality.APPLICATION_MODAL);
		}

		TextArea txa = new TextArea();
		txa.textProperty().bindBidirectional(string);
		VBox v = new VBox();
		v.getChildren().add(txa);
		VBox.setVgrow(txa, Priority.ALWAYS);

		stage.setScene(new Scene(v));
		stage.show();

	}

	public static void makeIntegerOnly(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}?")) {
					System.out.println("Rejecting: " +newValue);
					textField.setText(oldValue);
				}
			}
		});
	}

	public static void makeNumericOnly(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,6})?")) {
					System.out.println("Rejecting: " +newValue);
					textField.setText(oldValue);
				}
			}
		});
	}

	public static void resetTextFields(String value, TextField... textFields) {
		for (TextField txt : textFields) {
			txt.setText(value);
		}
	}

	public static void resetTextFields(TextField... textFields) {
		resetTextFields("", textFields);
	}

	public static void toGrid(GridPane p, int row, String name, Node node) {
		Label l = new Label(name);

		GridPane.setConstraints(l, 0, row);
		GridPane.setConstraints(node, 1, row);
		GridPane.setHgrow(l, Priority.NEVER);
		GridPane.setHgrow(node, Priority.ALWAYS);

		p.getChildren().addAll(l, node);
	}

}

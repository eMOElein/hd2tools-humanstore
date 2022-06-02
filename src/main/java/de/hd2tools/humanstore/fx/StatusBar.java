package de.hd2tools.humanstore.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class StatusBar extends HBox {

	private IntegerProperty sizeProperty = new SimpleIntegerProperty(0);

	public StatusBar(HumanStoreApplication app) {

		Label lNumber = new Label();
		Bindings.bindBidirectional(lNumber.textProperty(), sizeProperty, new NumberStringConverter());
		app.humanStoreProperty().addListener((observable, old, nevv) -> sizeProperty.bind(Bindings.size(nevv.getHumans())));

		getChildren().addAll(new Label("Humans: "), lNumber);
	}
}

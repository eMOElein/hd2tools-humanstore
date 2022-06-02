package de.hd2tools.humanstore.fx;

import java.util.Locale;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.Rank;
import de.hd2tools.humanstore.util.FxUtils;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.converter.NumberStringConverter;

public class HumanEditor extends HBox {

	private ObjectProperty<Human> humanProperty = new SimpleObjectProperty<Human>();

	private TextField txtFirstname = new TextField();
	private TextField txtSurname = new TextField();
	private TextField txtNickname = new TextField();
	private TextField txtPortrait = new TextField();
	private ComboBox<Rank> cbxRank = new ComboBox<Rank>();

	private TextField txtStatBiographyId = new TextField();
	private TextField txtStatHealth = new TextField();
	private TextField txtStatStrength = new TextField();
	private TextField txtStatEndurance = new TextField();
	private TextField txtStatMorale = new TextField();
	private TextField txtStatShooting = new TextField();
	private TextField txtStatExperience = new TextField();
	private TextField txtStatStealth = new TextField();
	private TextField txtStatFirstAid = new TextField();
	private TextField txtStatLockpicking = new TextField();

	public HumanEditor() {
		FxUtils.makeIntegerOnly(txtStatHealth);
		FxUtils.makeNumericOnly(txtStatStrength);
		FxUtils.makeNumericOnly(txtStatEndurance);
		FxUtils.makeIntegerOnly(txtStatMorale);
		FxUtils.makeNumericOnly(txtStatShooting);
		FxUtils.makeIntegerOnly(txtStatExperience);
		FxUtils.makeNumericOnly(txtStatStealth);
		FxUtils.makeNumericOnly(txtStatFirstAid);
		FxUtils.makeNumericOnly(txtStatLockpicking);
		humanProperty.addListener(this::onHumanChanged);
		cbxRank.setItems(FXCollections.observableArrayList(Rank.values()));
		cbxRank.setMaxWidth(Double.MAX_VALUE);

		GridPane p = new GridPane();
		p.setHgap(5);
		p.setVgap(5);
		int row = 0;

		FxUtils.toGrid(p, row++, "Firstname", txtFirstname);
		FxUtils.toGrid(p, row++, "Surname", txtSurname);
		FxUtils.toGrid(p, row++, "Nickname", txtNickname);
		FxUtils.toGrid(p, row++, "Portrait", txtPortrait);
		FxUtils.toGrid(p, row++, "Rank", cbxRank);
		FxUtils.toGrid(p, row++, "Biography ID", txtStatBiographyId);
		FxUtils.toGrid(p, row++, "Health", txtStatHealth);
		FxUtils.toGrid(p, row++, "Strength", txtStatStrength);
		FxUtils.toGrid(p, row++, "Endurance", txtStatEndurance);
		FxUtils.toGrid(p, row++, "Morale", txtStatMorale);
		FxUtils.toGrid(p, row++, "Shooting", txtStatShooting);
		FxUtils.toGrid(p, row++, "Experience", txtStatExperience);
		FxUtils.toGrid(p, row++, "Stealth", txtStatStealth);
		FxUtils.toGrid(p, row++, "First Aid", txtStatFirstAid);
		FxUtils.toGrid(p, row++, "Lockpicking", txtStatLockpicking);

		getChildren().addAll(p);
		HBox.setHgrow(p, Priority.ALWAYS);
	}

	public Human getHuman() {
		return humanProperty().get();
	}

	public ObjectProperty<Human> humanProperty() {
		return humanProperty;
	}

	private void onHumanChanged(Observable observable, Human old, Human nevv) {
		if (old != null) {
			txtFirstname.textProperty().unbindBidirectional(old.firstnameProperty());
			txtSurname.textProperty().unbindBidirectional(old.surnameProperty());
			txtNickname.textProperty().unbindBidirectional(old.nicknameProperty());
			txtPortrait.textProperty().unbindBidirectional(old.portraitProperty());
			old.rankProperty().unbind();
			Bindings.unbindBidirectional(txtStatBiographyId.textProperty(), old.getStatus().biographyIdProperty());
			Bindings.unbindBidirectional(txtStatHealth.textProperty(), old.getStatus().healthProperty());
			Bindings.unbindBidirectional(txtStatStrength.textProperty(), old.getStatus().strengthProperty());
			Bindings.unbindBidirectional(txtStatEndurance.textProperty(), old.getStatus().enduranceProperty());
			Bindings.unbindBidirectional(txtStatMorale.textProperty(), old.getStatus().moraleProperty());
			Bindings.unbindBidirectional(txtStatShooting.textProperty(), old.getStatus().shootingProperty());
			Bindings.unbindBidirectional(txtStatExperience.textProperty(), old.getStatus().experienceProperty());
			Bindings.unbindBidirectional(txtStatStealth.textProperty(), old.getStatus().stealthProperty());
			Bindings.unbindBidirectional(txtStatFirstAid.textProperty(), old.getStatus().firstAidProperty());
			Bindings.unbindBidirectional(txtStatLockpicking.textProperty(), old.getStatus().lockpickingProperty());

		}

		if (nevv != null) {
			txtFirstname.textProperty().bindBidirectional(nevv.firstnameProperty());
			txtSurname.textProperty().bindBidirectional(nevv.surnameProperty());
			txtNickname.textProperty().bindBidirectional(nevv.nicknameProperty());
			txtPortrait.textProperty().bindBidirectional(nevv.portraitProperty());
			cbxRank.getSelectionModel().select(nevv.getRank());
			nevv.rankProperty().bind(cbxRank.getSelectionModel().selectedItemProperty());

			Locale locale = Locale.US;
			Bindings.bindBidirectional(txtStatBiographyId.textProperty(), nevv.getStatus().biographyIdProperty(), new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatHealth.textProperty(), nevv.getStatus().healthProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatStrength.textProperty(), nevv.getStatus().strengthProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatEndurance.textProperty(), nevv.getStatus().enduranceProperty(),new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatMorale.textProperty(), nevv.getStatus().moraleProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatShooting.textProperty(), nevv.getStatus().shootingProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatExperience.textProperty(), nevv.getStatus().experienceProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatStealth.textProperty(), nevv.getStatus().stealthProperty(),new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatFirstAid.textProperty(), nevv.getStatus().firstAidProperty(),	new NumberStringConverter(locale));
			Bindings.bindBidirectional(txtStatLockpicking.textProperty(), nevv.getStatus().lockpickingProperty(), new NumberStringConverter(locale));

		} else {

		}
	}

	public void setHuman(Human human) {
		humanProperty().set(human);
	}
}

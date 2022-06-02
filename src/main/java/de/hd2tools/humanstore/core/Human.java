package de.hd2tools.humanstore.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Human {

	private StringProperty firstnameProperty = new SimpleStringProperty();
	private StringProperty surnameProperty = new SimpleStringProperty();
	private StringProperty nicknameProperty = new SimpleStringProperty();
	private StringProperty portraitProperty = new SimpleStringProperty();
	private ObjectProperty<Rank> rankProperty = new SimpleObjectProperty<Rank>();
	private ObjectProperty<byte[]> unknown1Property = new SimpleObjectProperty<byte[]>();
	private ObjectProperty<byte[]> unknown2Property = new SimpleObjectProperty<byte[]>();
	private ObjectProperty<byte[]> unknown3Property = new SimpleObjectProperty<byte[]>();
	private ObjectProperty<Stats> statusProperty = new SimpleObjectProperty<Stats>();

	public StringProperty firstnameProperty() {
		return firstnameProperty;
	}

	public String getFirstname() {
		return firstnameProperty().get();
	}

	public String getNickname() {
		return nicknameProperty().get();
	}

	public String getPortrait() {
		return portraitProperty().get();
	}

	public Rank getRank() {
		return rankProperty().get();
	}

	public Stats getStatus() {
		return statusProperty().get();
	}

	public String getSurname() {
		return surnameProperty().get();
	}

	public byte[] getUnknown1() {
		return unknown1Property().get();
	}

	public byte[] getUnknown2() {
		return unknown2Property().get();
	}

	public byte[] getUnknown3() {
		return unknown3Property().get();
	}

	public StringProperty nicknameProperty() {
		return nicknameProperty;
	}

	public StringProperty portraitProperty() {
		return portraitProperty;
	}

	public ObjectProperty<Rank> rankProperty() {
		return rankProperty;
	}

	public void setFirstname(String firstname) {
		firstnameProperty().set(firstname);
	}

	public void setNickname(String nickname) {
		nicknameProperty().set(nickname);
	}

	public void setPortrait(String portrait) {
		portraitProperty().set(portrait);
	}

	public void setRank(Rank rank) {
		rankProperty().set(rank);
	}

	public void setStatus(Stats status) {
		statusProperty().set(status);
	}

	public void setSurname(String surname) {
		surnameProperty().set(surname);
	}

	public void setUnknown1(byte[] bytes) {
		unknown1Property().set(bytes);
	}

	public void setUnknown2(byte[] bytes) {
		unknown2Property().set(bytes);
	}

	public void setUnknown3(byte[] bytes) {
		unknown3Property().set(bytes);
	}

	public ObjectProperty<Stats> statusProperty() {
		return statusProperty;
	}

	public StringProperty surnameProperty() {
		return surnameProperty;
	}

	public String toString() {
		return getFirstname() + " "+getSurname();
	}

	public ObjectProperty<byte[]> unknown1Property() {
		return unknown1Property;
	}

	public ObjectProperty<byte[]> unknown2Property() {
		return unknown2Property;
	}

	public ObjectProperty<byte[]> unknown3Property() {
		return unknown3Property;
	}

}

package de.hd2tools.humanstore.core;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HumanStore {

	private ObservableList<Human> humans = FXCollections.observableArrayList(new ArrayList<Human>());

	public ObservableList<Human> getHumans() {
		return humans;
	}

	public void setHumans(List<Human> humans) {
		this.humans.addAll(humans);
	}

	public void replaceHuman(Human original, Human nevv) {
		int index = humans.indexOf(original);
		if (index >= -1) {
			humans.set(index, nevv);
		}

	}

}

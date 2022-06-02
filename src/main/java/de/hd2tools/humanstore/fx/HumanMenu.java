package de.hd2tools.humanstore.fx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.HumanStore;
import de.hd2tools.humanstore.io.HumanStoreIO;
import de.hd2tools.humanstore.util.DialogUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HumanMenu extends ContextMenu {

	private ObjectProperty<Human> humanProperty = new SimpleObjectProperty<Human>();

	public HumanMenu(Human human, HumanStore humanStore) {
		setHuman(human);

		///// Export /////
		Menu menuExport = new Menu("Export");
		MenuItem exportToBin = new MenuItem("To Bin");
		MenuItem exportToJson = new MenuItem("To Json");

		MenuItem replaceWith = new MenuItem("ReplaceWith");
		replaceWith.setOnAction(e -> replaceWith(human, humanStore));
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e -> delete(humanStore, human));

		exportToBin.setOnAction(e -> exportToBin());
		exportToJson.setOnAction(e -> exportToJson());
		menuExport.getItems().addAll(exportToBin, exportToJson);

		this.getItems().addAll(menuExport);
		getItems().add(new SeparatorMenuItem());
		getItems().addAll(replaceWith);
		getItems().add(new SeparatorMenuItem());
		getItems().addAll(delete);
	}

	private void delete(HumanStore humanStore, Human human) {
		humanStore.getHumans().remove(human);
	}

	private void exportToBin() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("def", "*.def"));
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				HumanStoreIO.writeHumanToBin(getHuman(), out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				DialogUtils.show(e);
			}
		}
	}

	private void exportToJson() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("json", "*.json"));
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				HumanStoreIO.writeHumanToJson(getHuman(), out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				DialogUtils.show(e);
			}
		}
	}

	public Human getHuman() {
		return humanProperty().get();
	}

	public ObjectProperty<Human> humanProperty() {
		return humanProperty;
	}

	private void replaceWith(Human human, HumanStore humanStore) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("human", "*.json", "*.def"));

		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			try {
				Human nevv = HumanStoreIO.readHuman(file);
				humanStore.replaceHuman(human, nevv);
			} catch (Exception e) {
				e.printStackTrace();
				DialogUtils.show(e);
			}
		}
	}

	public void setHuman(Human human) {
		humanProperty().set(human);
	}
}

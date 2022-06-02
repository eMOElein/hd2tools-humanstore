package de.hd2tools.humanstore.fx;

import java.io.File;

import de.hd2tools.humanstore.core.Human;
import de.hd2tools.humanstore.core.HumanStore;
import de.hd2tools.humanstore.io.HumanStoreIO;
import de.hd2tools.humanstore.util.DialogUtils;
import de.hd2tools.humanstore.util.ListViewDnDReorder;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HumanStoreApplication extends Application {

	private static class MainMenu extends MenuBar {

		public MainMenu(HumanStoreApplication app) {
			///// File /////
			Menu fileMenu = new Menu("File");

			MenuItem fileOpenMenu = new MenuItem("Open");
			MenuItem fileSaveMenu = new MenuItem("Save");
			MenuItem fileExitMenu = new MenuItem("Exit");

			fileOpenMenu.setOnAction(e -> app.open());
			fileSaveMenu.setOnAction(e -> app.save());
			fileExitMenu.setOnAction(e -> System.exit(0));

			Menu fileImport = new Menu("Import");

			MenuItem fileImportHuman = new MenuItem("Human");
			fileImportHuman.setOnAction(e -> app.importHuman());

			fileImport.getItems().addAll(fileImportHuman);

			fileMenu.getItems().addAll(fileOpenMenu);
			fileMenu.getItems().add(new SeparatorMenuItem());
			fileMenu.getItems().addAll(fileSaveMenu);
			fileMenu.getItems().add(new SeparatorMenuItem());
			fileMenu.getItems().addAll(fileImport);
			fileMenu.getItems().add(new SeparatorMenuItem());
			fileMenu.getItems().add(fileExitMenu);

			this.getMenus().addAll(fileMenu);
		}

	}

	private static HumanStoreApplication thiz;

	public static HumanStoreApplication get() {
		return thiz;
	}

	public static void main(String[] args) {
		launch();
	}

	private ListView<Human> humanList = new ListView<Human>();

	private ObjectProperty<HumanStore> humanStoreProperty = new SimpleObjectProperty<HumanStore>();

	public HumanStoreApplication() {
		thiz = this;
		humanStoreProperty.addListener(this::onHumanStoreChanged);
		humanList.getSelectionModel().selectedItemProperty().addListener((observable, old, nevv) -> {
			if (nevv != null) {
				humanList.setContextMenu(new HumanMenu(humanList.getSelectionModel().getSelectedItem(), getHumanStore()));
			} else {
				humanList.setContextMenu(null);
			}
		});

		ListViewDnDReorder<Human> dnd = new ListViewDnDReorder<Human>(humanList);

		humanList.setCellFactory(new Callback<ListView<Human>, ListCell<Human>>() {

			@Override
			public ListCell<Human> call(ListView<Human> param) {
				ListCell<Human> cell = new TextFieldListCell<Human>();
				dnd.makeDraggable(cell);
				return cell;
			}

		});

		setHumanStore(new HumanStore());
	}

	public HumanStore getHumanStore() {
		return humanStoreProperty().get();
	}

	public ObjectProperty<HumanStore> humanStoreProperty() {
		return humanStoreProperty;
	}

	public void importHuman() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Human", "*.def", "*.json"));
		File file = fileChooser.showOpenDialog(null);
		if (file == null) {
			return;
		}

		try {
			Human human = HumanStoreIO.readHuman(file);
			importHuman(human);
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtils.show(e);
		}
	}

	private void importHuman(Human human) throws Exception {
		getHumanStore().getHumans().add(human);
	}

	private void onHumanStoreChanged(Observable observable, HumanStore old, HumanStore nevv) {
		humanList.setItems(nevv.getHumans());
	}

	public void open() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("HumanStore", "*.def", "*.json", "*.sav"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null && file.exists()) {
				HumanStore hs = HumanStoreIO.read(file);
				setHumanStore(hs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtils.show(e);
		}
	}

	public void save() {
		if (getHumanStore() == null) {
			return;
		}
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("json", "*.json"), new ExtensionFilter("def", "*.def"));
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				HumanStoreIO.write(getHumanStore(), file);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DialogUtils.show(e);
		}
	}

	public void setHumanStore(HumanStore humanStore) {
		humanStoreProperty().set(humanStore);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		HumanEditor humanEditor = new HumanEditor();
		humanEditor.humanProperty().bind(humanList.getSelectionModel().selectedItemProperty());

		VBox vbox = new VBox();

		SplitPane split = new SplitPane();
		split.getItems().addAll(humanList, humanEditor);
		split.setDividerPosition(0, 0.25);

		StatusBar statusBar = new StatusBar(this);

		VBox.setVgrow(split, Priority.ALWAYS);
		VBox.setVgrow(statusBar, Priority.NEVER);
		vbox.getChildren().addAll(new MainMenu(this), split, statusBar);

		primaryStage.setScene(new Scene(vbox));
		primaryStage.setWidth(800);
		primaryStage.show();
	}

}

package de.hd2tools.humanstore.util;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ListViewDnDReorder<T> {

	private T draggedObject;
	private ListView<T> list;

	public ListViewDnDReorder(ListView<T> list) {
		this.list = list;
	}

	public void makeDraggable(ListCell<T> cell) {
		cell.setOnDragDetected(e -> {
			if (cell.getItem() == null) {
				return;
			}

			Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
			draggedObject = cell.getItem();
			ClipboardContent content = new ClipboardContent();
			content.putString(draggedObject.toString());
			dragboard.setContent(content);

			e.consume();

		});

		cell.setOnDragOver(event -> {
			if (event.getGestureSource() != cell && draggedObject != null) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		cell.setOnDragEntered(e -> {
			if (e.getGestureSource() != cell && draggedObject != null) {
				cell.setOpacity(0.5);
			}

		});

		cell.setOnDragExited(e -> {
			if (e.getGestureSource() != cell) {
				cell.setOpacity(1);
			}

		});

		cell.setOnDragExited(e -> cell.setOpacity(1));

		cell.setOnDragDropped(e -> {
			if (draggedObject == null) {
				return;
			}

			if (!list.getItems().contains(draggedObject)) {
				return;
			}

			list.getItems().contains(draggedObject);

			int idxTarget = list.getItems().indexOf(cell.getItem());
			int idxSource = list.getItems().indexOf(draggedObject);

			list.getItems().remove(idxSource);
			list.getItems().add(idxTarget, draggedObject);

			list.getSelectionModel().select(idxTarget);

			draggedObject = null;
			e.consume();
		});
	}

}

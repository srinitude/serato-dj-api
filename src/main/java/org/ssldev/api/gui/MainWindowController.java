package org.ssldev.api.gui;

import java.util.List;

import org.ssldev.api.chunks.Adat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {
	private NowPlayingAppGUI main;
	
	//views
	@FXML private TextArea nowplayingTextArea;
	@FXML private TextArea logTextArea;
	@FXML private MenuItem editMenuClear;
	@FXML private Label cueTitleLbl;
	@FXML private AnchorPane layout;
	@FXML private ListView<String> trackList = new ListView<>(FXCollections.observableArrayList());

	public void setMain(NowPlayingAppGUI m) {
		main = m;
	}
	
	public void setListView(List<Adat> tracks) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<String> items = trackList.getItems();
				if(tracks.isEmpty()) items.add(":(");
				else tracks.forEach(t-> items.add(t.toString()));
			}
		});
	}

	public void clear() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				trackList.getItems().clear();;
			}
		});
	}
	
	public void stop() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// does nothing currently
			}
		});
	}
	
	public void setCueTitleLabel(String s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				cueTitleLbl.setText(s + "\n");
			}
		});
	}

	public void appendToNowPlayingWindow(String s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<String> items = trackList.getItems();
				items.add(s);
				
				// scroll to newly added item
		        trackList.scrollTo(trackList.getItems().size()-1);
			}
		});
	}
	public void appendToLogWindow(String s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				logTextArea.appendText(s + "\n");
			}
		});
	}
	
	// records relative x and y co-ordinates.
	class Delta { double x, y; }  
	// allow the app background to be used to drag the app around.
	final Delta dragDelta = new Delta();
	public void onMousePressed(MouseEvent mouseEvent) {
		// record a delta distance for the drag and drop operation.
        dragDelta.x = main.stage.getX() - mouseEvent.getScreenX();
        dragDelta.y = main.stage.getY() - mouseEvent.getScreenY();
        main.scene.setCursor(Cursor.MOVE);
	}
	public void onMouseReleased(MouseEvent mouseEvent) {
           main.scene.setCursor(Cursor.HAND);
	}
	public void onMouseDragged(MouseEvent mouseEvent) {
           main.stage.setX(mouseEvent.getScreenX() + dragDelta.x);
           main.stage.setY(mouseEvent.getScreenY() + dragDelta.y);
	}
	public void onMouseEntered(MouseEvent mouseEvent) {
           if (!mouseEvent.isPrimaryButtonDown()) {
             main.scene.setCursor(Cursor.HAND);
           }
	}
	public void onMouseExited(MouseEvent mouseEvent) {
           if (!mouseEvent.isPrimaryButtonDown()) {
             main.scene.setCursor(Cursor.DEFAULT);
           }
	}

}

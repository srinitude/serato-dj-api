package org.ssldev.api.gui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.ssldev.core.utils.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * GUI displays track data published, mostly for demo purposes.  
 */
public class NowPlayingAppGUI extends Application {
	private static MainWindowController controller;
	private static List<GuiEventListenerIF> eventListeners = new LinkedList<>();
	protected Stage stage;
	protected Scene scene;

	@Override
	public void init() throws Exception {
		super.init();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("demoGui.fxml"));
			AnchorPane pane = loader.load();
			
			scene = new Scene(pane);
			controller = loader.getController();
			controller.setMain(this);
			scene.getStylesheets().add(getClass().getResource("demoGui.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.setTitle("");
			stage.initStyle(StageStyle.UNIFIED);
	        
			stage.show();
			
		} catch (IOException e) {
			Logger.error(this, e.getMessage());
			e.printStackTrace();
			stop();
		}
	}

	@Override
	public void stop() throws Exception {
		Logger.info(this, "GUI exiting...");
		super.stop();
	}
	
	public static void setCueLabelText(String txt) {
		if(null == controller) {
			return;
		}
		controller.setCueTitleLabel(txt);
	}
	
	public static void appendToNowPlaying(String txt) {
		if(null == controller) {
//			Logger.warn(NowPlayingAppGUI.class, "GUI has not finished initializing yet. rejecting append: " + txt);
			return;
		}
		controller.appendToNowPlayingWindow(txt);
	}
	public static void appendToLogWindow(String txt) {
		if(null == controller) {
//			Logger.warn(NowPlayingAppGUI.class, "GUI has not finished initializing yet. rejecting append: " + txt);
			return;
		}
		controller.appendToLogWindow(txt);
	}

	public static void addListener(GuiEventListenerIF listener) {
		eventListeners.add(listener);
	}

}

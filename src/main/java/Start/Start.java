package Start;

import static FXML_Controllers.FXML_MainController.fxml_MainController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Scanner.Item;
import Scanner.Map;
import Scanner.Scanner;
import Scanner.StringWrap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Start extends Application
{
	private static StringWrap PATH_SOURCE = new StringWrap("D:\\");
	private static List<String> PATHS_STRINGS = new ArrayList<String>();
	private static ArrayList<String[]> MAP_STRING = new ArrayList<>();
	private static ArrayList<LinkedList<Item>> MAP_ITEMS = new ArrayList<>();
	private static TreeSet<Item> FINAL_MAP_ITEMS = new TreeSet<Item>();
	private static Scanner SCANNER = new Scanner();
	private static Map MAP = new Map();

	
	public static void main(String[] args)
	{
		
		launch();
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent mainScene = FXMLLoader.load(getClass().getResource("/FXML/FXML_Main.fxml"));	
		Scene scene = new Scene(mainScene);		
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setTitle("Map");
		primaryStage.setOnCloseRequest((WindowEvent event) ->
		{
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();
		
		initialize();
	}
	
	private static void initialize()
	{
		fxml_MainController.setReferencePathSource(PATH_SOURCE);
		fxml_MainController.setReferenceMap(MAP);
		fxml_MainController.setReferenceFinalMapItem(FINAL_MAP_ITEMS);

		SCANNER.setReferenceToPath(PATHS_STRINGS);
	
		MAP.setReferenceScanner(SCANNER);
		MAP.setReferencePathSource(PATH_SOURCE);
		MAP.setRefrencePath(PATHS_STRINGS);
		MAP.setReferenceMapString(MAP_STRING);
		MAP.setReferenceMapItems(MAP_ITEMS);
		MAP.setReferenceFinalMapItem(FINAL_MAP_ITEMS);
	}
}

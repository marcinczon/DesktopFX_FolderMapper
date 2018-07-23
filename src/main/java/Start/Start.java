package Start;

import static FXML_Controllers.FXML_MainController.fxml_MainController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Creator.Creator;
import Scanner.ScannerItem;
import Scanner.ScannerMap;
import Scanner.Scanner;
import Scanner.StringWrap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// Future work
// 1. Problem po wystapieniu bledu odczytu, po ponownym ustawieniu sciezki juz nie oczytuje
//
//
//



public class Start extends Application
{
	private static StringWrap PATH_SOURCE = new StringWrap("C:\\");
	private static List<String> PATHS_STRINGS = new ArrayList<String>();
	private static ArrayList<String[]> MAP_STRING = new ArrayList<>();
	private static ArrayList<LinkedList<ScannerItem>> MAP_ITEMS = new ArrayList<>();
	private static TreeSet<ScannerItem> FINAL_MAP_ITEMS = new TreeSet<ScannerItem>();
	
	private static Scanner SCANNER;
	private static ScannerMap MAP;
	private static Creator CREATOR;
	

	
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
		CREATOR =  new Creator();
		CREATOR.setPaneMap(fxml_MainController.getPaneMap());
		CREATOR.setReferencePathSource(PATH_SOURCE);
		
		MAP = new ScannerMap();
		SCANNER = new Scanner();
		
		fxml_MainController.setReferencePathSource(PATH_SOURCE);		
		fxml_MainController.setReferenceFinalMapItem(FINAL_MAP_ITEMS);
		fxml_MainController.setReferenceMap(MAP);
		fxml_MainController.setReferenceCreator(CREATOR);

		SCANNER.setReferenceToPath(PATHS_STRINGS);
		
		
		
		MAP.setReferenceScanner(SCANNER);
		MAP.setReferencePathSource(PATH_SOURCE);
		MAP.setRefrencePath(PATHS_STRINGS);
		MAP.setReferenceMapString(MAP_STRING);
		MAP.setReferenceMapItems(MAP_ITEMS);
		MAP.setReferenceFinalMapItem(FINAL_MAP_ITEMS);
		
	}
}

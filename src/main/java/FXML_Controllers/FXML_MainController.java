package FXML_Controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Scanner.Item;
import Scanner.Map;
import Scanner.Scanner;
import Scanner.StringWrap;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

public class FXML_MainController
{

	public static FXML_MainController fxml_MainController;

	public FXML_MainController()
	{
		fxml_MainController = this;
	}

	// References
	private Map map;
	private StringWrap pathSource = new StringWrap();
	private TreeSet<Item> finalMapItem;

	// *********************************************

	private Thread thread1;

	private Runnable runnable1;

	private StringBuilder stringBuilder = new StringBuilder();

	@FXML
	Button buttonFile;
	@FXML
	Button buttonRead;
	@FXML
	Button buttonCreate;
	@FXML
	ScrollPane spMap;
	@FXML
	Pane paneMap;

	@FXML
	public void onMouseClickedFile(MouseEvent event)
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);

		if (selectedDirectory == null)
		{
			printLog("No Directory selected");
		} else
		{
			pathSource.setString(selectedDirectory.getAbsolutePath());
			printLog(pathSource.getString());
		}
	}

	@FXML
	public void onMouseClickedRead(MouseEvent event)
	{
		runnable1 = new Runnable()
		{

			@Override
			public void run()
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						printLog("Status Clear");
						map.clear();
						printLog("Status Create List");
						map.createList();
						printLog("Status Create Map String");
						map.creatingMapString();
						printLog("Status Create Map Item");
						map.creatingMapItem();
						printLog("Status Sort Map");
						map.sortMap();
						printLog("Status Generate Map");
						map.generateMap();
						printLog("Status Creating Nodes");
						paneMap.getChildren().addAll(map.getNodes());
						printLog("Status Creating Lines");
						for (Item item : finalMapItem)
						{
							paneMap.getChildren().addAll(item.getLine());
						}
					}
				});

				printLog("Status Finish");
			}
		};
		thread1 = new Thread(runnable1);
		thread1.start();
		thread1.setName("Mapping");

	}

	@FXML
	public void onMouseClickedCreate(MouseEvent event)
	{
	}

	public void printLog(String status)
	{
		stringBuilder.setLength(0);
		stringBuilder.append(status);
		System.out.println(stringBuilder.toString());
	}

	public void setReferencePathSource(StringWrap pathSource)
	{
		this.pathSource = pathSource;
	}

	public void setReferenceMap(Map map)
	{
		this.map = map;
	}

	public void setReferenceFinalMapItem(TreeSet<Item> finalMapItem)
	{
		this.finalMapItem = finalMapItem;
	}

}

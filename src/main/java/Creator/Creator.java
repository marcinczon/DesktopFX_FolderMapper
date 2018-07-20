package Creator;

import java.io.File;

import GeneralParameters.Strings;
import Scanner.StringWrap;

import javafx.event.EventHandler;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

//import static FXML_Controllers.FXML_MainController.fxml_MainController;

public class Creator
{
	private static Pane paneMap;
	private static CreatorItem[] classFolderTable = null;

	private String masterPath;
	private StringWrap pathSource;

	//static int objectCounter = 1;
	static int objectCounter = 0;
	static int recurentCounter = 0;

	static int iDPrev = 0;
	static int iDNext = 0;
	static int iDSelectedOld = 0;
	static boolean folderIsSelected = false;
	static boolean isTrigger = false;

	public Creator()
	{
		classFolderTable = new CreatorItem[50];
	}

	public void startCreating()
	{
		masterPath = pathSource.getString();

		for (CreatorItem Folder : classFolderTable)
		{
			if (Folder != null)
				Folder.setPath(masterPath);
		}
		createFolderFromTable(classFolderTable);
	}

	private void runEvents()
	{
		paneMap.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				MouseButton button = event.getButton();

				if (button == MouseButton.PRIMARY)
				{
					objectCounter++;
					CreatorItem folder;
					folder = new CreatorItem(masterPath, String.valueOf(objectCounter), event.getX(), event.getY());
					classFolderTable[objectCounter] = folder;
				}
			}
		});
	}

	// Rekurent
	private void createFolderFromTable(CreatorItem folder)
	{
		if (folder.getPartnerPrev() != null)
		{
			for (CreatorItem index : folder.getPartnerPrev().getPartnerNext())
			{
				index.setPath(folder.getPartnerPrev().getPath() + Strings.textSymbol2 + index.getName());
				createFolderFromTable(folder.getPartnerPrev());
			}
		}
	}

	private void createFolderFromTable(CreatorItem[] folderTable)
	{
		for (CreatorItem x : folderTable)
		{
			if (x != null)
			{
				if (x.getPartnerPrev() != null)
				{
					for (CreatorItem index : x.getPartnerPrev().getPartnerNext())
					{
						if (x.equals(index))
						{
							createFolderFromTable(index);
						}
					}
				} else
				{
					x.setPath(masterPath + Strings.textSymbol2 + x.getName());
				}
			}
		}
		for (CreatorItem x : folderTable)
		{
			if (x != null)
			{
				System.out.println(x.getName() + " Path: " + x.getPath());
				x.createFolder2();
			}
		}
	}

	// ****************************
	//
	// Getters and Setters
	//
	// ****************************

	public static Pane getPaneCentrum()
	{
		return paneMap;
	}

	public static CreatorItem[] getTablicaClassFolderow()
	{
		return classFolderTable;
	}

	public static boolean isFolderSelected()
	{
		return folderIsSelected;
	}

	public static void setFolderSelected(boolean folderIsSelected)
	{
		Creator.folderIsSelected = folderIsSelected;
	}

	public static int getIDPrev()
	{
		return iDPrev;
	}

	public static void setIDPrev(int iDPrev)
	{
		Creator.iDPrev = iDPrev;
	}

	public static int getIDNext()
	{
		return iDNext;
	}

	public static void setIDNext(int iDNext)
	{
		Creator.iDNext = iDNext;
	}
	public static int getIDSelectedOld()
	{
		return iDSelectedOld;
	}

	public static void setIDSelectedOld(int iDSelectedOld)
	{
		Creator.iDSelectedOld = iDSelectedOld;
	}

	public static boolean isTrigger()
	{
		return isTrigger;
	}

	public static void setTrigger(boolean setIsTrigger)
	{
		isTrigger = setIsTrigger;
	}

	public void setReferencePathSource(StringWrap pathSource)
	{
		this.pathSource = pathSource;
	}

	public void setPaneMap(Pane paneMap)
	{
		System.out.println("                Panemenu initialized!");
		Creator.paneMap = paneMap;
		runEvents();
	}
}

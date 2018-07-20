package Creator;

import java.io.File;

import Scanner.StringWrap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;


import static FXML_Controllers.FXML_MainController.fxml_MainController;

public class Creator
{
	private static Pane paneMap;
	private static CreatorItem[] classFolderTable = null;
	
	private String masterPath;
	private StringWrap pathSource;

	static double oldX = 0;
	static double oldY = 0;

	static int objectCounter = 1;
	static int objectCounter2 = 0;
	static int recurentCounter = 0;

	static int iDSelected = 0;
	static int iDPrev = 0;
	static int iDNext = 0;
	static int iDSelectedNew = 0;
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
		if(paneMap!=null)
		paneMap.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				System.out.println("test klikniecia");
				MouseButton button = event.getButton();

				if (button == MouseButton.PRIMARY)
				{
					objectCounter2++;
					CreatorItem folder;
					folder = new CreatorItem(null, null, masterPath, String.valueOf(objectCounter2), event.getX(), event.getY());
					classFolderTable[objectCounter2] = folder;
				}
			}
		});
		else
			System.out.println("PaneMap null");
	}

	// Rekurent
	private void createFolderFromTable(CreatorItem folder)
	{
		if (folder.getPartnerPrev() != null)
		{
			for (CreatorItem index : folder.getPartnerPrev().getPartnerNext())
			{
				index.setPath(folder.getPartnerPrev().getPath() + "\\" + index.getName());
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
					x.setPath(masterPath + "\\" + x.getName());
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

	public static int getIDSelectedNew()
	{
		return iDSelectedNew;
	}

	public static void setIDSelectedNew(int iDSelectedNew)
	{
		Creator.iDSelectedNew = iDSelectedNew;
	}

	public static int getIDSelectedOld()
	{
		return iDSelectedOld;
	}

	public static void setIDSelectedOld(int iDSelectedOld)
	{
		Creator.iDSelectedOld = iDSelectedOld;
	}

	public static int getIDSelected()
	{
		return iDSelected;
	}

	public static void setIDSelected(int iDSelected)
	{
		Creator.iDSelected = iDSelected;
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

	public  void setPaneMap(Pane paneMap)
	{
		Creator.paneMap = paneMap;
		runEvents();
	}
}

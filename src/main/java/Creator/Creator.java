package Creator;

import GeneralParameters.Strings;
import Scanner.StringWrap;

import javafx.event.EventHandler;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Creator
{

	public static Creator creator;
	private Pane paneMap;

	private String masterPath;
	private StringWrap pathSource;

	public Creator()
	{
		creator = this;
	}

	public void startCreating()
	{
		masterPath = pathSource.getString();

		for (CreatorItem Folder : CreatorValues.getClassFolderTable())
		{
			if (Folder != null)
				Folder.setPath(masterPath);
		}
		createFolderFromTable(CreatorValues.getClassFolderTable());
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
					CreatorValues.setObjectCounter(CreatorValues.getObjectCounter() + 1);
					CreatorItem folder = new CreatorItem(masterPath, String.valueOf(CreatorValues.getObjectCounter()), event.getX(), event.getY());
					CreatorValues.getClassFolderTable()[CreatorValues.getObjectCounter()] = folder;
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

	public Pane getPaneCentrum()
	{
		return paneMap;
	}

	public void setReferencePathSource(StringWrap pathSource)
	{
		this.pathSource = pathSource;
	}

	public void setPaneMap(Pane paneMap)
	{
		this.paneMap = paneMap;				
		runEvents();
	}
}

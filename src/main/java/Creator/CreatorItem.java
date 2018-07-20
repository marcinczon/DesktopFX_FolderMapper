package Creator;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class CreatorItem
{
	private String path = "";
	private String name = "Empty";
	private CreatorItem partnerPrev = null;
	private ArrayList<CreatorItem> partnerNext = new ArrayList<CreatorItem>();
	private ObservableList<CreatorItem> oListPartnerNext = FXCollections.observableList(partnerNext);
	private static int objectCounter = 0;
	private int iD = 0;
	private double pointX = 0;
	private double pointY = 0;
	private TextField textField;
	private Label labelPrev = null;
	private Label labelNext = null;

	CreatorItem[] Foldery = Creator.getTablicaClassFolderow();

	public CreatorItem(CreatorItem fcPrev, CreatorItem fxNext, String spath, String sname, double pX, double pY)
	{

		objectCounter++;
		iD = objectCounter;

		if (spath != null)
		{
			this.path = spath;
		} else
		{
			this.path = "D:\\";
		}
		this.name = sname;
		this.pointX = pX;
		this.pointY = pY;

		textField = new TextField("Folder_" + name);
		textField.setPrefSize(100, 25);
		textField.setLayoutX(pointX - 50);
		textField.setLayoutY(pointY - 12);
		textField.toBack();
		name = textField.getText();

		labelPrev = new Label("Prev");
		labelPrev.setLayoutX(textField.getLayoutX() - 25);
		labelPrev.setLayoutY(textField.getLayoutY() + 25);

		labelNext = new Label("Next");
		labelNext.setLayoutX(textField.getLayoutX() + 100);
		labelNext.setLayoutY(textField.getLayoutY() + 25);

		Creator.getPaneCentrum().getChildren().add(textField);
		Creator.getPaneCentrum().getChildren().add(labelPrev);
		Creator.getPaneCentrum().getChildren().add(labelNext);

		textField.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				MouseButton button = event.getButton();
				if (button == MouseButton.MIDDLE)
				{
					if (Creator.isTrigger())
					{

						// Zabezpiecznie przed dodaniem prev i next do samego siebie
						if (Creator.getIDPrev() != Creator.getIDNext())
						{
							if (partnerPrev == null)
							{
								System.out.println(iD + " -Trigger: " + Creator.getIDPrev() + " -> " + Creator.getIDNext());
								partnerPrev = Creator.getTablicaClassFolderow()[Creator.getIDPrev()];
								Creator.getTablicaClassFolderow()[Creator.getIDPrev()].setPartnerNext(CreatorItem.this);
								Creator.setTrigger(false);
								Creator.setFolderSelected(false);
								Creator.setIDPrev(0);
								Creator.setIDNext(0);
								//
								Line _line = new Line();

								_line.setStartX(partnerPrev.getPointX());
								_line.setStartY(partnerPrev.getPointY());
								_line.setEndX(pointX);
								_line.setEndY(pointY);

								Creator.getPaneCentrum().getChildren().add(_line);

								if (partnerPrev.getName() != null)
								{
									labelPrev.setText(partnerPrev.getName());
								}

							} else
							{
								System.out.println("Child can have only one partent");
								System.out.println("Failed Connection " + iD + " - Trigger: " + Creator.getIDPrev() + " -> " + Creator.getIDNext());
							}
						} else
						{
							System.out.println("Error Same ID Number!");
						}
					}

				}
			}
		});
		textField.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				MouseButton button = event.getButton();
				if (button == MouseButton.MIDDLE)
				{
					// Usuwanie obiekt�w ktore s� takie same w prev, next nie moga byc takie same
					if (partnerNext.contains(partnerPrev))
					{
						System.out.println("Error Contain Prev");
						partnerNext.remove(partnerPrev);
						labelPrev.setText("Prev");
					}
					if (partnerNext.contains(CreatorItem.this))
					{
						System.out.println("Error Contain Actual");
						partnerNext.remove(CreatorItem.this);
						labelNext.setText("Next");
					}

					if (!Creator.isFolderSelected())
					{
						Creator.setFolderSelected(true);
						Creator.setIDPrev(iD);
					} else if (Creator.getIDSelectedOld() != iD)
					{
						Creator.setIDNext(iD);
						Creator.setFolderSelected(false);
						Creator.setTrigger(true);
					}

					Creator.setIDSelectedOld(iD);
				}
			}
		});
		textField.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldString, String newString)
			{
				name = newString;
			}
		});
		oListPartnerNext.addListener(new ListChangeListener<CreatorItem>()
		{
			public void onChanged(ListChangeListener.Change change)
			{
				String labelNextString = "";

				if (!partnerNext.isEmpty())
				{

					for (CreatorItem index : partnerNext)
					{
						if (index.getName() != null)
						{
							labelNextString = labelNextString + String.format("%s\n", index.getName());
							labelNext.setText(labelNextString);
						}
					}
				} else
				{
					System.out.println("Next is Empty ID: " + iD);
				}
			}
		});
	}

	public void createFolder()
	{
		name = textField.getText();

		if (objectCounter == 0 || partnerPrev == null)
		{
			String folderName = path + "\\" + name;
			boolean success = (new File(folderName)).mkdirs();
			if (!success)
			{
				System.out.println("Failed Init: " + objectCounter + " >> " + folderName);
			} else
			{
				path = folderName;
				System.out.println("Succes Init: " + objectCounter + " >> " + folderName);
			}
		} else
		{
			String folderName = partnerPrev.getPath() + "\\" + name;
			boolean success = (new File(folderName)).mkdirs();
			if (!success)
			{
				System.out.println("Failed Layer: " + objectCounter + " >> " + folderName);
			} else
			{
				path = folderName;
				System.out.println("Succes Layer: " + objectCounter + " >> " + folderName);
			}
		}
	}

	public void createFolder2()
	{
		boolean success = (new File(path)).mkdirs();
		if (!success)
		{
			System.out.println("Failed Create: >> " + path);
		} else
		{
			System.out.println("Succes Create: >> " + path);
		}

	}

	public String getPath()
	{
		return path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public CreatorItem getPartnerPrev()
	{
		return partnerPrev;
	}

	public CreatorItem getPartnerNext(int index)
	{
		return partnerNext.get(index);
	}

	public TextField getTextField()
	{
		return textField;
	}

	public void setTextField(TextField textField)
	{
		this.textField = textField;
	}

	public double getPointX()
	{
		return pointX;
	}

	public void setPointX(double pointX)
	{
		this.pointX = pointX;
	}

	public double getPointY()
	{
		return pointY;
	}

	public void setPointY(double pointY)
	{
		this.pointY = pointY;
	}

	public int getID()
	{
		return iD;
	}

	public void setPartnerPrev(CreatorItem partnerBack)
	{
		partnerPrev = partnerBack;
	}

	public void setPartnerNext(CreatorItem partnerNext)
	{
		oListPartnerNext.add(partnerNext);
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public ArrayList<CreatorItem> getPartnerNext()
	{
		return partnerNext;
	}

	public void setPartnerNext(ArrayList<CreatorItem> partnerNext)
	{
		this.partnerNext = partnerNext;
	}

}

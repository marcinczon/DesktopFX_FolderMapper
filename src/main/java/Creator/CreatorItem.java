package Creator;

import java.io.File;
import java.util.ArrayList;

import GeneralParameters.Parameters;
import GeneralParameters.Strings;
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
import static  Creator.Creator.creator;
public class CreatorItem
{
	private String path = Strings.textNull;
	private String name = Strings.textEmpty;
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


	public CreatorItem(String spath, String sname, double pX, double pY)
	{

		objectCounter++;
		iD = objectCounter;

		if (spath != null)
		{
			this.path = spath;
		} else
		{
			this.path = Strings.textDefaultPath;
		}
		this.name = sname;
		this.pointX = pX;
		this.pointY = pY;

		textField = new TextField(Strings.textDefaultName + name);
		textField.setPrefSize(Parameters.preferSizeWidth, Parameters.preferSizeHeigh);
		textField.setLayoutX(pointX - Parameters.positionCenterXCreatorItem);
		textField.setLayoutY(pointY - Parameters.positionCenterYCreatorItem);
		textField.setStyle(Strings.font1);
		textField.toBack();
		name = textField.getText();

		labelPrev = new Label(Strings.textPrev);
		labelPrev.setLayoutX(textField.getLayoutX() + Parameters.positionLabPrevX);
		labelPrev.setLayoutY(textField.getLayoutY() + Parameters.positionLabPrevY);

		labelNext = new Label(Strings.textNext);
		labelNext.setLayoutX(textField.getLayoutX() + Parameters.positionLabNextX);
		labelNext.setLayoutY(textField.getLayoutY() + Parameters.positionLabNextY);

		creator.getPaneCentrum().getChildren().add(textField);
		// Creator.getPaneCentrum().getChildren().add(labelPrev);
		// Creator.getPaneCentrum().getChildren().add(labelNext);

		ListenerInitialize();

		System.out.println("Creating new Item: " + name);

	}
	// ****************************
	//
	// LOGIC
	//
	// ****************************

	private void ListenerInitialize()
	{
		textField.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				MouseButton button = event.getButton();
				if (button == MouseButton.MIDDLE)
				{
					LogicMouseReleased();
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
					LogicMousePressed();
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
				LogicListenerPartnerNext();
			}
		});
	}

	private void LogicMouseReleased()
	{
		if (CreatorValues.isTrigger())
		{
			// Zabezpiecznie przed dodaniem prev i next do samego siebie
			if (CreatorValues.getiDPrev() != CreatorValues.getiDNext())
			{
				if (partnerPrev == null)
				{
					System.out.println(String.format(Strings.fxControlCommand2, iD, CreatorValues.getiDPrev(), CreatorValues.getiDNext()));
					partnerPrev = CreatorValues.getClassFolderTable()[CreatorValues.getiDPrev()];
					CreatorValues.getClassFolderTable()[CreatorValues.getiDPrev()].setPartnerNext(CreatorItem.this);
					CreatorValues.setTrigger(false);
					CreatorValues.setFolderIsSelected(false);
					CreatorValues.setiDPrev(0);
					CreatorValues.setiDNext(0);

					Line line = new Line();

					line.setStartX(partnerPrev.getPointX());
					line.setStartY(partnerPrev.getPointY());
					line.setEndX(pointX);
					line.setEndY(pointY);

					creator.getPaneCentrum().getChildren().add(line);

					if (partnerPrev.getName() != null)
					{
						labelPrev.setText(partnerPrev.getName());
					}

				} else
				{
					System.out.println(Strings.textMessage1);
					System.out.println(String.format(Strings.fxControlCommand3, iD, CreatorValues.getiDPrev(), CreatorValues.getiDNext()));

				}
			} else
			{
				System.out.println(Strings.textError1);
			}
		}
	}

	public void LogicMousePressed()
	{
		// Usuwanie obiekt�w ktore s� takie same w prev, next nie moga byc takie same
		if (partnerNext.contains(partnerPrev))
		{
			System.out.println(Strings.textError2);
			partnerNext.remove(partnerPrev);
			labelPrev.setText(Strings.textPrev);
		}
		if (partnerNext.contains(CreatorItem.this))
		{
			System.out.println(Strings.textError3);
			partnerNext.remove(CreatorItem.this);
			labelNext.setText(Strings.textNext);
		}

		if (!CreatorValues.isFolderIsSelected())
		{
			CreatorValues.setFolderIsSelected(true);
			CreatorValues.setiDPrev(iD);
		} else if (CreatorValues.getiDSelectedOld() != iD)
		{
			CreatorValues.setiDNext(iD);
			CreatorValues.setFolderIsSelected(false);
			CreatorValues.setTrigger(true);
		}

		CreatorValues.setiDSelectedOld(iD);
	}

	public void LogicListenerPartnerNext()
	{
		String labelNextString = Strings.textNull;

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
			System.out.println(Strings.textMessage2 + iD);
		}
	}

	public void createFolder()
	{
		name = textField.getText();

		if (objectCounter == 0 || partnerPrev == null)
		{
			String folderName = path + Strings.textSymbol2 + name;
			boolean success = (new File(folderName)).mkdirs();
			if (!success)
			{
				System.out.println(Strings.textMessage3 + objectCounter + Strings.textSymbol1 + folderName);
			} else
			{
				path = folderName;
				System.out.println(Strings.textMessage4 + objectCounter + Strings.textSymbol1 + folderName);
			}
		} else
		{
			String folderName = partnerPrev.getPath() + Strings.textSymbol2 + name;
			boolean success = (new File(folderName)).mkdirs();
			if (!success)
			{
				System.out.println(Strings.textMessage5 + objectCounter + Strings.textSymbol1 + folderName);
			} else
			{
				path = folderName;
				System.out.println(Strings.textMessage6 + objectCounter + Strings.textSymbol1 + folderName);
			}
		}
	}

	public void createFolder2()
	{
		boolean success = (new File(path)).mkdirs();
		if (!success)
		{
			System.out.println(Strings.textMessage7 + path);
		} else
		{
			System.out.println(Strings.textMessage8 + path);
		}

	}

	// ****************************
	//
	// Getters and Setters
	//
	// ****************************

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

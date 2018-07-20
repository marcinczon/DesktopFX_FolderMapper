package Scanner;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.javafx.geom.Shape;

import GeneralParameters.Parameters;
import GeneralParameters.Strings;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import static GeneralParameters.Strings.strings;

public class ScannerItem implements Comparable<ScannerItem>
{
	private String name = "";
	private int levelX;
	private int levelY;
	private ScannerItem prevItem = null;
	private ArrayList<ScannerItem> nextItems = new ArrayList<ScannerItem>();
	private TextField textField = new TextField();
	private Label labelPath = new Label();

	private String path = "";
	private String prevPartnerProcessInfo = "";
	private String processInfo = "";

	private Path Line = new Path();
	private LineTo lineTo1 = new LineTo();
	private LineTo lineTo2 = new LineTo();

	private Label labelPrev = new Label();
	private Label labelNext = new Label();

	public ScannerItem(String processInfo, int levelX, int levelY)
	{
		this.levelX = levelX;
		this.levelY = levelY;
		this.processInfo = processInfo;
		evaluationInfo();
		textField.setText(name);
		textField.setStyle(Strings.font1);
		textField.setPrefSize(Parameters.preferSizeWidth, Parameters.preferSizeHeigh);
		textField.setLayoutX(Parameters.positionOffsetX + levelX * Parameters.widthGap);
		textField.setLayoutY(Parameters.positionOffsetY + levelY * Parameters.heighGap);

		labelPath.setText(path);
		labelPath.setStyle(Strings.font1);
		labelPath.setLayoutX(textField.getLayoutX() + Parameters.positionLabPathX);
		labelPath.setLayoutY(textField.getLayoutY() + Parameters.positionLabPathY);

		labelPrev.setLayoutX(textField.getLayoutX() + Parameters.positionLabPrevX);
		labelPrev.setLayoutY(textField.getLayoutY() + Parameters.positionLabPrevY);
		labelPrev.setStyle(Strings.font2);
		labelPrev.setTextFill(Color.GREEN);

		labelNext.setLayoutX(textField.getLayoutX() + Parameters.positionLabNextX);
		labelNext.setLayoutY(textField.getLayoutY() + Parameters.positionLabNextY);
		labelNext.setStyle(Strings.font2);
		labelNext.setTextFill(Color.RED);

		this.setLineColor(Color.LIGHTGRAY);
		this.setTextFieldColor(Parameters.colorLightGray);

		initListener();

	}

	private void initListener()
	{
		textField.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				setSiblingsColors(Parameters.colorLightBlue, Color.BLUE, 3);
			}

		});
		textField.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				setSiblingsColors(Parameters.colorLightGray, Color.LIGHTGRAY, 1);
			}

		});
	}

	private void evaluationInfo()
	{
		int startIndex = 0;
		int endIndex = processInfo.indexOf("PATH:");

		name = processInfo.substring(startIndex, endIndex);
		startIndex = processInfo.indexOf("[");
		endIndex = processInfo.indexOf("]");
		path = processInfo.substring(startIndex + 1, endIndex);
		path = path.replace("/", "\\");
	}

	public static class CompareByName implements Comparator<ScannerItem>
	{
		@Override
		public int compare(ScannerItem a, ScannerItem b)
		{
			return a.name.compareTo(b.name);
		}
	}

	public static class CompareByPath implements Comparator<ScannerItem>
	{
		@Override
		public int compare(ScannerItem a, ScannerItem b)
		{
			return a.path.compareTo(b.path);
		}
	}

	public static class CompareByGridX implements Comparator<ScannerItem>
	{
		@Override
		public int compare(ScannerItem a, ScannerItem b)
		{
			String obA = Integer.toString(a.levelX);
			String obB = Integer.toString(b.levelX);
			return obA.compareTo(obB);
		}
	}

	private void setSiblingsColors(String stringColor, Color color, int width)
	{
		this.setLineColor(color);
		this.setTextFieldColor(stringColor);
		this.setLineWidth(width);
		if (this.prevItem != null)
		{
			setSiblingsColorsRecurent(this.prevItem, stringColor, color, width);
		}
		if (!this.nextItems.isEmpty())
		{
			setSiblingsColorsRecurent(this.nextItems, stringColor, color, width);
		}
	}

	private void setSiblingsColorsRecurent(ScannerItem item, String stringColor, Color color, int width)
	{
		item.setLineColor(color);
		item.setTextFieldColor(stringColor);
		item.setLineWidth(width);
		if (item.prevItem != null)
		{
			setSiblingsColorsRecurent(item.prevItem, stringColor, color, width);
		}
	}

	private void setSiblingsColorsRecurent(ArrayList<ScannerItem> items, String stringColor, Color color, int width)
	{
		for (ScannerItem item : items)
		{
			if (item != null)
			{
				item.setLineColor(color);
				item.setTextFieldColor(stringColor);
				item.setLineWidth(width);
				setSiblingsColorsRecurent(item.nextItems, stringColor, color, width);
			}
		}
	}

	public void setLineWidth(int width)
	{
		if (Line != null)
		{
			Line.setStrokeWidth(width);
		}
	}

	public void setLineColor(Color color)
	{
		Line.setStroke(color);
	}

	public void setTextFieldColor(String color)
	{

		textField.setStyle(Strings.fxControlCommand1 + color + ";" + Strings.font2);
	}

	@Override
	public String toString()
	{
		return "Item [name=" + name + ", levelX=" + levelX + ", levelY=" + levelY + "]";
	}

	public String printConnection()
	{
		if (prevItem != null)
			return "Item [Prev=" + prevItem.getName() + " name = " + name + "]";
		else
			return "Item [null name = " + name + "]";

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + levelX;
		result = prime * result + ((processInfo == null) ? 0 : processInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScannerItem other = (ScannerItem) obj;
		if (levelX != other.levelX)
			return false;
		if (processInfo == null)
		{
			if (other.processInfo != null)
				return false;
		} else if (!processInfo.equals(other.processInfo))
			return false;
		return true;
	}

	public ScannerItem getPrevItem()
	{
		return prevItem;
	}

	public void setPrevItem(ScannerItem prevItem)
	{
		this.prevItem = prevItem;
		labelPrev.setText(labelPath.getText() + "\n" + prevItem.getName() + " level Y :" + prevItem.levelY);
	}

	public ArrayList<ScannerItem> getNextItems()
	{
		return nextItems;
	}

	public void setNextItems(ScannerItem nextItems)
	{
		labelNext.setText(labelNext.getText() + "\n" + nextItems.getName());
		this.nextItems.add(nextItems);
	}

	@Override
	public int compareTo(ScannerItem item)
	{
		String a = Integer.toString(this.hashCode());
		String b = Integer.toString(item.hashCode());
		return a.compareTo(b);
	}

	// ****************************
	//
	// Getters and Setters
	//
	// ****************************

	public ArrayList<Node> getNodes()
	{
		// Add controll to visu labels from api
		ArrayList<Node> nodes = new ArrayList<Node>();
		// nodes.add(labelPath);
		// nodes.add(labelNext);
		// nodes.add(labelPrev);
		nodes.add(textField);
		return nodes;

	}

	public Node getLine()
	{
		if (prevItem != null)
		{
			double startX = prevItem.textField.getLayoutX() + textField.getPrefWidth();
			double startY = prevItem.textField.getLayoutY() + textField.getPrefHeight() / 2;

			double endX = textField.getLayoutX();
			double endY = textField.getLayoutY() + textField.getPrefHeight() / 2;

			lineTo1.setX(startX + ((endX - startX) / 2));
			lineTo1.setY(startY);

			lineTo2.setX(lineTo1.getX());
			lineTo2.setY(endY);

			Line.getElements().add(new MoveTo(startX, startY));
			Line.getElements().add(lineTo1);
			Line.getElements().add(lineTo2);
			Line.getElements().add(new LineTo(endX, endY));
		}
		return Line;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getLevelX()
	{
		return levelX;
	}

	public void setLevelX(int gridX)
	{
		this.levelX = gridX;
	}

	public int getLevelY()
	{
		return levelY;
	}

	public void setLevelY(int gridY)
	{
		this.levelY = gridY;
	}

	public TextField getTextField()
	{
		return textField;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getPrevPartnerProcessInfo()
	{
		return prevPartnerProcessInfo;
	}

	public void setPrevPartnerProcessInfo(String prevPartnerPath)
	{
		this.prevPartnerProcessInfo = prevPartnerPath;
	}

	public String getProcessInfo()
	{
		return processInfo;
	}

	public void setProcessInfo(String processInfo)
	{
		this.processInfo = processInfo;
	}
}

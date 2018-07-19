package Scanner;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.javafx.geom.Shape;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Item implements Comparable<Item>
{
	private String name = "";
	private int levelX;
	private int levelY;
	private Item prevItem = null;
	private ArrayList<Item> nextItems = new ArrayList<Item>();
	private TextField textField = new TextField();
	private Label labelPath = new Label();
	private String style = "-fx-font: normal 10px 'Calibri' ";
	private String styleSmall = "-fx-font: normal 8px 'Calibri' ";
	double offsetPositionX = 10;
	double offsetPositionY = 10;
	private String path = "";
	private String prevPartnerProcessInfo = "";
	private String processInfo = "";

	private Path Line = new Path();
	private LineTo lineTo1 = new LineTo();
	private LineTo lineTo2 = new LineTo();

	private Label labelPrev = new Label();
	private Label labelNext = new Label();

	public Item(String processInfo, int levelX, int levelY)
	{
		this.levelX = levelX;
		this.levelY = levelY;
		this.processInfo = processInfo;
		evaluationInfo();
		textField.setText(name);
		textField.setStyle(style);
		textField.setPrefSize(100, 10);
		textField.setLayoutX(offsetPositionX + levelX * 120);
		textField.setLayoutY(offsetPositionY + levelY * 40);

		labelPath.setText(path);
		labelPath.setStyle(style);
		labelPath.setLayoutX(textField.getLayoutX() + 10);
		labelPath.setLayoutY(textField.getLayoutY() + 22);

		labelPrev.setLayoutX(textField.getLayoutX());
		labelPrev.setLayoutY(textField.getLayoutY() + 10);
		labelPrev.setStyle(styleSmall);
		labelPrev.setTextFill(Color.GREEN);

		labelNext.setLayoutX(textField.getLayoutX() + 100);
		labelNext.setLayoutY(textField.getLayoutY() + 10);
		labelNext.setStyle(styleSmall);
		labelNext.setTextFill(Color.RED);
		
		this.setLineColor(Color.LIGHTGRAY);
		this.setTextFieldColor("lightgray");
		
		initListener();

	}
	
	private void initListener()
	{
		textField.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				setSiblingsColors("lightblue", Color.BLUE, 3);
			}

		});
		textField.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				setSiblingsColors("lightgray", Color.LIGHTGRAY, 1);
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

	public static class CompareByName implements Comparator<Item>
	{
		@Override
		public int compare(Item a, Item b)
		{
			return a.name.compareTo(b.name);
		}
	}

	public static class CompareByPath implements Comparator<Item>
	{
		@Override
		public int compare(Item a, Item b)
		{
			return a.path.compareTo(b.path);
		}
	}

	public static class CompareByGridX implements Comparator<Item>
	{
		@Override
		public int compare(Item a, Item b)
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

	private void setSiblingsColorsRecurent(Item item, String stringColor, Color color, int width)
	{
		item.setLineColor(color);
		item.setTextFieldColor(stringColor);
		item.setLineWidth(width);
		if (item.prevItem != null)
		{
			setSiblingsColorsRecurent(item.prevItem, stringColor, color, width);
		}
	}

	private void setSiblingsColorsRecurent(ArrayList<Item> items, String stringColor, Color color, int width)
	{
		for (Item item : items)
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
		
		textField.setStyle("-fx-control-inner-background: " + color + ";" + styleSmall);
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
		Item other = (Item) obj;
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

	public Item getPrevItem()
	{
		return prevItem;
	}

	public void setPrevItem(Item prevItem)
	{
		this.prevItem = prevItem;
		labelPrev.setText(labelPath.getText() + "\n" + prevItem.getName() + " level Y :" + prevItem.levelY);
	}

	public ArrayList<Item> getNextItems()
	{
		return nextItems;
	}

	public void setNextItems(Item nextItems)
	{
		labelNext.setText(labelNext.getText() + "\n" + nextItems.getName());
		this.nextItems.add(nextItems);
	}

	@Override
	public int compareTo(Item item)
	{
		String a = Integer.toString(this.hashCode());
		String b = Integer.toString(item.hashCode());
		return a.compareTo(b);
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

	public ArrayList<Node> getNodes()
	{
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

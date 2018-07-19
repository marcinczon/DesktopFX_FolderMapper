package Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javafx.concurrent.Service;
import javafx.scene.Node;

public class Map
{
	private StringWrap pathSource;
	List<String> pathsString;
	ArrayList<String[]> mapString;
	ArrayList<LinkedList<Item>> mapItems;
	TreeSet<Item> finalMapItem;
	TreeSet<Item> copyOfMapITem = new TreeSet<>();

	private Scanner scanner;

	
	public ArrayList<Node> getNodes()
	{
		//gui.setLOG("Creating GUI");
		ArrayList<Node> tempArray = new ArrayList<>();
		Iterator<Item> iterator = finalMapItem.iterator();
		while (iterator.hasNext())
		{
			tempArray.addAll(iterator.next().getNodes());

		}
		return tempArray;
	}

	public void clear()
	{
		pathsString.clear();
		mapString.clear();
		mapItems.clear();
		finalMapItem.clear();
		//gui.setLOG("Clear All");
	}

	public void generateMap()
	{

		//gui.setLOG("Generate Final Map");

		for (int index = 0; index < mapItems.size(); index++)
		{
			for (Item item : mapItems.get(index))
			{
				if (!mapItems.contains(item))
				{
					finalMapItem.add(item);
				}
			}
		}

		reorganizeMapPartners();

	}

	public void reorganizeMapPartners()
	{
		//gui.setLOG("Reorganize Map Partner");
		for (Item item : finalMapItem)
		{
			Item presentItem = finalMapItem.floor(item);
			Item prevItem = findItem(presentItem.getPrevPartnerProcessInfo());
			if (prevItem != null)
			{
				prevItem.setNextItems(item);
				item.setPrevItem(prevItem);
			}
		}
	}

	private Item findItem(String processPrevInfo)
	{
		for (Item item : finalMapItem)
		{
			if (item.getProcessInfo().equals(processPrevInfo))
			{
				return item;
			}
		}
		return null;
	}

	public void checkFinalMap()
	{
		//gui.setLOG("Check Final Map");
		Iterator<Item> iterator = finalMapItem.iterator();

		while (iterator.hasNext())
		{
			System.out.println(iterator.next().toString());
		}

	}

	public void createList()
	{
		//gui.setLOG(pathSource.toString());
		scanner.readFolderList(pathSource.getString());
	}

	public void creatingMapString()
	{
		//gui.setLOG("Creating Map String");
		for (String string : pathsString)
		{
			mapString.add(scanner.folderSegmentation(string));
		}
	}

	public void creatingMapItem()
	{
		//gui.setLOG("Creating Item Map");
		int levelY = 0;
		for (String[] strings : mapString)
		{
			int levelX = 0;
			// Item prevPartner;
			mapItems.add(new LinkedList<>());
			for (String string : strings)
			{
				mapItems.get(levelY).add(new Item(string, levelX, levelY));
				if (levelX > 0)
				{
					String prevProcessInfo = mapItems.get(levelY).get(levelX - 1).getProcessInfo();
					mapItems.get(levelY).get(levelX).setPrevPartnerProcessInfo(prevProcessInfo);
				}

				levelX++;
			}
			levelY++;
		}

	}

	public void sortMap()
	{
		//gui.setLOG("Sort Map");
		for (int index1 = 0; index1 < mapItems.size(); index1++)
		{
			mapItems.get(index1).sort(new Item.CompareByGridX());
		}

	}

	public void checkItemMap()
	{
		//gui.setLOG("Checking Item Map");
		Iterator<Item> mapItemIterator;
		for (int index1 = 0; index1 < mapItems.size(); index1++)
		{
			mapItemIterator = mapItems.get(index1).iterator();

			do
			{
				if (mapItemIterator.hasNext())
					System.out.print(mapItemIterator.next().toString() + " ");
			} while (mapItemIterator.hasNext());
			System.out.println();
		}
	}

	public void setReferencePathSource(StringWrap pathSource)
	{
		this.pathSource = pathSource;
	}

	public void setRefrencePath(List<String> path)
	{
		this.pathsString = path;
	}

	public void setReferenceMapString(ArrayList<String[]> mapString)
	{
		this.mapString = mapString;
	}

	public void setReferenceMapItems(ArrayList<LinkedList<Item>> mapItems)
	{
		this.mapItems = mapItems;
	}

	public void setReferenceFinalMapItem(TreeSet<Item> finalMapItem)
	{
		this.finalMapItem = finalMapItem;
	}

	public void setReferenceScanner(Scanner scanner)
	{
		this.scanner = scanner;
	}





}

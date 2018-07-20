package Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javafx.concurrent.Service;
import javafx.scene.Node;

public class ScannerMap
{
	private StringWrap pathSource;
	List<String> pathsString;
	ArrayList<String[]> mapString;
	ArrayList<LinkedList<ScannerItem>> mapItems;
	TreeSet<ScannerItem> finalMapItem;
	TreeSet<ScannerItem> copyOfMapITem = new TreeSet<>();

	private Scanner scanner;

	public ArrayList<Node> getNodes()
	{
		ArrayList<Node> tempArray = new ArrayList<>();
		Iterator<ScannerItem> iterator = finalMapItem.iterator();
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
		copyOfMapITem.clear();
	}

	public void generateMap()
	{

		for (int index = 0; index < mapItems.size(); index++)
		{
			for (ScannerItem item : mapItems.get(index))
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
		for (ScannerItem item : finalMapItem)
		{
			ScannerItem presentItem = finalMapItem.floor(item);
			ScannerItem prevItem = findItem(presentItem.getPrevPartnerProcessInfo());
			if (prevItem != null)
			{
				prevItem.setNextItems(item);
				item.setPrevItem(prevItem);
			}
		}
	}

	private ScannerItem findItem(String processPrevInfo)
	{
		for (ScannerItem item : finalMapItem)
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
		Iterator<ScannerItem> iterator = finalMapItem.iterator();

		while (iterator.hasNext())
		{
			System.out.println(iterator.next().toString());
		}

	}

	public void createList()
	{
		scanner.readFolderList(pathSource.getString());
	}

	public void creatingMapString()
	{
		for (String string : pathsString)
		{
			mapString.add(scanner.folderSegmentation(string));
		}
	}

	public void creatingMapItem()
	{
		int levelY = 0;
		for (String[] strings : mapString)
		{
			int levelX = 0;
			mapItems.add(new LinkedList<>());
			for (String string : strings)
			{
				mapItems.get(levelY).add(new ScannerItem(string, levelX, levelY));
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
		for (int index1 = 0; index1 < mapItems.size(); index1++)
		{
			mapItems.get(index1).sort(new ScannerItem.CompareByGridX());
		}

	}

	public void checkItemMap()
	{
		Iterator<ScannerItem> mapItemIterator;
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
	
	// ****************************
	//
	// 		Getters and Setters
	//
	// ****************************

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

	public void setReferenceMapItems(ArrayList<LinkedList<ScannerItem>> mapItems)
	{
		this.mapItems = mapItems;
	}

	public void setReferenceFinalMapItem(TreeSet<ScannerItem> finalMapItem)
	{
		this.finalMapItem = finalMapItem;
	}

	public void setReferenceScanner(Scanner scanner)
	{
		this.scanner = scanner;
	}

}

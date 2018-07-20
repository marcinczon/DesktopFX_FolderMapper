package Creator;

public class CreatorValues
{
	private static int objectCounter = 0;
	private static int iDPrev = 0;
	private static int iDNext = 0;
	private static int iDSelectedOld = 0;
	private static boolean folderIsSelected = false;
	private static boolean isTrigger = false;
	private static CreatorItem[] classFolderTable = new CreatorItem[50];

	
	// ****************************
	//
	// Getters and Setters
	//
	// ****************************
	
	public static int getObjectCounter()
	{
		return objectCounter;
	}
	public static void setObjectCounter(int objectCounter)
	{
		CreatorValues.objectCounter = objectCounter;
	}
	public static int getiDPrev()
	{
		return iDPrev;
	}
	public static void setiDPrev(int iDPrev)
	{
		CreatorValues.iDPrev = iDPrev;
	}
	public static int getiDNext()
	{
		return iDNext;
	}
	public static void setiDNext(int iDNext)
	{
		CreatorValues.iDNext = iDNext;
	}
	public static int getiDSelectedOld()
	{
		return iDSelectedOld;
	}
	public static void setiDSelectedOld(int iDSelectedOld)
	{
		CreatorValues.iDSelectedOld = iDSelectedOld;
	}
	public static boolean isFolderIsSelected()
	{
		return folderIsSelected;
	}
	public static void setFolderIsSelected(boolean folderIsSelected)
	{
		CreatorValues.folderIsSelected = folderIsSelected;
	}
	public static boolean isTrigger()
	{
		return isTrigger;
	}
	public static void setTrigger(boolean isTrigger)
	{
		CreatorValues.isTrigger = isTrigger;
	}
	public static CreatorItem[] getClassFolderTable()
	{
		return classFolderTable;
	}
	public static void setClassFolderTable(CreatorItem[] classFolderTable)
	{
		CreatorValues.classFolderTable = classFolderTable;
	}

}

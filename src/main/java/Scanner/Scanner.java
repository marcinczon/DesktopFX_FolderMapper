package Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import GeneralParameters.Strings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import static FXML_Controllers.FXML_MainController.fxml_MainController;

 
public class Scanner  
{
	private List<String> paths;
	private boolean continueLoop = true;
	private static int maxLevel = 0;


	// ***************************************
	// Segmentacja folderow, wejscie: sciezka pliku i tryb
	// wyjscie: tablica folderow
	// Funkcja skanuje stringa w poszukiwaniu "\\", odziela slowo znajdujace si�
	// miedzy nim
	// i dodaje do tablicy
	// ***************************************
	
	public static String[] folderSegmentation(String pathTemp)
	{
		StringBuffer sb = new StringBuffer();

		String separator = "\\";
		String[] tempListWord = pathTemp.split(Pattern.quote(separator));

		int tableLenght = tempListWord.length;
		String[] results = new String[tableLenght];

		for (int index = 0; index < tableLenght; index++)
		{
			String[] tempArray = pathTemp.split(Pattern.quote(separator), 2);
			String tempString = "";

			tempString = "PATH:[" + tempArray[0] + "]";
			results[index] = tempString;
			pathTemp = pathTemp.replaceFirst("\\\\", "/");
		}

		for (int i = 0; i < tempListWord.length; i++)
		{
			tempListWord[i] = tempListWord[i] + " " + results[i];
		}

		return tempListWord;
	}

	// ***************************************
	// Segmentacja folderow, wejscie: sciezka pliku i tryb
	// wyjscie: tablica folderow
	// Funkcja skanuje stringa w poszukiwaniu "\\", odziela slowo znajdujace się
	// miedzy nim
	// i dodaje do tablicy
	// ***************************************
	public static String[] folderSegmentation(String path, boolean flagMode)
	{
		// bMode=0 zawiera sciezke w pierwszej komorce
		// bMode=1 nie zawiera sciezki w pierwszej komorce

		ArrayList<String> alSegments = new ArrayList<String>();
		alSegments.clear();

		String sSegment;
		boolean flagLoop = false;
		int iIndex = 0;

		// Dodanie w 1 miejscu tablicy pelnej sciezki 0 - tak 1 - nie
		if (!flagMode)
		{
			alSegments.add(path);
		}
		do
		{
			if (path != null)
			{
				flagLoop = false;
				if (path.indexOf("\\") != -1)
				{
					iIndex = path.indexOf("\\");
					sSegment = path.substring(0, iIndex);
					path = path.substring(sSegment.length() + 1, path.length());
					alSegments.add(sSegment);
					flagLoop = true;
				} else
				{
					sSegment = path;
					alSegments.add(sSegment);
				}
			}
		} while (flagLoop);

		// ***************************************
		//
		// Utworzenie tablicy do ktorej beda zapisywane przetworzone elementy
		//
		// ***************************************

		String[] aSegments = new String[alSegments.size()];
		for (int i = 0; i < aSegments.length; i++)
		{
			aSegments[i] = alSegments.get(i);
		}

		// ***************************************
		//
		// Sprawdzenie max levelu
		//
		// ***************************************

		if (alSegments.size() > maxLevel)
		{
			maxLevel = alSegments.size();
		}

		return aSegments;
	}

	// ***************************************
	// Funkcja tworzy stringa z tablicy podanej na wejsciu
	// Parametr level mowi do jakiego poziomu ma zosta� utworzona sciezka
	//
	// ***************************************

	public static String folderDesegmentation(String[] aSegments, int level)
	{

		String path = "";

		if (aSegments.length > level)
		{
			for (int i = 0; i < level - 1; i++)
			{
				path = path + aSegments[i] + "\\";
			}
			path = path + aSegments[level - 1];
		} else
		{
			System.err.println(Strings.textAllert2);
		}

		return path;

	}

	// ***************************************
	//
	// Odczyt listy folderow za pomoc� rekurencji
	//
	// ***************************************

	public List<String> readFolderList(String path)
	{
		this.paths.clear();
		File folder = new File(path);
		readFolderListRecursion(folder.listFiles());
		return this.paths;
	}

	public void readFolderListRecursion(File[] folders)
	{
		String fileContent = null;

		if (folders == null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(Strings.textAllert1);
			alert.setHeaderText(Strings.textError);
			alert.setContentText(Strings.textContinue);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				setContinueLoop(true);
			} else {
				setContinueLoop(false);
			}
		}
		if (folders != null)
		{
			for (File file : folders)
			{
				// Zabezpiecznenie przed rekurencj , przerwie zagnierzdzone
				// petle
				if (!isContinueLoop())
				{
					break;
				}
				if (file.isDirectory())
				{
					paths.add(file.getAbsolutePath());
					readFolderListRecursion(file.listFiles());
				} else
				{
					paths.add(file.getAbsolutePath());
					if (fileContent != null)
						fileContent = fileContent + file.getAbsolutePath() + "\n";
					else
						fileContent = file.getAbsolutePath() + "\n";
				}

			}
		}
	}
	
	// ****************************
	//
	// 		Getters and Setters
	//
	// ****************************

	public boolean isContinueLoop()
	{
		return continueLoop;
	}

	public void setContinueLoop(boolean continueLoop)
	{
		this.continueLoop = continueLoop;
	}

	public static int getMaxLevel()
	{
		return maxLevel;
	}

	public void setReferenceToPath(List<String> path)
	{
		this.paths = path;
	}

}

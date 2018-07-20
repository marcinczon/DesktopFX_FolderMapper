package test;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class test
{

	private Pane paneMap;

	public test()
	{
		System.out.println("test created");
		
	}


	private void runEvents()
	{
		paneMap.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
			
					System.out.println("Click test");
				
			}
		});
	}


	public Pane getPaneMap()
	{
		return paneMap;
	}


	public void setPaneMap(Pane paneMap)
	{
		this.paneMap = paneMap;
		runEvents();

	//	paneMap.getChildren().remove(rec);
		
	}



}

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UnitTest {
	MainClass.Grid grid = new MainClass.Grid();
	MainClass.Generator generator = new MainClass.Generator(grid);
	
	public void setUp(){
		MainClass.mazeSizeHeight = 10;
		MainClass.mazeSizeWidth = 10;
		grid.clearData();
	}
	
	@Test
	public void test_setWalls() {
		setUp(); 
		int posX = 1;
		int posY = 1;
		TileClass tile = MainClass.tilesArray[posY][posX];
		generator.setWalls(tile);
		
		assertEquals(MainClass.tilesArray[posY][posX+1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY][posX-1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY+1][posX].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY-1][posX].getState(),"wall");

	}
	
	@Test
	public void test_FillBorders() {
		setUp();
		generator.FillBorders();
		for (int posY = 0; posY < MainClass.mazeSizeHeight; posY++){
			assertEquals(MainClass.tilesArray[posY][0].getState(),"hwall");
			assertEquals(MainClass.tilesArray[posY][MainClass.mazeSizeWidth-1].getState(),"hwall");
     		
        }
        for (int posX = 0; posX < MainClass.mazeSizeWidth; posX++){
        	assertEquals(MainClass.tilesArray[0][posX].getState(),"hwall");
        	assertEquals(MainClass.tilesArray[MainClass.mazeSizeHeight-1][posX].getState(),"hwall");
        	            
        }		
	}
	
	@Test
	public void test_setStartPoint(){
		setUp();
		generator.setStartPoint();
		for (int posY = 0; posY < MainClass.mazeSizeHeight; posY++){
			for (int posX = 0; posX < MainClass.mazeSizeWidth; posX++){
				if (MainClass.tilesArray[posY][posX].getState()!="empty"){
					assertEquals(MainClass.tilesArray[posY][posX].getState(),"start");
				}
			}
		}
	}
	
	@Test
	public void test_setFinishPoint(){
		setUp();
		int posX = 1;
		int posY = 1;
		TileClass tile = MainClass.tilesArray[posY][posX];
		generator.setFinishPoint(tile);
		
		assertEquals(MainClass.tilesArray[posY][posX].getState(),"finish");
		
		assertEquals(MainClass.tilesArray[posY][posX+1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY][posX-1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY+1][posX].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY-1][posX].getState(),"wall");
		
	}
	
	@Test
	public void test_generatePath(){
		setUp();
		int posX = 1;
		int posY = 1;
		TileClass tile = MainClass.tilesArray[posY][posX];
		
		MainClass.pathsArray = new ArrayList<PathClass>(); 
		assertEquals(MainClass.pathsArray.size(),0);
		generator.generatePath(tile, 2);
		assertTrue(MainClass.pathsArray.size()>0);
		
	}

	
	@Test
	public void test_generateIntersection(){
		setUp();
		int posX = 1;
		int posY = 1;
		TileClass tile = MainClass.tilesArray[posY][posX];
		MainClass.pathsArray = new ArrayList<PathClass>(); 
						
 		while (MainClass.pathsArray.size()<=1){
			generator.generatePath(tile, 10);
		}
	
		for (PathClass path : MainClass.pathsArray){
			if (path.getID() != 0){
				assertEquals(path.getTiles().get(0).getState() ,"cross");	
			}			
		}
			
	}

}

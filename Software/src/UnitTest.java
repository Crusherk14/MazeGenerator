import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitTest {
	MainClass.Grid grid = new MainClass.Grid();
	MainClass.Generator generator = new MainClass.Generator(grid);
	
	public void setUp(){
		MainClass.mazeSizeHeight = 10;
		MainClass.mazeSizeWidth = 10;
		grid.initArray();
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

}

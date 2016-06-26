/* 
 * By reading any further information you agree with terms of it's usage:
 * Unauthorized using or copying of this file is strictly prohibited
 *
 * Written by team: Kolesnikov Mikhail, Ilyin Evgeniy, Bogdanov Maksim, Makarov Pavel
 * Source code was specially created within the "Basics of Programming" of ITMO University (1st Course, 2nd Term)
 * February 2016 - July 2016
 *
 * For any further questions contact authors on GitHub repository <https://github.com/Crusherk14/MazeGenerator>
 */

/* 
 * Продолжая прочтение информации, изложенной в данном документе, Вы соглашаетесь с правилами её дальнейшего использования:
 * Незаконное использование в личных целях или распространение документа без ведома правообладателя запрещено
 * Является собственностью автора(-ов)
 *
 * Авторы: Колесников Михаил, Ильин Евгений, Богданов Максим, Макаров Павел
 * Исходный код проекта написан специально в рамках семестрового проекта по дисциплине "Основы Программирования" Университета ИТМО (1ый Курс, 2ой Семестр)
 * Февраль 2016 - Июль 2016
 *
 * По всем возникшим вопросам обращаться к создателям репозитория на GitHub <https://github.com/Crusherk14/MazeGenerator>
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UnitTest {
	MainClass.Grid grid = new MainClass.Grid();
	MainClass.Generator generator = new MainClass.Generator(grid);
	
	public void setUp(){
		MainClass.mazeSizeHeight = 10;
		MainClass.mazeSizeWidth = 10;
		grid = new MainClass.Grid();
		grid.clearData();
		generator.FillBorders();
	}
	
	@Test
	public void test_setWalls() {
		setUp(); 
		int posX = 2;
		int posY = 2;
		TileClass tile = MainClass.tilesArray[posY][posX];
		generator.setWalls(tile);
		
		assertEquals(MainClass.tilesArray[posY][posX+1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY][posX-1].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY+1][posX].getState(),"wall");
		assertEquals(MainClass.tilesArray[posY-1][posX].getState(),"wall");

	}
	
	@Test
	public void test_FillBorders() {
		//setUp();
		
		MainClass.mazeSizeHeight = 10;
		MainClass.mazeSizeWidth = 10;
		grid = new MainClass.Grid();
		grid.clearData();
		
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
		for (int posY = 1; posY < MainClass.mazeSizeHeight-1; posY++){
			for (int posX = 1; posX < MainClass.mazeSizeWidth-1; posX++){
				if (MainClass.tilesArray[posY][posX].getState()!="empty"){
					assertEquals(MainClass.tilesArray[posY][posX].getState(),"start");
				}
			}
		}
	}
	
	
	@Test
	public void test_setFinishPoint(){
		setUp();
		int posX = 5;
		int posY = 5;
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
		int posX = 5;
		int posY = 5;
		TileClass tile = MainClass.tilesArray[posY][posX];
		
		MainClass.pathsArray = new ArrayList<PathClass>(); 
		assertEquals(MainClass.pathsArray.size(),0);
		
		generator.generatePath(tile, 10);
		assertTrue(MainClass.pathsArray.size()>0);
	}

	
	@Test
	public void test_generateIntersection(){
		setUp();
		int posX = 5;
		int posY = 5;
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

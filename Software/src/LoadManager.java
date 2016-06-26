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


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoadManager {
	
	class InvalidByteException extends Exception{
		private static final long serialVersionUID = 1L;
		byte wrongByte;
		
		public InvalidByteException(byte wrongByte){
			this.wrongByte = (byte) wrongByte;
		}
	}
	
	public static TileClass[][] loadFile(File mazeFile) throws IOException{
		FileInputStream fis = new FileInputStream(mazeFile);
		DataInputStream dis = new DataInputStream(fis);
		
		MainClass.mazeSizeHeight = dis.readInt();
		MainClass.mazeSizeWidth = dis.readInt();
		
		TileClass[][] tilesArray = new TileClass[MainClass.mazeSizeHeight][MainClass.mazeSizeHeight];
		
		for (int posY = 0; posY < MainClass.mazeSizeHeight; posY++){
    		for (int posX = 0; posX < MainClass.mazeSizeWidth; posX++){
    			tilesArray[posY][posX] = new TileClass(posY*MainClass.mazeSizeWidth+posX,posY,posX);
    			tilesArray[posY][posX].setState("empty");
        	}
    	}
		
		for (int posY = 0; posY < MainClass.mazeSizeHeight; posY++){
    		for (int posX = 0; posX < MainClass.mazeSizeWidth; posX++){
				byte b = (byte) fis.read();
				switch(b){
					case(1): {
						tilesArray[posY][posX].setState("empty");
						break;
					}
					case(2): {
						tilesArray[posY][posX].setState("wall");
						break;
					}
					case(3): {
						tilesArray[posY][posX].setState("hwall");
						break;
					}
					case(4): {
						tilesArray[posY][posX].setState("start");
						break;
					}
					case(5): {
						tilesArray[posY][posX].setState("finish");
						break;
					}
					case(6): {
						tilesArray[posY][posX].setState("path");
						break;
					}
					case(7): {
						tilesArray[posY][posX].setState("turn");
						break;
					}
					case(8): {
						tilesArray[posY][posX].setState("cross");
						break;
					}
					default: {
						try {
							throw new LoadManager().new InvalidByteException((byte) b);
						} catch (InvalidByteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		dis.close();
		fis.close();
		return tilesArray;
	}
	
	public static void saveFile(File mazeFile) throws IOException{
		FileOutputStream fos = new FileOutputStream(mazeFile);
		DataOutputStream dos = new DataOutputStream(fos);
		
		dos.writeInt(MainClass.mazeSizeWidth);
		dos.writeInt(MainClass.mazeSizeHeight);
		
		for (int posY = 0; posY < MainClass.mazeSizeHeight; posY++){
    		for (int posX = 0; posX < MainClass.mazeSizeWidth; posX++){
				switch(MainClass.tilesArray[posY][posX].getState()){
					case("empty"): {
						fos.write(1);
						break;
					}
					case("wall"): {
						fos.write(2);
						break;
					}
					case("hwall"): {
						fos.write(3);
						break;
					}
					case("start"): {
						fos.write(4);
						break;
					}
					case("finish"): {
						fos.write(5);
						break;
					}
					case("path"): {
						fos.write(6);
						break;
					}
					case("turn"): {
						fos.write(7);
						break;
					}
					case("cross"): {
						fos.write(8);
						/*
						if (MainClass.tilesArray[posY][posX].getPaths().indexOf(MainClass.pathsArray.get(0)) != -1){
							fos.write(11);
						}else{
							fos.write(8);
						}
						*/
						break;
					}
					default:
				}
			}
		}
		dos.close();
		fos.close();
	}
}
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
 * ��������� ��������� ����������, ���������� � ������ ���������, �� ������������ � ��������� � ����������� �������������:
 * ���������� ������������� � ������ ����� ��� ��������������� ��������� ��� ������ ��������������� ���������
 * �������� �������������� ������(-��)
 *
 * ������: ���������� ������, ����� �������, �������� ������, ������� �����
 * �������� ��� ������� ������� ���������� � ������ ������������ ������� �� ���������� "������ ����������������" ������������ ���� (1�� ����, 2�� �������)
 * ������� 2016 - ���� 2016
 *
 * �� ���� ��������� �������� ���������� � ���������� ����������� �� GitHub <https://github.com/Crusherk14/MazeGenerator>
 */


import java.util.ArrayList;

public class PathClass {
	private long ID;	//Unique ID
	private int length;	//Length of the path
	//private int distance;	//Maximum distance from the ...
	private ArrayList<TileClass> intersections = new ArrayList<TileClass>();	//Intersections on path
	private ArrayList<TileClass> tiles = new ArrayList<TileClass>();	//Assigned Tiles
	
	public PathClass(int ID,TileClass startTile){
		this.ID = ID;
		addTile(startTile);
	}
	
	public void addTile(TileClass tile){
		this.tiles.add(tile);
		this.length +=1;
	}
	
	public void addCrossing(TileClass tile){
		this.intersections.add(tile);
	}
	
	public long getID(){
		return this.ID;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public ArrayList<TileClass> getTiles(){
		return tiles;
	}
	
	public ArrayList<TileClass> getCrossings(){
		return intersections;
	}
	/*
	public int countDistanceFromLastIntersection(){
		TileClass lastCrossTile = tiles.get(0);
		for (TileClass cTile : tiles){
			if (cTile.getState() == "cross"){
				lastCrossTile = cTile;
			}
		}
		return tiles.size()-tiles.indexOf(lastCrossTile)-1;
	}
	*/
}

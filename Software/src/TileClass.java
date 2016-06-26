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
import java.util.HashMap;
import java.util.Map;

public class TileClass {
	
	private long ID;	//Unique ID
	private int coordinate[] = new int[2];	//Y;X coordinates
	state state;
	public enum state{ empty, wall, hwall, start, finish, path, turn, cross };
	direction direction;
	public enum direction { up, down, left, right };
	
	private Map<PathClass,direction> assignedPaths = new HashMap<PathClass,direction>();
	
	public TileClass(long ID,int posY,int posX){
		this.ID = ID;
		this.coordinate[0]=posY;
		this.coordinate[1]=posX;
		setState("empty");
	}
	
	public String getState(){
		return state.toString();
	}
	
	public void setState(String state){
		this.state = this.state.valueOf(state);
	}
	
	public void assignPath(PathClass path, String direction){
		this.assignedPaths.put(path,this.direction.valueOf(direction));
	}
	
	public int[] getCoords(){
		return coordinate;
	}
	
	public String getDirection(PathClass path){
		return assignedPaths.get(path).toString();
	}
	
	
	public ArrayList<PathClass> getPaths(){
		ArrayList<PathClass> paths = new ArrayList<PathClass>();
		for (PathClass cPath: assignedPaths.keySet()){
			paths.add(cPath);
		}
		return paths;
	}
	
}

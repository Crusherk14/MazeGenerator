import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileClass {
	
	private int ID;	//Unique ID
	private int coordinate[] = new int[2];	//Y;X coordinates
	state state;
	public enum state{ empty, wall, hwall, start, finish, path };
	direction direction;
	public enum direction { up, down, left, right };
	//private ArrayList<Integer> paths = new ArrayList<Integer>(4);	//Assigned paths
	private Map<direction, PathClass> paths = new HashMap<direction, PathClass>();
	
	public TileClass(int ID,int posY,int posX){
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
		this.paths.put(this.direction.valueOf(direction),path);
	}
	
	public int[] getCoords(){
		return coordinate;
	}
}


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

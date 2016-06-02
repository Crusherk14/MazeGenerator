import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileClass {
	
	private int ID;	//Unique ID
	private int coordinate[] = new int[2];	//Y;X coordinates
	state state;
	public enum state{ empty, wall, hwall, start, finish, path, turn, cross };
	direction direction;
	public enum direction { up, down, left, right };
	
	private Map<PathClass,direction> paths = new HashMap<PathClass,direction>();
	
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
		this.paths.put(path,this.direction.valueOf(direction));
	}
	
	public int[] getCoords(){
		return coordinate;
	}
	
	public String getDirection(PathClass path){
		//System.out.println("[TileClass] Getting direction of tile at"+coordinate[0]+":"+coordinate[1]);
		return paths.get(path).toString();
	}
}

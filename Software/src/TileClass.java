import java.util.ArrayList;

public class TileClass {
	
	private int ID;	//Unique ID
	private int coordinate[] = new int[2];	//Y;X coordinates
	private String state;	//empty/wall/start/finish
	private ArrayList<Integer> paths = new ArrayList<Integer>(4);	//Assigned paths
	
	public TileClass(int ID,int posY,int posX){
		this.ID = ID;
		this.coordinate[0]=posY;
		this.coordinate[1]=posX;
		this.state = "empty";
	}
	
	public String getState(){
		return state;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public void assignPath(int pathID){
		this.paths.add(pathID);
	}
	
	public int[] getCoords(){
		return coordinate;
	}
}

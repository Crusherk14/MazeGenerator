import java.util.ArrayList;

public class PathClass {
	private int ID;	//Unique ID
	private int length;	//Length of the path
	private int distance;	//Maximum distance from the ...
	private ArrayList<TileClass> tiles = new ArrayList<TileClass>();	//Assigned Tiles
	
	public PathClass(int ID,TileClass startTile){
		this.ID = ID;
		addTile(startTile);
	}
	
	public void addTile(TileClass tile){
		this.tiles.add(tile);
	}
	
	public int getID(){
		return this.ID;
	}

}

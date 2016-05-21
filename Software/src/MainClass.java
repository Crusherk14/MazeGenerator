import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumnModel;


public class MainClass {
	
	public static int mazeSizeWidth = 0;
	public static int mazeSizeHeight = 0;
	
	public final static int tileSize = 10;
	
	public static JFrame window = new JFrame();
	public static JPanel panel=new JPanel();
	
	public static TileClass tilesArray[][];

	public static Random random = new Random();
	
	
	public static void main(String[] args){	

	}
	
	//Grid
	public static class Grid extends JPanel {
		private static final long serialVersionUID = 6584878493870359373L;

		public Grid() {
        	
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        	
            //Draws basic black grid
            int TileSize = MainClass.tileSize;
            int MazeHeight = MainClass.mazeSizeHeight;
            int MazeWidth = MainClass.mazeSizeWidth;
            g.setColor(Color.BLACK);
            g.drawRect (TileSize, TileSize, MazeWidth*TileSize, MazeHeight*TileSize);

            for (int i = TileSize; i <= MazeWidth*TileSize+TileSize; i+= TileSize)
                g.drawLine (i, TileSize, i, (1+MazeHeight)*TileSize);

            for (int i = 10; i <= MazeHeight*TileSize+TileSize; i+= TileSize)
                g.drawLine (TileSize, i, (1+MazeWidth)*TileSize, i);
            
            
          //Filling tiles with different colors
        	for (int posY = 0; posY < mazeSizeHeight; posY++){
        		for (int posX = 0; posX < mazeSizeWidth; posX++){
        			
        			int cellY = 10 + (posY * 10);
                    int cellX = 10 + (posX * 10);
                    
                    //TODO: check if tilesArray is empty at start?
        			switch (tilesArray[posY][posX].getState()){
        			case "wall":
        				g.setColor(Color.GRAY);	//LIGHT_GRAY / DARK_GRAY for other types of wall
        				g.fillRect(cellX, cellY, 10, 10);
        				break;
        			case "start":
        				g.setColor(Color.GREEN);
        				g.fillRect(cellX, cellY, 10, 10);
        				break;
        			case "path":
        				g.setColor(Color.BLUE);
        				g.fillRect(cellX, cellY, 10, 10);
        				break;
        			}
            	}
        	}
            
        }

        //Calls grid to be completely repainted according to TilesAraay
        public void repaintCells(){
        	repaint();
        }
        
        public void initArray(){
        	tilesArray = new TileClass[mazeSizeHeight][mazeSizeWidth];
        	
        	for (int posY = 0; posY < mazeSizeHeight; posY++){
        		for (int posX = 0; posX < mazeSizeWidth; posX++){
        			tilesArray[posY][posX] = new TileClass(posY*mazeSizeWidth+posX,posY,posX);
            	}
        	}
        }

    }
	
	public static class Generator {
		public int pathLength;
		private final Random random = new Random();
		public Grid grid;
		
	    public Generator(Grid getGrid) {
	     	grid = getGrid;
	    }
			    
	    //Main methods of Generator
	    
	    //Filling borders of tilesArray
	    public void FillBorders(){
		     	for (int posY = 1; posY <= mazeSizeHeight-1; posY++){
		     		tilesArray[posY][0].setState("wall");
		     		tilesArray[posY][mazeSizeWidth-1].setState("wall");
		        }
		        for (int posX = 0; posX<mazeSizeWidth; posX++){
		        	tilesArray[0][posX].setState("wall");
		            tilesArray[mazeSizeHeight-1][posX].setState("wall");
		        }
	        }
	    
	    //Choosing random StartPoint position
	    public TileClass setStartPoint(){
	    	int startY = random.nextInt(mazeSizeHeight-2)+1;
	    	int startX = random.nextInt(mazeSizeWidth-2)+1;
	    	tilesArray[startY][startX].setState("start");
	    	System.out.println("[StartPoint] Set at:"+startY+":"+startX);
			return tilesArray[startY][startX];
        }
		 
		 public void clearMaze(){
			 for (int posY = 0; posY < mazeSizeHeight; posY++){
	        		for (int posX = 0; posX < mazeSizeWidth; posX++){
	        			tilesArray[posY][posX].setState("empty");
	        		}
				 }
		 }
		 
		 public void generatePath(TileClass fromTile,int length){
			 TileClass cTile=fromTile;
			 
			 for (int cLength = 1;cLength<=length;cLength++){
				 System.out.println("[PATH] generating #"+cLength+" tile");
				 TileClass newTile = generateNextTile(cTile);
				 if (newTile == cTile) {break;}
				 else{
					 cTile = newTile;
				 }
			 }
		 }
		 
		 //TODO: Sum of percentages have to 100, not 99.9999999
		 //TODO: Make searching for surroundings work as an external method
		 
		 public TileClass generateNextTile(TileClass fromTile){
			 TileClass newTile = fromTile;
			 Map<String, TileClass> surroundTiles = new HashMap<String, TileClass>();
			 Map<String, Double> surroundTilesChance = new HashMap<String, Double>();
			 
			 //Getting sourroundings
			 int[] cCoords = fromTile.getCoords();
			 if (tilesArray[cCoords[0]+1][cCoords[1]+0].getState() == "empty"){
				 surroundTiles.put("down", tilesArray[cCoords[0]+1][cCoords[1]+0]);
			 }
			 if (tilesArray[cCoords[0]-1][cCoords[1]+0].getState() == "empty"){
				 surroundTiles.put("up", tilesArray[cCoords[0]-1][cCoords[1]+0]);
			 }
			 if (tilesArray[cCoords[0]+0][cCoords[1]+1].getState() == "empty"){
				 surroundTiles.put("right", tilesArray[cCoords[0]+0][cCoords[1]+1]);
			 }
			 if (tilesArray[cCoords[0]+0][cCoords[1]-1].getState() == "empty"){
				 surroundTiles.put("left", tilesArray[cCoords[0]+0][cCoords[1]-1]);
			 }
			 
			 System.out.print("[TILE] Free surroundings: ");
			 for (String cKey : surroundTiles.keySet()) {
				 System.out.print(cKey+";");
				}
			 System.out.println();
			 
			 //Check if there is any available tiles to go
			 if (surroundTiles.size() != 0){
				 System.out.println("[TILE] Available tiles to generate: "+surroundTiles.size());
			 
				 //Checking directions
				 TileClass cTile;
				 
				 //up
				 cTile = surroundTiles.get("up");
				 if (cTile != null){
					 //Checking absolute distance
					 int distance = cTile.getCoords()[0]-2;	//Y distance to top border
					 
					 //Summarizing
					 double summaryChance = distance*1.0;	// put different values with their coefficients
					 surroundTilesChance.put("up", summaryChance);
				 }
				 
				 //down
				 cTile = surroundTiles.get("down");
				 if (cTile != null){
					 //Checking absolute distance
					 int distance = mazeSizeHeight-cTile.getCoords()[0]-1;	//Y distance to bottom border
					 
					//Summarizing
					 double summaryChance = distance*1.0;	// put different values with their coefficients
					 surroundTilesChance.put("down", summaryChance);
				 }
				 
				//left
				 cTile = surroundTiles.get("left");
				 if (cTile != null){
					 //Checking absolute distance
					 int distance = cTile.getCoords()[1]-1;	//X distance to left border
					 
					//Summarizing
					 double summaryChance = distance*1.0;	// put different values with their coefficients
					 surroundTilesChance.put("left", summaryChance);
				 }
				 
				//right
				 cTile = surroundTiles.get("right");
				 if (cTile != null){
					 //Checking absolute distance
					 int distance = mazeSizeWidth-cTile.getCoords()[1]-2;	//X distance to right border
					 
					//Summarizing
					 double summaryChance = distance*1.0;	// put different values with their coefficients
					 surroundTilesChance.put("right", summaryChance);
				 }
				 
				 //Getting sum of all chances
				 double sumChance = 0;
				 for (Double cChance : surroundTilesChance.values()) {
					    sumChance += cChance;
					}
				 
				 System.out.print("[TILE] Chances for tiles: ");
				 for (String cKey : surroundTilesChance.keySet()) {
					 System.out.print(cKey+"|"+surroundTilesChance.get(cKey)+";");
					}
				 System.out.println();
				 
				 //Calculating percentage for each direction
				 for (String cKey : surroundTilesChance.keySet()) {
					 	surroundTilesChance.put(cKey,surroundTilesChance.get(cKey)/sumChance*100);
					}
				 
				 System.out.print("[TILE] Percentages for tiles: ");
				 for (String cKey : surroundTilesChance.keySet()) {
					 System.out.print(cKey+"|"+surroundTilesChance.get(cKey)+";");
					}
				 System.out.println();
				 
				 
				 //Choosing direction
				 
				 //Creating random integer [1-100]
				 int roll = 1+random.nextInt(99);
				 System.out.println("[TILE] Given random roll: "+roll);
				 //Percentage structure: up(%),down(%),left(%),right(%)
				 double lastPercent = 0;
				 for (String cKey : surroundTilesChance.keySet()) {
					 	lastPercent+=surroundTilesChance.get(cKey);
					 	System.out.println("[TILE] Checking percentage for ["+cKey+"|"+surroundTilesChance.get(cKey)+"]. "+roll+" have to be under"+lastPercent);
					 	if (roll<=lastPercent){
					 		newTile = surroundTiles.get(cKey);
					 		
					 		//Setting state of previous surroundings as wall
							 for (String cKey1 : surroundTiles.keySet()) {
								 	surroundTiles.get(cKey1).setState("wall");
								}
							 
							//Setting state of generated Tile as path
							 newTile.setState("path");
							 
							System.out.println("[TILE] Generating new tile at ["+cKey+"]. Coords: "+newTile.getCoords()[0]+";"+newTile.getCoords()[1]);
					 		
					 		break;
					 	}
					}
			 }
			 return newTile;
		 }
		 
		 public void generateIntersections(PathClass path){
			 
		 }
		 
		 
		 //Path Generator class
		 /*
		 public class PathGenerator{
	        	private TileClass currentTile;
	        	private int currentCoords[] = new int[2];
	        	
	        	//? Sets coordinate of current tile
	        	public void setCoordinate(int posY, int posX){
	        		currentCoords[0] = posY;
	        		currentCoords[1] = posX;
	        		currentTile = tilesArray[currentCoords[0]][currentCoords[1]];
	        	}
	        	
	        	//? Gets coordinate of current tile
	        	public int[] getCoordinate(){
	    			return this.currentCoords;
	        	}
	        	
	        	public int[] getNextTile(int[] coords, String dir){
	        		int[] result = {coords[0], coords[1]};
	    	    	switch(dir){
	    				case "up":
	    					result[0]--;
	    					break;
	    				case "down":
	    					result[0]++;
	    					break;
	    				case "left":
	    					result[1]--;
	    					break;
	    				case "right":
	    					result[1]++;
	    					break;
	    			}
	    	    	return result;
	        	}
	        	
	        	public boolean checkState(int[] coords, String state){
	        		return (tilesArray[coords[0]][coords[1]].getState().equals(state));
	        	}
	        }
		  */
	}
}
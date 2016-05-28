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

import sun.rmi.runtime.Log;

import javax.swing.table.TableColumnModel;


public class MainClass {
	
	public static int mazeSizeWidth = 0;
	public static int mazeSizeHeight = 0;
	
	public final static int tileSize = 10;
	
	public static JFrame window = new JFrame();
	public static JPanel panel=new JPanel();
	
	public static TileClass tilesArray[][];
	public static ArrayList<PathClass> pathsArray =  new ArrayList<PathClass>();

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
            
          //Filling tiles with different colors
        	for (int posY = 0; posY < mazeSizeHeight; posY++){
        		for (int posX = 0; posX < mazeSizeWidth; posX++){
        			
        			int cellY = 10 + (posY * 10);
                    int cellX = 10 + (posX * 10);
                    
                    //TODO: check if tilesArray is empty at start?
        			switch (tilesArray[posY][posX].getState()){
        			case "wall":
        				g.setColor(new Color(150,150,150));
        				g.fillRect(cellY, cellX, 10, 10);
        				break;
        			case "hwall":
        				g.setColor(new Color(90,90,90));
        				g.fillRect(cellY, cellX, 10, 10);
        				break;
        			case "start":
        				g.setColor(Color.GREEN);
        				g.fillRect(cellY, cellX, 10, 10);
        				break;
        			case "path":
        				g.setColor(new Color(30,144,255));
        				g.fillRect(cellY, cellX, 10, 10);
        				break;
        			}
            	}
        	}
        	
	    	//Draws basic black grid
	        int TileSize = MainClass.tileSize;
	        int MazeHeight = MainClass.mazeSizeHeight;
	        int MazeWidth = MainClass.mazeSizeWidth;
	        g.setColor(Color.BLACK);
	        g.drawRect (TileSize, TileSize, MazeHeight*TileSize, MazeWidth*TileSize);
	        
	        for (int i = TileSize; i <= MazeHeight*TileSize+TileSize; i+= TileSize)
	        	g.drawLine (i, TileSize, i, (1+MazeWidth)*TileSize);
	
	        for (int i = TileSize; i <= MazeWidth*TileSize+TileSize; i+= TileSize)
	        	g.drawLine (TileSize, i, (1+MazeHeight)*TileSize, i);
	            
            
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
		
		
		private double GEN_TILE_COEFFICIENT_distanceAbs = 0.2;	//20%
		private double GEN_TILE_COEFFICIENT_distanceBlock = 0.8;	//80%
		
	    public Generator(Grid getGrid) {
	     	grid = getGrid;
	    }
			    
	    //Main methods of Generator
	    
	    //Filling borders of tilesArray
	    public void FillBorders(){
		     	for (int posY = 1; posY <= mazeSizeHeight-1; posY++){
		     		tilesArray[posY][0].setState("hwall");
		     		tilesArray[posY][mazeSizeWidth-1].setState("hwall");
		        }
		        for (int posX = 0; posX<mazeSizeWidth; posX++){
		        	tilesArray[0][posX].setState("hwall");
		            tilesArray[mazeSizeHeight-1][posX].setState("hwall");
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
	    
	    //Sets finishing tile as Finish
	    public void setFinishPoint(TileClass tile){
	    	
	    }
		 
		 public void clearMaze(){
			 for (int posY = 0; posY < mazeSizeHeight; posY++){
	        		for (int posX = 0; posX < mazeSizeWidth; posX++){
	        			tilesArray[posY][posX].setState("empty");
	        		}
				 }
		 }
		 
		 public void generatePath(TileClass fromTile,int length){
			 PathClass cPath = new PathClass(pathsArray.size(),fromTile);
			 pathsArray.add(cPath);
			 
			 System.out.println("[PATH] Generating new path #"+cPath.getID());
			 
			 TileClass cTile=fromTile;
			 
			 for (int cLength = 1;cLength<=length;cLength++){
				 System.out.println("[PATH] Generating #"+cLength+" tile");
				 TileClass newTile = generateNextTile(cTile,cPath);
				 if (newTile == cTile) {break;}
				 else{
					 cTile = newTile;
					 cPath.addTile(newTile);
				 }
			 }
		 }
		 
		 //TODO: Sum of percentages have to be 100, not 99.9999
		 //DONE: Make searching for surroundings work as an external method
		 //DONE: Chances need to be counted as described in dialog (%=(cDistanceAbs/sum(iDistanceAbs))*0.2+(cDistanceBlock/sum(iDistanceBlock))*0.8) and so on...
		 //DONE: Chances don't have to be counted for surroundings. Change cTile to fromTile
		 
		 public Map<String, TileClass> getSurroundTiles(TileClass cTile, String[] stateFilters){
			 Map<String, TileClass> surroundTiles = new HashMap<String, TileClass>();
			 
			 int[] cCoords = cTile.getCoords();
			 for (String state: stateFilters){
				 if (tilesArray[cCoords[0]+1][cCoords[1]+0].getState() == state){
					 surroundTiles.put("down", tilesArray[cCoords[0]+1][cCoords[1]+0]);
				 }
				 if (tilesArray[cCoords[0]-1][cCoords[1]+0].getState() == state){
					 surroundTiles.put("up", tilesArray[cCoords[0]-1][cCoords[1]+0]);
				 }
				 if (tilesArray[cCoords[0]+0][cCoords[1]+1].getState() == state){
					 surroundTiles.put("right", tilesArray[cCoords[0]+0][cCoords[1]+1]);
				 }
				 if (tilesArray[cCoords[0]+0][cCoords[1]-1].getState() == state){
					 surroundTiles.put("left", tilesArray[cCoords[0]+0][cCoords[1]-1]);
				 }
			 }
			 
			 return surroundTiles;
		 }
		 
		 public TileClass generateNextTile(TileClass fromTile, PathClass cPath){
			 TileClass newTile = fromTile;
			 Map<String, TileClass> surroundTiles = new HashMap<String, TileClass>();
			 Map<String, Integer[]> surroundTilesParameters = new HashMap<String, Integer[]>();
			 Map<String, Double> surroundTilesChances = new HashMap<String, Double>();
			 
			 //Getting surroundings
			 surroundTiles = getSurroundTiles(fromTile, new String[]{"empty"});
			 
			 System.out.print("[TILE] Free surroundings: ");
			 for (String cKey : surroundTiles.keySet()) {
				 System.out.print(cKey+";");
				}
			 System.out.println();
			 
			 //Checking if there is any available tiles to go
			 if (surroundTiles.size() != 0){
				 System.out.println("[TILE] Available tiles to generate: "+surroundTiles.size());
			 
				 //Checking directions
				 TileClass cTile;
				 String cKey;
				 
				 //up
				 cKey="up";
				 cTile = surroundTiles.get(cKey);
				 if (cTile != null){					 
					 //Checking absolute distance
					 int distanceAbs = fromTile.getCoords()[0]-1;	//Y distance to top border
					 
					 //Checking distance to closest roadblock
					 int distanceBlock = 0;
					 int posY = fromTile.getCoords()[0];
					 int posX = fromTile.getCoords()[1];
					 for (posY-=1;posY>=0;posY--){
						 if (tilesArray[posY][posX].getState() != "empty"){
							 break;
						 }
						 distanceBlock+=1;
					 }
					 System.out.println("[TILE] Counting distance to closest roadblock for "+cKey+":"+distanceBlock);
					 
					 //Summarizing
					 Integer[] params = {distanceAbs,distanceBlock};
					 surroundTilesParameters.put(cKey,params);
					 
					 //double summaryChance = distanceAbs*GEN_TILE_COEFFICIENT_distanceAbs+distanceBlock*GEN_TILE_COEFFICIENT_distanceBlock;	// put different values with their coefficients
					 //surroundTilesChance.put(cKey, summaryChance);
				 }
				 
				 //down
				 cKey="down";
				 cTile = surroundTiles.get(cKey);
				 if (cTile != null){
					 //Checking absolute distance
					 int distanceAbs = mazeSizeHeight-fromTile.getCoords()[0]-2;	//Y distance to bottom border
					 
					//Checking distance to closest roadblock
					 int distanceBlock = 0;
					 int posY = fromTile.getCoords()[0];
					 int posX = fromTile.getCoords()[1];
					 for (posY+=1;posY<=mazeSizeHeight-1;posY++){
						 if (tilesArray[posY][posX].getState() != "empty"){
							 break;
						 }
						 distanceBlock+=1;
					 }
					 System.out.println("[TILE] Counting distance to closest roadblock for "+cKey+":"+distanceBlock);
					 
					 //Summarizing
					 Integer[] params = {distanceAbs,distanceBlock};
					 surroundTilesParameters.put(cKey,params);
					 
					 //double summaryChance = distanceAbs*GEN_TILE_COEFFICIENT_distanceAbs+distanceBlock*GEN_TILE_COEFFICIENT_distanceBlock;	// put different values with their coefficients
					 //surroundTilesChance.put(cKey, summaryChance);
				 }
				 
				//left
				 cKey="left";
				 cTile = surroundTiles.get(cKey);
				 if (cTile != null){
					 //Checking absolute distance
					 int distanceAbs = fromTile.getCoords()[1]-1;	//X distance to left border
					 
					//Checking distance to closest roadblock
					 int distanceBlock = 0;
					 int posY = fromTile.getCoords()[0];
					 int posX = fromTile.getCoords()[1];
					 for (posX-=1;posX>=0;posX--){
						 if (tilesArray[posY][posX].getState() != "empty"){
							 break;
						 }
						 distanceBlock+=1;
					 }
					 System.out.println("[TILE] Counting distance to closest roadblock for "+cKey+":"+distanceBlock);
					 
					 //Summarizing
					 Integer[] params = {distanceAbs,distanceBlock};
					 surroundTilesParameters.put(cKey,params);
					 
					 //double summaryChance = distanceAbs*GEN_TILE_COEFFICIENT_distanceAbs+distanceBlock*GEN_TILE_COEFFICIENT_distanceBlock;	// put different values with their coefficients
					 //surroundTilesChance.put(cKey, summaryChance);
				 }
				 
				//right
				 cKey="right";
				 cTile = surroundTiles.get(cKey);
				 if (cTile != null){
					 //Checking absolute distance
					 int distanceAbs = mazeSizeWidth-fromTile.getCoords()[1]-2;	//X distance to right border
					 
					//Checking distance to closest roadblock
					 int distanceBlock = 0;
					 int posY = fromTile.getCoords()[0];
					 int posX = fromTile.getCoords()[1];
					 for (posX+=1;posX<=mazeSizeWidth-1;posX++){
						 if (tilesArray[posY][posX].getState() != "empty"){
							 break;
						 }
						 distanceBlock+=1;
					 }
					 System.out.println("[TILE] Counting distance to closest roadblock for "+cKey+":"+distanceBlock);
					 
					 //Summarizing
					 Integer[] params = {distanceAbs,distanceBlock};
					 surroundTilesParameters.put(cKey,params);
					 
					 //double summaryChance = distanceAbs*GEN_TILE_COEFFICIENT_distanceAbs+distanceBlock*GEN_TILE_COEFFICIENT_distanceBlock;	// put different values with their coefficients
					 //surroundTilesChance.put(cKey, summaryChance);
				 }
				 
				 //Getting sum of all chances
				 /*
				 double sumChance = 0;
				 for (Double cChance : surroundTilesChance.values()) {
					    sumChance += cChance;
					}
				  */
				 
				 //Calculating percentages
				 //Getting sum of distanceAbs
				 int sumDistanceAbs = 0;
				 for (String ccKey : surroundTilesParameters.keySet()) {
					 sumDistanceAbs += surroundTilesParameters.get(ccKey)[0];
				 }
				 System.out.println("[TILE] Sum of distanceAbs: "+sumDistanceAbs);
				 
				 //Getting sum of distanceBlock
				 int sumDistanceBlock = 0;
				 for (String ccKey : surroundTilesParameters.keySet()) {
					 sumDistanceBlock += surroundTilesParameters.get(ccKey)[1];
				 }
				 System.out.println("[TILE] Sum of distanceBlock: "+sumDistanceBlock);
				 
				 //Calculating percentages for each available direction
				 for (String ccKey : surroundTilesParameters.keySet()) {
					 Double chanceDistanceAbs = (double) surroundTilesParameters.get(ccKey)[0]/sumDistanceAbs*100;
					 Double chanceDistanceBlock = (double) surroundTilesParameters.get(ccKey)[1]/sumDistanceBlock*100;
					 Double chance = (double) GEN_TILE_COEFFICIENT_distanceAbs*chanceDistanceAbs+GEN_TILE_COEFFICIENT_distanceBlock*chanceDistanceBlock;
					 surroundTilesChances.put(ccKey,chance);
				 }
				 
				 /*
				 System.out.print("[TILE] Chances for tiles: ");
				 for (String ccKey : surroundTilesChances.keySet()) {
					 System.out.print(cKey+"|"+surroundTilesChances.get(cKey)+";");
					}
				 System.out.println();
				 
				 //Calculating percentage for each direction
				 for (String ccKey : surroundTilesChances.keySet()) {
					 	surroundTilesChances.put(cKey,surroundTilesChances.get(cKey)/sumChance*100);
					}
				 */
				 
				 System.out.print("[TILE] Percentages for tiles: ");
				 for (String ccKey : surroundTilesChances.keySet()) {
					 System.out.print(ccKey+"|"+surroundTilesChances.get(ccKey)+";");
					}
				 System.out.println();
				 
				 
				 //Choosing direction
				 
				 //Creating random integer [1-100]
				 int roll = 1+random.nextInt(99);
				 System.out.println("[TILE] Given random roll: "+roll);
				 //Percentage structure: up(%),down(%),left(%),right(%)
				 double lastPercent = 0;
				 for (String ccKey : surroundTilesChances.keySet()) {
					 	lastPercent+=surroundTilesChances.get(ccKey);
					 	System.out.println("[TILE] Checking percentage for ["+ccKey+"|"+surroundTilesChances.get(ccKey)+"]. "+roll+" have to be under "+lastPercent);
					 	if (roll<=lastPercent){
					 		newTile = surroundTiles.get(ccKey);
					 		
					 		//Setting state of previous surroundings as wall
							 for (String cccKey : surroundTiles.keySet()) {
								 	surroundTiles.get(cccKey).setState("wall");
								}
							 
							//Setting state of generated Tile as path
							 newTile.setState("path");
							 newTile.assignPath(cPath, ccKey);
							 
							System.out.println("[TILE] Generating new tile at ["+ccKey+"]. Coords: "+newTile.getCoords()[0]+";"+newTile.getCoords()[1]);
					 		
					 		break;
					 	}
					}
			 }
			 return newTile;
		 }
		 
		 public void generateIntersections(PathClass path){
			 
		 }
	}
}
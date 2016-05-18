import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
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
	    public int[] setStartPoint(){
	    	int startY = random.nextInt(mazeSizeHeight-2)+1;
	    	int startX = random.nextInt(mazeSizeWidth-2)+1;
	    	tilesArray[startY][startX].setState("start");
	    	int[] returnList = {startY, startX};
			return returnList;
        }
		 
		 public void clearMaze(){
			 for (int posY = 0; posY < mazeSizeHeight; posY++){
	        		for (int posX = 0; posX < mazeSizeWidth; posX++){
	        			tilesArray[posY][posX].setState("empty");
	        		}
				 }
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
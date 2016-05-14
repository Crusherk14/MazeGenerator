import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComponent;


public class MainClass {
	
	public static int mazeSizeWidth = 20;
	public static int mazeSizeHeight = 20;
	
	public final static int tileSize = 10;
	
	public static JFrame window = new JFrame();
	public static JPanel panel=new JPanel();
	
	public static TileClass tilesArray[][];
	
	public static Random random = new Random();
	
	
	public static void main(String[] args){		
		window.setSize(640, 640);
		//window.setResizable(false);
		
		window.setTitle("MazeGenerator v0.1a");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		Box mainBox = Box.createHorizontalBox();
		
		Box topBox = Box.createVerticalBox();
		topBox.setBorder(new TitledBorder("Parameters"));
		mainBox.add(topBox);
		
		Grid grid = new Grid();
		Box box_grid = Box.createVerticalBox();
		box_grid.setBorder(new TitledBorder("Maze display"));
		box_grid.add(grid);
		//window.add(box_grid);
		mainBox.add(box_grid);
		
		window.setContentPane(mainBox);
		
		Generator generator = new Generator(grid);
		generator.setStartPoint();
	}
	
	//Grid
	public static class Grid extends JPanel {

        public Grid() {
        	tilesArray = new TileClass[mazeSizeWidth][mazeSizeHeight];
    		
    		for (int xpos = 0; xpos < mazeSizeWidth; xpos++){
        		for (int ypos = 0; ypos < mazeSizeHeight; ypos++){
        			
        			int value = random.nextInt(6);
        			
        			if (value <= 3){
        				tilesArray[xpos][ypos] = new TileClass("empty");
        			}else{
        				tilesArray[xpos][ypos] = new TileClass("wall");
        			}
        			
            	}
        	}
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            //Filling tiles with different colors
            for (int xpos = 0; xpos < mazeSizeWidth; xpos++){
        		for (int ypos = 0; ypos < mazeSizeHeight; ypos++){
        			if (tilesArray[xpos][ypos].getState()=="wall"){
        				int cellX = 10 + (xpos * 10);
                        int cellY = 10 + (ypos * 10);
                        g.setColor(Color.RED);
                        g.fillRect(cellX, cellY, 10, 10);
        			}
            	}
        	}
        	
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
        }

        //Calls grid to be completely repainted according to TilesAraay
        public void repaintCells(){
        	repaint();
        }

    }
	
	public static class Generator {
		 public List<Point> tilesArray;
		 public int pathLength;
		 
		 private final Random random = new Random();
		 
		 public Grid grid;

	        public Generator(Grid getGrid) {
	            grid = getGrid;
	        }
	     
		 public void setStartPoint(){
			 int posX = random.nextInt(mazeSizeWidth-1);
			 int posY = random.nextInt(mazeSizeWidth-1);
			 grid.repaintCells();
		 }
		 
		 public void generatePath(){
			 
		 }
	        
	}
}

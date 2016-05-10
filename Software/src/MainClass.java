import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComponent;


public class MainClass {
	
	public static int mazeSizeHeight = 10;
	public static int mazeSizeWidth = 10;
	
	public final static int tileSize = 10;
	
	public static JFrame window = new JFrame();
	public static JPanel panel=new JPanel();
	
	
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
		
		grid.fillCell(2, 1);	//X;Y coordinates starting from 0;0
	}
	
	//Grid
	public static class Grid extends JPanel {

        private List<Point> fillCells;

        public Grid() {
            fillCells = new ArrayList<>();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Point fillCell : fillCells) {
                int cellX = 10 + (fillCell.x * 10);
                int cellY = 10 + (fillCell.y * 10);
                g.setColor(Color.RED);
                g.fillRect(cellX, cellY, 10, 10);
            }
            g.setColor(Color.BLACK);
            g.drawRect (MainClass.tileSize, MainClass.tileSize, MainClass.mazeSizeWidth*MainClass.tileSize, MainClass.mazeSizeHeight*MainClass.tileSize);

            for (int i = MainClass.tileSize; i <= MainClass.mazeSizeWidth*MainClass.tileSize+MainClass.tileSize; i+= MainClass.tileSize)
                g.drawLine (i, MainClass.tileSize, i, (1+MainClass.mazeSizeHeight)*MainClass.tileSize);

            for (int i = 10; i <= MainClass.mazeSizeHeight*MainClass.tileSize+MainClass.tileSize; i+= MainClass.tileSize)
                g.drawLine (MainClass.tileSize, i, (1+MainClass.mazeSizeWidth)*MainClass.tileSize, i);
        }

        public void fillCell(int x, int y) {
            fillCells.add(new Point(x, y));
            repaint();
        }

    }
}

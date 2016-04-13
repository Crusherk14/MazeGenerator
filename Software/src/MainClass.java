import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class MainClass {
	
	public static int mazeSizeHeight = 30;
	public static int mazeSizeWidth = 30;
	
	public static JFrame window = new JFrame();
	public static JPanel panel=new JPanel();
	public int[][] tilesArray = new int[mazeSizeWidth][mazeSizeHeight];
	
	
	public static void main(String[] args){
		window.setSize(640, 640);
		window.setResizable(false);
		window.setTitle("MazeGenerator v0.1a");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		
		panel.setLayout(new GridLayout(4,4));
		
		for (int i = 0; i<4*4; i++){
		    JLabel label = new JLabel("Label "+i);
		    panel.add(label);
		}
		window.add(panel);
		
	}
}

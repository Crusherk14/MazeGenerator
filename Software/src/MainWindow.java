import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class MainWindow {

	private JFrame frame;
	private JTextField textField_Width;
	private JTextField textField_Height;
	
	public static MainClass.Grid grid;
	public static MainClass.Generator generator;
	private JTextField textField_PathLength;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JLabel lblPathlength;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[grow]"));
		
		JPanel MazeDisplay = new JPanel();
		frame.getContentPane().add(MazeDisplay, "cell 1 0,grow");
		MazeDisplay.setLayout(new BoxLayout(MazeDisplay, BoxLayout.X_AXIS));
		
		JPanel Parameters = new JPanel();
		frame.getContentPane().add(Parameters, "cell 0 0,grow");
		Parameters.setLayout(new MigLayout("", "[][100,grow]", "[20px][20px][20px][]"));
		
		grid = new MainClass.Grid();
		grid.initArray();
		MazeDisplay.add(grid);
		grid.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		grid.setLayout(null);
		
		generator = new MainClass.Generator(grid);
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.mazeSizeWidth = Integer.parseInt(textField_Width.getText());
				MainClass.mazeSizeHeight = Integer.parseInt(textField_Height.getText());
				grid.initArray();
				
				generator.FillBorders();
				generator.generatePath(generator.setStartPoint(), (int) Math.round(MainClass.mazeSizeWidth*MainClass.mazeSizeHeight/100*Integer.parseInt(textField_PathLength.getText())));
				
				//generator.generatePath();
				grid.repaintCells();
				
			}
		});
		Parameters.add(btnNewButton, "cell 1 0,growx,aligny center");
		
		lblHeight = new JLabel("Height");
		Parameters.add(lblHeight, "cell 0 1,alignx center");
		
		textField_Width = new JTextField();
		textField_Width.setText("30");
		textField_Width.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(textField_Width, "cell 1 1,growx,aligny top");
		textField_Width.setColumns(10);
		
		lblWidth = new JLabel("Width");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(lblWidth, "cell 0 2,alignx center");
		
		textField_Height = new JTextField();
		textField_Height.setText("30");
		textField_Height.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(textField_Height, "cell 1 2,growx,aligny top");
		textField_Height.setColumns(10);
		
		lblPathlength = new JLabel("PathLength (%)");
		Parameters.add(lblPathlength, "cell 0 3,alignx center");
		
		textField_PathLength = new JTextField();
		textField_PathLength.setText("5");
		textField_PathLength.setHorizontalAlignment(SwingConstants.CENTER);
		textField_PathLength.setColumns(10);
		Parameters.add(textField_PathLength, "cell 1 3,growx");
	}

}

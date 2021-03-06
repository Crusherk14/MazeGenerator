/* 
 * By reading any further information you agree with terms of it's usage:
 * Unauthorized using or copying of this file is strictly prohibited
 *
 * Written by team: Kolesnikov Mikhail, Ilyin Evgeniy, Bogdanov Maksim, Makarov Pavel
 * Source code was specially created within the "Basics of Programming" of ITMO University (1st Course, 2nd Term)
 * February 2016 - July 2016
 *
 * For any further questions contact authors on GitHub repository <https://github.com/Crusherk14/MazeGenerator>
 */

/* 
 * ��������� ��������� ����������, ���������� � ������ ���������, �� ������������ � ��������� � ����������� �������������:
 * ���������� ������������� � ������ ����� ��� ��������������� ��������� ��� ������ ��������������� ���������
 * �������� �������������� ������(-��)
 *
 * ������: ���������� ������, ����� �������, �������� ������, ������� �����
 * �������� ��� ������� ������� ���������� � ������ ������������ ������� �� ���������� "������ ����������������" ������������ ���� (1�� ����, 2�� �������)
 * ������� 2016 - ���� 2016
 *
 * �� ���� ��������� �������� ���������� � ���������� ����������� �� GitHub <https://github.com/Crusherk14/MazeGenerator>
 */


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import java.io.File;
import javax.swing.border.BevelBorder;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;


public class MainWindow {

	private JFrame frmMazegeneratorV;
	private JTextField textField_Width;
	private JTextField textField_Height;
	
	public static MainClass.Grid grid;
	public static MainClass.Generator generator;
	public static LoadManager loaderSaver;
	private JTextField textField_PathLength;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JLabel lblPathlength;
	private JLabel lblTilesize;
	private JTextField textField_TileSize;
	private JLabel lblGenerationdelay;
	private JTextField textField_GenerationDelay;
	
	private aTask task = new aTask();
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblRealtimeVariables;
	private JButton btnSaveTo;
	private JButton btnLoadFrom;
	private JCheckBox chckbxShowAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMazegeneratorV.setVisible(true);
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
		frmMazegeneratorV = new JFrame();
		frmMazegeneratorV.setTitle("MazeGenerator v1.0");
		frmMazegeneratorV.setBounds(100, 100, 1200, 900);
		frmMazegeneratorV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMazegeneratorV.getContentPane().setLayout(new MigLayout("", "[][grow]", "[grow]"));
		
		JPanel MazeDisplay = new JPanel();
		frmMazegeneratorV.getContentPane().add(MazeDisplay, "cell 1 0,grow");
		MazeDisplay.setLayout(new BoxLayout(MazeDisplay, BoxLayout.X_AXIS));
		
		JPanel Parameters = new JPanel();
		frmMazegeneratorV.getContentPane().add(Parameters, "cell 0 0,grow");
		Parameters.setLayout(new MigLayout("", "[][][100,grow]", "[20px][][20px][20px][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		grid = new MainClass.Grid();
		grid.initArray();
		MazeDisplay.add(grid);
		grid.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		grid.setLayout(null);
		
		generator = new MainClass.Generator(grid);
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MainClass.tileSize = Integer.parseInt(textField_TileSize.getText());
				MainClass.mazeSizeWidth = Integer.parseInt(textField_Width.getText());
				MainClass.mazeSizeHeight = Integer.parseInt(textField_Height.getText());
				MainClass.generationDelay = Integer.parseInt(textField_GenerationDelay.getText());
				
				
				if ((task.getState() == SwingWorker.StateValue.STARTED)&&(task.getState() != SwingWorker.StateValue.DONE)){
					grid.clearData();
					task.cancel(true);
				}
				
				grid = new MainClass.Grid();
				grid.clearData();
				
				
				task = new aTask();
				task.execute();
				
				chckbxShowAnswer.setEnabled(true);
			}
		});
		Parameters.add(btnNewButton, "cell 0 0 3 1,growx,aligny center");
		
		lblTilesize = new JLabel("TileSize");
		Parameters.add(lblTilesize, "cell 0 1,alignx center");
		
		textField_TileSize = new JTextField();
		textField_TileSize.setHorizontalAlignment(SwingConstants.CENTER);
		textField_TileSize.setText("10");
		Parameters.add(textField_TileSize, "cell 2 1,growx");
		textField_TileSize.setColumns(10);
		
		lblHeight = new JLabel("Height");
		Parameters.add(lblHeight, "cell 0 2,alignx center");
		
		textField_Width = new JTextField();
		textField_Width.setText("80");
		textField_Width.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(textField_Width, "cell 2 2,growx,aligny top");
		textField_Width.setColumns(10);
		
		lblWidth = new JLabel("Width");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(lblWidth, "cell 0 3,alignx center");
		
		textField_Height = new JTextField();
		textField_Height.setText("80");
		textField_Height.setHorizontalAlignment(SwingConstants.CENTER);
		Parameters.add(textField_Height, "cell 2 3,growx,aligny top");
		textField_Height.setColumns(10);
		
		lblPathlength = new JLabel("PathLength (1-10%)");
		Parameters.add(lblPathlength, "cell 0 4,alignx center");
		
		textField_PathLength = new JTextField();
		textField_PathLength.setText("5");
		textField_PathLength.setHorizontalAlignment(SwingConstants.CENTER);
		textField_PathLength.setColumns(10);
		Parameters.add(textField_PathLength, "cell 2 4,growx");
		
		lblRealtimeVariables = new JLabel("Realtime variables:");
		Parameters.add(lblRealtimeVariables, "cell 0 6 3 1,alignx center,aligny center");
		
		lblGenerationdelay = new JLabel("GenerationDelay (ms)");
		Parameters.add(lblGenerationdelay, "cell 0 7,alignx center");
		textField_GenerationDelay = new JTextField();
		textField_GenerationDelay.setHorizontalAlignment(SwingConstants.CENTER);
		textField_GenerationDelay.setText("20");
		Parameters.add(textField_GenerationDelay, "cell 2 7,growx");
		textField_GenerationDelay.setColumns(10);
		textField_GenerationDelay.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//empty
		    }
		    public void removeUpdate(DocumentEvent e) {
		    	if (textField_GenerationDelay.getText().length()>0){
		    		MainClass.generationDelay = Integer.parseInt(textField_GenerationDelay.getText());
		    	}
		    }
		    public void insertUpdate(DocumentEvent e) {
		    	MainClass.generationDelay = Integer.parseInt(textField_GenerationDelay.getText());
		    }
		});
		
		chckbxNewCheckBox = new JCheckBox("Black&White Mode");
		Parameters.add(chckbxNewCheckBox, "cell 0 8 3 1,alignx right");
		
		chckbxNewCheckBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        MainClass.bawMode = chckbxNewCheckBox.isSelected();
		        generator.updateUI();
		    }
		});
				
		chckbxShowAnswer = new JCheckBox("Show Answer");
		Parameters.add(chckbxShowAnswer, "cell 0 9 3 1,alignx right");
		
		chckbxShowAnswer.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        MainClass.answerMode = chckbxShowAnswer.isSelected();
		        generator.updateUI();
		    }
		});
		
		//Save and Load
				btnLoadFrom = new JButton("Load From");
				Parameters.add(btnLoadFrom, "cell 0 28 3 1,alignx center,aligny center");
				btnLoadFrom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Loading methods
						
						//Stop generation
						if ((task.getState() == SwingWorker.StateValue.STARTED)&&(task.getState() != SwingWorker.StateValue.DONE)){
							task.cancel(true);
						}
						
						//Load
						try {
							MainClass.tileSize = Integer.parseInt(textField_TileSize.getText());
							JFileChooser fileopen = new JFileChooser();
							fileopen.setFileFilter(new FileNameExtensionFilter(".maze","maze"));
							int ret = fileopen.showDialog(null, "Load maze");                
			                if (ret == JFileChooser.APPROVE_OPTION) {
			                	grid.clearData();
								File mazeFile = fileopen.getSelectedFile();
								MainClass.tilesArray = LoadManager.loadFile(mazeFile);
								generator.updateUI();
			                }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//Block
						chckbxShowAnswer.setSelected(false);
						chckbxShowAnswer.setEnabled(false);
					}
				});
				
				btnSaveTo = new JButton("Save To");
				Parameters.add(btnSaveTo, "cell 0 29 3 1,alignx center,aligny center");
				btnSaveTo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Saving methods
						
						//Stop generation
						if ((task.getState() == SwingWorker.StateValue.STARTED)&&(task.getState() != SwingWorker.StateValue.DONE)){
							task.cancel(true);
						}
						
						//Save
						try {
							JFileChooser fileopen = new JFileChooser();
							fileopen.setFileFilter(new FileNameExtensionFilter(".maze","maze"));
			                int ret = fileopen.showDialog(null, "Save maze");                
			                if (ret == JFileChooser.APPROVE_OPTION) {
			                	File mazeFile = new File(fileopen.getSelectedFile().getPath() + ".maze");
			                	LoadManager.saveFile(mazeFile);
			                }
			                
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	
	class aTask extends SwingWorker<Void, Object> {
	       @Override
	       public Void doInBackground() {
	    	   Thread generating = new Thread()
	           {
	               public void run() {
	            	   generator.FillBorders();
					generator.generatePath(generator.setStartPoint(), (int) Math.round(MainClass.mazeSizeWidth*MainClass.mazeSizeHeight/100*Integer.parseInt(textField_PathLength.getText())));
	               }
	           };
	           generating.start();
	           
	    	   while (!isCancelled()){
		    	   	
	    	   }
	    	   generating.stop();
			return null;
	       }
	   }

}

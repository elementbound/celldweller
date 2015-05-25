package hu.unideb.inf.elementbound.celldweller.view;

import hu.unideb.inf.elementbound.celldweller.controller.EditableCellverseController;
import hu.unideb.inf.elementbound.celldweller.controller.IEditableCellverseListener;
import hu.unideb.inf.elementbound.celldweller.controller.ISimulator;
import hu.unideb.inf.elementbound.celldweller.controller.VonNeumannSimulator;
import hu.unideb.inf.elementbound.celldweller.model.CSVAdapter;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.IOAdapter;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.BitSet;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;

public class EditableCellverseView extends JFrame implements IEditableCellverseListener {
	private JTextField textFieldRule;
	private Canvas displayCanvas;
	private JLabel lblStats;
	private EditableCellverseController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditableCellverseView frame = new EditableCellverseView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JTextField getTextFieldRule() {
		return textFieldRule;
	}
	
	public Canvas getDisplayCanvas() {
		return displayCanvas;
	}
	
	/**
	 * Create the frame.
	 */
	public EditableCellverseView() {
		setTitle("Celldweller");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.EAST);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{129, 0};
		gbl_settingsPanel.rowHeights = new int[] {32, 32, 32, 32, 32, 0, 0, 0, 0};
		gbl_settingsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		JPanel rulePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) rulePanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_rulePanel = new GridBagConstraints();
		gbc_rulePanel.fill = GridBagConstraints.BOTH;
		gbc_rulePanel.anchor = GridBagConstraints.EAST;
		gbc_rulePanel.insets = new Insets(0, 0, 5, 0);
		gbc_rulePanel.gridx = 0;
		gbc_rulePanel.gridy = 0;
		settingsPanel.add(rulePanel, gbc_rulePanel);
		
		JLabel lblRule = new JLabel("Rule: ");
		rulePanel.add(lblRule);
		
		textFieldRule = new JTextField();
		textFieldRule.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				controller.ruleChange(BitSet.valueOf(textFieldRule.getText().getBytes()));
			}
		});
		textFieldRule.setColumns(10);
		rulePanel.add(textFieldRule);
		
		JButton btnRandomRule = new JButton("Random");
		btnRandomRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.randomizeRule();
			}
		});
		rulePanel.add(btnRandomRule);
		
		JButton btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.singleStep();
			}
		});
		
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 5, 0);
		gbc_btnStep.fill = GridBagConstraints.BOTH;
		gbc_btnStep.gridx = 0;
		gbc_btnStep.gridy = 1;
		settingsPanel.add(btnStep, gbc_btnStep);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clear();
			}
		});
		
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.BOTH;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		settingsPanel.add(btnClear, gbc_btnClear);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveToFile();
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 3;
		settingsPanel.add(btnSave, gbc_btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadFromFile();
			}
		});
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoad.fill = GridBagConstraints.BOTH;
		gbc_btnLoad.gridx = 0;
		gbc_btnLoad.gridy = 4;
		settingsPanel.add(btnLoad, gbc_btnLoad);
		
		JLabel lblFileFormat = new JLabel("File Format: ");
		GridBagConstraints gbc_lblFileFormat = new GridBagConstraints();
		gbc_lblFileFormat.anchor = GridBagConstraints.WEST;
		gbc_lblFileFormat.insets = new Insets(0, 0, 5, 0);
		gbc_lblFileFormat.gridx = 0;
		gbc_lblFileFormat.gridy = 5;
		settingsPanel.add(lblFileFormat, gbc_lblFileFormat);
		
		JComboBox cboxFileFormat = new JComboBox();
		cboxFileFormat.setModel(new DefaultComboBoxModel(
				new String[] {
						"Comma Separated Values (CSV)" 
						//"Extensible Markup Language (XML)"
				}));
		GridBagConstraints gbc_cboxFileFormat = new GridBagConstraints();
		gbc_cboxFileFormat.insets = new Insets(0, 0, 5, 0);
		gbc_cboxFileFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboxFileFormat.gridx = 0;
		gbc_cboxFileFormat.gridy = 6;
		settingsPanel.add(cboxFileFormat, gbc_cboxFileFormat);
		
		JPanel displayPanel = new JPanel();
		getContentPane().add(displayPanel, BorderLayout.CENTER);
		displayPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel thingsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) thingsPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		displayPanel.add(thingsPanel, BorderLayout.SOUTH);
		
		lblStats = new JLabel("Things will possibly hapen here. ");
		lblStats.setVerticalAlignment(SwingConstants.TOP);
		lblStats.setHorizontalAlignment(SwingConstants.LEFT);
		thingsPanel.add(lblStats);
		
		displayCanvas = new CellverseDisplay();
		displayCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					double cellX, cellY;
					CellverseDisplay d = (CellverseDisplay)displayCanvas;
					cellX = e.getX();
					cellY = e.getY();
					
					cellX -= d.getWidth()/2;
					cellY -= d.getHeight()/2;
					
					cellX /= d.zoom;
					cellY /= d.zoom;
					
					cellX -= d.originX;
					cellY -= d.originY;
					
					controller.setCell((int)cellX, (int)cellY);
				}
			}
		});
		
		displayCanvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 
						((CellverseDisplay)displayCanvas).originX -= 1.0;
						((CellverseDisplay)displayCanvas).repaint();
					break;

					case KeyEvent.VK_RIGHT: 
						((CellverseDisplay)displayCanvas).originX += 1.0;
						((CellverseDisplay)displayCanvas).repaint();
					break;

					case KeyEvent.VK_DOWN: 
						((CellverseDisplay)displayCanvas).originY += 1.0;
						((CellverseDisplay)displayCanvas).repaint();
					break;
					
					case KeyEvent.VK_UP: 
						((CellverseDisplay)displayCanvas).originY -= 1.0;
						((CellverseDisplay)displayCanvas).repaint();
					break;
					
					case KeyEvent.VK_ADD: 
						((CellverseDisplay)displayCanvas).zoom *= Math.pow(2.0, 1.0/8.0);
						((CellverseDisplay)displayCanvas).repaint();
						notifyZoom();
					break;
					
					case KeyEvent.VK_SUBTRACT: 
						((CellverseDisplay)displayCanvas).zoom /= Math.pow(2.0, 1.0/8.0);
						((CellverseDisplay)displayCanvas).repaint();
						notifyZoom();
					break;
					
					case KeyEvent.VK_SPACE: 
						controller.singleStep();
					break;
				}
			}
		});
		displayCanvas.setForeground(Color.BLACK);
		displayCanvas.setBackground(Color.WHITE);
		displayPanel.add(displayCanvas, BorderLayout.CENTER);
		
		//

		controller = new EditableCellverseController();
		controller.init(this);
		
		((CellverseDisplay)displayCanvas).setCellverse(controller.getCellverse());
	}
	
	public void notifyCellcount() {
		updateStats();
	}
	
	public void notifyZoom() {
		updateStats();
	}
	
	public void updateStats() {
		lblStats.setText("Cells alive: " + controller.getCellverse().getAliveCells().size() + " | " + 
						 "Zoom: " + ((CellverseDisplay)displayCanvas).zoom);
	}

	@Override
	public void requestRepaint() {
		displayCanvas.repaint();
	}

	@Override
	public void cellverseUpdate() {
		requestRepaint();
	}

	@Override
	public File requestSaveFile(FileNameExtensionFilter filter) {
		JFileChooser jfc = new JFileChooser();
		try {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(filter);
			int result = jfc.showSaveDialog(this);
			
			if(result == JFileChooser.APPROVE_OPTION)
				return jfc.getSelectedFile();
			else
				return null;
		} 
		catch (HeadlessException ex) {
			return null;
		}
	}

	@Override
	public File requestOpenFile(FileNameExtensionFilter filter) {
		JFileChooser jfc = new JFileChooser();
		try {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(filter);
			int result = jfc.showOpenDialog(this);
			
			if(result == JFileChooser.APPROVE_OPTION)
				return jfc.getSelectedFile();
			else
				return null;
		} 
		catch (HeadlessException ex) {
			return null;
		}
	}

	@Override
	public void requestRuleUpdate(BitSet rule) {
		textFieldRule.setText(new String(rule.toByteArray()));
	}
	
	
}

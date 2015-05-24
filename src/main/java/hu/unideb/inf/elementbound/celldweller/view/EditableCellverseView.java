package hu.unideb.inf.elementbound.celldweller.view;

import hu.unideb.inf.elementbound.celldweller.controller.ISimulator;
import hu.unideb.inf.elementbound.celldweller.controller.VonNeumannSimulator;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.awt.EventQueue;

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
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.BitSet;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditableCellverseView extends JFrame {
	private JTextField textFieldRule;
	private Canvas displayCanvas;
	private Cellverse cellverse;
	private ISimulator simulator;

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
		cellverse = new Cellverse();
		cellverse.setCell(new Point(0,0), true);
		cellverse.swapBuffers();
		
		simulator = new VonNeumannSimulator();
		BitSet rule = new BitSet();
		for(int i=0; i<32; i++)
			rule.set(i);
		simulator.setRule(rule);
		
		setTitle("Celldweller");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.EAST);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{129, 0};
		gbl_settingsPanel.rowHeights = new int[] {32, 32, 32, 0};
		gbl_settingsPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		JPanel rulePanel = new JPanel();
		GridBagConstraints gbc_rulePanel = new GridBagConstraints();
		gbc_rulePanel.fill = GridBagConstraints.BOTH;
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
				simulator.setRule(BitSet.valueOf(textFieldRule.getText().getBytes()));
			}
		});
		textFieldRule.setColumns(10);
		rulePanel.add(textFieldRule);
		
		JButton btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Component component = (Component)arg0.getSource();
				EditableCellverseView root = (EditableCellverseView)SwingUtilities.getRoot(component);
				CellverseDisplay display = (CellverseDisplay)root.getDisplayCanvas();
				display.repaint();*/
				simulator.step(cellverse);
				cellverse.swapBuffers();
				displayCanvas.repaint();
			}
		});
		
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.fill = GridBagConstraints.BOTH;
		gbc_btnStep.gridx = 0;
		gbc_btnStep.gridy = 1;
		settingsPanel.add(btnStep, gbc_btnStep);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cellverse.clear();
				cellverse.swapBuffers();
				displayCanvas.repaint();
			}
		});
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.BOTH;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		settingsPanel.add(btnClear, gbc_btnClear);
		
		JPanel displayPanel = new JPanel();
		getContentPane().add(displayPanel, BorderLayout.CENTER);
		displayPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel thingsPanel = new JPanel();
		displayPanel.add(thingsPanel, BorderLayout.SOUTH);
		
		JLabel lblPlaceholder = new JLabel("Things will possibly hapen here. ");
		thingsPanel.add(lblPlaceholder);
		
		displayCanvas = new CellverseDisplay();
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
					break;
					
					case KeyEvent.VK_SUBTRACT: 
						((CellverseDisplay)displayCanvas).zoom /= Math.pow(2.0, 1.0/8.0);
						((CellverseDisplay)displayCanvas).repaint();
					break;
					
					case KeyEvent.VK_SPACE: 
						simulator.step(cellverse);
						cellverse.swapBuffers();
						((CellverseDisplay)displayCanvas).repaint();
					break;
				}
			}
		});
		displayCanvas.setForeground(Color.BLACK);
		displayCanvas.setBackground(Color.WHITE);
		displayPanel.add(displayCanvas, BorderLayout.CENTER);
		
		((CellverseDisplay)displayCanvas).setCellverse(cellverse);
	}
}

package hu.unideb.inf.elementbound.celldweller.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import javax.swing.JToolBar;

import java.awt.FlowLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JSplitPane;
import javax.swing.JTextField;

import java.awt.GridBagLayout;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditableCellverseView extends JFrame {
	private JTextField textFieldRule;
	private Canvas displayCanvas;

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
		textFieldRule.setColumns(10);
		rulePanel.add(textFieldRule);
		
		JButton btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Component component = (Component)arg0.getSource();
				EditableCellverseView root = (EditableCellverseView)SwingUtilities.getRoot(component);
				CellverseDisplay display = (CellverseDisplay)root.getDisplayCanvas();
				display.advanceText();
				display.repaint();
			}
		});
		
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.fill = GridBagConstraints.BOTH;
		gbc_btnStep.gridx = 0;
		gbc_btnStep.gridy = 1;
		settingsPanel.add(btnStep, gbc_btnStep);
		
		JButton btnClear = new JButton("Clear");
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
		displayPanel.add(displayCanvas, BorderLayout.CENTER);
	}
}

package hu.unideb.inf.elementbound.celldweller.controller;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.BitSet;

import javax.swing.JFileChooser;

import hu.unideb.inf.elementbound.celldweller.model.CSVAdapter;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;
import hu.unideb.inf.elementbound.celldweller.model.IOAdapter;
import hu.unideb.inf.elementbound.celldweller.view.CellverseDisplay;
import hu.unideb.inf.elementbound.celldweller.view.EditableCellverseView;

public class EditableCellverseController {
	private Cellverse cellverse;
	private ISimulator simulator;
	private Canvas displayCanvas;
	private Component viewComponent;
	
	private void sendCellcountNotif() {
		EditableCellverseView v = (EditableCellverseView)viewComponent;
		v.notifyCellcount();
	}
	
	public Cellverse getCellverse() {
		return cellverse;
	}

	public void setCellverse(Cellverse cellverse) {
		this.cellverse = cellverse;
	}

	public ISimulator getSimulator() {
		return simulator;
	}

	public void setSimulator(ISimulator simulator) {
		this.simulator = simulator;
	}

	public Canvas getDisplayCanvas() {
		return displayCanvas;
	}

	public void setDisplayCanvas(Canvas displayCanvas) {
		this.displayCanvas = displayCanvas;
	}

	public Component getViewComponent() {
		return viewComponent;
	}

	public void setViewComponent(Component viewComponent) {
		this.viewComponent = viewComponent;
	}

	public void init(Canvas displayCanvas, Component viewComponent) {
		this.displayCanvas = displayCanvas;
		this.viewComponent = viewComponent;
		
		cellverse = new Cellverse();
		cellverse.setCell(new Point(0,0), true);
		cellverse.swapBuffers();
		
		simulator = new VonNeumannSimulator();
		BitSet rule = new BitSet();
		for(int i=0; i<32; i++)
			rule.set(i);
		simulator.setRule(rule);
	}
	
	public void ruleChange(BitSet newRule) {
		simulator.setRule(newRule);
	}
	
	public void singleStep() {
		simulator.step(cellverse);
		cellverse.swapBuffers();
		displayCanvas.repaint();
		
		sendCellcountNotif();
	}
	
	public void clear() {
		cellverse.clear();
		cellverse.swapBuffers();
		displayCanvas.repaint();
		
		sendCellcountNotif();
	}
	
	public void saveToFile() {
		JFileChooser jfc = new JFileChooser();
		try {
			int result = jfc.showSaveDialog(viewComponent);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(result == JFileChooser.APPROVE_OPTION) {
				IOAdapter adapter = new CSVAdapter();
				adapter.Write(jfc.getSelectedFile(), cellverse);
			}
		} 
		catch (HeadlessException | IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void loadFromFile() {
		JFileChooser jfc = new JFileChooser();
		try {
			int result = jfc.showOpenDialog(viewComponent);
			if(result == JFileChooser.APPROVE_OPTION) {
				IOAdapter adapter = new CSVAdapter();
				adapter.Read(jfc.getSelectedFile(), cellverse);
				displayCanvas.repaint();
				
				sendCellcountNotif();
			}
		} 
		catch (HeadlessException | IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setCell(double cellX, double cellY) {
		CellverseDisplay d = (CellverseDisplay)displayCanvas;
		
		cellX -= d.getWidth()/2;
		cellY -= d.getHeight()/2;
		
		cellX /= d.zoom;
		cellY /= d.zoom;
		
		cellX -= d.originX;
		cellY -= d.originY;
		
		Point cell = new Point((int)cellX, (int)cellY);
		cellverse.setCell(cell, true);
		
		cellverse.swapBuffers();
		d.repaint();
		
		sendCellcountNotif();
	}
}

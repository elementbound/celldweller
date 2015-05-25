package hu.unideb.inf.elementbound.celldweller.controller;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.BitSet;

import javax.swing.JFileChooser;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
	private Logger logger;
	
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
		this.logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
		logger.info("Init");
		
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
		logger.info("Rule change");
	}
	
	public void singleStep() {
		logger.info("Single step");
		
		simulator.step(cellverse);
		cellverse.swapBuffers();
		displayCanvas.repaint();
		
		logger.info("Cell count: " + cellverse.getAliveCells().size());
		
		sendCellcountNotif();
	}
	
	public void clear() {
		logger.info("Clear");
		
		cellverse.clear();
		cellverse.swapBuffers();
		displayCanvas.repaint();
		
		sendCellcountNotif();
	}
	
	public void saveToFile() {
		logger.info("Saving to file");
		logger.info("Prompting user");
		
		JFileChooser jfc = new JFileChooser();
		try {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = jfc.showSaveDialog(viewComponent);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				logger.info("Selected " + jfc.getSelectedFile().toString());
				IOAdapter adapter = new CSVAdapter();
				adapter.Write(jfc.getSelectedFile(), cellverse);
			}
		} 
		catch (HeadlessException | IOException ex) {
			logger.error("Exception while saving", ex);
		}
	}
	
	public void loadFromFile() {
		logger.info("Loading from file");
		logger.info("Prompting user");
		
		
		JFileChooser jfc = new JFileChooser();
		try {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = jfc.showOpenDialog(viewComponent);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				logger.info("Selected " + jfc.getSelectedFile().toString());
				IOAdapter adapter = new CSVAdapter();
				adapter.Read(jfc.getSelectedFile(), cellverse);
				displayCanvas.repaint();
				
				logger.info("Loaded " + cellverse.getAliveCells().size() + " cell(s)");
				
				sendCellcountNotif();
			}
		} 
		catch (HeadlessException | IOException ex) {
			logger.error("Exception while loading", ex);
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

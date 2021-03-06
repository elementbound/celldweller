package hu.unideb.inf.elementbound.celldweller.controller;

import java.io.File;
import java.util.BitSet;
import java.util.Random;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;
import hu.unideb.inf.elementbound.celldweller.model.IOAdapter;
import hu.unideb.inf.elementbound.celldweller.model.XMLAdapter;
import hu.unideb.inf.elementbound.celldweller.model.CSVAdapter;
import hu.unideb.inf.elementbound.celldweller.view.EditableCellverseView;

/**
 * Controller for cellverse editor. 
 * The editor maintains a cellverse and a simulator. Communication towards the user is solved via 
 * an instance that implements IEditableCellverseListener. This interface is used to notify the
 * user interface about the cellvere's state changes, and is used to request UI-specific 
 * behaviour, like prompting the user for a file to save the cellverse's state. 
 * 
 * The user interface can interact with the controller through its methods. 
 * @see EditableCellverseView
 * @author elementbound
 *
 */
public class EditableCellverseController {
	/**
	 * Internal cellverse to perform operations on. 
	 */
	private Cellverse cellverse;
	
	/**
	 * Internal simulator to use. 
	 */
	private ISimulator simulator;
	
	/**
	 * The view. 
	 */
	private IEditableCellverseListener view;
	
	/**
	 * Logger.
	 */
	private Logger logger;
	
	/**
	 * Get Internal cellverse.
	 * @return Internal cellverse
	 */
	public Cellverse getCellverse() {
		return cellverse;
	}

	/**
	 * Replace internal cellverse.
	 * @param cellverse Internal cellverse
	 */
	public void setCellverse(Cellverse cellverse) {
		this.cellverse = cellverse;
	}

	/**
	 * Get internal simulator.
	 * @return Internal simulator
	 */
	public ISimulator getSimulator() {
		return simulator;
	}

	/**
	 * Replace internal simulator.
	 * @param simulator Internal simulator
	 */
	public void setSimulator(ISimulator simulator) {
		this.simulator = simulator;
	}

	/**
	 * Init the editor. 
	 * A new cellverse and simulator is created. 
	 * The simulator is assigned a default always-live rule.
	 * @param view User interface component to communicate with
	 */
	public void init(IEditableCellverseListener view) {
		this.view = view;
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
	
	/**
	 * Change the simulator's rule. 
	 * @param newRule The new rule to be used. 
	 */
	public void ruleChange(BitSet newRule) {
		simulator.setRule(newRule);
		logger.info("Rule change: " + (new String(newRule.toByteArray())));
	}
	
	/**
	 * Generate a new, random rule. 
	 * requestRuleUpdate() is called on the view, with the new random rule
	 */
	public void randomizeRule() {
		BitSet rule = new BitSet();
		Random rng = new Random();
		
		for(int i = 0; i < 32; i++)
			rule.set(i, rng.nextBoolean());
		
		simulator.setRule(rule);
		
		logger.info("Randomgenerated rule: " + (new String(rule.toByteArray())));
		view.requestRuleUpdate(rule);
	}
	
	/**
	 * Request a single step of simulation.
	 * cellverseUpdate() is called on the view, since the cellverse is modified. 
	 */
	public void singleStep() {
		logger.info("Single step");
		
		simulator.step(cellverse);
		cellverse.swapBuffers();
		logger.info("Cell count: " + cellverse.getAliveCells().size());
		
		view.cellverseUpdate();
	}
	
	/**
	 * Request a cellverse clear. 
	 * cellverseUpdate() is called on the view, since the cellverse is modified. 
	 */
	public void clear() {
		logger.info("Clear");
		
		cellverse.clear();
		cellverse.swapBuffers();

		view.cellverseUpdate();
	}
	
	/**
	 * Save the current cellverse to a file. 
	 * File path and format is determined using the view's functions. 
	 */
	public void saveToFile() {
		logger.info("Saving to file");
		logger.info("Prompting user");

		FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("Comma separated values (CSV)", "csv");
		FileNameExtensionFilter filterXML = new FileNameExtensionFilter("Extensible markup language (XML)", "xml");
		File fout = view.requestSaveFile(filterCSV, filterXML);
		IOAdapter adapter = determineAdapter(fout);
		
		if(adapter == null) {
			view.showError("Wrong file type!");
			return ;
		}
		
		if(fout == null) 
			return ;
		
		logger.info("Saving to " + fout.toString());
		try {
			adapter.Write(fout, cellverse);
		} catch (Exception e) {
			logger.error("Failed save", e);
		}
	}
	
	/**
	 * Determine which IOAdapter to use based on file name. 
	 * @param file File to check. 
	 * @return An instance of a class implementing IOAdapter. 
	 */
	private IOAdapter determineAdapter(File file) {
		String name = file.getName();
		
		if(name.endsWith(".xml"))
			return new XMLAdapter();
		
		if(name.endsWith(".csv"))
			return new CSVAdapter();
		
		return null;
	}

	/**
	 * Load cellverse from a file. 
	 * File path and format is determined using the view's functions. 
	 */
	public void loadFromFile() {
		logger.info("Loading from file");
		logger.info("Prompting user");

		FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("Comma separated values (CSV)", "csv");
		FileNameExtensionFilter filterXML = new FileNameExtensionFilter("Extensible markup language (XML)", "xml");
		File fin = view.requestOpenFile(filterCSV, filterXML);
		IOAdapter adapter = determineAdapter(fin);
		
		if(adapter == null) {
			view.showError("Wrong file type!");
			return ;
		}
		
		if(fin == null) 
			return ;
		
		logger.info("Loading from " + fin.toString());
		try {
			adapter.Read(fin, cellverse);
			view.cellverseUpdate();
		} catch (Exception e) {
			logger.error("Failed load", e);
		}
	}
	
	/**
	 * Set cell, aka. set it to alive
	 * @param cellX x coordinate of the cell
	 * @param cellY y coordinate of the cell
	 */
	public void setCell(int cellX, int cellY) {
		Point cell = new Point(cellX, cellY);
		cellverse.setCell(cell, true);
		
		cellverse.swapBuffers();
		view.cellverseUpdate();
	}
}

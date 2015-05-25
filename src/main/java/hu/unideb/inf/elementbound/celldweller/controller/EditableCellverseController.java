package hu.unideb.inf.elementbound.celldweller.controller;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import hu.unideb.inf.elementbound.celldweller.model.CSVAdapter;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;
import hu.unideb.inf.elementbound.celldweller.model.IOAdapter;

public class EditableCellverseController {
	private Cellverse cellverse;
	private ISimulator simulator;
	private IEditableCellverseListener view;
	private Logger logger;
	
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
	
	public void ruleChange(BitSet newRule) {
		simulator.setRule(newRule);
		logger.info("Rule change: " + (new String(newRule.toByteArray())));
	}
	
	public void randomizeRule() {
		BitSet rule = new BitSet();
		Random rng = new Random();
		
		for(int i = 0; i < 32; i++)
			rule.set(i, rng.nextBoolean());
		
		simulator.setRule(rule);
		
		logger.info("Randomgenerated rule: " + (new String(rule.toByteArray())));
		view.requestRuleUpdate(rule);
	}
	
	public void singleStep() {
		logger.info("Single step");
		
		simulator.step(cellverse);
		cellverse.swapBuffers();
		logger.info("Cell count: " + cellverse.getAliveCells().size());
		
		view.cellverseUpdate();
	}
	
	public void clear() {
		logger.info("Clear");
		
		cellverse.clear();
		cellverse.swapBuffers();

		view.cellverseUpdate();
	}
	
	public void saveToFile() {
		logger.info("Saving to file");
		logger.info("Prompting user");
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma separated values (CSV)", "csv");
		File fout = view.requestSaveFile(filter);
		if(fout != null) {
			logger.info("Saving to " + fout.toString());
			IOAdapter adapter = new CSVAdapter();
			try {
				adapter.Write(fout, cellverse);
			} catch (IOException e) {
				logger.error("Failed save", e);
			}
		}
	}
	
	public void loadFromFile() {
		logger.info("Loading from file");
		logger.info("Prompting user");
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma separated values (CSV)", "csv");
		File fin= view.requestOpenFile(filter);
		if(fin != null) {
			logger.info("Loading from " + fin.toString());
			IOAdapter adapter = new CSVAdapter();
			try {
				adapter.Read(fin, cellverse);
				view.cellverseUpdate();
			} catch (IOException e) {
				logger.error("Failed load", e);
			}
		}
	}
	
	public void setCell(int cellX, int cellY) {
		Point cell = new Point(cellX, cellY);
		cellverse.setCell(cell, true);
		
		cellverse.swapBuffers();
		view.cellverseUpdate();
	}
}

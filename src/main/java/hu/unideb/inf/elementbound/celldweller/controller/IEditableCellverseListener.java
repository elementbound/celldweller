package hu.unideb.inf.elementbound.celldweller.controller;

import java.io.File;
import java.util.BitSet;

import javax.swing.filechooser.FileFilter;

/**
 * A class implementing this interface becomes suitable for use with EditableCellverseController. 
 * @author elementbound
 */
public interface IEditableCellverseListener {
	/**
	 * Called when the controller's cellverse gets modified. 
	 */
	public void cellverseUpdate();
	
	/**
	 * The controller requests a File to save data to. 
	 * @param filters to filter files
	 * @return Return a valid file, or null on error or user interruption
	 */
	public File requestSaveFile(FileFilter... filters);

	/**
	 * The controller requests a File to load data from. 
	 * @param filters to filter files
	 * @return Return a valid file, or null on error or user interruption
	 */
	public File requestOpenFile(FileFilter... filters);
	
	/**
	 * The controller has changed the simulator's rule and requests the view to update its display. 
	 * @param rule The new rule
	 */
	public void requestRuleUpdate(BitSet rule);
	
	/**
	 * The controller encountered an error and requests the view to display it
	 * @param msg Error message
	 */
	public void showError(String msg);
}

package hu.unideb.inf.elementbound.celldweller.model;

import java.io.File;
import java.io.IOException;

/**
 * Interface for IOAdapters.
 * An IOAdapter's task is to provide capabilities to save and load cellverse states to a certain
 * file format. 
 * @author elementbound
 */
public interface IOAdapter {
	/**
	 * Save cellverse state to file.
	 * @param fout Output file
	 * @param cellverse Cellverse
	 * @throws IOException if an error occurs
	 */
	public void Write(File fout, Cellverse cellverse) throws IOException;
	
	/**
	 * Load cellverse state from file. 
	 * 
	 * After load, the cellverse is in a usable state, no need to swapBuffers(). 
	 * @param fin Input file
	 * @param cellverse Cellverse
	 * @throws IOException if an error occurs
	 */
	public void Read(File fin, Cellverse cellverse) throws IOException;
}

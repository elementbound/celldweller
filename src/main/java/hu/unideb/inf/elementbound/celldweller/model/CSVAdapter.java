package hu.unideb.inf.elementbound.celldweller.model;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class for saving and loading cellverse states from/to CSV files. 
 * The file contains each living cell: <br>
 * x1, y1, x2, y2, ...
 * @author elementbound
 */
public class CSVAdapter implements IOAdapter {
	/**
	 * Read next CSV value. 
	 * @param reader Source
	 * @return CSV value read, as String
	 * @throws IOException if an error occurs
	 */
	private String readCSVValue(BufferedReader reader) throws IOException {
		StringBuilder strb = new StringBuilder();
		
		for(int c = reader.read(); c != ',' && c > 0; c = reader.read())
			strb.append((char)c);
		
		return strb.toString();
	}
	
	/**
	 * Save cellverse state to CSV.
	 * @param fout Output file
	 * @param cellverse Cellverse
	 * @throws IOException if an error occurs
	 */
	@Override
	public void Write(File fout, Cellverse cellverse) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(fout.toPath());
		
		for(Point p : cellverse.getAliveCells()) {
			writer.write(String.valueOf(p.x));
			writer.write(',');
			writer.write(String.valueOf(p.y));
			writer.write(',');
		}
		
		writer.close();
	}

	/**
	 * Load cellverse state from CSV. 
	 * 
	 * After load, the cellverse is in a usable state, no need to swapBuffers(). 
	 * @param fin Input file
	 * @param cellverse Cellverse
	 * @throws IOException if an error occurs
	 */
	@Override
	public void Read(File fin, Cellverse cellverse) throws IOException {
		BufferedReader reader = Files.newBufferedReader(fin.toPath());
		cellverse.clear();
		
		while(reader.ready()) {
			Point p = new Point(
					Integer.valueOf(readCSVValue(reader)), 
					Integer.valueOf(readCSVValue(reader)));
			
			cellverse.setCell(p, true);
		}
		
		cellverse.swapBuffers();
	}

}

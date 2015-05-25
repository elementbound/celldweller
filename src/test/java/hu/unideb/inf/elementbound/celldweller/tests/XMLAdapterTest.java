package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;
import hu.unideb.inf.elementbound.celldweller.model.XMLAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

public class XMLAdapterTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void emptyFileTest() throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException {
		Cellverse cellverse = new Cellverse();
		XMLAdapter xml = new XMLAdapter();
		File file = tempFolder.newFile("empty.csv");
		
		xml.Write(file, cellverse);
		
		xml.Read(file, cellverse);
		assertEquals(0, cellverse.getAliveCells().size());
	}
	
	@Test
	public void nonEmptyFileTest() throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException {
		Cellverse cellverse = new Cellverse();
		XMLAdapter xml = new XMLAdapter();
		File file = tempFolder.newFile("not-so-empty.csv");
		
		cellverse.setCell(new Point(0, 0), true);
		cellverse.setCell(new Point(1, 1), true);
		cellverse.swapBuffers();
		
		int expectedCellCount = cellverse.getAliveCells().size();
		
		xml.Write(file, cellverse);
		
		//
		
		xml.Read(file, cellverse);
		assertEquals(expectedCellCount, cellverse.getAliveCells().size());
	}
}

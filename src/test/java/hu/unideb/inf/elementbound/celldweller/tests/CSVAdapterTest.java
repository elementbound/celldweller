package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import hu.unideb.inf.elementbound.celldweller.model.CSVAdapter;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CSVAdapterTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void emptyFileTest() throws IOException {
		Cellverse cellverse = new Cellverse();
		CSVAdapter csv = new CSVAdapter();
		File file = tempFolder.newFile("empty.csv");
		
		csv.Write(file, cellverse);
		byte[] byteContent = Files.readAllBytes(file.toPath());
		assertEquals(0, byteContent.length);
		
		//
		
		csv.Read(file, cellverse);
		assertTrue(cellverse.isEmpty());
	}
	
	@Test
	public void nonEmptyFileTest() throws IOException {
		Cellverse cellverse = new Cellverse();
		CSVAdapter csv = new CSVAdapter();
		File file = tempFolder.newFile("not-so-empty.csv");
		
		cellverse.setCell(new Point(0, 0), true);
		cellverse.setCell(new Point(1, 1), true);
		cellverse.swapBuffers();
		
		int expectedCellCount = cellverse.getAliveCells().size();
		
		csv.Write(file, cellverse);
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] coords = content.split(",");
		assertEquals(expectedCellCount*2, coords.length);
		
		//
		
		csv.Read(file, cellverse);
		assertEquals(expectedCellCount, cellverse.getAliveCells().size());
	}
}

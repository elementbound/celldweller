package hu.unideb.inf.elementbound.celldweller.model;

import java.io.File;
import java.io.IOException;

public interface IOAdapter {
	public void Write(File fout, Cellverse cellverse) throws IOException;
	public void Read(File fin, Cellverse cellverse) throws IOException;
}

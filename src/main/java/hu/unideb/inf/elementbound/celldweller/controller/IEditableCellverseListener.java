package hu.unideb.inf.elementbound.celldweller.controller;

import java.io.File;
import java.util.BitSet;

import javax.swing.filechooser.FileNameExtensionFilter;

public interface IEditableCellverseListener {
	public void requestRepaint();
	public void cellverseUpdate();
	public File requestSaveFile(FileNameExtensionFilter filter);
	public File requestOpenFile(FileNameExtensionFilter filter);
	public void requestRuleUpdate(BitSet rule);
}

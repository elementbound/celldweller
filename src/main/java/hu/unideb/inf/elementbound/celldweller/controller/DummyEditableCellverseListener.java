package hu.unideb.inf.elementbound.celldweller.controller;

import java.io.File;
import java.util.BitSet;

import javax.swing.filechooser.FileFilter;

public class DummyEditableCellverseListener implements
		IEditableCellverseListener {

	@Override
	public void cellverseUpdate() {
		return ;
	}

	@Override
	public File requestSaveFile(FileFilter filter) {
		return null;
	}

	@Override
	public File requestOpenFile(FileFilter filter) {
		return null;
	}

	@Override
	public void requestRuleUpdate(BitSet rule) {
		return ;
	}

}

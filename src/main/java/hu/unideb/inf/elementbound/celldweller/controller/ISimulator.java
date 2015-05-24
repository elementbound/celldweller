package hu.unideb.inf.elementbound.celldweller.controller;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;

import java.util.BitSet;

public interface ISimulator {
	public void setRule(BitSet rule) throws IllegalArgumentException;
	public BitSet getRule();
	
	public void step(Cellverse cellverse);
}

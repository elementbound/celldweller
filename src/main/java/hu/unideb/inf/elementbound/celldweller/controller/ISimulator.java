package hu.unideb.inf.elementbound.celldweller.controller;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;

import java.util.BitSet;

/**
 * A simulator is given a cellverse to modify it, based on a set of rules. 
 * This set is defined by a bitset. Each simulator can use these rules differently
 * and can use a different amount of neighboring cells. 
 * 
 * A bitset can represent arbitrary automata, by giving each situation an ID, and mapping
 * these IDs to a bit, stating the automata's resulting state. 
 * @author elementbound
 */
public interface ISimulator {
	/**
	 * Setter for rule. 
	 * @param rule Bitset of rules
	 */
	public void setRule(BitSet rule);
	
	/**
	 * Getter for rule. 
	 * @return Bitset of rules
	 */
	public BitSet getRule();
	
	/**
	 * Perform a simulation step. 
	 * <b>Do note that you still have to call Cellverse.swapBuffers() manually</b>
	 * @param cellverse Cellverse to modify
	 */
	public void step(Cellverse cellverse);
}

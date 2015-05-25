package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import java.util.BitSet;

import hu.unideb.inf.elementbound.celldweller.controller.DummyEditableCellverseListener;
import hu.unideb.inf.elementbound.celldweller.controller.EditableCellverseController;
import hu.unideb.inf.elementbound.celldweller.controller.IEditableCellverseListener;

import org.junit.Test;

public class EditableCellverseControllerTest {

	@Test
	public void randomRuleTest() {
		EditableCellverseController controller = new EditableCellverseController();
		IEditableCellverseListener listener = new DummyEditableCellverseListener();
		controller.init(listener);
		
		BitSet previousRules = null;
		//Random means can generate at least 16 distinct rules in succession
		for(int i = 0; i < 16; i++) {
			previousRules = (BitSet)controller.getSimulator().getRule().clone();
			controller.randomizeRule();
			
			assertFalse(previousRules.equals(controller.getSimulator().getRule()));
		}
	}

}

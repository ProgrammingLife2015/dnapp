package nl.tudelft.ti2806.pl1.gui;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2806.pl1.gui.contentpane.ZoomSelector;

import org.junit.Test;

public class ZoomSelectorTest {

	@Test
	public void zoomOne() {
		assertEquals(ZoomSelector.getGraph(1), "zoomin 1");
	}

	@Test
	public void zoomOutOne() {
		assertEquals(ZoomSelector.getGraph(-1), "zoomout 1");
	}

	@Test
	public void zoomStandard() {
		assertEquals(ZoomSelector.getGraph(0), "standard");
	}

	@Test
	public void zoomUndefined() {
		assertEquals(ZoomSelector.getGraph(-10), "special");
	}
}

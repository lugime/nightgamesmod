package nightgames.global;

import nightgames.gui.GUI;
import nightgames.gui.TestGUI;

/**
 * Creates a version of Global that has no visible GUI.
 *
 * TODO: Currently you need to re-initialize this for every test. Refactor the project so you don't.
 */
public class TestGlobal extends Global {
    public TestGlobal() {
        super(false);
    }

    @Override protected GUI makeGUI(boolean headless) {
        return new TestGUI();
    }
}

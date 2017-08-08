package nightgames.global;

import nightgames.gui.GUI;
import nightgames.gui.TestGUI;

/**
 * Creates a version of GameState that has no visible GUI.
 *
 * TODO: Currently you need to re-initialize this for every test. Refactor the project so you don't.
 */
public class TestGameState extends GameState {
    public TestGameState() {
        super(false);
    }

    @Override protected GUI makeGUI(boolean headless) {
        return new TestGUI();
    }
}

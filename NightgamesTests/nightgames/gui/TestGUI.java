package nightgames.gui;

import nightgames.global.GameState;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class TestGUI extends GUI {
    /**
     * 
     */
    private static final long serialVersionUID = 1739250786661411957L;

    public TestGUI() {
        super(new CompletableFuture<>());
        GUI.gui = this;
    }

    @Override public void setVisible(boolean visible) {
        // pass
    }

    // Don't use save dialog in tests
    @Override public Optional<File> askForSaveFile() {
        return Optional.empty();
    }
}

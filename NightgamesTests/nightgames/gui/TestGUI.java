package nightgames.gui;

import java.io.File;
import java.util.Observable;
import java.util.Optional;

import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.combat.IEncounter;
import nightgames.skills.Skill;

public class TestGUI extends GUI {
    /**
     * 
     */
    private static final long serialVersionUID = 1739250786661411957L;

    public TestGUI() {
    }

    @Override public void setVisible(boolean visible) {
        // pass
    }

    // Don't use save dialog in tests
    @Override public Optional<File> askForSaveFile() {
        return Optional.empty();
    }

    @Override
    public void clearText() {}

    @Override
    public void message(String text) {}

    @Override
    public void clearCommand() {}

    @Override
    public void promptAmbush(IEncounter enc, Character target) {}

}

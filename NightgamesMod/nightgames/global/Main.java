package nightgames.global;

import nightgames.Resources.ResourceLoader;
import nightgames.characters.TraitTree;
import nightgames.gui.GUI;
import nightgames.gui.HeadlessGui;
import nightgames.requirements.TraitRequirement;

import java.util.Date;

/**
 * Program entry point
 */
public class Main {
    public static void main(String[] args) {
        new Logwriter();
        Logwriter.makeLogger(new Date());
        parseDebugFlags(args);
        initialize();
        makeGUI();
    }

    private static void makeGUI() {
        if (DebugFlags.isDebugOn(DebugFlags.NO_GUI)) {
            new HeadlessGui();
        } else {
            new GUI();
        }
    }

    public static void parseDebugFlags(String[] args) {
        for (String arg : args) {
            try {
                DebugFlags flag = DebugFlags.valueOf(arg);
                DebugFlags.debug[flag.ordinal()] = true;
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    public static void initialize() {
        TraitRequirement.setTraitRequirements(new TraitTree(ResourceLoader.getFileResourceAsStream("data/TraitRequirements.xml")));
    }
}


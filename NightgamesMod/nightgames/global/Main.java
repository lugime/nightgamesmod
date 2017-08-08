package nightgames.global;

import nightgames.Resources.ResourceLoader;
import nightgames.characters.TraitTree;
import nightgames.gui.GUI;
import nightgames.requirements.TraitRequirement;

import java.util.Date;

/**
 * Program entry point
 */
public class Main {
    public static void main(String[] args) {
        new Logwriter();
        for (String arg : args) {
            try {
                DebugFlags flag = DebugFlags.valueOf(arg);
                DebugFlags.debug[flag.ordinal()] = true;
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
        initialize();
        new GUI();
    }

    public static void initialize() {
        Logwriter.makeLogger(new Date());
        TraitRequirement.setTraitRequirements(new TraitTree(ResourceLoader.getFileResourceAsStream("data/TraitRequirements.xml")));
    }
}


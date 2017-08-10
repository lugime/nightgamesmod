package nightgames.global;

import nightgames.Resources.ResourceLoader;
import nightgames.characters.TraitTree;
import nightgames.gui.GUI;
import nightgames.gui.HeadlessGui;
import nightgames.requirements.TraitRequirement;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Program entry point
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<GameState> stateFuture = new CompletableFuture<>();
        new Logwriter();
        Logwriter.makeLogger(new Date());
        parseDebugFlags(args);
        initialize();
        makeGUI(stateFuture);
        GameState state = stateFuture.get();  // blocking call
        state.gameLoop();
    }

    private static void makeGUI(CompletableFuture<GameState> stateFuture) {
        if (DebugFlags.isDebugOn(DebugFlags.NO_GUI)) {
            new HeadlessGui(stateFuture);
        } else {
            new GUI(stateFuture);
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


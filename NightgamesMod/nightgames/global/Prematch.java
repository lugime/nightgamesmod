package nightgames.global;

import nightgames.characters.Player;
import nightgames.modifier.Modifier;

/**
 * Base class for match setup.
 */
public abstract class Prematch implements Scene {
    protected Prematch() {
        GameState.current = this;
        Flag.unflag(Flag.victory);
    }

    public abstract void prompt(Player player);

    @Override public abstract void respond(String response);
}

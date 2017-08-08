package nightgames.gui;

import nightgames.global.GameState;

public class SceneButton extends RunnableButton {
    private static final long serialVersionUID = -4333729595458261030L;
    public SceneButton(String label) {
        super(label, () -> GameState.current.respond(label));
    }
}
